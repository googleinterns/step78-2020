package com.google.sps.data;

import java.util.List;
import java.util.HashMap;

public class GenerateGoogleCalendarRequest {
  Schedule schedule;
  TermDates termDates;

  public GenerateGoogleCalendarRequest(){};
  
  public Schedule getSchedule() {
    return this.schedule;
  }

  public String getStartDate() {
    return this.termDates.startDate;
  }

  public String getEndDate() {
    return this.termDates.endDate;
  }
}

class TermDates {
  String startDate;
  String endDate;
}
