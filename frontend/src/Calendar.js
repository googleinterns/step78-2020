import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

import moment from 'moment'
import timeGridPlugin from '@fullcalendar/timegrid'

import {fetchUserCalendar} from './script'

// the conversion of one minute to 60000 milliseconds
const MIN_TO_MS = 60000;

export default class Calendar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      schedulesTimes: [],
      selectedScheduleId: 0     // Current schedule
    }
  }

  componentDidMount(){
    this.getAllSchedules()
  }

  async getAllSchedules() {
    const schedules = await fetchUserCalendar()
    for(const schedule of schedules) {
      this.generateScheduleMeetingTimes(schedule)
    }
  }

  selectNextSchedule() {
    if(this.state.selectedScheduleId < this.state.schedulesTimes.length - 1) {
      this.setState({selectedScheduleId : this.state.selectedScheduleId + 1})
    }
  }

  selectPreviousSchedule() {
    if(this.state.selectedScheduleId > 0) {
      this.setState({selectedScheduleId : this.state.selectedScheduleId - 1})
    }
  }

  generateScheduleMeetingTimes(schedule) {
    let combinedMeetingTimes = []

    for (const course of schedule.courses) {
      // Lecture Sections
      const lectureTime = this.generateCourseMeetingTimes(course.name, course.lectureSection.meetingTimes)
      // Lab Sections
      if (course.labSection) {
        const labTime = this.generateCourseMeetingTimes(course.name, course.labSection.meetingTimes)
        combinedMeetingTimes = combinedMeetingTimes.concat(labTime)
      }
      combinedMeetingTimes = combinedMeetingTimes.concat(lectureTime)
    }

    this.setState({
      //schedulesTimes:  [...this.state.schedulesTimes, combinedMeetingTimes]
      schedulesTimes: this.state.schedulesTimes.concat(combinedMeetingTimes)
    })
  }
  
  generateCourseMeetingTimes(courseName, meetingTimes) {
    const lastSunday = this.getLastSunday(Date.now())
    const courseMeetingTimes = []

    for (const meetingTime of meetingTimes) {
      const startTime = new Date(lastSunday.getTime() + meetingTime.start*MIN_TO_MS)
      const endTime = new Date(startTime.getTime() + meetingTime.duration*MIN_TO_MS)

      courseMeetingTimes.push({
        title: courseName,
        start: moment(startTime).format("YYYY-MM-DD HH:mm"),
        end: moment(endTime).format("YYYY-MM-DD HH:mm")
      })
    }
    return courseMeetingTimes
  }

  getLastSunday(d) {
    var t = new Date(d);
    t.setDate(t.getDate() - t.getDay());
    t.setHours(0,0,0,0);
    return t;
  }

  render() {
    return (
      <div>
        <button onClick={() => {this.selectPreviousSchedule()}}>Previous Schedule</button>
        <button onClick={() => {this.selectNextSchedule()}}>Next Schedule</button>
        {
          this.state.selectedScheduleId < this.state.schedulesTimes.length &&
          (
            <FullCalendar
              plugins={[ dayGridPlugin, timeGridPlugin ]}
              initialView="timeGridWeek"
              events={
                this.state.schedulesTimes[this.state.selectedScheduleId]      
              }
            />
          )
        }
      </div>
    )
  }
}
