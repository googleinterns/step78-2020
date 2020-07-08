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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

import com.google.api.services.calendar.Calendar;
import com.google.gson.Gson;
import com.google.sps.data.*;

/**
 * Exports the ordered schedules to secondary calendars on the user's Google calendar    
 */
@WebServlet("/handleSchedules")
public class ScheduleDisplayHandler extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      Calendar client = Utils.loadCalendarClient();

      // Get the ordered schedules
      // Temporary hard-coded schedule, will be retrieved from Rank.java later on 
      Schedule schedule = TesterSchedule.singleCourseSchedule();
      Collection<Schedule> schedules = new ArrayList<>();
      schedules.add(schedule);

      String calendarId = null;

      //for each schedule, create new secondary calendary and put schedule on it
      Iterator<Schedule> iterator = schedules.iterator();
      while (iterator.hasNext()) {
        ScheduleCalendar cal = new ScheduleCalendar(client);
        cal.addSchedule(iterator.next());
        calendarId = cal.getCalendarId();
      }

      //only displaying one calendar schedule right now, will later display all calendars / schedules
      com.google.api.services.calendar.model.Calendar newCalendar = client.calendars().get(calendarId).execute();

      Gson gson = new Gson();
      response.setContentType("application/json");
      response.getWriter().println(gson.toJson(newCalendar));
    } catch (IOException e) {
      System.out.println("Couldn't iterate through the given schedules.");
    }
  }
}
