import React from 'react';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import InputForm from './InputForm';
import Calendar from './Calendar';
import moment from 'moment';

// the conversion of one minute to 60000 milliseconds
const MIN_TO_MS = 60000;

class Schedulr extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeStep: (new URLSearchParams(window.location.search).get('signedIn') === 'true') ? 1 : 0,
      steps: ['Sign In', 'Input Courses and Preferences', 'View Schedules', 'Export to Google Calendar'],
      scheduleList: [],
      schedulesTimes: [],
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
    this.exportToGoogleCalendar = this.exportToGoogleCalendar.bind(this);
    this.setTermDates = this.setTermDates.bind(this);
  }

  getStepContent() {
    const step = this.state.activeStep;
    switch (step) {
    case 0:
      return (
        <Button onClick={this.signIn}>Get Started</Button>
      );
    case 1:
      return (<InputForm
        handleNext={this.handleNext}
        setScheduleList={this.setScheduleList}
        setTermDates={this.setTermDates}/>);
    case 2:
      return (<Calendar
        scheduleList={this.state.scheduleList}
        schedulesTimes={this.state.schedulesTimes}
        exportToGoogleCalendar={this.exportToGoogleCalendar}/>);
    case 3:
      return (<iframe title="google calendar" id="calendar" src={this.state.calId} width="800" height="600" frameBorder="0" scrolling="no"></iframe>);
    default:
      return 'Unknown step';
    }
  }

  signIn() {
    window.location.assign('https://course-scheduler-step-2020.uc.r.appspot.com/auth');
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

  exportToGoogleCalendar(scheduleId) {
    const scheduleToExport = this.state.scheduleList[scheduleId];
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
          <Typography>{this.getStepContent()}</Typography>
          <div>
            {(this.state.activeStep === 2 || this.state.activeStep === 3) && (
              <Button onClick={this.handleBack}>
                Back
              </Button>
            )}
            {(this.state.activeStep === 2) && (
              <Button onClick={this.handleNext}>
                Next
              </Button>
            )}
          </div>
        </div>
      </div>);
  }
}

export default Schedulr;
