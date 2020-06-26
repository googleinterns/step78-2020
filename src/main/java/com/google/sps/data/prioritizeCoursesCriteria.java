package com.google.sps.data;

public class prioritizeCoursesCriteria implements Preference {

  public prioritizeCoursesCriteria(float minCredits, float maxCredits) {
    this.minCredits = minCredits;
    this.maxCredits = maxCredits;
  }

   public float preferenceScore(Schedule schedule) {
    return null;
  }

}
