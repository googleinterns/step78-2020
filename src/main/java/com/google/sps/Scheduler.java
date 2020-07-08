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
    List<Course> requiredCourses = new ArrayList<>();
    List<Course> nonRequiredCourses = new ArrayList<>();

    for (Course course: courses) {
      if (course.isRequired()) {
        requiredCourses.add(course);
      } else {
        nonRequiredCourses.add(course);
      }
    }

    // Generate all possible valid Schedules that include only required courses.
    float reqCredits = totalCredits(requiredCourses);
    Set<List<Course>> requiredSet = new HashSet<>();

    generateSchedulesHelper(requiredCourses, new ArrayList<>(), 
        new Invariants(reqCredits, reqCredits), requiredSet);
    
    // Using those Schedules as a building block, generate all valid schedules
    Set<List<Course>> scheduleSet = new HashSet<>();
    for (List<Course> requiredCourseList : requiredSet) {
      generateSchedulesHelper(nonRequiredCourses, requiredCourseList, invariants, scheduleSet);
    }

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
   * @param courses     The list of courses to iterate through
   * @param courseList  The current course list to be added/operated on
   * @param invariants  The invariants that constrain the valid schedules
   * @param courseLists The set of course lists that is being generated/added to.
   */
  private void generateSchedulesHelper(List<Course> availableCourses, List<Course> currentCourseList,
      Invariants invariants, Set<List<Course>> generatedCourseLists) {

    // This means that we have a valid schedule, and should add it to the list.
    float currCredits = totalCredits(currentCourseList);
    if (invariants.meetsCreditRequirement(currCredits)) {
      generatedCourseLists.add(currentCourseList);
    }

    // We've reached the end of the list or have a full schedule, we are finished.
    if (availableCourses.isEmpty() || currCredits > invariants.getMaxCredits()) {
      return;
    }

    Course course = availableCourses.remove(0);
    List<Section> courseSections = course.getSections();
    List<Section> scheduleSections = currentCourseList.stream()
        .map(c -> c.getSections().get(0))
        .collect(Collectors.toList());

    /*
     * Iterate over each section in the current course and check for overlap. If the
     * section doesn't overlap and adding it would result in a valid schedule, add
     * it and make a new recursive call.
     */
    // TODO: Implement logic for required courses having no valid schedules
    for (Section section : courseSections) {
      boolean doesNotOverlapSchedule = !sectionsOverlap(section, scheduleSections);
      boolean doesNotExceedCreditLimit = 
          currCredits + course.getCredits() <= invariants.getMaxCredits();
      
      if (doesNotOverlapSchedule && doesNotExceedCreditLimit) {
        List<Course> newCourseList = new ArrayList<>(currentCourseList);
        newCourseList.add(new Course(course.getName(), course.getCourseID(), course.getSubject(),
            course.getCredits(), course.isRequired(), Arrays.asList(section)));
        generateSchedulesHelper(
            new ArrayList<>(availableCourses), newCourseList, invariants, generatedCourseLists);
      }
    }

    //If a course is not required, continue permuting with it *not* added to the schedule
    if (!course.isRequired()) {
      generateSchedulesHelper(
          new ArrayList<>(availableCourses), currentCourseList, invariants, generatedCourseLists);
    }
  }

  /**
   * Returns the total number of credits in a given list of courses.
   */
  private float totalCredits(List<Course> courseList) {
    float total = 0;
    for (Course course : courseList) {
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
