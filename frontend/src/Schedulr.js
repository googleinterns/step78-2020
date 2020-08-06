/* global gapi */
import React from 'react';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import InputForm from './InputForm';
import Calendar from './Calendar';
import moment from 'moment';

// the conversion of one minute to 60000 milliseconds
const MIN_TO_MS = 60000;

class Schedulr extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeStep: 0,
      steps: ['Sign In', 'Input Courses and Preferences', 'View Schedules', 'Export to Google Calendar'],
      scheduleList: [],
      schedulesTimes: [],
      selectedScheduleId: 0,
      calId: '',
      termDates: {
        startDate: '',
        endDate: '',
      },
    };

    this.handleNext = this.handleNext.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.setScheduleList = this.setScheduleList.bind(this);
    this.generateScheduleMeetingTimes = this.generateScheduleMeetingTimes.bind(this);
    this.selectPreviousSchedule = this.selectPreviousSchedule.bind(this);
    this.selectNextSchedule = this.selectNextSchedule.bind(this);
    this.exportToGoogleCalendar = this.exportToGoogleCalendar.bind(this);
    this.setTermDates = this.setTermDates.bind(this);
  }

  getStepContent() {
    const step = this.state.activeStep;

    switch (step) {
    case 0:
      return (
        <div>
          <h2>Welcome to SchedulR</h2>
          <p>To get started, sign in so that we can export your perfect class schedule to your Google Calendar!</p>
          <Button onClick={this.signIn}>Sign In</Button>
        </div>);
    case 1:
      return (<InputForm
        handleNext={this.handleNext}
        setScheduleList={this.setScheduleList}
        setTermDates={this.setTermDates}/>);
    case 2:
      return (<Calendar
        scheduleList={this.state.scheduleList}
        schedulesTimes={this.state.schedulesTimes}
        selectedScheduleId={this.state.selectedScheduleId}
        selectNextSchedule={this.selectNextSchedule}
        selectPreviousSchedule={this.selectPreviousSchedule}
        exportToGoogleCalendar={this.exportToGoogleCalendar}/>);
    case 3:
      return (
        <iframe
          title="google calendar"
          id="calendar"
          src={this.state.calId}
          height="750"
          width="900"
          frameBorder="0"
          scrolling="no">
        </iframe>);
    default:
      return 'Unknown step';
    }
  }

  signIn() {
    window.location.assign('/auth');
  }

  handleNext() {
    if (this.state.activeStep === 1) {
      this.generateSchedulesTimes();
    }

    this.setState({...this.state, activeStep: this.state.activeStep + 1});
  }

  handleBack() {
    this.setState({...this.state, activeStep: this.state.activeStep - 1});
  }

  setScheduleList(listOfSchedules) {
    this.setState({...this.state, scheduleList: listOfSchedules});
  }

  generateSchedulesTimes() {
    this.state.scheduleList.forEach((schedule) => {
      this.generateScheduleMeetingTimes(schedule);
    });
    return true;
  }

  generateScheduleMeetingTimes(schedule) {
    let combinedMeetingTimes = [];

    for (const course of schedule.courses) {
      // Lecture Sections
      const lectureTime = this.generateCourseMeetingTimes(course.name, course.lectureSection.meetingTimes);

      // Lab Sections
      if (course.labSection) {
        const labTime = this.generateCourseMeetingTimes(course.name, course.labSection.meetingTimes);
        combinedMeetingTimes = combinedMeetingTimes.concat(labTime);
      }

      combinedMeetingTimes = combinedMeetingTimes.concat(lectureTime);
    }

    this.setState({
      ...this.state, schedulesTimes: [...this.state.schedulesTimes, combinedMeetingTimes],
    });
  }

  generateCourseMeetingTimes(courseName, meetingTimes) {
    const lastSunday = this.getLastSunday(Date.now());
    const courseMeetingTimes = [];

    for (const meetingTime of meetingTimes) {
      const startTime = new Date(lastSunday.getTime() + meetingTime.start*MIN_TO_MS);
      const endTime = new Date(startTime.getTime() + meetingTime.duration*MIN_TO_MS);

      courseMeetingTimes.push({
        title: courseName,
        start: moment(startTime).format('YYYY-MM-DD HH:mm'),
        end: moment(endTime).format('YYYY-MM-DD HH:mm'),
      });
    }

    return courseMeetingTimes;
  }

  getLastSunday(d) {
    const t = new Date(d);
    t.setDate(t.getDate() - t.getDay());
    t.setHours(0, 0, 0, 0);
    return t;
  }

  setCalId(calId) {
    const url = 'https://calendar.google.com/calendar/embed?src=' + calId;
    this.setState({...this.state, calId: url});
  }

  selectNextSchedule() {
    if (this.state.selectedScheduleId < this.state.schedulesTimes.length - 1) {
      this.setState({selectedScheduleId: this.state.selectedScheduleId + 1});
    }
  }

  selectPreviousSchedule() {
    if (this.state.selectedScheduleId > 0) {
      this.setState({selectedScheduleId: this.state.selectedScheduleId - 1});
    }
  }

  exportToGoogleCalendar() {
    const scheduleToExport = this.state.scheduleList[this.state.selectedScheduleId];
    const schedule = {
      'schedule': scheduleToExport,
      'termDates': {
        'startDate': this.state.termDates.startDate,
        'endDate': this.state.termDates.endDate,
      },
    };
    fetch('/exportToGoogleCalendar', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(schedule),
    }).then((response) => response.text())
      .then((calId) => {
        this.setCalId(calId);
        this.handleNext();
      });
  }

  setTermDates(startDate, endDate) {
    this.setState((state) => ({
      ...this.state,
      termDates: {
        ...state.termDates,
        startDate: startDate,
        endDate: endDate,
      },
    }));
  }

  componentDidMount() {
    window.gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: '119851197452-crnk45b5i6gsi5povitstfpd203n7j6b.apps.googleusercontent.com',
      });

      this.auth2.then(() => {
        if (this.auth2.isSignedIn.get()) {
          this.setState({...this.state, activeStep: 1});
        }
      });
    });
  }

  render() {
    return (
      <div>
        <Stepper activeStep={this.state.activeStep}>
          {this.state.steps.map((label, index) => {
            return (
              <Step key={label}>
                <StepLabel>{label}</StepLabel>
              </Step>
            );
          })}
        </Stepper>
        <div>
          {this.getStepContent()}
          <div className="navButtons">
            {(this.state.activeStep === 2 || this.state.activeStep === 3) && (
              <Button variant="outlined" onClick={this.handleBack}>
                Back
              </Button>
            )}
            {(this.state.activeStep === 2) && (
              <Button variant="contained" color="primary" onClick={
                this.exportToGoogleCalendar
              }>Export to Google Calendar</Button>
            )}
          </div>
        </div>
      </div>);
  }
}

export default Schedulr;
