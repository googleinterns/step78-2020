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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;

import com.google.sps.data.*;

public class CalendarUtils {
   /**
   * Helper function to get the date for the calendar event
   *
   * @param startDate the start date of the user's college term
   * @param timeInMin the time, in minutes of the week, for an event (either the start or end time) 
   * @param courseDayOfWeek the day of week the current course section starts on 
   * @return a LocalDate representing the date to put the course on the calendar
   */
  private static LocalDate getCourseDate(LocalDate startDate, int timeInMin, int courseDayOfWeek) {
    int startDateDayOfWeek = startDate.getDayOfWeek().getValue();
    long daysToAdd = 0;
    if (courseDayOfWeek > startDateDayOfWeek) {
      daysToAdd = courseDayOfWeek - startDateDayOfWeek;
    } else {
      daysToAdd = courseDayOfWeek + 7 - startDateDayOfWeek;
    }
    LocalDate courseDate = startDate.plusDays(daysToAdd);
    return courseDate;
  }

   /**
   * Helper function to get the time (start or end) for the calendar event
   *
   * @param timeInMin the time, in minutes of the week, for an event (either the start or end time) 
   * @param courseDayOfWeek the day of week the current course section starts on 
   * @return a LocalTime representing the time for creating the calendar event for the current course
   */
  private static LocalTime getCourseTime(int timeInMin, int courseDayOfWeek) {
    int dayInHours = courseDayOfWeek * 24;
    int hour = ((timeInMin / 60) % 24);
    int minute = (timeInMin % 60);
    LocalTime courseTime = LocalTime.of(hour, minute);
    return courseTime;
  } 

   /**
   * Helper function to get the zoneId for the calendar event
   *
   * @param client the user's Google calendar
   * @return a ZoneId representing the timezone for the calendar event for the current course
   */
  private static ZoneId getZoneId(Calendar client) {
    ZoneId zoneId = null;
    try {
      CalendarListEntry calendarListEntry = client.calendarList().get("primary").execute();
      String calendarTimeZone = calendarListEntry.getTimeZone();
      zoneId = ZoneId.of(calendarTimeZone);
    } catch (Exception e) {
      System.out.println("Error getting ZoneId: " + e.getMessage());
    }
    return zoneId;
  }
  
   /**
   * Helper function to format the relevant times (start or end) for a course 
   * being converted into a calendar event. 
   *
   * @param client the user's Google calendar
   * @param startDate the start date of the user's college term
   * @param timeInMin the time, in minutes of the week, for an event (either the start or end time)
   * @return a ZonedDateTime, to be used in creating a calendar event 
   */
  public static ZonedDateTime calculateDateTime(Calendar client, LocalDate startDate, int timeInMin) { 
    int courseDayOfWeek = (timeInMin / (24 * 60));
    LocalDate courseDate = getCourseDate(startDate, timeInMin, courseDayOfWeek);
    LocalTime courseTime = getCourseTime(timeInMin, courseDayOfWeek);
    ZoneId zoneId = getZoneId(client);
    ZonedDateTime dateTime = ZonedDateTime.of(courseDate, courseTime, zoneId);
    return dateTime;
  }
}
