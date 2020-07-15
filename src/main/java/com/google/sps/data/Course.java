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
 * A course class. Better documentation to be added later.
 */
public class Course {
  private String name;
  private String courseID;
  private String subject;
  private float credits;
  private boolean isRequired;
  private List<Section> lectureSections;
  private List<Section> labSections;

  public Course(String name, String courseID, String subject, float credits, boolean isRequired, 
      List<Section> lectureSections, List<Section> labSections) {
    this.name = name;
    this.courseID = courseID;
    this.subject = subject;
    this.credits = credits;
    this.isRequired = isRequired;
    this.lectureSections = lectureSections;
    this.labSections = labSections;
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

  public List<Section> getlectureSections() {
    return this.lectureSections;
  }

  public List<Section> getLabSections() {
    return this.labSections;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Course)) {
      return false;
    }

    Course otherCourse = (Course) other;
    return otherCourse.name.equals(this.name) 
        && otherCourse.courseID.equals(this.courseID)
        && otherCourse.subject.equals(this.subject) 
        && Math.abs(otherCourse.credits - this.credits) < 0.01
        && otherCourse.isRequired == this.isRequired 
        && otherCourse.lectureSections.equals(this.lectureSections)
        && otherCourse.labSections.equals(this.labSections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, courseID, subject, credits, isRequired, lectureSections, labSections);
  }

}
