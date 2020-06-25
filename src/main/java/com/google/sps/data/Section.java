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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Section {
  private String professor;
  // Meeting times will be sorted in ascending order of time,
  // with the first lesson being first in the list
  private List<TimeRange> meetingTimes;

  public Section(String professor, List<TimeRange> meetingTimes) {
    this.professor = professor;
    this.meetingTimes = meetingTimes;
    Collections.sort(this.meetingTimes, TimeRange.ORDER_BY_START);

    for (int i = 0; i < meetingTimes.size() - 1; i++) {
      if (meetingTimes.get(i).overlaps(meetingTimes.get(i + 1))) {
        throw new IllegalArgumentException("A section's lecture times can't overlap!");
      }
    }
  }

  public List<TimeRange> getMeetingTimes() {
    return this.meetingTimes;
  }

  /**
   * Function to return whether or not a section overlaps with another.
   * 
   * @param other The other section.
   * @return If there's overlap between the current section and the other section
   */
  public boolean overlaps(Section other) {
    LinkedList<TimeRange> thisTimes = new LinkedList<>(this.meetingTimes);
    LinkedList<TimeRange> otherTimes = new LinkedList<>(other.getMeetingTimes());

    TimeRange thisCurr = thisTimes.removeFirst();
    TimeRange otherCurr = otherTimes.removeFirst();

    while (!thisTimes.isEmpty() && !otherTimes.isEmpty()) {
      if (thisCurr.overlaps(otherCurr)) {
        return false;
      } else if (thisCurr.end() > otherCurr.end()) {
        thisCurr = thisTimes.removeFirst();
      } else {
        otherCurr = otherTimes.removeFirst();
      }
    }

    // This bit of redundant logic is to account for the single element list case.
    if (thisCurr.overlaps(otherCurr)) {
      return true;
    }
    return false;
  }
}