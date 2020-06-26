package com.google.sps.data;

public class restrictTimesCriteria implements Preference {

  public restrictTimesCriteria(float minCredits, float maxCredits) {
    this.minCredits = minCredits;
    this.maxCredits = maxCredits;
  }

  public List<TimeRange> userNoClassTimes;

  public float preferenceScore(Schedule schedule) {
    //Todo: Find if schedule has the restricted times that are wanted
    
    // Sorted the user time preferrences chronologically
    Collections.sort(userNoClassTimes, TimeRange.ORDER_BY_START);
    
    List<Course> courses = schedule.courses;
    List<TimeRange> timeOrderedCourses = getCourseTimes(courses);

    for (TimeRange event : userNoClassTimes)
  }


}
