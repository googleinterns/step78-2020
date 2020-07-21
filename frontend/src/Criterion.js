import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Card, CardContent, Input } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class Criterion extends React.Component {
  constructor(props) {
    super(props);

    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
  }

  updateTimeStartPreference(id, startTime) {
    this.props.updateTimeStartPreference(id, startTime);
  }

  updateTimeEndPreference(id, endTime) {
    this.props.updateTimeEndPreference(id, endTime);
  }
  
  updateSubjectPreference(subject) {
    this.props.updateSubjectPreference(subject);
  }
  
  render() {
    return (
      <Card>
        <TimePreference 
          updateTimeStartPreference={this.updateTimeStartPreference}
          updateTimeEndPreference={this.updateTimeEndPreference}/>
        <SubjectPreference 
          subject={this.props.subject}
          updateSubjectPreference={this.updateSubjectPreference}/>
      </Card>
    )
  }
}

class TimePreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
  }

  handleStartTimeChange(event) {
    this.props.updateTimeStartPreference(this.props.id, event.target.value);
  }

  handleEndTimeChange(event) {
    this.props.updateTimeEndPreference(this.props.id, event.target.value);
  }

  render() {
    return (
      <CardContent>
        <TextField
          id="time-before-input" label="Start time: " type="time"
          defaultValue="08:00" onChange={this.handleStartTimeChange}
          InputLabelProps={{ shrink: true, }} inputProps={{ step: 300, }}
        />
        <TextField
          id="time-after-input" label="End time: " type="time"
          defaultValue="17:00" onChange={this.handleEndTimeChange}
          InputLabelProps={{ shrink: true, }} inputProps={{ step: 300, }}
        />
      </CardContent>
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

class SubjectPreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleSubjectChange = this.handleSubjectChange.bind(this);
  }

  handleSubjectChange(event) {
    this.props.updateSubjectPreference(event.target.value)
  }

  render() {
    return (
      <CardContent>
        <div id="label">Preferred subject: </div>
        <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }} 
          value={this.props.subject} onChange={this.handleSubjectChange} />
      </CardContent>
    );
  }
}

export default Criterion;
