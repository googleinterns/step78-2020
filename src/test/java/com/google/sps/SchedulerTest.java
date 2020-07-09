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
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class SchedulerTest {

  private static final int DURATION_30_MINUTES = 30;
  private static final int DURATION_60_MINUTES = 60;
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;
  

  private Scheduler scheduler;
  
  @Before
  public void setUp() {
    scheduler = new Scheduler();
  }

  /**
   * Returns a list of times on monday, wednesday, and friday, to be used in section constructors.
   */
  public List<TimeRange> monWedFri(int hour, int minute, int durationMinutes) {
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

  @Test
  public void singleCourseValidSchedule() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, section);
    
    Collection<Schedule> actual = scheduler.generateSchedules(
        Arrays.asList(course1), new Invariants(1, 2));
    Schedule expected = new Schedule(Arrays.asList(course1));
    Assert.assertEquals(Arrays.asList(expected), actual);
  }

  @Test
  public void notEnoughCreditsNoSchedules() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, section);
    
    Collection<Schedule> actual = scheduler.generateSchedules(Arrays.asList(course1), 
        new Invariants(3, 5));
    Assert.assertEquals(Collections.emptyList(), actual);
  }

  @Test
  public void twoCoursesValidSchedule() {
    //TODO: This
  }

  @Test
  public void noCourseOverlap() {
    /*//TODO min credits = 1, max = 2, 
             2 courses that overlap, should only return 2 schedules w single course
    */
  }
}
