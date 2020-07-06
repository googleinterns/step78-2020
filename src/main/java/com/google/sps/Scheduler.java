package com.google.sps;

import com.google.sps.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Static class for permuting schedules given a list of courses.
 */
public final class Scheduler {
  /**
   * Generates all valid schedules given a list of courses and invariant
   * constraints.
   * 
   * @param courses    The list of courses to generate the schedules from.
   * @param invariants The invariants that constrain the valid schedules
   * @return A list of valid schedules.
   */
  public List<Schedule> generateSchedules(List<Course> courses, Invariants invariants) {
    ArrayList<Course> orderedCourses = new ArrayList<>(courses);
    Collections.sort(orderedCourses, Course.REQUIRED_TO_FRONT);

    Set<List<Course>> scheduleSet = new HashSet<>();

    generateSchedulesHelper(orderedCourses, new ArrayList<>(), invariants, scheduleSet);

    List<Schedule> schedules = new ArrayList<>();
    for (List<Course> courseList: scheduleSet) {
      schedules.add(new Schedule(courseList));
    }

    return schedules;
  }

  /**
   * Recursively iterates through a list of courses and adds schedules to the
   * given set.
   * 
   * @param courses    The list of courses to iterate through
   * @param schedule   The current schedule to be added/operated on
   * @param invariants The invariants that constrain the valid schedules
   * @param schedules  The set of schedules that is being generated/added to.
   */
  private void generateSchedulesHelper(List<Course> courses, List<Course> schedule, 
      Invariants invariants, Set<List<Course>> schedules) {

    // This means that we have a valid schedule, and should add it to the list.
    float currCredits = totalCredits(schedule);
    if (currCredits >= invariants.getMinCredits() && currCredits <= invariants.getMaxCredits()) {
      schedules.add(schedule);
    }

    // We've reached the end of the list or have a full schedule, we are finished.
    if (courses.isEmpty() || currCredits >= invariants.getMaxCredits()) {
      return;
    }

    Course course = courses.remove(0);
    List<Section> courseSections = course.getSections();
    List<Section> scheduleSections = schedule.stream()
        .map(c -> c.getSections().get(0))
        .collect(Collectors.toList());

    /*
     * Iterate over each section in the current course and check for overlap. If the
     * section doesn't overlap and adding it would result in a valid schedule, add
     * it and make a new recursive call.
     */
    // TODO: Implement logic for required courses having no valid schedules
    for (Section section : courseSections) {
      if (!sectionsOverlap(section, scheduleSections)
          && currCredits + course.getCredits() <= invariants.getMaxCredits()) {
        List<Course> newSched = new ArrayList<>(schedule);
        newSched.add(new Course(course.getName(), course.getCourseID(), course.getSubject(),
            course.getCredits(), course.isRequired(), Arrays.asList(section)));
        generateSchedulesHelper(new ArrayList<>(courses), newSched, invariants, schedules);
      }
    }

    //If a course is not required, continue permuting with it *not* added to the schedule
    if (!course.isRequired()) {
      generateSchedulesHelper(new ArrayList<>(courses), schedule, invariants, schedules);
    }
  }

  /**
   * Returns the total number of credits in a given list of courses.
   */
  private float totalCredits(List<Course> schedule) {
    float total = 0;
    for (Course course : schedule) {
      total += course.getCredits();
    }
    return total;
  }

  /**
   * Checks if a section overlaps with any of the sections in another list.
   */
  private boolean sectionsOverlap(Section toCheck, List<Section> sections) {
    for (Section section : sections) {
      if (toCheck.overlaps(section)) {
        return true;
      }
    }
    return false;
  }
}
