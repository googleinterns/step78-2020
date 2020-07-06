package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class RestrictTimesCriteria implements Preference {

  public RestrictTimesCriteria(List<TimeRange> userNoClassTimes) {
    this.userNoClassTimes = userNoClassTimes;
  }

  public List<TimeRange> userNoClassTimes;

  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the TimeRanges that a user does not want class during
   */
  public float preferenceScore(Schedule schedule) {
    // count the number of minutes that overlap
    float minutesPenalty = 0;
    
    List<Course> courses = (List) schedule.getCourses();
    List<TimeRange> timeOrderedCourses = getCourseTimes(courses);

    for (TimeRange event : userNoClassTimes) {
      for (TimeRange course : timeOrderedCourses) {
        if (event.overlaps(course)) {
          float timeDiff = (float) calculateDifference(event, course);
          minutesPenalty -= timeDiff;
        }
      }
    }
    return minutesPenalty;
  }

  // Calculates the overlapping minutes between two TimeRanges
  int calculateDifference(TimeRange a, TimeRange b) {
    int start = Math.max(a.start(), b.start());
    int end = Math.min(a.end(), b.end());
    return end - start;
  }

  // Returns a List of TimeRanges from a list of courses
  List<TimeRange> getCourseTimes(List<Course> courses) {
    List<TimeRange> times = new ArrayList<TimeRange>();
    for(Course course : courses) {
      times.addAll(course.getSections().get(0).getMeetingTimes());
    }
    return times;
  }
}
