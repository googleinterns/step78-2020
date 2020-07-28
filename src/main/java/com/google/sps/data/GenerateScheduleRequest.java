package com.google.sps.data;

import java.util.List;

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

  public Invariants getCredits() {
    return this.basicInfo.credits;
  }

  public String getTermStartDate() {
    return this.basicInfo.termDates.startDate;
  }

  public String getTermEndDate() {
    return this.basicInfo.termDates.endDate;
  }
}

class InputCriterion {
  List<TimeRange> timePreferences;
  String preferredSubject;
}

class BasicInfo {
  Invariants credits;
  TermDates termDates;
}

class TermDates {
  String startDate;
  String endDate;
}
