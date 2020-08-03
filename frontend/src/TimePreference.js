import React from 'react';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import {Card, CardContent} from '@material-ui/core';

class TimePreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleTimeBeforeChange = this.handleTimeBeforeChange.bind(this);
    this.handleTimeAfterChange = this.handleTimeAfterChange.bind(this);
  }

  handleTimeBeforeChange(event) {
    this.props.updateTimeBeforePreference(event.target.value);
  }

  handleTimeAfterChange(event) {
    this.props.updateTimeAfterPreference(event.target.value);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Typography variant="subtitle1" gutterBottom>
            When would you not like class?
          </Typography>
          <TextField
            label="Before: " type="time" onChange={this.handleTimeBeforeChange}
            value={this.props.timeBefore} InputLabelProps={{shrink: true}} inputProps={{step: 300}}
          />
          <TextField
            label="After: " type="time" onChange={this.handleTimeAfterChange}
            value={this.props.timeAfter} InputLabelProps={{shrink: true}} inputProps={{step: 300}}
          />
        </CardContent>
      </Card>
    );
  }
}

export default TimePreference;
