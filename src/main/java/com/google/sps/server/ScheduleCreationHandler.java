/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.sps.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.google.sps.data.*;
import com.google.sps.Scheduler;

/**
 * Create and rank the schedules     
 */
public class ScheduleCreationHandler {
  
  private static final int DURATION_90_MINUTES = 90;
  private static final int DURATION_3_HOUR = 180;

  public static List<Schedule> createSchedules() {
    // Currently using hard-coded courses and preferences 
    List<Course> courses = createCoursesPriority();
    List<TimeRange> userNoClassTimes = TesterSchedule.monWedFri(7, 00, DURATION_3_HOUR);
    List<Course> courseList = createCoursesPriority();
    String preferredSubject = "Computer Science";

    // Permute the courses to create the schedules 
    Scheduler scheduler = new Scheduler();
    List<Schedule> schedules = scheduler.generateSchedules(courses, new Invariants(10,54));

    PrioritizeCoursesCriteria coursePriority = new PrioritizeCoursesCriteria(courseList);
    RestrictTimesCriteria restrictCriteria = new RestrictTimesCriteria(userNoClassTimes);
    SubjectCoursesCriteria subjectCourses = new SubjectCoursesCriteria(preferredSubject);
    Preferences preferenceList = new Preferences(Arrays.asList(coursePriority, restrictCriteria, subjectCourses));

    // Rank the schedules
    preferenceList.sortSchedules(schedules);
    return schedules;
  }

   /**
   * Helper function to instantiate hard-coded courses, and their preferred order 
   *
   * @return a List of the ordered Course objects      
   */
  private static List<Course> createCoursesPriority() {
    Section section1 = new Section("Professor A", TesterSchedule.monWedFri(9, 00, DURATION_90_MINUTES));
    Course course1 = new Course("Operating System", "15410", 
        "Computer Science", 15, true, Arrays.asList(section1));
    
    Section section2 = new Section("Professor B", TesterSchedule.tuesThurs(10, 30, DURATION_90_MINUTES));
    Course course2 = new Course("Compilers", "15411", 
        "Computer Science", 15, false, Arrays.asList(section2));

    Section section3 = new Section("Professor C", TesterSchedule.tuesThurs(12, 00, DURATION_90_MINUTES));
    Course course3 = new Course("Algorithms", "15210", 
        "Computer Science", 12, false, Arrays.asList(section3));

    Section section4 = new Section("Professor D", TesterSchedule.tuesThurs(13, 30, DURATION_90_MINUTES));
    Course course4 = new Course("Experimental Physics", "33104", 
        "Physics", 9, true, Arrays.asList(section4));

    return Arrays.asList(course1, course2, course3, course4);
  }
}
