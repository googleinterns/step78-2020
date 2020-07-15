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
        "Wrongdoing", 1, true, section, null);
    
    Collection<Schedule> actual = scheduler.generateSchedules(
        Arrays.asList(course1), new Invariants(1, 2));
    Schedule expected = new Schedule(Arrays.asList(new ScheduledCourse(course1, section)));
    Assert.assertEquals(Arrays.asList(expected), actual);
  }

  @Test
  public void notEnoughCreditsNoSchedules() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, section, null);
    
    Collection<Schedule> actual = scheduler.generateSchedules(Arrays.asList(course1), 
        new Invariants(3, 5));
    Assert.assertEquals(Collections.emptyList(), actual);
  }

  @Test
  public void twoCoursesValidSchedules() {
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES));
    Section section2 = new Section("Sonic", tuesThurs(10, 30, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "HERO1983", 
        "Heroism", 1, false, Arrays.asList(section1), Collections.emptyList());
    Course course2 = new Course("Running Fast", "HERO1991",
        "Heroism", 1, false, Arrays.asList(section2), Collections.emptyList());
      
    HashSet<Schedule> actual = new HashSet<>(
          scheduler.generateSchedules(Arrays.asList(course1, course2), new Invariants(1, 2)));

    ScheduledCourse schedCourse1 = new ScheduledCourse(course1, section1);
    ScheduledCourse schedCourse2 = new ScheduledCourse(course2, section2);
        
    Schedule schedule1 = new Schedule(Arrays.asList(schedCourse1));
    Schedule schedule2 = new Schedule(Arrays.asList(schedCourse2));
    Schedule schedule3 = new Schedule(Arrays.asList(schedCourse1, schedCourse2));

    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2, schedule3));

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void noCourseOverlap() {
    /*min credits = 1, max = 2, 
      2 courses that overlap, should only return 2 schedules w single course
    */
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES)); 
    Section section2 = new Section("Sonic", monWedFri(10, 30, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "HERO1983", 
        "Heroism", 1, false, Arrays.asList(section1), Collections.emptyList());
    Course course2 = new Course("Running Fast", "HERO1991",
        "Heroism", 1, false, Arrays.asList(section2), Collections.emptyList());
      
    HashSet<Schedule> actual = new HashSet<>(
        scheduler.generateSchedules(Arrays.asList(course1, course2), new Invariants(1, 2)));

    ScheduledCourse schedCourse1 = new ScheduledCourse(course1, section1);
    ScheduledCourse schedCourse2 = new ScheduledCourse(course2, section2);

    Schedule schedule1 = new Schedule(Arrays.asList(schedCourse1));
    Schedule schedule2 = new Schedule(Arrays.asList(schedCourse2));
    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2));

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void requiredCourseMustBeIncluded() {
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES));
    Section section2 = new Section("Sonic", tuesThurs(10, 30, DURATION_60_MINUTES));
    Section section3 = new Section("Megaman", tuesThurs(15, 0, DURATION_1_HOUR));
   
    Course course1 = new Course("Jumping High", "HERO1983", 
        "Heroism", 1, true, Arrays.asList(section1), Collections.emptyList());
    Course course2 = new Course("Running Fast", "HERO1991",
        "Heroism", 1, false, Arrays.asList(section2), Collections.emptyList());
    Course course3 = new Course("Shooting Lemons", "HERO1987",
        "Heroism", 1, false, Arrays.asList(section3), Collections.emptyList());

    HashSet<Schedule> actual = new HashSet<>(scheduler.generateSchedules(
        Arrays.asList(course2, course1, course3), new Invariants(2, 3)));

    ScheduledCourse schedCourse1 = new ScheduledCourse(course1, section1);
    ScheduledCourse schedCourse2 = new ScheduledCourse(course2, section2);
    ScheduledCourse schedCourse3 = new ScheduledCourse(course3, section3);
    
    Schedule schedule1 = new Schedule(Arrays.asList(schedCourse1, schedCourse2));
    Schedule schedule2 = new Schedule(Arrays.asList(schedCourse1, schedCourse3));
    Schedule schedule3 = new Schedule(Arrays.asList(schedCourse1, schedCourse2, schedCourse3));

    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2, schedule3));

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void requiredCoursesOverlapReturnsEmpty() {
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES));
    Section section2 = new Section("Sonic", monWedFri(10, 30, DURATION_60_MINUTES));
    Section section = new Section("Dr. Eggman", tuesThurs(13, 30, DURATION_30_MINUTES));

    Course course1 = new Course("Jumping High", "HERO1983", 
        "Heroism", 1, true, Arrays.asList(section1), Collections.emptyList());
    Course course2 = new Course("Running Fast", "HERO1991",
        "Heroism", 1, true, Arrays.asList(section2), Collections.emptyList());
    Course course3 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, false, Arrays.asList(section), Collections.emptyList());

    List<Schedule> actual = 
        scheduler.generateSchedules(Arrays.asList(course1, course2, course3), new Invariants(1, 2));
      
    Assert.assertEquals(Collections.emptyList(), actual);
  }

  @Test
  public void allRequiredCoursesOnlyTwoOverlap() {
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES));
    Section section2 = new Section("Sonic", monWedFri(10, 30, DURATION_60_MINUTES));
    Section section = new Section("Dr. Eggman", tuesThurs(13, 30, DURATION_30_MINUTES));

    Course course1 = new Course("Jumping High", "HERO1983", 
        "Heroism", 1, true, Arrays.asList(section1), Collections.emptyList());
    Course course2 = new Course("Running Fast", "HERO1991",
        "Heroism", 1, true, Arrays.asList(section2), Collections.emptyList());
    Course course3 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section), Collections.emptyList());

    List<Schedule> actual = 
        scheduler.generateSchedules(Arrays.asList(course1, course3, course2), new Invariants(1, 2));
      
    Assert.assertEquals(Collections.emptyList(), actual);
  }

  @Test
  public void requiredSectionsOverlapButWorks() {
    Section section1 = new Section("Mario", monWedFri(10, 0, DURATION_60_MINUTES));
    Section section2 = new Section("Mario", monWedFri(15, 30, DURATION_60_MINUTES));
    Section section3 = new Section("Sonic", monWedFri(10, 30, DURATION_60_MINUTES));
    Section section4 = new Section("Sonic", monWedFri(16, 00, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "HERO1983",
        "Heroism", 1, true, Arrays.asList(section1, section2), Collections.emptyList());
    Course course2 = new Course("Running Fast", "Hero1991",
        "Heroism", 1, true, Arrays.asList(section3, section4), Collections.emptyList());

    HashSet<Schedule> actual = new HashSet<>(scheduler.generateSchedules(
        Arrays.asList(course1, course2), new Invariants(1, 3)));

    ScheduledCourse expected1 = new ScheduledCourse("Jumping High", "HERO1983",
        "Heroism", 1, true, section1, null);
    ScheduledCourse expected2 = new ScheduledCourse("Jumping High", "HERO1983",
        "Heroism", 1, true, section2, null);
    ScheduledCourse expected3 = new ScheduledCourse("Running Fast", "Hero1991",
        "Heroism", 1, true, section3, null);
    ScheduledCourse expected4 = new ScheduledCourse("Running Fast", "Hero1991",
        "Heroism", 1, true, section4, null);

    Schedule schedule1 = new Schedule(Arrays.asList(expected1, expected4));
    Schedule schedule2 = new Schedule(Arrays.asList(expected2, expected3));

    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void LabLectureSectionsCountedAsOne()  {
    Section lectureSection1 = new Section("Mario", monWedFri(10, 0, DURATION_30_MINUTES));
    Section labSection1 = new Section("Trampoline", tuesThurs(13, 0, DURATION_60_MINUTES));
    Section otherClass = new Section("IDK", tuesThurs(13, 30, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "Hero1983", "Heroism", 1, false, 
        Arrays.asList(lectureSection1), Arrays.asList(labSection1));
    Course course2 = new Course("Smthn", "1337", "Testing", 1, false, 
        Arrays.asList(otherClass), Collections.emptyList());
    
    HashSet<Schedule> actual = new HashSet<>(scheduler.generateSchedules(
        Arrays.asList(course1, course2), new Invariants(2, 2)));
    
    Assert.assertEquals(Collections.emptyList(), actual);
  }

  @Test
  public void nonRequiredWithLabBothIncluded() {
    Section lectureSection1 = new Section("Mario", monWedFri(10, 0, DURATION_30_MINUTES));
    Section labSection1 = new Section("Trampoline", tuesThurs(13, 0, DURATION_60_MINUTES));
    Section labSection2 = new Section("Working", tuesThurs(10, 0, DURATION_30_MINUTES));
    Section otherSection = new Section("IDK", tuesThurs(13, 30, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "Hero1983", "Heroism", 1, false, 
        Arrays.asList(lectureSection1), Arrays.asList(labSection1, labSection2));
    Course course2 = new Course("Smthn", "1337", "Testing", 1, false, 
        Arrays.asList(otherSection), Collections.emptyList());

    HashSet<Schedule> actual = new HashSet<>(scheduler.generateSchedules(
        Arrays.asList(course1, course2), new Invariants(1, 2)));

    ScheduledCourse lab1 = new ScheduledCourse(course1, lectureSection1, labSection1);
    ScheduledCourse lab2 = new ScheduledCourse(course1, lectureSection1, labSection2);
    ScheduledCourse otherCourse = new ScheduledCourse(course2, otherSection);

    Schedule schedule1 = new Schedule(Arrays.asList(lab2, otherCourse));
    Schedule schedule2 = new Schedule(Arrays.asList(lab1));
    Schedule schedule3 = new Schedule(Arrays.asList(lab2));
    Schedule schedule4 = new Schedule(Arrays.asList(otherCourse));
    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2, schedule3, schedule4));

    Assert.assertEquals(expected, actual);
  }
}
