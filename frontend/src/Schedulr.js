import React from 'react';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import InputForm from './InputForm';

class Schedulr extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeStep: 0,
      steps: ['Input Courses and Preferences', 'View Schedules', 'Export to Google Calendar'],
      scheduleList: {}
    };

    this.handleNext = this.handleNext.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.setScheduleList = this.setScheduleList.bind(this);
  }

  getStepContent() {
    let step = this.state.activeStep;
    switch (step) {
    case 0:
      return (<InputForm
        handleNext={this.handleNext}
        setScheduleList={this.setScheduleList}/>);
    case 1:
      return (<p>{JSON.stringify(this.state.scheduleList, null, 2)}</p>);
    case 2:
      return 'This is the bit I really care about!';
    default:
      return 'Unknown step';
    }
  }

  handleNext() {
    this.setState({...this.state, activeStep: this.state.activeStep + 1});
  }

  handleBack() {
    this.setState({...this.state, activeStep: this.state.activeStep - 1});
  }

  setScheduleList(listOfSchedules) {
    this.setState({...this.state, scheduleList: listOfSchedules});
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
