package com.google.sps.data;

import java.util.Collection;

public class Schedule {
    private Collection<Course> courses;
    private long weight;

    public Schedule(Collection<Course> courses){
        this.courses = courses;
        this.weight = -1;
    }

    //Todo: Add comparator functions based on weight
    //Todo: Add recalculate weight function/set weight function
}