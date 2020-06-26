package com.google.sps.data;

public class numOfCoursesInSubjectCriteria implements Preference {

  public numOfCoursesInSubjectCriteria(float minCredits, float maxCredits) {
    this.minCredits = minCredits;
    this.maxCredits = maxCredits;
  }

  public String preferredSubject;

  public float preferenceScore(Schedule schedule) {
    int preferredSubjectCount = 0;
    List<Course> courses = schedule.courses;
    for (Course course : courses) {
      if (preferredSubject.equals(course.getSubject())) {
        preferredSubjectCount++;
      }
    }
    return preferredSubjectCount;
  }

}
