package com.google.sps;

import com.google.sps.data.Section;
import com.google.sps.data.TimeRange;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class SectionTest {

  private static final TimeRange MONDAY_10AM = 
      TimeRange.fromStartDuration(TimeRange.MONDAY, 10, 0, 60);
  private static final TimeRange MONDAY_1030AM = 
      TimeRange.fromStartDuration(TimeRange.MONDAY, 10, 0, 60);
  private static final TimeRange MONDAY_1PM = 
      TimeRange.fromStartDuration(TimeRange.MONDAY, 13, 0, 60);
  private static final TimeRange TUESDAY_10AM = 
      TimeRange.fromStartDuration(TimeRange.TUESDAY, 10, 0, 60);

  private static final int DURATION_30_MIN = 30;
  private static final int DURATION_60_MIN = 60;

  @Test
  public void singleTimeOverlap() {
    Section s1 = new Section("Prof", Arrays.asList(MONDAY_10AM));
    Section s2 = new Section("Other Prof", Arrays.asList(MONDAY_1030AM));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void sameTimeDiffDayNoOverlap() {
    Section s1 = new Section("Prof", Arrays.asList(MONDAY_10AM));
    Section s2 = new Section("Other Prof", Arrays.asList(TUESDAY_10AM));

    Assert.assertFalse(s1.overlaps(s2));
  }

  @Test
  public void multipleElementsLastOverlaps() {
    List<TimeRange> s1Times = Arrays.asList(MONDAY_10AM, TUESDAY_10AM);
    List<TimeRange> s2Times = Arrays.asList(MONDAY_1PM, TUESDAY_10AM);

    Section s1 = new Section("Prof", s1Times);
    Section s2 = new Section("Other Prof", s2Times);

    Assert.assertTrue(s1.overlaps(s2));
  }

  @Test
  public void multipleElementsMidOverlaps() {
    List<TimeRange> s1Times = Arrays.asList(MONDAY_10AM, MONDAY_1PM, TUESDAY_10AM);
    List<TimeRange> s2Times = Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.MONDAY, 11, 30, DURATION_30_MIN),
        TimeRange.fromStartDuration(TimeRange.MONDAY, 13, 30, DURATION_60_MIN),
        TimeRange.fromStartDuration(TimeRange.SATURDAY, 15, 30, DURATION_60_MIN));

    Section s1 = new Section("Prof", s1Times);
    Section s2 = new Section("Other Prof", s2Times);
    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void overnightOverlaps() {
    Section s1 = new Section("Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 23, 30, DURATION_60_MIN)));
    Section s2 = new Section("Other Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.THURSDAY, 0, 0, DURATION_60_MIN)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void satSunOvernightOverlaps() {
    Section s1 = new Section("Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.SATURDAY, 23, 30, DURATION_60_MIN)));
    Section s2 = new Section("Other Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.SUNDAY, 0, 0, DURATION_60_MIN)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void satSunOvernightOverlapsMultiple() {
    Section s1 = new Section("Prof", Arrays.asList(
        MONDAY_10AM, 
        TimeRange.fromStartDuration(TimeRange.SATURDAY, 23, 30, DURATION_60_MIN)));


    //When sorting, the list will become Sunday then Monday.
    Section s2 = new Section("Other Prof", Arrays.asList(
        MONDAY_1PM,
        TimeRange.fromStartDuration(TimeRange.SUNDAY, 0, 0, DURATION_60_MIN)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void oneEmptyList() {
    Section nonEmpty = new Section("Prof", Arrays.asList(MONDAY_10AM));
    Section empty = new Section("prof", Collections.emptyList());
    Assert.assertFalse(nonEmpty.overlaps(empty));
    Assert.assertFalse(empty.overlaps(nonEmpty));
  }

  @Test
  public void bothEmpty() {
    Section s1 = new Section("prof", Collections.emptyList());
    Section s2 = new Section("prof", Collections.emptyList());
    Assert.assertFalse(s1.overlaps(s2));
    Assert.assertFalse(s2.overlaps(s1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void overlappingRangesNotAllowed() {
    List<TimeRange> overlaps = Arrays.asList(MONDAY_10AM, MONDAY_1030AM);
    Section shouldFail = new Section ("Prof", overlaps);
  }
}
