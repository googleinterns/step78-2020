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

/**
 * A course class. Better documentation to be added later.
 */
public class Course {
  private String name;
  private String courseID;
  private String subject;
  private float credits;
  private boolean isRequired;
  private List<Section> sections;

  public Course(String name, String courseID, String subject, float credits, boolean isRequired, List<Section> sections) {
    this.name = name;
    this.courseID = courseID;
    this.subject = subject;
    this.credits = credits;
    this.isRequired = isRequired;
    this.sections = sections;
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

  public boolean getIsRequired() {
    return this.isRequired;
  }

  public List<Section> getSections() {
    return this.sections;
  }
}