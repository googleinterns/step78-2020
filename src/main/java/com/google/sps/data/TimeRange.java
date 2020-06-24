// Copyright 2019 Google LLC
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

import java.util.Comparator;

/**
 * Class representing a span of time, enforcing properties (e.g. start comes before end) and
 * providing methods to make ranges easier to work with (e.g. {@code overlaps}).
 */
public final class TimeRange {
  public static final int SUNDAY = 0;
  public static final int MONDAY = 1;
  public static final int TUESDAY = 2;
  public static final int WEDNESDAY = 3;
  public static final int THURSDAY = 4;
  public static final int FRIDAY = 5;
  public static final int SATURDAY = 6;

  public static final int START_OF_SUNDAY = convertTimeToMinutes(SUNDAY, 0, 0);
  public static final int END_OF_SUNDAY = convertTimeToMinutes(SUNDAY, 23, 59);

  public static final int START_OF_MONDAY = convertTimeToMinutes(MONDAY, 0, 0);
  public static final int END_OF_MONDAY = convertTimeToMinutes(MONDAY, 23, 59);

  public static final int START_OF_TUESDAY = convertTimeToMinutes(TUESDAY, 0, 0);
  public static final int END_OF_TUESDAY = convertTimeToMinutes(TUESDAY, 23, 59);

  public static final int START_OF_WEDNESDAY = convertTimeToMinutes(WEDNESDAY, 0, 0);
  public static final int END_OF_WEDNESDAY = convertTimeToMinutes(WEDNESDAY, 23, 59);

  public static final int START_OF_THURSDAY = convertTimeToMinutes(THURSDAY, 0, 0);
  public static final int END_OF_THURSDAY = convertTimeToMinutes(THURSDAY, 23, 59);

  public static final int START_OF_FRIDAY = convertTimeToMinutes(FRIDAY, 0, 0);
  public static final int END_OF_FRIDAY = convertTimeToMinutes(FRIDAY, 23, 59);

  public static final int START_OF_SATURDAY = convertTimeToMinutes(SATURDAY, 0, 0);
  public static final int END_OF_SATURDAY = convertTimeToMinutes(SATURDAY, 23, 59);

  public static final int START_OF_WEEK = convertTimeToMinutes(0, 0);
  public static final int END_OF_WEEK = convertTimeToMinutes(6, 23, 59);

  public static final int WHOLE_DAY_DURATION = 24 * 60;
  public static final TimeRange WHOLE_WEEK = new TimeRange(0, 24 * 7 * 60);

  /**
   * A comparator for sorting ranges by their start time in ascending order.
   */
  public static final Comparator<TimeRange> ORDER_BY_START = new Comparator<TimeRange>() {
    @Override
    public int compare(TimeRange a, TimeRange b) {
      return Long.compare(a.start, b.start);
    }
  };

  /**
   * A comparator for sorting ranges by their end time in ascending order.
   */
  public static final Comparator<TimeRange> ORDER_BY_END = new Comparator<TimeRange>() {
    @Override
    public int compare(TimeRange a, TimeRange b) {
      return Long.compare(a.end(), b.end());
    }
  };

  private final int start;
  private final int duration;

  private TimeRange(int start, int duration) {
    this.start = start;
    this.duration = duration;
  }

  /**
   * Returns the start of the range in minutes.
   */
  public int start() {
    return start;
  }

  /**
   * Returns the number of minutes between the start and end.
   */
  public int duration() {
    return duration;
  }

  /**
   * Returns the end of the range. This ending value is the closing exclusive bound.
   */
  public int end() {
    return start + duration;
  }

  /**
   * Checks if two ranges overlap. This means that at least some part of one range falls within the
   * bounds of another range.
   */
  public boolean overlaps(TimeRange other) {
    // For two ranges to overlap, one range must contain the start of another range.
    //
    // Case 1: |---| |---|
    //
    // Case 2: |---|
    //            |---|
    //
    // Case 3: |---------|
    //            |---|
    return this.contains(other.start) || other.contains(this.start);
  }

  /**
   * Checks if this range completely contains another range. This means that {@code other} is a
   * subset of this range. This is an inclusive bounds, meaning that if two ranges are the same,
   * they contain each other.
   */
  public boolean contains(TimeRange other) {
    // If this range has no duration, it cannot contain anything.
    if (duration <= 0) {
      return false;
    }

    // If the other range has no duration, then we must treat it like a point in time rather than a
    // range.
    if (other.duration <= 0) {
      return contains(this, other.start);
    }

    // We need the inclusive end for this check in order for this case to equal true:
    // |------|
    //     |--|
    int otherInclusiveEnd = other.start + other.duration - 1;
    return contains(this, other.start) && contains(this, otherInclusiveEnd);
  }

