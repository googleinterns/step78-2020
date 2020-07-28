import React from 'react';
import TextField from '@material-ui/core/TextField';
import { CardContent } from '@material-ui/core';

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

export default TermDates;
