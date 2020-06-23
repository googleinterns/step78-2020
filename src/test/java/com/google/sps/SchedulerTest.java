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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.google.sps.data.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class SchedulerTest {

  //Some times and durations for setting up sections.
  private static final int TIME_0800AM = TimeRange.getTimeInMinutes(8, 0);
  private static final int TIME_0830AM = TimeRange.getTimeInMinutes(8, 30);
  private static final int TIME_0900AM = TimeRange.getTimeInMinutes(9, 0);
  private static final int TIME_0930AM = TimeRange.getTimeInMinutes(9, 30);
  private static final int TIME_1000AM = TimeRange.getTimeInMinutes(10, 0);
  private static final int TIME_1100AM = TimeRange.getTimeInMinutes(11, 00);
  private static final int TIME_0100PM = TimeRange.getTimeInMinutes(13, 0);

  private static final int DURATION_30_MINUTES = 30;
  private static final int DURATION_60_MINUTES = 60;
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;
  

  private Scheduler scheduler;
  
  @Before
  public void setUp(){
    scheduler = new Scheduler();
  }

  //TODO: All tests will have to be changed to account for new timerange/section structuree
  @Test
  public void singleCourseValidSchedule() {
    TimeRange[] times = new TimeRange[7];
    for (int i = 1; i < 6; i += 2) {
      times[i] = TimeRange.fromStartDuration(TIME_1000AM, DURATION_60_MINUTES);
    }

    Section section = new Section("Dr. Eggman", times);
    Course course1 = new Course("Intro to Evil", "EVIL100", "Sonic", true, Arrays.asList(section));
    
    Collection<Schedule> actual = scheduler.generateSchedules(Arrays.asList(course1), 1, 2);
    Schedule expected = new Schedule(Arrays.asList(course1));
    Assert.assertEquals(Arrays.asList(expected), actual);
  }

  @Test
  public void notEnoughCreditsNoSchedules() {
    TimeRange[] times = new TimeRange[7];
    for (int i = 1; i < 6; i += 2) {
      times[i] = TimeRange.fromStartDuration(TIME_1000AM, DURATION_60_MINUTES);
    }

    Section section = new Section("Dr. Eggman", times);
    Course course1 = new Course("Intro to Evil", "EVIL100", "Sonic", true, Arrays.asList(section));
    Collection<Schedule> actual = scheduler.generateSchedules(Arrays.asList(course1), 3, 5);
    Assert.assertEquals(Collections.emptyList(), actual);
  }
}