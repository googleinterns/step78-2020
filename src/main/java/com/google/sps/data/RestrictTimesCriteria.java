package com.google.sps.data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class RestrictTimesCriteria implements Criterion {
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
    
    Collection<ScheduledCourse> courses = schedule.getCourses();
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
  List<TimeRange> getCourseTimes(Collection<ScheduledCourse> courses) {
    List<TimeRange> times = new ArrayList<TimeRange>();

    for(ScheduledCourse course : courses) {
      times.addAll(course.getLectureSection().getMeetingTimes());
      if(course.getLabSection() != null){
        times.addAll(course.getLabSection().getMeetingTimes());      
      }
    }

    return times;
  }
}
