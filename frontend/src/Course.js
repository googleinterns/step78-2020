import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class Course extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      courseID: "",
      subject: "",
      credits: "",
      isRequired: false,
      section: [] //Will be updated to handle multiple sections later
    }

    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleIDChange = this.handleIDChange.bind(this);
    this.handleSubjectChange = this.handleSubjectChange.bind(this);
    this.handleCreditsChange = this.handleCreditsChange.bind(this);
  }

  handleNameChange(event) {
    this.setState({...this.state, name: event.target.value});
  }

  handleIDChange(event) {
    this.setState({...this.state, courseID: event.target.value});
  }

  handleSubjectChange(event) {
    this.setState({...this.state, subject: event.target.value});
  }

  handleCreditsChange(event) {
    this.setState({...this.state, credits: event.target.value});
  }

  handleisRequiredChange(event) {
    this.setState({...this.state, isRequired: event.target.checked})
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Course Name" inputProps={{ 'aria-label': 'description' }} 
            value={this.state.name} onChange={this.handleNameChange} />
          <Input placeholder="Course ID" inputProps={{ 'aria-label': 'description' }}
            value={this.state.courseID} onChange={this.handleIDChange} />
          <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
            value={this.state.subject} onChange={this.handleSubjectChange} />
          <TextField placeholder="Credits" value={this.state.credits} onChange={this.handleCreditsChange}
            InputProps={{ inputComponent: NumberFormatCustom }} />
          <Section />
            
          {/* <FormControlLabel>
            control = {<Switch id="isRequired-input" checked={this.state.isRequired} onChange={this.handleisRequiredChange} />}
            label = "Required"
          </FormControlLabel> */}

          {/* //TODO: Implement proper section inputs */}
        </CardContent>
      </Card>
    );
  }
}

class Section extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      professor: "",
      startTime: "",
      endTime: ""
    }

    this.handleProfessorChange = this.handleProfessorChange.bind(this);
    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
  }

  handleProfessorChange(event) {
    this.setState({...this.state, professor: event.target.value});
  }

  handleStartTimeChange(event) {
    this.setState({...this.state, startTime: event.target.value});
  }

  handleEndTimeChange(event) {
    this.setState({...this.state, endTime: event.target.value});
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Professor" inputProps={{ 'aria-label': 'description' }} 
            value={this.state.professor} onChange={this.handleProfessorChange} />
          <TextField label="Start time: " type="time" defaultValue="08:00" 
            onChange={this.handleStartTimeChange} InputLabelProps={{ shrink: true, }}
            inputProps={{ step: 300, }}
          />
          <TextField label="End time: " type="time" defaultValue="09:00" 
            onChange={this.handleEndTimeChange} InputLabelProps={{ shrink: true, }}
            inputProps={{ step: 300, }}
          />
        </CardContent>
      </Card>
    );
  }
}

function NumberFormatCustom(props) {
  const { inputRef, onChange, ...other } = props;

  return (
    <NumberFormat
      {...other}
      getInputRef={inputRef}
      onValueChange={(values) => {
        onChange({
          target: {
            name: props.name,
            value: values.value,
          },
        });
      }}
      thousandSeparator
      isNumericString
    />
  );
}

NumberFormatCustom.propTypes = {
  inputRef: PropTypes.func.isRequired,
  name: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
};

export default Course;
