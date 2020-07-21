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

  private static final Schedule bigSchedule1 = createBigSchedule1();
  private static final Schedule bigSchedule2 = createBigSchedule2();
  private static final Schedule bigSchedule3 = createBigSchedule3();
  private static final Schedule bigSchedule4 = createBigSchedule4();

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
   * Returns a list of times on monday and friday, to be used in section constructors.
   */
  public static List<TimeRange> monFri(int hour, int minute, int durationMinutes) {
    TimeRange mon = TimeRange.fromStartDuration(TimeRange.MONDAY, hour, minute, durationMinutes);
    TimeRange fri = TimeRange.fromStartDuration(TimeRange.FRIDAY, hour, minute, durationMinutes);
    return Arrays.asList(mon, fri);
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
        "Computer Science", 15, true, Arrays.asList(section1), Arrays.asList());
    
    Section section2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    Course course2 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section2), Arrays.asList());

    Section section3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section3), Arrays.asList());

    Section section4 = new Section("Professor D", tuesThurs(13, 30, DURATION_90_MINUTES));
    Course course4 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section4), Arrays.asList());

    return Arrays.asList(course1, course2, course3, course4);
  }


  // Creates Schedule 1
  public static Schedule createSchedule1() {
    Section lectureSection1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);

    Section lectureSection4 = new Section("Professor D", tuesThurs(13, 30, DURATION_90_MINUTES));
    Section labSection4 = null;
    ScheduledCourse course4 = new ScheduledCourse("Experimental Physics", "33104", 
        "Physics", 33, true, lectureSection4, labSection4);

    return new Schedule(Arrays.asList(course1, course4));
  }

  // Creates Schedule 2
  public static Schedule createSchedule2() {
    Section lectureSection2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    Section labSection2 = null;
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, lectureSection2, labSection2);

    Section lectureSection3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    Section labSection3 = null;
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, lectureSection3, labSection3);

    return new Schedule(Arrays.asList(course2, course3));
  }

  // Creates Schedule 3
  public static Schedule createSchedule3() {
    Section lectureSection1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);
    
    Section lectureSection2 = new Section("Professor B", tuesThurs(10, 30, DURATION_90_MINUTES));
    Section labSection2 = null;
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, lectureSection2, labSection2);

    return new Schedule(Arrays.asList(course1, course2));
  }

  // Creates Schedule 4
  public static Schedule createSchedule4() {
    Section lectureSection1 = new Section("Professor A", monWedFri(9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);
    
    Section lectureSection3 = new Section("Professor C", tuesThurs(12, 00, DURATION_90_MINUTES));
    Section labSection3 = null;
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, lectureSection3, labSection3);

    return new Schedule(Arrays.asList(course1, course3));
  }

  // =========================================================================================================

  // Creates the big schedule courses
  public static List<Course> createBigScheduleCourses() {
    Section section1 = new Section("Professor A", monWedFri(10, 30, DURATION_90_MINUTES));
    //Recitation recitation1 = new Recitation(Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 13, 30, DURATION_1_HOUR)));
    Section recitation1 = new Section("Professsor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 13, 30, DURATION_1_HOUR)));
    ScheduledCourse course1 = new ScheduledCourse("Parallel and Sequential Data Structures and Algorithms", 
    "15210", "Computer Science", 12, true, section1, recitation1);

    Section section2 = new Section("Professor B", monFri(12, 00, DURATION_90_MINUTES));
    //Recitation recitation2 = new Recitation(Arrays.asList());
    Section recitation2 = new Section("Professor B", Arrays.asList());
    ScheduledCourse course2 = new ScheduledCourse("Research and Innovation in Computer Science", "15300", 
    "Computer Science", 9, true, section2, recitation2);

    Section section3 = new Section("Professor C", tuesThurs(9, 00, DURATION_90_MINUTES));
    //Recitation recitation3 = new Recitation(Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 13, 30, DURATION_1_HOUR)));
    Section recitation3 = new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 13, 30, DURATION_1_HOUR)));
    ScheduledCourse course3 = new ScheduledCourse("Compiler Design", "15411", 
    "Computer Science", 15, true, section3, recitation3);

    Section section4 = new Section("Professor D", tuesThurs(15, 00, DURATION_90_MINUTES));
    //Recitation recitation4 = new Recitation(Arrays.asList());
    Section recitation4 = new Section("Professor D", Arrays.asList());
    ScheduledCourse course4 = new ScheduledCourse("Organizational Behavior", 
    "70311", "Tepper", 9, false, section4, recitation4);

    Section section5 = new Section("Professor E", Arrays.asList(TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 14, 30, DURATION_90_MINUTES)));
    //Recitation recitation5 = new Recitation(Arrays.asList(monFri(14, 30, DURATION_60_MINUTES)));
    Section recitation5 = new Seciton("Professor E", Arrays.asList(monFri(14, 30, DURATION_60_MINUTES)));
    ScheduledCourse course5 = new ScheduledCourse("Intro to Civil Engineering", 
    "12100", "Civil Engineering", 12, false, section5, recitation5);

    return Arrays.asList(course1, course2, course3, course4, course5);
  }

  // 57 units
  // Creates the Big Schedule 1
  public static Schedule createBigSchedule1() {
    List<Course> courses = createBigScheduleCourses();
    Schedule schedule = new Schedule(courses);
    return schedule;
  }

  // 36 units
  // Creates the Big Schedule 2
  public static Schedule createBigSchedule2() {
    List<Course> courses = createBigScheduleCourses();
    Course course1 = courses.get(0);
    Course course2 = courses.get(1);
    Course course3 = courses.get(2);

    Schedule schedule = new Schedule(course1, course2, course3);
    return schedule;
  }

  // 45 units
  // Creates the Big Schedule 3
  public static Schedule createBigSchedule3() {
    List<Course> courses = createBigScheduleCourses();
    Course course1 = courses.get(0);
    Course course2 = courses.get(1);
    Course course3 = courses.get(2);
    Course course4 = courses.get(3);

    Schedule schedule = new Schedule(course1, course2, course3, course4);
    return schedule;
  }

  // 48 units
  // Creates the Big Schedule 4
  public static Schedule createBigSchedule4() {
    List<Course> courses = createBigScheduleCourses();
    Course course1 = courses.get(0);
    Course course2 = courses.get(1);
    Course course3 = courses.get(2);
    Course course5 = courses.get(4);

    Schedule schedule = new Schedule(course1, course2, course3, course5);
    return schedule;
  }


  // =========================================================================================================


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

  // TODO: Add all the tests for the big schedules, once merged into HandleRecitations branch

  @Test
  public void bigTestEmptyPreference() {
    Preferences preferenceList = new Preferences(Arrays.asList());
    //List<Course> courses = createBigScheduleCourses();

    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule1, bigSchedule4, bigSchedule3, bigSchedule2);
    Assert.assertEquals(expected, actual);

    // For printing purposes, will remove when merged into HandleRecitations branch
    //List<Schedule> schedules = preferenceList.generateSchedules(courses, new Invariant(36, 60));
  }

}
