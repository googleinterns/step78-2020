package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class RestrictTimesCriteria implements Preference {
  public List<TimeRange> userNoClassTimes;
  
  public RestrictTimesCriteria(List<TimeRange> userNoClassTimes) {
    this.userNoClassTimes = userNoClassTimes;
  }

  /**
   * Implements the preference score of the schedule, based on the criteria
   * that prioritizes the TimeRanges that a user does not want class during
   */
  public float preferenceScore(Schedule schedule) {
    // count the number of minutes that overlap
    float minutesPenalty = 0;
    
    Collection<Course> courses = schedule.getCourses();
    List<TimeRange> courseTimes = getCourseTimes(courses);

    for (TimeRange event : userNoClassTimes) {
      for (TimeRange course : courseTimes) {
        if (event.overlaps(course)) {
          float timeDiff = (float) event.calculateMinutesOverlap(course);
          minutesPenalty -= timeDiff;
        }
      }
    }

    return minutesPenalty;
  }

  /**
   * Returns a list of TimeRanges from a collection of courses
   */
  List<TimeRange> getCourseTimes(Collection<Course> courses) {
    List<TimeRange> times = new ArrayList<TimeRange>();

    for(Course course : courses) {
      times.addAll(course.getSections().get(0).getMeetingTimes());
    }

    return times;
  }
}