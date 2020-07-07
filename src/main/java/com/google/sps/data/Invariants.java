package com.google.sps.data;

public class Invariants {
  private float minCredits;
  private float maxCredits;

  public Invariants(float minCredits, float maxCredits) {
    this.minCredits = minCredits;
    this.maxCredits = maxCredits;
  }

  public float getMinCredits() {
    return this.minCredits;
  }

  public float getMaxCredits() {
    return this.maxCredits;
  }
}
