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
import java.util.Objects;

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

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Section)) {
      return false;
    }

    Section otherSection = (Section) other;

    return otherSection.professor.equals(this.professor) 
      && otherSection.meetingTimes.equals(this.meetingTimes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(professor, meetingTimes);
  }

  public List<TimeRange> getMeetingTimes() {
    return this.meetingTimes;
  }

  /**
   * Function to return whether or not a section overlaps with another.
   * 
   * @param other the section to check for overlap with.
   * @return If there's overlap between the current section and the other section
   */
  public boolean overlaps(Section other) {
    LinkedList<TimeRange> thisTimes = new LinkedList<>(this.meetingTimes);
    LinkedList<TimeRange> otherTimes = new LinkedList<>(other.getMeetingTimes());

    TimeRange thisCurr = thisTimes.removeFirst();
    TimeRange otherCurr = otherTimes.removeFirst();
    TimeRange thisFirst = thisCurr;
    TimeRange otherFirst = otherCurr;

    while (!thisTimes.isEmpty() || !otherTimes.isEmpty()) {
      if (thisCurr.overlaps(otherCurr)) {
        return true;
      } else if (thisCurr.end() < otherCurr.end()) {
        if (!thisTimes.isEmpty()) {
          thisCurr = thisTimes.removeFirst();
        } else {
          otherCurr = otherTimes.removeFirst();
        }
      } else {
        if (!otherTimes.isEmpty()) {
          otherCurr = otherTimes.removeFirst();
        } else {
          thisCurr = thisTimes.removeFirst();
        }
      }
    }

    return (thisCurr.overlaps(otherCurr))
      || (thisCurr.end() > TimeRange.END_OF_WEEK && saturdayOverlapsSunday(thisCurr, otherFirst)) 
      || (otherCurr.end() > TimeRange.END_OF_WEEK && saturdayOverlapsSunday(otherCurr, thisFirst));
  }

  /**
   * A helper function that checks if a TimeRange that starts on Saturday but ends on Sunday 
   * overlaps with a TimeRange earlier in the week.
   * 
   * @param satEvent the event that starts on Sat but ends on Sunday
   * @param earliestEvent the event earlier in the week.
   * 
   * @return a boolean representing if the two events overlap.
   */
  private boolean saturdayOverlapsSunday(TimeRange satEvent, TimeRange earliestEvent) {
    return earliestEvent.contains(satEvent.end() - TimeRange.END_OF_WEEK);
  }
}
