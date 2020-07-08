package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class PrioritizeCoursesCriteria implements Preference {
  List<Course> courseList;
  HashMap<Course, Integer> courseScores = new HashMap<Course, Integer>();
  
  public PrioritizeCoursesCriteria(List<Course> courseList, HashMap<Course, Integer> courseScores) {
    this.courseList = courseList;
    this.courseScores = courseScores;
  }
  
  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the order of courses that the user would like to take
   */
  public float preferenceScore(Schedule schedule) {
    // Instantiate the HashMap, courseScores
    for (int i = 0; i < courseList.size(); i++) {
      courseScores.put(courseList.get(i), courseList.size()-i);
    }

    int coursePrioritySum = 0;
    Collection<Course> courses = schedule.getCourses();
    
    for (Course course : courses) {
      int priorityIndex = courseScores.get(course);
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
