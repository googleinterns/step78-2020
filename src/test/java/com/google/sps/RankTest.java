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
public final class RankTest {

  private static final int DURATION_30_MINUTES = 30;
  private static final int DURATION_60_MINUTES = 60;
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;
  private static final int DURATION_3_HOUR = 180;
  

  private Preferences preferences;
  private Preference restrictTimes;
  private Preference numOfSubjectCourses;
  private Preference coursePriority;

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
  public void emptyPreferenceList() {
    // Empty preference list
    Preferences preferenceList = new Preferences(Arrays.asList());

    Section section1 = new Section("Dr. Foo", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Operating Systems", "15410", 
        "Computer Science", 15, true, Arrays.asList(section1));
    Schedule schedule1 = new Schedule(Arrays.asList(course1));
    
    Section section2 = new Section("Dr. Bar", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course2 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section2));
    Schedule schedule2 = new Schedule(Arrays.asList(course2));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    // Schedules remain in the same order, since there are no preferences to sort them
    Assert.assertEquals(schedules.get(0), schedule1);
    Assert.assertEquals(schedules.get(1), schedule2);
  }

  @Test
  public void restrictTimesPreference() {
    List<TimeRange> userNoClassTimes = monWedFri(7, 00, DURATION_3_HOUR);
    System.out.println("hehe");
    userNoClassTimes.addAll(monWedFri(16, 00, DURATION_3_HOUR));
    System.out.println("ha");
    // Arrays.asList(TimeRange.fromStartEnd(1, 7, 0, 1, 10, 0, false), 
    //                                                  TimeRange.fromStartEnd(1, 4, 0, 1, 7, 0, true));
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);

    Preferences preferenceList = new Preferences(Arrays.asList(restrictCriteria));

    Section section1 = new Section("Dr. Foo", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section1));
    
    Section section2 = new Section("Dr. Bar", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course2 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section2));

    Schedule schedule1 = new Schedule(Arrays.asList(course1));
    Schedule schedule2 = new Schedule(Arrays.asList(course2));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    Assert.assertEquals(schedules.get(0), schedule1);
    Assert.assertEquals(schedules.get(1), schedule2);
  }

  @Test
  public void subjectCoursesPreference() {
    String preferredSubject = "Computer Science";
    SubjectCoursesCriteria numOfSubjectCourses = new SubjectCoursesCriteria(preferredSubject);

    Preferences preferenceList = new Preferences(Arrays.asList(numOfSubjectCourses));

    Section section1 = new Section("Dr. Foo", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section1));

    Section section2 = new Section("Dr. Hello", tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course2 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section2));
    
    Section section3 = new Section("Dr. Bar", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section3));
    
    Schedule schedule1 = new Schedule(Arrays.asList(course1, course2));
    Schedule schedule2 = new Schedule(Arrays.asList(course1, course2, course3));

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    Assert.assertEquals(schedules.get(0), schedule2);
    Assert.assertEquals(schedules.get(1), schedule1);
  }

  @Test
  public void prioritizeCoursesPreference() {
    List<Course> courseList = createCoursesPriority();
    PrioritizeCoursesCriteria numOfSubjectCourses = new PrioritizeCoursesCriteria(courseList);

    Preferences preferenceList = new Preferences(Arrays.asList(numOfSubjectCourses));

    Section section1 = new Section("Dr. Foo", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section1));

    Section section2 = new Section("Dr. Hello", tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course2 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section2));
    
    Section section3 = new Section("Dr. Bar", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section3));
    
    Schedule schedule1 = new Schedule(Arrays.asList(course2, course3));  // Priority score of 3 (2 + 1)
    Schedule schedule2 = new Schedule(Arrays.asList(course1, course2));  // Priority score of 5 (3 + 2)

    List<Schedule> schedules = Arrays.asList(schedule1, schedule2);
    preferenceList.sortSchedules(schedules);

    Assert.assertEquals(schedules.get(0), schedule2);
    Assert.assertEquals(schedules.get(1), schedule1);
  }

  List<Course> createCoursesPriority() {
    Section section1 = new Section("Dr. Foo", monWedFri(10, 30, DURATION_90_MINUTES));
    Course course1 = new Course("Compilers", "15411", 
        "Computer Science", 15, true, Arrays.asList(section1));
    
    Section section2 = new Section("Dr. Bar", monWedFri(9, 00, DURATION_90_MINUTES));
    Course course2 = new Course("Algorithms", "15210", 
        "Computer Science", 15, true, Arrays.asList(section2));

    Section section3 = new Section("Dr. Hello", tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Experimental Physics", "33104", 
        "Physics", 33, true, Arrays.asList(section3));

    return Arrays.asList(course1, course2, course3);
  }

  // TODO: Add tests that combine multiple criteria preferences together
    
}
