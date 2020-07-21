import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class Course extends React.Component {  
  constructor(props) {
    super(props);
    
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleIDChange = this.handleIDChange.bind(this);
    this.handleSubjectChange = this.handleSubjectChange.bind(this);
    this.handleCreditsChange = this.handleCreditsChange.bind(this);
  }

  handleNameChange(event) {
    this.props.updateCourseName(this.props.id, event.target.value);
  }

  handleIDChange(event) {
    this.props.updateCourseID(this.props.id, event.target.value);
  }

  handleSubjectChange(event) {
    this.props.updateCourseSubject(this.props.id, event.target.value);
  }

  handleCreditsChange(event) {
    this.props.updateCourseCredits(this.props.id, event.target.value);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Course Name" inputProps={{ 'aria-label': 'description' }} 
            value={this.props.name} onChange={this.handleNameChange} />
          <Input placeholder="Course ID" inputProps={{ 'aria-label': 'description' }}
            value={this.props.courseID} onChange={this.handleIDChange} />
          <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
            value={this.props.subject} onChange={this.handleSubjectChange} />
          <TextField placeholder="Credits" value={this.props.credits} onChange={this.handleCreditsChange}
            InputProps={{ inputComponent: NumberFormatCustom }} />
          <Section />
          <button onClick={this.props.createNewSection}>Add Section</button>
            
          {/* <FormControlLabel>
            control = {<Switch id="isRequired-input" checked={this.state.isRequired} onChange={this.handleisRequiredChange} />}
            label = "Required"
          </FormControlLabel> */}

        </CardContent>
      </Card>
    );
  }
}

class Section extends React.Component {
  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Professor" inputProps={{ 'aria-label': 'description' }} 
            value={this.props.professor} onChange={this.handleProfessorChange} />
          <TextField label="Start time: " type="time" defaultValue="08:00" 
            onChange={this.handleStartTimeChange} InputLabelProps={{ shrink: true, }}
            value={this.props.startTime} inputProps={{ step: 300, }}
          />
          <TextField label="End time: " type="time" defaultValue="09:00" 
            onChange={this.handleEndTimeChange} InputLabelProps={{ shrink: true, }}
            value={this.props.endTime} inputProps={{ step: 300, }}
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
