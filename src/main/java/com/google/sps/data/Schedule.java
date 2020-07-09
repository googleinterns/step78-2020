package com.google.sps.data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class Schedule {
  private Collection<ScheduledCourse> courses;
  private long weight;

  public Schedule(Collection<ScheduledCourse> courses) {
    this.courses = courses;
    this.weight = -1;
  }

  @Override
  public int hashCode() {
    HashSet<Course> courseSet = new HashSet<>(courses);
    return Objects.hash(courseSet, weight);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Schedule)) {
      return false;
    }

    Schedule otherSchedule = (Schedule) other;
    HashSet<ScheduledCourse> otherCourses = new HashSet<>(otherSchedule.courses);
    HashSet<ScheduledCourse> thisCourses = new HashSet<>(this.courses);
    return otherSchedule.weight == this.weight && otherCourses.equals(thisCourses);
  }

  public Collection<ScheduledCourse> getCourses() {
    return this.courses;
  }

  // Todo: Add comparator functions based on weight
  // Todo: Add recalculate weight function/set weight function

}
