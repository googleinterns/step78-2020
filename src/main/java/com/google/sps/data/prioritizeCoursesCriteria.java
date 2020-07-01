package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class prioritizeCoursesCriteria implements Preference {

  public void prioritizeCoursesCriteria(List<Course> courseList) {
    this.courseList = courseList;
  }

  List<Course> courseList;
  
  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the order of courses that the user would like to take
   */
  public float preferenceScore(Schedule schedule) {
    List<String> courseListNames = getCourseListNames(courseList);
    int coursePrioritySum = 0;
    List<Course> courses = (List) schedule.getCourses();
    
    for (Course course : courses) {
      String courseName = course.getName();
      int priorityIndex = courseListNames.size() - courseListNames.indexOf(courseName);
      coursePrioritySum += priorityIndex;
    }
    return coursePrioritySum;
  }

  // Returns a list of course names, given a list of courses
  public List<String> getCourseListNames(List<Course> courses) {
    List<String> names = new ArrayList<String>();
    for (Course course : courses) {
      names.add(course.getName());
    }
    return names;
  }
}
