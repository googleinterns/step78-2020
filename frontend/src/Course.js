import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
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

    this.updateSectionProfessor = this.updateSectionProfessor.bind(this);
    this.updateSectionStartTime = this.updateSectionStartTime.bind(this);
    this.updateSectionEndTime = this.updateSectionEndTime.bind(this);
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

  updateSectionProfessor(sectionId, professor) {
    this.props.updateSectionProfessor(this.props.id, sectionId, professor);
  }

  updateSectionStartTime(sectionId, startTime) {
    this.props.updateSectionStartTime(this.props.id, sectionId, startTime);
  }

  updateSectionEndTime(sectionId, endTime) {
    this.props.updateSectionEndTime(this.props.id, sectionId, endTime);
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
          {this.props.sections.map((section, index) => (
            <Section
              id={index}
              key={index}
              section={section}
              updateSectionProfessor={this.updateSectionProfessor}
              updateSectionStartTime={this.updateSectionStartTime}
              updateSectionEndTime={this.updateSectionEndTime}/>))}
          <button onClick={this.createNewSection}>Add Section</button>
        </CardContent>
      </Card>
    );
  }
}

export default Course;
