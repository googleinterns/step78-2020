import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import FormLabel from '@material-ui/core/FormLabel';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

class LectureSection extends React.Component {
  constructor(props) {
    super(props);

    this.handleProfessorChange = this.handleProfessorChange.bind(this);
    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
    this.handleDayChange = this.handleDayChange.bind(this);
    this.handleDeleteSection = this.handleDeleteSection.bind(this);
  }

  handleProfessorChange(event) {
    this.props.updateLectureSectionProfessor(this.props.id, event.target.value);
  }

  handleStartTimeChange(event) {
    this.props.updateLectureSectionStartTime(this.props.id, event.target.value);
  }

  handleEndTimeChange(event) {
    this.props.updateLectureSectionEndTime(this.props.id, event.target.value);
  }

  handleDayChange(day) {
    this.props.updateLectureSectionDays(this.props.id, day);
  }

  handleDeleteSection() {
    this.props.deleteLectureSection(this.props.id);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Professor" inputProps={{ 'aria-label': 'description' }} 
            value={this.props.professor} onChange={this.handleProfessorChange} 
          />
          <TextField label="Start time: " type="time"
            onChange={this.handleStartTimeChange} InputLabelProps={{ shrink: true, }}
            value={this.props.startTime} inputProps={{ step: 300, }}
          />
          <TextField label="End time: " type="time"
            onChange={this.handleEndTimeChange} InputLabelProps={{ shrink: true, }}
            value={this.props.endTime} inputProps={{ step: 300, }}
          />
          <IconButton aria-label="delete" onClick={this.handleDeleteSection}>
            <DeleteIcon />
          </IconButton>
          <FormLabel component="legend">Days of Week</FormLabel>
          <FormGroup row>
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(0)} />}
              label="Sun"
              checked={this.props.sun}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(1)} />}
              label="Mon"
              checked={this.props.mon}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(2)} />}
              label="Tues"
              checked={this.props.tue}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(3)} />}
              label="Wed"
              checked={this.props.wed}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(4)} />}
              label="Thurs"
              checked={this.props.thur}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(5)} />}
              label="Fri"
              checked={this.props.fri}
            />
            <FormControlLabel
              control={<Checkbox
              onChange={() => this.handleDayChange(6)} />}
              label="Sat"
              checked={this.props.sat}
            />
          </FormGroup>
        </CardContent>
      </Card>
    );
  }
}

export default LectureSection;
