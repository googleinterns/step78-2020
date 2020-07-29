import React from 'react';
import TextField from '@material-ui/core/TextField';
import {Card, CardContent} from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

class TimePreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
    this.handleDeleteTimePreference = this.handleDeleteTimePreference.bind(this);
  }

  handleStartTimeChange(event) {
    this.props.updateTimeStartPreference(this.props.id, event.target.value);
  }

  handleEndTimeChange(event) {
    this.props.updateTimeEndPreference(this.props.id, event.target.value);
  }

  handleDeleteTimePreference() {
    this.props.deleteTimePreference(this.props.id);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <TextField
            label="Start time: " type="time" onChange={this.handleStartTimeChange}
            value={this.props.startTime} InputLabelProps={{shrink: true}} inputProps={{step: 300}}
          />
          <TextField
            label="End time: " type="time" onChange={this.handleEndTimeChange}
            value={this.props.endTime} InputLabelProps={{shrink: true}} inputProps={{step: 300}}
          />
          <IconButton aria-label="delete" onClick={this.handleDeleteTimePreference}>
            <DeleteIcon />
          </IconButton>
        </CardContent>
      </Card>
    );
  }
}

export default TimePreference;
