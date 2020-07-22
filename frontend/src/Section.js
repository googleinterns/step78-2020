import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';

class Section extends React.Component {
  constructor(props) {
    super(props);

    this.handleProfessorChange = this.handleProfessorChange.bind(this);
    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
  }

  handleProfessorChange(event) {
    this.props.updateSectionProfessor(this.props.id, event.target.value);
  }

  handleStartTimeChange(event) {
    this.props.updateSectionStartTime(this.props.id, event.target.value);
  }

  handleEndTimeChange(event) {
    this.props.updateSectionEndTime(this.props.id, event.target.value);
  }

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

export default Section;
