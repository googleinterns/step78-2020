package com.google.sps;

import java.util.Arrays;
import java.util.List;

import com.google.sps.data.Section;
import com.google.sps.data.TimeRange;
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
      TimeRange.fromStartDuration(TimeRange.MONDAY, 10, 0, 60);

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
  public void overnightOverlaps() {
    Section s1 = new Section("Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.WEDNESDAY, 23, 30, 60)));
    Section s2 = new Section("Other Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.THURSDAY, 0, 0, 60)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void satSunOvernightOverlaps() {
    Section s1 = new Section("Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.SATURDAY, 23, 30, 60)));
    Section s2 = new Section("Other Prof", Arrays.asList(
        TimeRange.fromStartDuration(TimeRange.SUNDAY, 0, 0, 60)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }

  @Test
  public void satSunOvernightOverlapsMultiple() {
    Section s1 = new Section("Prof", Arrays.asList(
        MONDAY_10AM, 
        TimeRange.fromStartDuration(TimeRange.SATURDAY, 23, 30, 60)));


    //When sorting, the list will become Sunday then Monday.
    Section s2 = new Section("Other Prof", Arrays.asList(
        MONDAY_1PM,
        TimeRange.fromStartDuration(TimeRange.SUNDAY, 0, 0, 60)));

    Assert.assertTrue(s1.overlaps(s2));
    Assert.assertTrue(s2.overlaps(s1));
  }
}
