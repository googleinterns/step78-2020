import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Section from './Section';
import { NumberFormatCustom } from './NumberFormat';

class Course extends React.Component {  
  constructor(props) {
    super(props);
    
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleIDChange = this.handleIDChange.bind(this);
    this.handleSubjectChange = this.handleSubjectChange.bind(this);
    this.handleCreditsChange = this.handleCreditsChange.bind(this);
    this.handleIsRequiredChange = this.handleIsRequiredChange.bind(this);
    this.handleRankChange = this.handleRankChange.bind(this);
    this.updateRankSelectOptions = this.updateRankSelectOptions.bind(this);

    this.updateSectionProfessor = this.updateSectionProfessor.bind(this);
    this.updateSectionStartTime = this.updateSectionStartTime.bind(this);
    this.updateSectionEndTime = this.updateSectionEndTime.bind(this);
    this.updateSectionDays = this.updateSectionDays.bind(this);
    this.createNewSection = this.createNewSection.bind(this);
  }

  handleNameChange(event) {
    this.props.updateCourseName(this.props.id, event.target.value);
  }

  handleIDChange(event) {
    this.props.updateCourseID(this.props.id, event.target.value);
  }

  handleSubjectChange(event) {
    this.props.updateCourseSubject(this.props.id, event.target.value);
  }

  handleCreditsChange(event) {
    this.props.updateCourseCredits(this.props.id, event.target.value);
  }

  handleIsRequiredChange(event) {
    this.props.updateCourseIsRequired(this.props.id);
  }

  handleRankChange(event) {
    this.props.updateCourseRank(this.props.id, event.target.value);
  }

  updateRankSelectOptions() {
    let items = [];
    items = this.props.updateRankSelectOptions();
    return items;
  }

  updateSectionProfessor(sectionId, professor) {
    this.props.updateSectionProfessor(this.props.id, sectionId, professor);
  }

  updateSectionStartTime(sectionId, startTime) {
    this.props.updateSectionStartTime(this.props.id, sectionId, startTime);
  }

  updateSectionEndTime(sectionId, endTime) {
    this.props.updateSectionEndTime(this.props.id, sectionId, endTime);
  }

  updateSectionDays(sectionId, day) {
    this.props.updateSectionDays(this.props.id, sectionId, day);
  }

  createNewSection() {
    this.props.createNewSection(this.props.id);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Course Name" inputProps={{ 'aria-label': 'description' }} 
            value={this.props.name} onChange={this.handleNameChange} />
          <Input placeholder="Course ID" inputProps={{ 'aria-label': 'description' }}
            value={this.props.courseID} onChange={this.handleIDChange} />
          <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
            value={this.props.subject} onChange={this.handleSubjectChange} />
          <TextField placeholder="Credits" value={this.props.credits} onChange={this.handleCreditsChange}
            InputProps={{ inputComponent: NumberFormatCustom }} />
          <FormControlLabel
            control={<Switch checked={this.props.isRequired} onChange={this.handleIsRequiredChange} />}
            label="Required"/>

          <FormControl>
            <InputLabel>Rank</InputLabel>
            <Select onChange={this.handleRankChange} value={this.props.selected}>
              {this.updateRankSelectOptions()}
            </Select>
          </FormControl>

          {this.props.sections.map((section, index) => (
            <Section
              id={index}
              key={index}
              section={section}
              updateSectionProfessor={this.updateSectionProfessor}
              updateSectionStartTime={this.updateSectionStartTime}
              updateSectionEndTime={this.updateSectionEndTime}
              updateSectionDays={this.updateSectionDays}/>))}
          <button onClick={this.createNewSection}>Add Section</button>
        </CardContent>
      </Card>
    );
  }
}

export default Course;
