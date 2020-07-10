package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;

public class PrioritizeCoursesCriteria implements Criterion {
  List<Course> courseList;
  HashMap<ScheduledCourse, Integer> courseScores = new HashMap<ScheduledCourse, Integer>();
  
  public PrioritizeCoursesCriteria(List<Course> courseList) {
    this.courseList = courseList;
    this.courseScores = new HashMap<ScheduledCourse, Integer>();
  }
  
  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the order of courses that the user would like to take
   */
  public float preferenceScore(Schedule schedule) {
    // Instantiate the HashMap, courseScores
    for (int i = 0; i < courseList.size(); i++) {
      Course currentCourse = courseList.get(i);
      for(Section section: currentCourse.getSections()){
        ScheduledCourse convertedCourse = new ScheduledCourse(currentCourse, section);
        courseScores.put(convertedCourse, courseList.size()-i);
      }
    }

    int coursePrioritySum = 0;
    Collection<ScheduledCourse> courses = schedule.getCourses();
    
    for (ScheduledCourse course : courses) {
      int priorityIndex = courseScores.get(course);
      coursePrioritySum += priorityIndex;
    }

    return coursePrioritySum;
  }

  /**
   * Returns a list of course names, given a list of courses
   */
  public List<String> getCourseListNames(List<ScheduledCourse> courses) {
    List<String> names = new ArrayList<String>();

    for (ScheduledCourse course : courses) {
      names.add(course.getName());
    }

    return names;
  }
}
