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

import java.util.ArrayList;
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
   * Returns a list of times on monday and friday, to be used in section constructors.
   */
  public List<TimeRange> monFri(int hour, int minute, int durationMinutes) {
    TimeRange mon = TimeRange.fromStartDuration(TimeRange.MONDAY, hour, minute, durationMinutes);
    TimeRange fri = TimeRange.fromStartDuration(TimeRange.FRIDAY, hour, minute, durationMinutes);
    return Arrays.asList(mon, fri);
  }

  /**
   * Returns a list of times on monday and friday, to be used in section constructors.
   */
  public List<TimeRange> monWed(int hour, int minute, int durationMinutes) {
    TimeRange mon = TimeRange.fromStartDuration(TimeRange.MONDAY, hour, minute, durationMinutes);
    TimeRange wed = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, hour, minute, durationMinutes);
    return Arrays.asList(mon, wed);
  }

    /**
   * Returns a list of times on monday, wednesday, and friday, to be used in section constructors.
   */
  public List<TimeRange> wedFri(int hour, int minute, int durationMinutes) {
    TimeRange wed = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, hour, minute, durationMinutes);
    TimeRange fri = TimeRange.fromStartDuration(TimeRange.FRIDAY, hour, minute, durationMinutes);
    return Arrays.asList(wed, fri);
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
  public void testScheduledCourseEquality() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section), Collections.emptyList());

    ScheduledCourse t1 = new ScheduledCourse(course1, section);
    ScheduledCourse t2 = new ScheduledCourse(course1, section, null);
    ScheduledCourse t3 = new ScheduledCourse("Intro to Evil", "EVIL100", "Wrongdoing", 1, true, section);
    ScheduledCourse t4 = new ScheduledCourse("Intro to Evil", "EVIL100", "Wrongdoing", 1, true, section, null);
    ScheduledCourse[] courses = {t1, t2, t3, t4};
    for(int i = 0; i < courses.length; i++) {
      for (int j = i + 1; j < courses.length; j++){
        Assert.assertEquals(courses[i], courses[j]);
      }
    }
  }

  @Test
  public void singleCourseValidSchedule() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section), Collections.emptyList());
    
    List<Schedule> actual = scheduler.generateSchedules(
        Arrays.asList(course1), new Invariants(1, 2));
    List<Schedule> expected = Arrays.asList(new Schedule(Arrays.asList(new ScheduledCourse(course1, section))));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void notEnoughCreditsNoSchedules() {
    Section section = new Section("Dr. Eggman", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Intro to Evil", "EVIL100", 
        "Wrongdoing", 1, true, Arrays.asList(section), Collections.emptyList());
    
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
        "Heroism", 1, true, section1);
    ScheduledCourse expected2 = new ScheduledCourse("Jumping High", "HERO1983",
        "Heroism", 1, true, section2);
    ScheduledCourse expected3 = new ScheduledCourse("Running Fast", "Hero1991",
        "Heroism", 1, true, section3);
    ScheduledCourse expected4 = new ScheduledCourse("Running Fast", "Hero1991",
        "Heroism", 1, true, section4);

    Schedule schedule1 = new Schedule(Arrays.asList(expected1, expected4));
    Schedule schedule2 = new Schedule(Arrays.asList(expected2, expected3));

    HashSet<Schedule> expected = new HashSet<>(Arrays.asList(schedule1, schedule2));
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void labLectureSectionsCountedAsOne()  {
    Section lectureSection1 = new Section("Mario", monWedFri(10, 0, DURATION_30_MINUTES));
    Section labSection1 = new Section("Trampoline", tuesThurs(13, 0, DURATION_60_MINUTES));
    Section otherClass = new Section("IDK", tuesThurs(13, 30, DURATION_60_MINUTES));

    Course course1 = new Course("Jumping High", "Hero1983", "Heroism", 1, false, 
        Arrays.asList(lectureSection1), Arrays.asList(labSection1));
    Course course2 = new Course("Smthn", "1337", "Testing", 1, false, 
        Arrays.asList(otherClass), Collections.emptyList());
    
    List<Schedule> actual = scheduler.generateSchedules(
        Arrays.asList(course1, course2), new Invariants(2, 2));
    
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

  public List<Course> generateLargeSchedule() {
    // PARALLEL ALGORITHMS
    List<Section> section1 = Arrays.asList(new Section("Professor A", monWedFri(9, 30, DURATION_2_HOUR)));
    List<Section> recitation1 = Arrays.asList(new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 10, 30, DURATION_1_HOUR))),
                                              new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 11, 30, DURATION_1_HOUR))));
    Course course1 = new Course("Parallel and Sequential Data Structures and Algorithms", 
    "15210", "Computer Science", 12, true, section1, recitation1);

    // RESEARCH AND INNOVATION
    List<Section> section2 = Arrays.asList(new Section("Professor B", monFri(11, 30, DURATION_90_MINUTES)),
                                           new Section("Professor B", wedFri(11, 30, DURATION_90_MINUTES)));
    List<Section> recitation2 = Arrays.asList();
    Course course2 = new Course("Research and Innovation in Computer Science", "15300", 
    "Computer Science", 9, true, section2, recitation2);

    // COMPILIERS
    List<Section> section3 = Arrays.asList(new Section("Professor C", tuesThurs(8, 00, DURATION_90_MINUTES)));
    List<Section> recitation3 = Arrays.asList(new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 14, 30, DURATION_1_HOUR))),
                                              new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 16, 00, DURATION_1_HOUR))));
    Course course3 = new Course("Compiler Design", "15411", 
    "Computer Science", 15, true, section3, recitation3);

    // ORGANIZATIONAL BEHAVIOR
    List<Section> section4 = Arrays.asList(new Section("Professor D", monWed(13, 30, DURATION_2_HOUR)),
                                           new Section("Professor D", tuesThurs(15, 00, DURATION_2_HOUR)));
    List<Section> recitation4 = Arrays.asList();
    Course course4 = new Course("Organizational Behavior", 
    "70311", "Tepper", 9, false, section4, recitation4);

    // INTRO TO CIVIL ENGINEERING
    List<Section> section5 = Arrays.asList(new Section("Professor E", Arrays.asList(TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 16, 00, DURATION_2_HOUR))));
    List<Section> recitation5 = Arrays.asList(new Section("Professor E", monFri(16,00, DURATION_1_HOUR)));
    Course course5 = new Course("Intro to Civil Engineering", 
    "12100", "Civil Engineering", 12, false, section5, recitation5);

    return Arrays.asList(course1, course2, course3, course4, course5);
  }

  @Test
  public void nonRequiredNotDeleted() {
    List<Course> courses = generateLargeSchedule();
    List<Schedule> schedules = scheduler.generateSchedules(courses, new Invariants(54, 60));
    Assert.assertTrue(schedules.size() == 8);
  }
}
