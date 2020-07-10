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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class RankTest {

  private static final int DURATION_30_MINUTES = 30;
  private static final int DURATION_60_MINUTES = 60;
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;
  private static final int DURATION_3_HOUR = 180;

  private static final Schedule schedule1 = createSchedule1();
  private static final Schedule schedule2 = createSchedule2();
  private static final Schedule schedule3 = createSchedule3();
  private static final Schedule schedule4 = createSchedule4();

  private static final List<TimeRange> userNoClassTimes = monWedFri(7, 00, DURATION_3_HOUR);
  private static final List<Course> courseList = createCoursesPriority();
  private static final String preferredSubject = "Computer Science";

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
  public static List<TimeRange> tuesThurs(int hour, int minute, int durationMinutes) {
    TimeRange tues = TimeRange.fromStartDuration(TimeRange.TUESDAY, hour, minute, durationMinutes);
    TimeRange thurs = TimeRange.fromStartDuration(TimeRange.THURSDAY, hour, minute, durationMinutes);
    return Arrays.asList(tues, thurs);
  }

  /**
   * Returns a list of courses in the order of priority that they should be considered
   */
  public static List<Course> createCoursesPriority() {
    Section section1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course1 = new Course("Operating System", "15410", 
        "Computer Science", 15, true, Arrays.asList(section1));
    
    Section section2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    Course course2 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section2));

    Section section3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section3));

    Section section4 = new Section("Professor D", tuesThurs(13, 30, DURATION_90_MINUTES));
    Course course4 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section4));

    return Arrays.asList(course1, course2, course3, course4);
  }


  // Creates Schedule 1
  public static Schedule createSchedule1() {
    Section section1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, section1);

    Section section4 = new Section("Professor D", tuesThurs(13, 30, DURATION_90_MINUTES));
    ScheduledCourse course4 = new ScheduledCourse("Experimental Physics", "33104", 
        "Physics", 33, true, section4);

    return new Schedule(Arrays.asList(course1, course4));
  }

  // Creates Schedule 2
  public static Schedule createSchedule2() {
    Section section2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, section2);

    Section section3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, section3);

    return new Schedule(Arrays.asList(course2, course3));
  }

  // Creates Schedule 3
  public static Schedule createSchedule3() {
    Section section1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, section1);
    
    Section section2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, section2);

    return new Schedule(Arrays.asList(course1, course2));
  }

  // Creates Schedule 4
  public static Schedule createSchedule4() {
    Section section1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, section1);
    
    Section section3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, section3);

    return new Schedule(Arrays.asList(course1, course3));
  }


  @Test
  public void emptyPreferenceList() {
    // Empty preference list
    Preferences preferenceList = new Preferences(Arrays.asList());

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    // Schedules remain in the same order; there are no preferences to sort them
    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule1, schedule2);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void restrictTimesPreference() {
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);
    Preferences preferenceList = new Preferences(Arrays.asList(restrictCriteria));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule2, schedule1);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void subjectCoursesPreference() {
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubject);
    Preferences preferenceList = new Preferences(Arrays.asList(subjectCourses));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule3);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule3, schedule1);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void prioritizeCoursesPreference() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseList);
    Preferences preferenceList = new Preferences(Arrays.asList(coursePriority));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2, schedule3);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule3, schedule1, schedule2);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void mixedPreferencesNoTie() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseList);
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubject);
    Preferences preferenceList = new Preferences(Arrays.asList(coursePriority, restrictCriteria, subjectCourses));

    List<Schedule> schedules = Arrays.asList(schedule2, schedule3);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule3, schedule2);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void mixedPreferencesSingleTie() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseList);
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubject);
    Preferences preferenceList = new Preferences(Arrays.asList(coursePriority, restrictCriteria, subjectCourses));
    
    // coursePriority becomes a tie => sort by restrictCriteria
    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule2, schedule1);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void mixedPreferencesDoubleTie() {
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubject);
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseList);
    Preferences preferenceList = new Preferences(Arrays.asList(restrictCriteria, subjectCourses, coursePriority));

    // restrictCriteria becomes a tie => subjectCourses becomes a tie => sort by coursePriority
    List<Schedule> schedules = Arrays.asList(schedule3, schedule4);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(schedule3, schedule4);
    Assert.assertEquals(expected, actual);
  }
}
