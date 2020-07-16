// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * A class representing a course that has been added to a schedule.
 */
public class ScheduledCourse extends Course {

  private Section lectureSection;
  private Section labSection;

  /**
   * Constructor for the Scheduled Course object.
   * @param name The course name.
   * @param courseID The course ID.
   * @param subject The course subject.
   * @param credits The number of credits the course is worth.
   * @param isRequired Whether or not the course is required.
   * @param section The course's section (professor and times).
   */
  public ScheduledCourse(String name, String courseID, String subject, float credits, 
      boolean isRequired, Section lectureSection, Section labSection) {
    super(name, courseID, subject, credits, isRequired, Collections.emptyList(), Collections.emptyList());
    this.lectureSection = lectureSection;
    this.labSection = labSection;
  }

    /**
   * Constructor for the Scheduled Course object.
   * @param name The course name.
   * @param courseID The course ID.
   * @param subject The course subject.
   * @param credits The number of credits the course is worth.
   * @param isRequired Whether or not the course is required.
   * @param section The course's section (professor and times).
   */
  public ScheduledCourse(String name, String courseID, String subject, float credits, 
      boolean isRequired, Section lectureSection) {
    super(name, courseID, subject, credits, isRequired, Collections.emptyList(), Collections.emptyList());
    this.lectureSection = lectureSection;
    this.labSection = null;
  }

    /**
   * Constructor for the Scheduled course object.
   * @param course The course to add to a schedule
   * @param lectureSection The course's lecture Section (professor and times)
   * @param labSection The course's lab Section
   */
  public ScheduledCourse(Course course, Section lectureSection, Section labSection) {
    super(course.getName(), course.getCourseID(), course.getSubject(), course.getCredits(), 
        course.isRequired(), Collections.emptyList(), Collections.emptyList());
    this.lectureSection = lectureSection;
    this.labSection = labSection;
  }

  /**
   * Constructor for the Scheduled course object.
   * @param course The course to add to a schedule
   * @param section The course's section (professor and times)
   */
  public ScheduledCourse(Course course, Section lectureSection) {
    super(course.getName(), course.getCourseID(), course.getSubject(), course.getCredits(), 
        course.isRequired(), Collections.emptyList(), Collections.emptyList());
    this.lectureSection = lectureSection;
    this.labSection = null;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ScheduledCourse) {
      ScheduledCourse otherCourse = (ScheduledCourse) other;
      boolean labsEqual = false;
      if(this.labSection != null && otherCourse.labSection != null) {
        labsEqual = otherCourse.labSection.equals(this.labSection);
      } else if (this.labSection == null && otherCourse.labSection == null) {
        labsEqual = true;
      }
      return super.equals(other) 
        && this.lectureSection.equals(otherCourse.lectureSection)
        && labsEqual;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * Objects.hash(lectureSection, labSection);
  }

  public Section getLectureSection() {
    return this.lectureSection;
  }

  public Section getLabSection() {
    return this.labSection;
  }

}
