package com.google.sps.data;

import java.util.List;
import java.util.HashMap;

public class GenerateScheduleRequest {
  List<Course> courses;
  InputCriterion criterion;
  BasicInfo basicInfo;

  public GenerateScheduleRequest(){};
  
  public List<Course> getCourses() {
    return this.courses;
  }

  public List<TimeRange> getTimePreferences() {
    return this.criterion.timePreferences;
  }

  public String getPreferredSubject() {
    return this.criterion.preferredSubject;
  }

  public HashMap<String, Integer> getCourseScores() {
    return this.criterion.courseScores;
  }

  public Invariants getCredits() {
    return this.basicInfo.credits;
  }

}

class InputCriterion {
  List<TimeRange> timePreferences;
  String preferredSubject;
  HashMap<String, Integer> courseScores;
}

class BasicInfo {
  Invariants credits;
  TermDates termDates;
}
