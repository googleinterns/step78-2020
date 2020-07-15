import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Card, CardContent } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class BasicInfo extends React.Component {
  render() {
    return (
      <Card>
        <Credits />
        <TermDates />
      </Card>
    )
  }
}

class Credits extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      minCredits: 12,
      maxCredits: 18
    }

    this.handleMinCreditsChange = this.handleMinCreditsChange.bind(this);
    this.handleMaxCreditsChange = this.handleMaxCreditsChange.bind(this);
  }

  handleMinCreditsChange(event) {
    this.setState({...this.state, minCredits: event.target.value});
  }

  handleMaxCreditsChange(event) {
    this.setState({...this.state, maxCredits: event.target.value});
  }

  render() {
    return (
      <CardContent>
        <TextField label="Minimum credits:" value={this.state.minCredits} onChange={this.handleMinCreditsChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />
        <TextField label="Maximum credits:" value={this.state.maxCredits} onChange={this.handleMaxCreditsChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />
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

class TermDates extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: "",
      endDate: ""
    }

    this.handleStartDateChange = this.handleStartDateChange.bind(this);
    this.handleEndDateChange = this.handleEndDateChange.bind(this);
  }

  handleStartDateChange(event) {
    this.setState({...this.state, startDate: event.target.value});
  }

  handleEndDateChange(event) {
    this.setState({...this.state, endDate: event.target.value});
  }

  render() {
    return (
      <CardContent>    
        <TextField label="Term start date:" type="date" onChange={this.handleStartDateChange}
          defaultValue="2020-08-24" InputLabelProps={{ shrink: true, }}
        />
        <TextField label="Term end date:" type="date" onChange={this.handleEndDateChange}
          defaultValue="2020-12-11" InputLabelProps={{ shrink: true, }}
        />
      </CardContent>
    );
  }
}

export default BasicInfo;
