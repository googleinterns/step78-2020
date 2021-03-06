import React from 'react';
import TextField from '@material-ui/core/TextField';
import {Input, Card, CardContent} from '@material-ui/core';
import FormLabel from '@material-ui/core/FormLabel';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

class Section extends React.Component {
  constructor(props) {
    super(props);

    this.handleProfessorChange = this.handleProfessorChange.bind(this);
    this.handleStartTimeChange = this.handleStartTimeChange.bind(this);
    this.handleEndTimeChange = this.handleEndTimeChange.bind(this);
    this.handleDayChange = this.handleDayChange.bind(this);
    this.handleDeleteSection = this.handleDeleteSection.bind(this);
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

  handleDayChange(day) {
    this.props.updateSectionDays(this.props.id, day);
  }

  handleDeleteSection() {
    this.props.deleteSection(this.props.id);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Professor" value={this.props.professor} onChange={this.handleProfessorChange}
          />
          <TextField label="Start time: " type="time"
            onChange={this.handleStartTimeChange} InputLabelProps={{shrink: true}}
            value={this.props.startTime} inputProps={{step: 300}}
          />
          <TextField label="End time: " type="time"
            onChange={this.handleEndTimeChange} InputLabelProps={{shrink: true}}
            value={this.props.endTime} inputProps={{step: 300}}
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
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(1)} />}
              label="Mon"
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(2)} />}
              label="Tues"
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(3)} />}
              label="Wed"
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(4)} />}
              label="Thurs"
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(5)} />}
              label="Fri"
            />
            <FormControlLabel
              control={<Checkbox
                onChange={() => this.handleDayChange(6)} />}
              label="Sat"
            />
          </FormGroup>
        </CardContent>
      </Card>
    );
  }
}

export default Section;
