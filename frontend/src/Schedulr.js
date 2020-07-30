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
      activeStep: 0,
      steps: ['Input Courses and Preferences', 'View Schedules', 'Export to Google Calendar'],
      scheduleList: [],
      schedulesTimes: [],
    };

    this.handleNext = this.handleNext.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.setScheduleList = this.setScheduleList.bind(this);
    this.generateScheduleMeetingTimes = this.generateScheduleMeetingTimes.bind(this);
  }

  getStepContent() {
    const step = this.state.activeStep;
    switch (step) {
    case 0:
      return (<InputForm
        handleNext={this.handleNext}
        setScheduleList={this.setScheduleList}/>);
    case 1:
      return (<Calendar
        scheduleList={this.state.scheduleList}
        schedulesTimes={this.state.schedulesTimes}/>);
    case 2:
      return 'Export to google calendar: coming soon ;)';
    default:
      return 'Unknown step';
    }
  }

  handleNext() {
    if (this.state.activeStep === 0) {
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
            {(this.state.activeStep === 1 || this.state.activeStep === 2) && (
              <Button onClick={this.handleBack}>
                Back
              </Button>
            )}
            {(this.state.activeStep === 1) && (
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
