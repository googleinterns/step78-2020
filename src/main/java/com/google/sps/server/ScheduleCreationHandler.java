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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.google.sps.data.*;
import com.google.sps.Scheduler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Create and rank the schedules     
 */
@WebServlet("/handleUserInput")
public class ScheduleCreationHandler extends HttpServlet {
  

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    Gson gson = new Gson();
    GenerateScheduleRequest userInput;
    try {
      JsonObject wholeJSON = JsonParser.parseReader(request.getReader()).getAsJsonObject();
      userInput = gson.fromJson(wholeJSON, GenerateScheduleRequest.class);
    } catch (Exception e) {
      System.out.println("Error reading JSON: " + e.getMessage());
      return; //TODO: Handle this properly
    }

    List<Course> courses = userInput.getCourses();
    Invariants invariants = userInput.getCredits();

    Scheduler scheduler = new Scheduler();

    System.out.printf("Generating Schedules from %d courses", courses.size());
    List<Schedule> schedules = scheduler.generateSchedules(courses, invariants);
    
    response.setContentType("application/json");
    try {
      response.getWriter().write(gson.toJson(schedules));
      response.getWriter().flush();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Error writing JSON to response! \n" + e.getMessage());
    }
  }
}
