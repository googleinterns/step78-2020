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

import java.util.List;
import java.util.Objects;

/**
 * A class representing a course that has been added to a schedule.
 */
public class ScheduledCourse {
  private String name;
  private String courseID;
  private String subject;
  private float credits;
  private boolean isRequired;
  private Section section;

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
      boolean isRequired, Section section) {
    this.name = name;
    this.courseID = courseID;
    this.subject = subject;
    this.credits = credits;
    this.isRequired = isRequired;
    this.section = section;
  }

  public String getName() {
    return this.name;
  }

  public String getCourseID() {
    return this.courseID;
  }

  public String getSubject() {
    return this.subject;
  }

  public float getCredits() {
    return this.credits;
  }

  public boolean isRequired() {
    return this.isRequired;
  }

  public Section getSection() {
    return this.section;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof ScheduledCourse)) {
      return false;
    }

    ScheduledCourse otherCourse = (ScheduledCourse) other;
    return otherCourse.name.equals(this.name) 
        && otherCourse.courseID.equals(this.courseID)
        && otherCourse.subject.equals(this.subject) 
        && Math.abs(otherCourse.credits - this.credits) < 0.01
        && otherCourse.isRequired == this.isRequired 
        && otherCourse.section.equals(this.section);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, courseID, subject, credits, isRequired, section);
  }

}
