package com.google.sps;

import java.util.Collection;
import java.util.Comparator;

import com.google.sps.data.Schedule;
import com.google.sps.data.TimeRange;

public final class Rank {

  //List instead of Collection

  public Collection<Schedule> sortSchedules(Collection<Schedule> schedules){
    return null;
  }

  /**
   * A comparator for sorting ranges by their start time in ascending order.
   */
  public static final Comparator<Schedule> ORDER_BY_CRITERIA = new Comparator<TimeRange>() {
    @Override
    public int compare(TimeRange a, TimeRange b) {
      return Long.compare(a.start, b.start);
    }
  };
}