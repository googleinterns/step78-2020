import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
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
    this.handleDeleteCourse = this.handleDeleteCourse.bind(this);
    this.updateRankSelectOptions = this.updateRankSelectOptions.bind(this);

    this.updateSectionProfessor = this.updateSectionProfessor.bind(this);
    this.updateSectionStartTime = this.updateSectionStartTime.bind(this);
    this.updateSectionEndTime = this.updateSectionEndTime.bind(this);
    this.updateSectionDays = this.updateSectionDays.bind(this);
    this.createNewSection = this.createNewSection.bind(this);
    this.deleteSection = this.deleteSection.bind(this);
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

  handleDeleteCourse() {
    this.props.deleteCourse(this.props.id);
  }

  updateRankSelectOptions() {
    return this.props.updateRankSelectOptions();
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

  deleteSection(sectionId) {
    this.props.deleteSection(this.props.id, sectionId);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Course Name" value={this.props.name} onChange={this.handleNameChange} 
          />
          <Input placeholder="Course ID" value={this.props.courseID} onChange={this.handleIDChange} 
          />
          <IconButton aria-label="delete" onClick={this.handleDeleteCourse}>
            <DeleteIcon />
          </IconButton>
          <Input placeholder="Subject" value={this.props.subject} onChange={this.handleSubjectChange} 
          />
          <TextField placeholder="Credits" value={this.props.credits} onChange={this.handleCreditsChange}
            InputProps={{ inputComponent: NumberFormatCustom }} 
          />
          <br/>
          <FormControl>
            <InputLabel>Rank</InputLabel>
            <Select onChange={this.handleRankChange} value={this.props.selected}>
              {this.updateRankSelectOptions()}
            </Select>
          </FormControl>
          <FormControlLabel
            control={<Switch checked={this.props.isRequired} onChange={this.handleIsRequiredChange} />}
            label="Required"
          />
          {this.props.sections.map((section, index) => (
            <Section
              id={index}
              key={index}
              section={section}
              updateSectionProfessor={this.updateSectionProfessor}
              updateSectionStartTime={this.updateSectionStartTime}
              updateSectionEndTime={this.updateSectionEndTime}
              updateSectionDays={this.updateSectionDays}
              deleteSection={this.deleteSection}
            />))}
          <Button onClick={this.createNewSection}>+ section</Button>
        </CardContent>
      </Card>
    );
  }
}

export default Course;
