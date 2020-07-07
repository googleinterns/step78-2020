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
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.io.IOException;

import com.google.sps.data.*;

class AddSchedule {
   /**
   * Puts schedule onto secondary calendar
   * 
   * @param client the user's Google calendar 
   * @param calendarId the id of the secondary calendar to put the schedule on
   */
  public static void addSchedule(Schedule schedule, com.google.api.services.calendar.Calendar client, String calendarId) throws IOException {
    try {
      // get current date, so can add the courses to the appropriate days of the current month 
      java.util.Calendar calendar = java.util.Calendar.getInstance();
      int nextSun = getDateOfNextSunday(calendar);
      int month = getCurrentMonth(calendar);
      int year = getCurrentYear(calendar);

      // for each course in the schedule, add an event to the calendar
      Iterator<Course> courseIterator = schedule.getCourses().iterator();
      while (courseIterator.hasNext()) {
        Course currentCourse = courseIterator.next();
        List<TimeRange> sectionTimes = currentCourse.getSections().get(0).getMeetingTimes();
        for (int i = 0; i < sectionTimes.size(); i++) {
          String startTime = calculateStartTime(sectionTimes.get(i), month, year, nextSun);
          String endTime = calculateEndTime(sectionTimes.get(i), month, year, nextSun);
          addEvent(currentCourse, client, calendarId, startTime, endTime);
        }
      }       
    } catch (IOException e) {
      System.out.println("Couldn't iterate through the courses in the schedule.");
    }
  }

   /**
   * Helper function to calculate the day of the month of the next Sunday,
   * to know when to start the schedule. 
   *
   * @param calendar an instance of Calendar, so can know the current date
   * 
   * @return an int representing the date of next Sunday
   */
  public static int getDateOfNextSunday(java.util.Calendar calendar) {
    int currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
    int currentDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
    int nextSun = currentDay + 8 - currentDayOfWeek;
    return nextSun;
  }
 
   /**
   * Helper function to know the current month. 
   *
   * @param calendar an instance of Calendar, so can know the current date
   * 
   * @return an int representing the month, adjusted for DateTime's formatting
   */
  public static int getCurrentMonth(java.util.Calendar calendar) {
    return calendar.get(java.util.Calendar.MONTH) + 1;
  }
  
   /**
   * Helper function to know the current year. 
   *
   * @param calendar an instance of Calendar, so can know the current date
   * 
   * @return an int representing the current year
   */
  public static int getCurrentYear(java.util.Calendar calendar) {
    return calendar.get(java.util.Calendar.YEAR);
  }
  
   /**
   * Helper function to format the relevant times (start or end) for a course 
   * being converted into a calendar event. 
   *
   * @param timeInMin the time, in minutes of the week, for an event (either the start or end time)
   * @param month the current month
   * @param year the current year
   * @param nextSun the day of the month of the next Sunday
   * 
   * @return a String formatted for DateTime, to be used in creating a calendar event 
   */
  public static String 
     DateTime(int timeInMin, int month, int year, int nextSun) {
    int dayOfWeek = (int) Math.floorDiv(timeInMin, 24 * 60);
    int dayInHours = dayOfWeek * 24;
    int hour = (int) Math.floor((timeInMin / 60) - dayInHours);
    int minute = (int) (60 * (((timeInMin / 60.0) - dayInHours) - hour));
    int day = dayOfWeek + nextSun;
     
    String dayString = day > 9 ? Integer.toString(day) : "0" + Integer.toString(day);
    String monthString = month > 9 ? Integer.toString(month) : "0" + Integer.toString(month);
    String hourString = hour > 9 ? Integer.toString(hour) : "0" + Integer.toString(hour);     
    String minuteString = minute > 9 ? Integer.toString(minute) : "0" + Integer.toString(minute);

    String dateTime = year + "-" + monthString + "-" + dayString + "T" + hourString + ":" + minuteString + ":00";
    return dateTime;
  }

   /**
   * Helper function to calculate the properly formatted start time of a course
   *
   * @param timerange the time range for a course's meeting time (ex. 10:30-12:00)
   * @param month the current month
   * @param year the current year
   * @param nextSun the day of the month of the next Sunday
   * 
   * @return a string representing the start time
   */
  public static String calculateStartTime(TimeRange timerange, int month, int year, int nextSun) {
    int startTimeInMin = timerange.start();
    String startTime = calculateDateTime(startTimeInMin, month, year, nextSun);
    return startTime;
  }

   /**
   * Helper function to calculate the properly formatted end time of a course
   *
   * @param timerange the time range for a course's meeting time (ex. 10:30-12:00)
   * @param month the current month
   * @param year the current year
   * @param nextSun the day of the month of the next Sunday
   * 
   * @return a string representing the end time
   */
  public static String calculateEndTime(TimeRange timerange, int month, int year, int nextSun) {
    int endTimeInMin = timerange.end();
    String endTime = calculateDateTime(endTimeInMin, month, year, nextSun);
    return endTime;
  }

   /**
   * Helper function to add a course in the schedule to the calendar, as an event. 
   *
   * @param course the course being added 
   * @param client the user's Google calendar
   * @param calendarId the id of the secondary calendar to add the course to
   * @param startTime the start time of the course
   * @param endTime the end time of the course
   */
  public static void addEvent(Course course, com.google.api.services.calendar.Calendar client, String calendarId, String startTime, String endTime) throws IOException {
    try {
      com.google.api.services.calendar.model.Calendar currentCalendar = client.calendars().get(calendarId).execute();

      CalendarListEntry calendarListEntry = client.calendarList().get("primary").execute();
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

      event = client.events().insert(calendarId, event).execute();
    } catch (IOException e) {
      System.out.println("Couldn't add course as a calendar event");
    }
  }
}
