package com.google.sps.data;

import java.util.Collection;
import java.util.Objects;

public class Schedule {
    private Collection<Course> courses;
    private long weight;

    public Schedule(Collection<Course> courses){
        this.courses = courses;
        this.weight = -1;
    }

  @Override
  public int hashCode() {
    return Objects.hash(courses, weight);
  }

    //Todo: Add comparator functions based on weight
    //Todo: Add recalculate weight function/set weight function
}