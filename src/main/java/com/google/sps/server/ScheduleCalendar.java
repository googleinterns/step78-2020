/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.sps.server;

import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.google.sps.data.*;

public class ScheduleCalendar {
  private String calendarId;
  private com.google.api.services.calendar.Calendar client;

   /**
   * Creates a new secondary calendar to put a class schedule on.
   * 
   * @param client the user's Google calendar
   */
  public ScheduleCalendar(com.google.api.services.calendar.Calendar client) throws IOException {
    try {
      this.client = client;

      // Get the timezone of the user's primary calendar
      CalendarListEntry calendarListEntry = client.calendarList().get("primary").execute();
      String calendarTimeZone = calendarListEntry.getTimeZone();

      // Create a new calendar
      com.google.api.services.calendar.model.Calendar newCalendar = new com.google.api.services.calendar.model.Calendar();
      newCalendar.setSummary("class schedule");
      newCalendar.setTimeZone(calendarTimeZone);

      // Insert the new calendar
      com.google.api.services.calendar.model.Calendar createdCalendar = client.calendars().insert(newCalendar).execute();
      this.calendarId = createdCalendar.getId(); 
    } catch (IOException e) {
      System.out.println("Couldn't create secondary calendar");
    }
  }

  /**
   * Puts schedule onto secondary calendar
   * 
   * @param schedule the schedule to add to the calendar 
   */
  public void addSchedule(Schedule schedule) throws IOException {
    try {
      // get current calendar, so can add the courses to the appropriate days of the current month 
      java.util.Calendar calendar = java.util.Calendar.getInstance();

      // for each course in the schedule, add all section times to calendar
      for (ScheduledCourse currentCourse : schedule.getCourses()) {
        List<TimeRange> lectureSectionTimes = currentCourse.getLectureSection().getMeetingTimes();
        
        addSectionToCalendar(lectureSectionTimes, calendar, currentCourse);
        if (currentCourse.getLabSection() != null) {
          List<TimeRange> labSectionTimes = currentCourse.getLabSection().getMeetingTimes();
          addSectionToCalendar(labSectionTimes, calendar, currentCourse);
        }
      } 
    } catch (IOException e) {
      System.out.println("Couldn't add schedule");
    }   
  }

   /**
   * Helper function to turn all meeting times for a course section into calendar events
   *
   * @param sectionTimes
   * @param calendar
   * @param currentCourse 
   */
  private void addSectionToCalendar(List<TimeRange> sectionTimes, java.util.Calendar calendar, ScheduledCourse currentCourse) throws IOException {
    try {
      for (int i = 0; i < sectionTimes.size(); i++) {
        String startTime = CalendarUtils.calculateStartTime(sectionTimes.get(i), calendar);
        String endTime = CalendarUtils.calculateEndTime(sectionTimes.get(i), calendar);
        addEvent(currentCourse, startTime, endTime);
      }
    } catch (IOException e) {
      System.out.println("Couldn't add course section to the calendar");
    }
  }

   /**
   * To access the secondar calendar's id
   *
   * @return a String representing the id 
   */
  public String getCalendarId() {
    return this.calendarId;
  }

   /**
   * Helper function to add a course in the schedule to the calendar, as an event. 
   *
   * @param course the course being added 
   * @param startTime the start time for the calendar event
   * @param endTime the end time for the calendar event
   */
  private void addEvent(ScheduledCourse course, String startTime, String endTime) throws IOException {
    try {  
      /* Get an event from the primary calendar to get the timezone offset from the 
       event (to use for properly adjusting the start and end times to the correct timezone) */
      Events events = this.client.events().list("primary").execute();
      List<Event> items = events.getItems();
      String existingDateTime = items.get(0).getStart().getDateTime().toString();
      String[] dateTimeParts = existingDateTime.split("-");
      if (dateTimeParts.length == 4) {
        String offset = dateTimeParts[3];
        startTime = startTime + "-" + offset;
        endTime = endTime + "-" + offset;
      }

      com.google.api.services.calendar.model.Calendar currentCalendar = this.client.calendars().get(this.calendarId).execute();

      CalendarListEntry calendarListEntry = this.client.calendarList().get("primary").execute();
      String calendarTimeZone = calendarListEntry.getTimeZone();

      String courseName = course.getName();
      Event event = new Event()
          .setSummary(courseName);

      DateTime startDateTime = new DateTime(startTime);
      EventDateTime start = new EventDateTime()
          .setDateTime(startDateTime)
          .setTimeZone(calendarTimeZone);
      event.setStart(start);
      
      DateTime endDateTime = new DateTime(endTime);
      EventDateTime end = new EventDateTime()
          .setDateTime(endDateTime)
          .setTimeZone(calendarTimeZone);
      event.setEnd(end);

      // will recur as long as their semester / quarter 
      String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=4"};
      event.setRecurrence(Arrays.asList(recurrence));

      event = this.client.events().insert(this.calendarId, event).execute();
    } catch (IOException e) {
      System.out.println("Couldn't add schedule to calendar");
    }
  }
}
