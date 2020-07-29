import React from 'react';
//import { makeStyles } from '@material-ui/core/styles';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import InputForm from './InputForm';

/*const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
  },
  button: {
    marginRight: theme.spacing(1),
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1),
  },
}));*/

class Schedulr extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      activeStep: 0,
      steps: ['Input Courses and Preferences', 'View Schedules', 'Export to Google Calendar'],
      scheduleList: {}
    }

    this.handleNext = this.handleNext.bind(this);
    this.handleBack = this.handleBack.bind(this);
    this.setScheduleList = this.setScheduleList.bind(this);
  }


  getStepContent(step) {
    switch (step) {
      case 0:
        return (<InputForm 
          handleNext={this.handleNext}
          setScheduleList={this.setScheduleList}/>);
      case 1:
        return (<p>{JSON.stringify(this.state.scheduleList)}</p>);
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
    this.setState({...this.state, scheduleList: listOfSchedules})
  }

  render() {
    return (
      <div>
        <Stepper activeStep={this.state.activeStep}>
          {this.state.steps.map((label, index) => {
            const stepProps = {};
            const labelProps = {};
            return (
              <Step key={label} {...stepProps}>
                <StepLabel {...labelProps}>{label}</StepLabel>
              </Step>
            );
          })}
        </Stepper>
        <div>
          <Typography>{this.getStepContent(this.state.activeStep)}</Typography>
          {(this.state.activeStep === 1 || this.state.activeStep === 2) && (
            <div>
              <Button onclick={this.handleBack}>
                Back
              </Button>
            </div>)}
          {(this.state.activeStep === 1) && (
            <div>
              <Button onclick={this.handleNext}>
                Next
              </Button>
            </div>)}
        </div>
      </div>);
  }
}

export default Schedulr;
