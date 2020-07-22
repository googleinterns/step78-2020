import React from 'react';
import TextField from '@material-ui/core/TextField';
import { CardContent } from '@material-ui/core';

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
          label="Start time: " type="time"
          defaultValue="08:00" onChange={this.handleStartTimeChange}
          InputLabelProps={{ shrink: true, }} inputProps={{ step: 300, }}
        />
        <TextField
          label="End time: " type="time"
          defaultValue="17:00" onChange={this.handleEndTimeChange}
          InputLabelProps={{ shrink: true, }} inputProps={{ step: 300, }}
        />
      </CardContent>
    );
  }
}

export default TimePreference;
