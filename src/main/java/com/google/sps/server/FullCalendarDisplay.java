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
import com.google.sps.data.*;

/**
 * Exports the ordered schedules to the FullCalendar display in order for users
   to select which schedules would like to be exported via Google Calendar
 */
@WebServlet("/displayFullCalendar")
public class FullCalendarDisplay extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      // Get the ordered schedules
      List<Schedule> schedules = ScheduleCreationHandler.createSchedules();

      Gson gson = new Gson();
      response.setContentType("application/json");
      response.getWriter().println(gson.toJson(schedules));
    } catch (IOException e) {
      System.out.println("Couldn't display schedules on FullCalendar");
    }
  }
}