// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import com.google.sps.data.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;

public final class JSONTest {
  public static void main(String[] args) {
    Gson gson = new Gson();
    // JsonParser parser = new JsonParser();
    try {
      JsonObject wholeJSON = JsonParser.parseReader(new FileReader("/home/maceothompson/step78-2020/src/test/java/com/google/sps/everythingJSON.json")).getAsJsonObject();
      
      Type courseListType = new TypeToken<List<Course>>(){}.getType();
      List<Course> courses = gson.fromJson(wholeJSON.getAsJsonArray("courses"), courseListType);
      
      JsonObject basicInfo = wholeJSON.getAsJsonObject("basicInfo");
      Invariants invariants = gson.fromJson(basicInfo.get("credits"), Invariants.class);
      Scheduler scheduler = new Scheduler();
      List<Schedule> schedules = scheduler.generateSchedules(courses, invariants);
    } catch (JsonIOException e) {
      System.out.println(e.getMessage());
    } catch (JsonSyntaxException e) {
      System.out.println(e.getMessage());
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
    
  }

}