  public boolean contains(int point) {
    return contains(this, point);
  }

  @Override
  public boolean equals(Object other) {
    return other instanceof TimeRange && equals(this, (TimeRange) other);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(start) ^ Long.hashCode(duration);
  }

  @Override
  public String toString() {
    return String.format("Range: [%d, %d)", start, start + duration);
  }

  private static boolean contains(TimeRange range, int point) {
    // If a range has no duration, it cannot contain anything.
    if (range.duration <= 0) {
      return false;
    }

    // If the point comes before the start of the range, the range cannot contain it.
    if (point < range.start) {
      return false;
    }

    // If the point is on the end of the range. We don't count it as included in the range. For
    // example, if we have a range that starts at 8:00 and is 30 minutes long, it would end at 8:30.
    // But that range should on contain 8:30 because it would end just before 8:30 began.
    return point < range.start + range.duration;
  }

  private static boolean equals(TimeRange a, TimeRange b) {
    return a.start == b.start && a.duration == b.duration;
  }

  /**
   * Returns the time in minutes given a day, for use when creating new timeRanges.
   * @param hours an int representing the time (in hours) during the day
   * @param minutes an int representing the time (in minutes) during the hour
   * @return time in minutes during a week
   */
  public static int convertTimeToMinutes(int hours, int minutes) {
    if (hours < 0 || hours >= 24) {
      throw new IllegalArgumentException("Hours can only be 0 through 23 (inclusive).");
    }

    if (minutes < 0 || minutes >= 60) {
      throw new IllegalArgumentException("Minutes can only be 0 through 59 (inclusive).");
    }

    return (hours * 60) + minutes;
  }

  /**
   * Returns the time in minutes given a day, for use when creating new timeRanges.
   * @param day an int representing the day (0 being sunday, 6 being saturday)
   * @param hours an int representing the time (in hours) during the day
   * @param minutes an int representing the time (in minutes) during the hour
   * @return time in minutes during a week
   */
  public static int convertTimeToMinutes(int day, int hours, int minutes) {
    if (day < 0 || day >= 7) {
      throw new IllegalArgumentException("Day of the week must be between 0 and 6 (inclusive).");
    }

    if (hours < 0 || hours >= 24) {
      throw new IllegalArgumentException("Hours can only be 0 through 23 (inclusive).");
    }

    if (minutes < 0 || minutes >= 60) {
      throw new IllegalArgumentException("Minutes can only be 0 through 59 (inclusive).");
    }

    return (day * 24 * 60) + (hours * 60) + minutes;
  }

    /**
   * Returns the day of the week in int form (0 being sunday, 6 being saturday).
   * @param hours The hours in the timeframe
   * @return int representing the day of the week
   */
  public static int hoursToDayOfWeek (int hours) {
    if (hours < 0 || hours > 24 * 7) {
      throw new IllegalArgumentException("Time must be within a week period (between 0 and 167, inclusive).");
    }

    return (int) Math.floorDiv(hours, 24);
  }

  /**
   * Returns the day of the week in int form (0 being sunday, 6 being saturday)
   * @param minutes
   * @return int representing the day of the week
   */
  public static int minutesToDayOfWeek (int minutes) {
    if (minutes < 0 || minutes > 24 * 7 * 60) {
      throw new IllegalArgumentException("Time must be within a week period (between 0 and 10079, inclusive).");
    }

    return (int) Math.floorDiv(minutes, 24 * 60);
  }

  /**
   * Creates a {@code TimeRange} from {@code start} (in minutes) to {@code end}. Whether or not {@code end} is
   * included in the range will depend on {@code inclusive}. If {@code inclusive} is {@code true},
   * then @{code end} will be in the range.
   */
  public static TimeRange fromStartEnd(int start, int end, boolean inclusive) {
    return inclusive ? new TimeRange(start, end - start + 1) : new TimeRange(start, end - start);
  }

  /**
   * Create a {@code TimeRange} starting at {@code start} (in minutes) with a duration equal to {@code duration}.
   */
  public static TimeRange fromStartDuration(int start, int duration) {
    return new TimeRange(start, duration);
  }
}
