package com.google.sps.data;

import java.util.List;
import java.util.Collection;
import java.util.Comparator;

public class Preferences {
    // Criteria sorted in order of user preference
    private List<Preference> criteriaPreferences;

    private int NUM_OF_CRITERIA = 3;

    public List<Preference> Preferences(List<Preference> criteriaPreferences){
      this.criteriaPreferences = criteriaPreferences;
    }

    public void sortSchedules(List<Schedule> schedules) {
      /**
       * A comparator for sorting schedules by the user criteria.
       */
      Comparator<Schedule> ORDER_BY_CRITERIA = new Comparator<TimeRange>() {
        @Override
        public int compare(Schedule a, Schedule b) {
          int index = 0;
          while(index < criteriaPreferences.size()) {
            Preference idxCriteria = criteriaPreferences[index];
            if(idxCriteria.preferenceScore(a) > idxCriteria.preferenceScore(b)) {
              return 1;
            } else if(idxCriteria.preferenceScore(a) < idxCriteria.preferenceScore(b)) {
              return -1;
            } else {
              index++;
            }
          }
          return 0;
        }
      };

      Collections.sort(schedules, ORDER_BY_CRITERIA);
    }
}