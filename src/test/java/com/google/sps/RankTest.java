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
  private static final int DURATION_6_HOUR = 360;
  private static final int DURATION_24_HOUR = 1440;

  // Days of the week
  private static final int[] MON_WED_FRI = {TimeRange.MONDAY, TimeRange.WEDNESDAY, TimeRange.FRIDAY};
  private static final int[] MON_FRI = {TimeRange.MONDAY, TimeRange.FRIDAY};
  private static final int[] WED_FRI = {TimeRange.WEDNESDAY, TimeRange.FRIDAY};
  private static final int[] MON_WED = {TimeRange.MONDAY, TimeRange.WEDNESDAY};
  private static final int[] TUES_THURS = {TimeRange.TUESDAY, TimeRange.THURSDAY};

  // Small schedule stuff
  private static final Schedule schedule1 = createSchedule1();
  private static final Schedule schedule2 = createSchedule2();
  private static final Schedule schedule3 = createSchedule3();
  private static final Schedule schedule4 = createSchedule4();

  // Big schedule stuff
  private static final List<ScheduledCourse> courses = createBigScheduleCourses();
  // Algos
  private static final ScheduledCourse course1 = courses.get(0);
  private static final ScheduledCourse course2 = courses.get(1);

  // Research innovation
  private static final ScheduledCourse course3 = courses.get(2);
  private static final ScheduledCourse course4 = courses.get(3);

  // Compilers
  private static final ScheduledCourse course5 = courses.get(4);
  private static final ScheduledCourse course6 = courses.get(5);

  // Organizational Behavior
  private static final ScheduledCourse course7 = courses.get(6);
  private static final ScheduledCourse course8 = courses.get(7);

  // Intro to civE
  private static final ScheduledCourse course9 = courses.get(8);

  private static final Schedule bigSchedule1 = createBigSchedule1();
  private static final Schedule bigSchedule2 = createBigSchedule2();
  private static final Schedule bigSchedule3 = createBigSchedule3();
  private static final Schedule bigSchedule4 = createBigSchedule4();
  private static final Schedule bigSchedule5 = createBigSchedule5();
  private static final Schedule bigSchedule6 = createBigSchedule6();
  private static final Schedule bigSchedule7 = createBigSchedule7();
  private static final Schedule bigSchedule8 = createBigSchedule8();
  private static final Schedule bigSchedule9 = createBigSchedule9();
  private static final Schedule bigSchedule10 = createBigSchedule10();
  private static final Schedule bigSchedule11 = createBigSchedule11();
  private static final Schedule bigSchedule12 = createBigSchedule12();
  private static final Schedule bigSchedule13 = createBigSchedule13();
  private static final Schedule bigSchedule14 = createBigSchedule14();
  private static final Schedule bigSchedule15 = createBigSchedule15();
  private static final Schedule bigSchedule16 = createBigSchedule16();
  private static final Schedule bigSchedule17 = createBigSchedule17();
  private static final Schedule bigSchedule18 = createBigSchedule18();
  private static final Schedule bigSchedule19 = createBigSchedule19();
  private static final Schedule bigSchedule20 = createBigSchedule20();
  private static final Schedule bigSchedule21 = createBigSchedule21();
  private static final Schedule bigSchedule22 = createBigSchedule22();
  private static final Schedule bigSchedule23 = createBigSchedule23();
  private static final Schedule bigSchedule24 = createBigSchedule24();

  private static final List<TimeRange> userNoClassTimesBigTest = createSectionTimes(TUES_THURS, 00, 00, DURATION_24_HOUR);
  private static final List<Course> courseListBigTest = createCoursesPriorityBigTest();
  private static final String preferredSubjectBigTest = "Civil Engineering";

  private static final List<TimeRange> userNoClassTimes = createSectionTimes(MON_WED_FRI, 7, 00, DURATION_3_HOUR);
  private static final List<Course> courseList = createCoursesPriority();
  private static final String preferredSubject = "Computer Science";
  
  private static List<TimeRange> createSectionTimes(int[] days, int hour, int minute, int durationMinutes) {
    List<TimeRange> timeRanges = new ArrayList<>();
    for (int day : days) {
      TimeRange timeRange = TimeRange.fromStartDuration(day, hour, minute, durationMinutes);
      timeRanges.add(timeRange);
    }
    return timeRanges;
  }

  /**
   * Returns a list of courses in the order of priority that they should be considered
   */
  public static List<Course> createCoursesPriority() {
    Section section1 = new Section("Professor A", createSectionTimes(MON_WED_FRI, 9, 00, DURATION_90_MINUTES));
    Course course1 = new Course("Operating System", "15410", 
        "Computer Science", 15, true, Arrays.asList(section1), Arrays.asList());
    
    Section section2 = new Section("Professor B", createSectionTimes(TUES_THURS ,10, 30, DURATION_90_MINUTES));
    Course course2 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section2), Arrays.asList());

    Section section3 = new Section("Professor C", createSectionTimes(TUES_THURS, 12, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section3), Arrays.asList());

    Section section4 = new Section("Professor D", createSectionTimes(TUES_THURS, 13, 30, DURATION_90_MINUTES));
    Course course4 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section4), Arrays.asList());

    return Arrays.asList(course1, course2, course3, course4);
  }

  public static List<Course> createCoursesPriorityBigTest() {
    // PARALLEL ALGORITHMS
    List<Section> section1 = Arrays.asList(new Section("Professor A", createSectionTimes(MON_WED_FRI, 9, 30, DURATION_2_HOUR)));
    List<Section> recitation1 = Arrays.asList(new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 10, 30, DURATION_1_HOUR))),
                                              new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 11, 30, DURATION_1_HOUR))));
    Course course1 = new Course("Parallel and Sequential Data Structures and Algorithms", 
    "15210", "Computer Science", 12, true, section1, recitation1);

    // RESEARCH AND INNOVATION
    List<Section> section2 = Arrays.asList(new Section("Professor B", createSectionTimes(MON_FRI, 11, 30, DURATION_90_MINUTES)),
                                           new Section("Professor B", createSectionTimes(WED_FRI, 11, 30, DURATION_90_MINUTES)));
    List<Section> recitation2 = Arrays.asList();
    Course course2 = new Course("Research and Innovation in Computer Science", "15300", 
    "Computer Science", 9, true, section2, recitation2);

    // COMPILIERS
    List<Section> section3 = Arrays.asList(new Section("Professor C", createSectionTimes(TUES_THURS, 8, 00, DURATION_90_MINUTES)));
    List<Section> recitation3 = Arrays.asList(new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 14, 30, DURATION_1_HOUR))),
                                              new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 16, 00, DURATION_1_HOUR))));
    Course course3 = new Course("Compiler Design", "15411", 
    "Computer Science", 15, true, section3, recitation3);

    // ORGANIZATIONAL BEHAVIOR
    List<Section> section4 = Arrays.asList(new Section("Professor D", createSectionTimes(MON_WED, 13, 30, DURATION_2_HOUR)),
                                           new Section("Professor D", createSectionTimes(TUES_THURS, 15, 00, DURATION_2_HOUR)));
    List<Section> recitation4 = Arrays.asList();
    Course course4 = new Course("Organizational Behavior", 
    "70311", "Tepper", 9, true, section4, recitation4);

    // INTRO TO CIVIL ENGINEERING
    List<Section> section5 = Arrays.asList(new Section("Professor E", Arrays.asList(TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 16, 00, DURATION_2_HOUR))));
    List<Section> recitation5 = Arrays.asList(new Section("Professor E", createSectionTimes(MON_FRI, 16,00, DURATION_1_HOUR)));
    Course course5 = new Course("Intro to Civil Engineering", 
    "12100", "Civil Engineering", 12, false, section5, recitation5);

    return Arrays.asList(course1, course2, course3, course4, course5);
  }

  // Creates the big schedule courses
  public static List<ScheduledCourse> createBigScheduleCourses() {
    // PARALLEL ALGORITHMS
    Section section1 = new Section("Professor A", createSectionTimes(MON_WED_FRI,9, 30, DURATION_2_HOUR));
    List<Section> recitation1 = Arrays.asList(new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 10, 30, DURATION_1_HOUR))),
                                              new Section("Professor A", Arrays.asList(TimeRange.fromStartDuration(TimeRange.TUESDAY, 11, 30, DURATION_1_HOUR))));
    ScheduledCourse course1_sectionA = new ScheduledCourse("Parallel and Sequential Data Structures and Algorithms", 
    "15210", "Computer Science", 12, true, section1, recitation1.get(0));
    ScheduledCourse course1_sectionB = new ScheduledCourse("Parallel and Sequential Data Structures and Algorithms", 
    "15210", "Computer Science", 12, true, section1, recitation1.get(1));

    // RESEARCH AND INNOVATION
    List<Section> section2 = Arrays.asList(new Section("Professor B", createSectionTimes(MON_FRI, 11, 30, DURATION_90_MINUTES)),
                                           new Section("Professor B", createSectionTimes(WED_FRI ,11, 30, DURATION_90_MINUTES)));
    Section recitation2 = null;
    ScheduledCourse course2_lectureA = new ScheduledCourse("Research and Innovation in Computer Science", "15300", 
    "Computer Science", 9, true, section2.get(0), recitation2);
    ScheduledCourse course2_lectureB = new ScheduledCourse("Research and Innovation in Computer Science", "15300", 
    "Computer Science", 9, true, section2.get(1), recitation2);

    // COMPILIERS
    Section section3 = new Section("Professor C", createSectionTimes(TUES_THURS, 8, 00, DURATION_90_MINUTES));
    List<Section> recitation3 = Arrays.asList(new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 14, 30, DURATION_1_HOUR))),
                                              new Section("Professor C", Arrays.asList(TimeRange.fromStartDuration(TimeRange.FRIDAY, 16, 00, DURATION_1_HOUR))));
    ScheduledCourse course3_sectionA = new ScheduledCourse("Compiler Design", "15411", 
    "Computer Science", 15, true, section3, recitation3.get(0));
    ScheduledCourse course3_sectionB = new ScheduledCourse("Compiler Design", "15411", 
    "Computer Science", 15, true, section3, recitation3.get(1));

    // ORGANIZATIONAL BEHAVIOR
    List<Section> section4 = Arrays.asList(new Section("Professor D", createSectionTimes(MON_WED, 13, 30, DURATION_2_HOUR)),
                                           new Section("Professor D", createSectionTimes(TUES_THURS, 15, 00, DURATION_2_HOUR)));
    Section recitation4 = null;
    ScheduledCourse course4_sectionA = new ScheduledCourse("Organizational Behavior", 
    "70311", "Tepper", 9, true, section4.get(0), recitation4);
    ScheduledCourse course4_sectionB = new ScheduledCourse("Organizational Behavior", 
    "70311", "Tepper", 9, true, section4.get(1), recitation4);

    // INTRO TO CIVIL ENGINEERING
    Section section5 = new Section("Professor E", Arrays.asList(TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 16, 00, DURATION_2_HOUR)));
    Section recitation5 = new Section("Professor E", createSectionTimes(MON_FRI ,16,00, DURATION_1_HOUR));
    ScheduledCourse course5 = new ScheduledCourse("Intro to Civil Engineering", 
    "12100", "Civil Engineering", 12, true, section5, recitation5);

    return Arrays.asList(course1_sectionA, course1_sectionB, course2_lectureA, course2_lectureB, 
                         course3_sectionA, course3_sectionB, course4_sectionA, course4_sectionB, course5);
  }

  // Creates Schedule 1
  public static Schedule createSchedule1() {
    Section lectureSection1 = new Section("Professor A", createSectionTimes(MON_WED_FRI, 9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);

    Section lectureSection4 = new Section("Professor D", createSectionTimes(TUES_THURS ,13, 30, DURATION_90_MINUTES));
    Section labSection4 = null;
    ScheduledCourse course4 = new ScheduledCourse("Experimental Physics", "33104", 
        "Physics", 33, true, lectureSection4, labSection4);

    return new Schedule(Arrays.asList(course1, course4));
  }

  // Creates Schedule 2
  public static Schedule createSchedule2() {
    Section lectureSection2 = new Section("Professor B", createSectionTimes(TUES_THURS , 10, 30, DURATION_90_MINUTES));
    Section labSection2 = null;
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, lectureSection2, labSection2);

    Section lectureSection3 = new Section("Professor C", createSectionTimes(TUES_THURS, 12, 00, DURATION_90_MINUTES));
    Section labSection3 = null;
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, lectureSection3, labSection3);

    return new Schedule(Arrays.asList(course2, course3));
  }

  // Creates Schedule 3
  public static Schedule createSchedule3() {
    Section lectureSection1 = new Section("Professor A", createSectionTimes(MON_WED_FRI, 9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);
    
    Section lectureSection2 = new Section("Professor B", createSectionTimes(TUES_THURS ,10, 30, DURATION_90_MINUTES));
    Section labSection2 = null;
    ScheduledCourse course2 = new ScheduledCourse("Compilers", "15411", 
        "Computer Science", 15, true, lectureSection2, labSection2);

    return new Schedule(Arrays.asList(course1, course2));
  }

  // Creates Schedule 4
  public static Schedule createSchedule4() {
    Section lectureSection1 = new Section("Professor A", createSectionTimes(MON_WED_FRI, 9, 00, DURATION_90_MINUTES));
    Section labSection1 = null;
    ScheduledCourse course1 = new ScheduledCourse("Operating System", "15410", 
        "Computer Science", 15, true, lectureSection1, labSection1);
    
    Section lectureSection3 = new Section("Professor C", createSectionTimes(TUES_THURS, 12, 00, DURATION_90_MINUTES));
    Section labSection3 = null;
    ScheduledCourse course3 = new ScheduledCourse("Algorithms", "15210", 
        "Computer Science", 15, true, lectureSection3, labSection3);

    return new Schedule(Arrays.asList(course1, course3));
  }

  // Big Schedule 1
  public static Schedule createBigSchedule1() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course5, course8, course9));
    return schedule;
  }

  // Big Schedule 2
  public static Schedule createBigSchedule2() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course5, course8, course9));
    return schedule;
  }

  // Big Schedule 3
  public static Schedule createBigSchedule3() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course5, course8, course9));
    return schedule;
  }

  // Big Schedule 4
  public static Schedule createBigSchedule4() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course6, course7));
    return schedule;
  }

  // Big Schedule 5
  public static Schedule createBigSchedule5() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course6, course7));
    return schedule;
  }


  // Big Schedule 6
  public static Schedule createBigSchedule6() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course6, course8));
    return schedule;
  }

  // Big Schedule 7
  public static Schedule createBigSchedule7() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course5, course8, course9));
    return schedule;
  }

  // Big Schedule 8
  public static Schedule createBigSchedule8() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course5, course8));
    return schedule;
  }

  // Big Schedule 9
  public static Schedule createBigSchedule9() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course5, course7, course9));
    return schedule;
  }

  // Big Schedule 10
  public static Schedule createBigSchedule10() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course5, course7));
    return schedule;
  }

  // Big Schedule 11
  public static Schedule createBigSchedule11() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course5, course8));
    return schedule;
  }

  // Big Schedule 12
  public static Schedule createBigSchedule12() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course5, course7));
    return schedule;
  }

  // Big Schedule 13
  public static Schedule createBigSchedule13() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course6, course7));
    return schedule;
  }

  // Big Schedule 14
  public static Schedule createBigSchedule14() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course5, course7));
    return schedule;
  }

  // Big Schedule 15
  public static Schedule createBigSchedule15() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course5, course7));
    return schedule;
  }

  // Big Schedule 16
  public static Schedule createBigSchedule16() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course5, course7, course9));
    return schedule;
  }

  // Big Schedule 17
  public static Schedule createBigSchedule17() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course5, course7));
    return schedule;
  }

  // Big Schedule 18
  public static Schedule createBigSchedule18() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course5, course8));
    return schedule;
  }

  // Big Schedule 19
  public static Schedule createBigSchedule19() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course3, course5, course7, course9));
    return schedule;
  }

  // Big Schedule 20
  public static Schedule createBigSchedule20() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course5, course8));
    return schedule;
  }

  // Big Schedule 21
  public static Schedule createBigSchedule21() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course4, course6, course8));
    return schedule;
  }

  // Big Schedule 22
  public static Schedule createBigSchedule22() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course6, course8));
    return schedule;
  }

  // Big Schedule 23
  public static Schedule createBigSchedule23() {
    Schedule schedule = new Schedule(Arrays.asList(course2, course4, course6, course8));
    return schedule;
  }

  // Big Schedule 24
  public static Schedule createBigSchedule24() {
    Schedule schedule = new Schedule(Arrays.asList(course1, course3, course5, course7, course9));
    return schedule;
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
    
  @Test
  public void emptyPreferenceBigTest() {
    Preferences preferenceList = new Preferences(Arrays.asList());
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void restrictTimesPreferenceBigTest() {
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimesBigTest);
    Preferences preferenceList = new Preferences(Arrays.asList(restrictCriteria));
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule4, bigSchedule5, bigSchedule9, bigSchedule10, bigSchedule12, bigSchedule13,
                                             bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule19, bigSchedule24,
                                             bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule6, bigSchedule7, bigSchedule8,
                                             bigSchedule11, bigSchedule18, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void subjectCoursesPreferenceBigTest() {
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubjectBigTest);
    Preferences preferenceList = new Preferences(Arrays.asList(subjectCourses));
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule7, bigSchedule9, bigSchedule16,
                                             bigSchedule19, bigSchedule24, bigSchedule4, bigSchedule5, bigSchedule6, bigSchedule8,
                                             bigSchedule10, bigSchedule11, bigSchedule12, bigSchedule13, bigSchedule14, bigSchedule15,
                                             bigSchedule17, bigSchedule18, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23);
    
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void prioritizeCoursesPreferenceBigTest() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseListBigTest);
    Preferences preferenceList = new Preferences(Arrays.asList(coursePriority));
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);
    
    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule7, bigSchedule9, bigSchedule16,
                                             bigSchedule19, bigSchedule24, bigSchedule4, bigSchedule5, bigSchedule6, bigSchedule8,
                                             bigSchedule10, bigSchedule11, bigSchedule12, bigSchedule13, bigSchedule14, bigSchedule15,
                                             bigSchedule17, bigSchedule18, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void mixedPreferencesSingleTieBigTest() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseListBigTest);
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimesBigTest);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubjectBigTest);
    Preferences preferenceList = new Preferences(Arrays.asList(restrictCriteria, coursePriority, subjectCourses));
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule9, bigSchedule16, bigSchedule19, bigSchedule24, bigSchedule4, bigSchedule5,
                                             bigSchedule10, bigSchedule12, bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule17,
                                             bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule7, bigSchedule6, bigSchedule8,
                                             bigSchedule11, bigSchedule18, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void mixedPreferencesDoubleTieBigTest() {
    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseListBigTest);
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimesBigTest);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubjectBigTest);
    Preferences preferenceList = new Preferences(Arrays.asList(subjectCourses, restrictCriteria, coursePriority));
    List<Schedule> schedules = Arrays.asList(bigSchedule1, bigSchedule2, bigSchedule3, bigSchedule4, bigSchedule5, bigSchedule6,
                                             bigSchedule7, bigSchedule8, bigSchedule9, bigSchedule10, bigSchedule11, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule16, bigSchedule17, bigSchedule18,
                                             bigSchedule19, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23, bigSchedule24);
    preferenceList.sortSchedules(schedules);

    List<Schedule> actual = schedules;
    List<Schedule> expected = Arrays.asList(bigSchedule9, bigSchedule16, bigSchedule19, bigSchedule24, bigSchedule1, bigSchedule2,
                                             bigSchedule3, bigSchedule7, bigSchedule4, bigSchedule5, bigSchedule10, bigSchedule12,
                                             bigSchedule13, bigSchedule14, bigSchedule15, bigSchedule17, bigSchedule6, bigSchedule8,
                                             bigSchedule11, bigSchedule18, bigSchedule20, bigSchedule21, bigSchedule22, bigSchedule23);
    Assert.assertEquals(expected, actual);
  }
}
