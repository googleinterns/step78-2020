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

import com.google.api.services.calendar.Calendar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.sps.data.*;

/**
 * Exports the ordered schedules to secondary calendars on the user's Google calendar    
 */
@WebServlet("/handleSchedules")
public class ScheduleDisplayHandler extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      Calendar client = Utils.loadCalendarClient();
      Gson gson = new Gson();
      GenerateGoogleCalendarRequest scheduleInfo;

      JsonObject wholeJSON = JsonParser.parseReader(request.getReader()).getAsJsonObject();
      scheduleInfo = gson.fromJson(wholeJSON, GenerateGoogleCalendarRequest.class);

      Schedule schedule = scheduleInfo.getSchedule();
      String startDate = scheduleInfo.getStartDate();
      String endDate = scheduleInfo.getEndDate();

      String calId;

      //create new secondary calendary and put schedule on it
      ScheduleCalendar cal = new ScheduleCalendar(client);
      cal.addSchedule(schedule, startDate, endDate);
      calId = cal.getCalendarId();

      response.setContentType("text/plain");
      response.getWriter().println(calId);
    } catch (Exception e) {
      System.out.println("Error reading JSON: " + e.getMessage());
      return;
    }
  }
}
