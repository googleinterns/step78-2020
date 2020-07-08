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

package com.google.sps;

import com.google.sps.data.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class TimeRangeTest {

  private static final int DURATION_0_MINUTES = 0;
  private static final int DURATION_1_HOUR = 60;
  private static final int DURATION_2_HOUR = 120;

  private static final TimeRange timeRangeA = TimeRange.fromStartDuration(TimeRange.MONDAY, 23, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeB = TimeRange.fromStartDuration(TimeRange.TUESDAY, 0, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeC = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 10, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeD = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 12, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeE = TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 13, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeF = TimeRange.fromStartDuration(TimeRange.SUNDAY, 23, 00, DURATION_2_HOUR);
  private static final TimeRange timeRangeG = TimeRange.fromStartDuration(TimeRange.MONDAY, 0, 00, DURATION_2_HOUR);

  @Test
  public void noOverlapSameDayTest() {
    int overlap = timeRangeC.calculateMinutesOverlap(timeRangeD);
    int actual = overlap;
    int expected = DURATION_0_MINUTES;
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void noOverlapDiffDaysTest() {
    int overlap = timeRangeB.calculateMinutesOverlap(timeRangeC);
    int actual = overlap;
    int expected = DURATION_0_MINUTES;
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void normalOverlapTest() {
    int overlap = timeRangeD.calculateMinutesOverlap(timeRangeE);
    int actual = overlap;
    int expected = DURATION_1_HOUR;
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void overnightOverlapTest() {
    int overlap = timeRangeA.calculateMinutesOverlap(timeRangeB);
    int actual = overlap;
    int expected = DURATION_1_HOUR;
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void sunToMonOverlapTest() {
    int overlap = timeRangeF.calculateMinutesOverlap(timeRangeG);
    int actual = overlap;
    int expected = DURATION_1_HOUR;
    Assert.assertEquals(expected, actual);
  }
}
