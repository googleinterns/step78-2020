package com.google.sps.data;

import java.util.List;
import java.util.ArrayList;
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
    List<TimeRange> timePreferences = new ArrayList<>();
    timePreferences.add(this.criterion.timePreferences.timeBefore);
    timePreferences.add(this.criterion.timePreferences.timeAfter);
    return timePreferences;
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
  TimePreferences timePreferences;
  String preferredSubject;
  HashMap<String, Integer> courseScores;
}

class BasicInfo {
  Invariants credits;
  TermDates termDates;
}

class TermDates {
  String startDate;
  String endDate;
}

class TimePreferences {
  TimeRange timeBefore;
  TimeRange timeAfter;
}
