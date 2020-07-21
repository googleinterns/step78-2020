import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Card, CardContent } from '@material-ui/core';
import NumberFormat from 'react-number-format';
import PropTypes from 'prop-types';

class BasicInfo extends React.Component {
  constructor(props) {
    super(props);

    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);
  }

  updateMinCredits(minCredits) {
    this.props.updateMinCredits(minCredits);
  }

  updateMaxCredits(maxCredits) {
    this.props.updateMaxCredits(maxCredits);
  }

  updateTermStartDate(startDate) {
    this.props.updateTermStartDate(startDate);
  }

  updateTermEndDate(endDate) {
    this.props.updateTermEndDate(endDate);
  }
  
  render() {
    return (
      <Card>
        <Credits 
          minCredits={this.props.minCredits}
          maxCredits={this.props.maxCredits}
          updateMinCredits={this.updateMinCredits}
          updateMaxCredits={this.updateMaxCredits}/>
        <TermDates 
          startDate={this.props.startDate}
          endDate={this.props.endDate}
          updateTermStartDate={this.updateTermStartDate}
          updateTermEndDate={this.updateTermEndDate}/>
      </Card>
    )
  }
}

class Credits extends React.Component {
  constructor(props) {
    super(props);

    this.handleMinCreditsChange = this.handleMinCreditsChange.bind(this);
    this.handleMaxCreditsChange = this.handleMaxCreditsChange.bind(this);
  }

  handleMinCreditsChange(event) {
    this.props.updateMinCredits(event.target.value);
  }

  handleMaxCreditsChange(event) {
    this.props.updateMaxCredits(event.target.value);
  }

  render() {
    return (
      <CardContent>
        <TextField label="Minimum credits:" value={this.props.minCredits} onChange={this.handleMinCreditsChange}
          InputProps={{ inputComponent: NumberFormatCustom }} />
        <TextField label="Maximum credits:" value={this.props.maxCredits} onChange={this.handleMaxCreditsChange}
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

    this.handleStartDateChange = this.handleStartDateChange.bind(this);
    this.handleEndDateChange = this.handleEndDateChange.bind(this);
  }

  handleStartDateChange(event) {
    this.props.updateTermStartDate(event.target.value);
  }

  handleEndDateChange(event) {
    this.props.updateTermEndDate(event.target.value);
  }

  render() {
    return (
      <CardContent>    
        <TextField label="Term start date:" type="date" onChange={this.handleStartDateChange}
          value={this.props.startDate} InputLabelProps={{ shrink: true, }}
        />
        <TextField label="Term end date:" type="date" onChange={this.handleEndDateChange}
          value={this.props.endDate} InputLabelProps={{ shrink: true, }}
        />
      </CardContent>
    );
  }
}

export default BasicInfo;
