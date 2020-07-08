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

package com.google.sps.data;

import com.google.sps.data.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public final class TesterSchedule {

  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_2_HOUR = 120;

  /**
   * Returns a list of times on monday, wednesday, and friday, to be used in section constructors.
   */
  public static List<TimeRange> monWedFri(int hour, int minute, int durationMinutes) {
    TimeRange mon = TimeRange.fromStartDuration(TimeRange.MONDAY, hour, minute, durationMinutes);
    TimeRange wed = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, hour, minute, durationMinutes);
    TimeRange fri = TimeRange.fromStartDuration(TimeRange.FRIDAY, hour, minute, durationMinutes);
    return Arrays.asList(mon, wed, fri);
  }

  /**
   * Returns a list of times on Tuesday and Thursday, to be used in section constructors.
   */
  public List<TimeRange> tuesThurs(int hour, int minute, int durationMinutes) {
    TimeRange tues = TimeRange.fromStartDuration(TimeRange.TUESDAY, hour, minute, durationMinutes);
    TimeRange thurs = TimeRange.fromStartDuration(TimeRange.THURSDAY, hour, minute, durationMinutes);
    return Arrays.asList(tues, thurs);
  }

  public static Schedule singleCourseSchedule() {
    List<TimeRange> times = monWedFri(10, 30, DURATION_90_MINUTES);
    Section section = new Section("Dr. Eggman", times);
    Course course1 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section));
    
    Collection<Course> courses = new ArrayList<>();
    courses.add(course1);
    Schedule schedule = new Schedule(courses);
    return schedule;
  }
}
