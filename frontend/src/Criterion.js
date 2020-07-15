import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Card, CardContent, Input } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class Criterion extends React.Component {
  render() {
    return (
      <Card>
        <TimePreference />
        <SubjectNumberPreference />
      </Card>
    )
  }
}

class TimePreference extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      timeBefore: "",
      timeAfter: ""
    }

    this.handleTimeBeforeChange = this.handleTimeBeforeChange.bind(this);
    this.handleTimeAfterChange = this.handleTimeAfterChange.bind(this);
  }

  handleTimeBeforeChange(event) {
    this.setState({...this.state, timeBefore: event.target.value});
  }

  handleTimeAfterChange(event) {
    this.setState({...this.state, timeAfter: event.target.value});
  }

  render() {
    return (
      <CardContent>
        <TextField
          id="time-before-input" label="Earliest class: " type="time"
          defaultValue="08:00" onChange={this.handleTimeBeforeChange}
          InputLabelProps={{ shrink: true, }}
          inputProps={{ step: 300, }}
        />
        <TextField
          id="time-after-input" label="Latest class: " type="time"
          defaultValue="17:00" onChange={this.handleTimeAfterChange}
          InputLabelProps={{ shrink: true, }}
          inputProps={{ step: 300, }}
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

class SubjectNumberPreference extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      subjectOne: "",
      numberOne: 0,
      subjectTwo: "",
      numberTwo: 0,
      subjectThree: "",
      numberThree: 0
    }

    this.handleSubjectOneChange = this.handleSubjectOneChange.bind(this);
    this.handleNumberOneChange = this.handleNumberOneChange.bind(this);
    this.handleSubectTwoChange = this.handleSubjectTwoChange.bind(this);
    this.handleNumberTwoChange = this.handleNumberTwoChange.bind(this);
    this.handleSubectThreeChange = this.handleSubjectThreeChange.bind(this);
    this.handleNumberThreeChange = this.handleNumberThreeChange.bind(this);
  }

  handleSubjectOneChange(event) {
    this.setState({...this.state, subjectOne: event.target.value});
  }

  handleNumberOneChange(event) {
    this.setState({...this.state, numberOne: event.target.value});
  }

  handleSubjectTwoChange(event) {
    this.setState({...this.state, subjectTwo: event.target.value});
  }

  handleNumberTwoChange(event) {
    this.setState({...this.state, numberTwo: event.target.value});
  }

  handleSubjectThreeChange(event) {
    this.setState({...this.state, subjectThree: event.target.value});
  }

  handleNumberThreeChange(event) {
    this.setState({...this.state, numberThree: event.target.value});
  }

  render() {
    return (
      <CardContent>
        <div id="label">Number of courses in each subject: </div>
        
        <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }} 
          value={this.state.subjectOne} onChange={this.handleSubjectOneChange} />
          
        <TextField value={this.state.numberOne} onChange={this.handleNumberOneChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />

        <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
          value={this.state.subjectTwo} onChange={this.handleSubjectTwoChange} />
        
        <TextField value={this.state.numberTwo} onChange={this.handleNumberTwoChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />
        
        <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
          value={this.state.subjectThree} onChange={this.handleSubjectThreeChange} />
        
        <TextField value={this.state.numberThree} onChange={this.handleNumberThreeChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />
      </CardContent>
    );
  }
}

export default Criterion;
