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

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;

class CreateCalendar {
  /**
   * Creates a new secondary calendar to put a class schedule on.
   * 
   * @param client the user's Google calendar 
   * 
   * @return a string of the new secondary calendar's Id
   */
  public static String createCalendar(com.google.api.services.calendar.Calendar client) throws IOException {
    try {    
      // Get the timezone of the user's primary calendar
      CalendarListEntry calendarListEntry = client.calendarList().get("primary").execute();
      String calendarTimeZone = calendarListEntry.getTimeZone();

      // Create a new calendar
      com.google.api.services.calendar.model.Calendar newCalendar = new com.google.api.services.calendar.model.Calendar();
      newCalendar.setSummary("class schedule");
      newCalendar.setTimeZone(calendarTimeZone);

      // Insert the new calendar
      com.google.api.services.calendar.model.Calendar createdCalendar = client.calendars().insert(newCalendar).execute();

      // Return the calendar Id
      return createdCalendar.getId();      
    } catch (IOException e) {
      System.out.println("Couldn't create secondary calendar");
      return null;
    }
  }
}
