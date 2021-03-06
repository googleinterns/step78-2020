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
import org.joda.time.Weeks;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.io.IOException;

import com.google.sps.data.*;

public class ScheduleCalendar {
  private String calendarId;
  private String calendarTimeZone;
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
      this.calendarTimeZone = calendarListEntry.getTimeZone();

      // Create a new calendar
      com.google.api.services.calendar.model.Calendar newCalendar = new com.google.api.services.calendar.model.Calendar();
      newCalendar.setSummary("class schedule");
      newCalendar.setTimeZone(this.calendarTimeZone);

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
   * @param startDateString the user's college term start date 
   * @param endDateString the user's college term end date
   */
  public void addSchedule(Schedule schedule, String startDateString, String endDateString) throws IOException {
    try {
      // calculate the number of weeks in their term, to set recurrence for calendar event
      org.joda.time.LocalDate termStart = org.joda.time.LocalDate.parse(startDateString);
      org.joda.time.LocalDate termEnd = org.joda.time.LocalDate.parse(endDateString);
      int termLength = Weeks.weeksBetween(termStart, termEnd).getWeeks();

      LocalDate startDate = LocalDate.parse(startDateString);

      // for each course in the schedule, add an event to the calendar
      for (ScheduledCourse currentCourse : schedule.getCourses()) {
        String professor = currentCourse.getLectureSection().getProfessor();
        List<TimeRange> lectureSectionTimes = currentCourse.getLectureSection().getMeetingTimes();
        addSectionToCalendar(currentCourse, lectureSectionTimes, startDate, termLength, professor);
        if (currentCourse.getLabSection() != null) {
          professor = currentCourse.getLabSection().getProfessor();
          List<TimeRange> labSectionTimes = currentCourse.getLabSection().getMeetingTimes();
          addSectionToCalendar(currentCourse, labSectionTimes, startDate, termLength, professor);
        }
      } 
    } catch (IOException e) {
      System.out.println("Couldn't add schedule");
    }   
  }

     /**
   * Helper function to turn all meeting times for a course section into calendar events
   *
   * @param currentCourse the current course being added 
   * @param sectionTimes all of the meeting times for the current course's lecture or lab section
   * @param startDate the user's college term start date
   * @param termLength the number of weeks in the user's college term
   * @param professor the current course's professor
   */
  private void addSectionToCalendar(ScheduledCourse currentCourse, List<TimeRange> sectionTimes, LocalDate startDate, int termLength, String professor) throws IOException {
    try {
      for (int i = 0; i < sectionTimes.size(); i++) {
        ZonedDateTime startTime = CalendarUtils.calculateDateTime(this.client, startDate, sectionTimes.get(i).start());
        ZonedDateTime endTime = CalendarUtils.calculateDateTime(this.client, startDate, sectionTimes.get(i).end());
        addEvent(currentCourse, startTime, endTime, termLength, professor); 
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
   * @param startTime the start time of the course
   * @param endTime the end time of the course
   * @param termLength the number of weeks in the user's college term
   * @param professor the course's professor
   */
  private void addEvent(Course course, ZonedDateTime startTime, ZonedDateTime endTime, int termLength, String professor) throws IOException {
    try {  
      com.google.api.services.calendar.model.Calendar currentCalendar = this.client.calendars().get(this.calendarId).execute();

      String courseName = course.getName();
      String courseID = course.getCourseID();
      String subject = course.getSubject();
      float credits = course.getCredits();
      Event event = new Event()
          .setSummary(courseName)
          .setDescription("Course ID: " + courseID + "\nProfessor: " + professor + "\nSubject: " + subject + "\nCredits: " + String.valueOf(credits));

      TimeZone zoneId = TimeZone.getTimeZone(startTime.getZone());
      DateTime startDateTime = new DateTime(Date.from(startTime.toInstant()), zoneId);
      EventDateTime start = new EventDateTime()
          .setDateTime(startDateTime)
          .setTimeZone(this.calendarTimeZone);
      event.setStart(start);

      DateTime endDateTime = new DateTime(Date.from(endTime.toInstant()), zoneId);
      EventDateTime end = new EventDateTime()
          .setDateTime(endDateTime)
          .setTimeZone(this.calendarTimeZone);
      event.setEnd(end);

      // recurs as long as their semester / quarter 
      String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT="+Integer.toString(termLength)};
      event.setRecurrence(Arrays.asList(recurrence));

      event = this.client.events().insert(this.calendarId, event).execute();
    } catch (IOException e) {
      System.out.println("Couldn't add schedule to calendar");
    }
  }
}
