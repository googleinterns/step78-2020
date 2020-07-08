package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class SubjectCoursesCriteria implements Preference {
  public String preferredSubject;

  public SubjectCoursesCriteria(String preferredSubject) {
    this.preferredSubject = preferredSubject;
  }

  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the number of courses pertaining to a particular subject
   */
  public float preferenceScore(Schedule schedule) {
    int preferredSubjectCount = 0;
    List<Course> courses = (List) schedule.getCourses();

    for (Course course : courses) {
      if (preferredSubject.equals(course.getSubject())) {
        preferredSubjectCount++;
      }
    }
    
    return preferredSubjectCount;
  }
}
