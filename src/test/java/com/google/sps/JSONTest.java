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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import com.google.gson.Gson;

public final class JSONTest {
  public static void main(String[] args) {
    Section section1 = new Section("Dr. Eggman", monWedFri(10, 30, 90));
    Section section2 = new Section("Dr. Eggman", tuesThurs(13, 30, 30));
    Course course1 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section1, section2));

    Invariants inv = new Invariants(3, 5);

    Gson gson = new Gson();
    String json = gson.toJson(course1);
    // System.out.println(json);
    System.out.println(gson.toJson(inv));


    Course courseFrom = gson.fromJson(json, Course.class);
    System.out.println(courseFrom);
  }

  public static List<TimeRange> tuesThurs(int hour, int minute, int durationMinutes) {
    TimeRange tues = TimeRange.fromStartDuration(TimeRange.TUESDAY, hour, minute, durationMinutes);
    TimeRange thurs = TimeRange.fromStartDuration(TimeRange.THURSDAY, hour, minute, durationMinutes);
    return Arrays.asList(tues, thurs);
  }
  
  public static List<TimeRange> monWedFri(int hour, int minute, int durationMinutes) {
    TimeRange mon = TimeRange.fromStartDuration(TimeRange.MONDAY, hour, minute, durationMinutes);
    TimeRange wed = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, hour, minute, durationMinutes);
    TimeRange fri = TimeRange.fromStartDuration(TimeRange.FRIDAY, hour, minute, durationMinutes);
    return Arrays.asList(mon, wed, fri);
  }
}
