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
import java.util.Objects;

/**
 * A class representing a course that has been added to a schedule.
 */
public class ScheduledCourse extends Course {

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
    super(name, courseID, subject, credits, isRequired, Arrays.asList(section));
    this.section = section;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ScheduledCourse) {
      return super.equals(other) && this.section.equals(((ScheduledCourse) other).getSection());
    }

    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * Objects.hashCode(section);
  }

  public Section getSection() {
    return this.section;
  }

}
