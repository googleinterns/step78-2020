import React from 'react'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import moment from 'moment'
import timeGridPlugin from '@fullcalendar/timegrid'

import {fetchUserCalendar} from './script'

export default class Calendar extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      fetchDone: false,
      schedulesTimes: [],
      scheduleIdx: 0                 // Current schedule
    }
  }

  componentDidMount(){
    this.getAllSchedules()
  }

  async getAllSchedules() {
    const schedules = await fetchUserCalendar()
    console.log(schedules)
    for(const schedule of schedules) {
      this.getMeetingTimes(schedule)
    }
    
    this.setState({
      fetchDone: true
    })
    console.log(this.state.schedulesTimes)
  }

  getNextSchedule() {
    if(this.state.scheduleIdx < this.state.schedulesTimes.length - 1) {
      this.setState({scheduleIdx : this.state.scheduleIdx + 1})
    }
  }

  getPrevSchedule() {
    if(this.state.scheduleIdx > 0) {
      this.setState({scheduleIdx : this.state.scheduleIdx - 1})
    }
  }

  getMeetingTimes(schedule) {
    let combinedMeetingTimes = []
    console.log(schedule)
    for (const course of schedule.courses) {
      // Lecture Sections
      const lectureTime = this.getScheduleSections(course.name, course.lectureSection.meetingTimes)
      // Lab Sections
      if (course.labSection) {
        const labTime = this.getScheduleSections(course.name, course.labSection.meetingTimes)
        combinedMeetingTimes = combinedMeetingTimes.concat(labTime)
      }
      combinedMeetingTimes = combinedMeetingTimes.concat(lectureTime)
    }
    //console.log(combinedMeetingTimes)
    this.setState({
      schedulesTimes:  [...this.state.schedulesTimes, combinedMeetingTimes]
    })
    //return combinedMeetingTimes
  }
  
  getScheduleSections(courseName, meetingTimes) {
    const lastSunday = this.getLastMonday(Date.now())
    const courseMeetingTimes = []
    for (const meetingTime of meetingTimes) {
      // 60000 is seconds to miliseconds
      const startTime = new Date(lastSunday.getTime() + meetingTime.start*60000)
      const endTime = new Date(startTime.getTime() + meetingTime.duration*60000)

      courseMeetingTimes.push({
        title: courseName,
        start: moment(startTime).format("YYYY-MM-DD HH:mm"),
        end: moment(endTime).format("YYYY-MM-DD HH:mm")
      })
    }
    return courseMeetingTimes
  }

  getLastMonday(d) {
    var t = new Date(d);
    t.setDate(t.getDate() - t.getDay());
    t.setHours(0,0,0,0);
    return t;
  }

  render() {
  return (
    <div>
      <button onClick = {() => {this.getPrevSchedule()}}>Previous Schedule</button>
      <button onClick = {() => {this.getNextSchedule()}}>Next Schedule</button>
      {this.state.fetchDone &&
      (<FullCalendar
        plugins={[ dayGridPlugin, timeGridPlugin ]}
        initialView="timeGridWeek"
        //weekends={true}
        events={
          this.state.schedulesTimes[this.state.scheduleIdx]      
        }
      />)}
    </div>
    )
  }
}