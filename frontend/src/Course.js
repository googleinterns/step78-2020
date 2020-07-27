import React from 'react';
import TextField from '@material-ui/core/TextField';
import { Input, Card, CardContent } from '@material-ui/core';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import LectureSection from './LectureSection';
import LabSection from './LabSection';
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

    this.updateLectureSectionProfessor = this.updateLectureSectionProfessor.bind(this);
    this.updateLectureSectionStartTime = this.updateLectureSectionStartTime.bind(this);
    this.updateLectureSectionEndTime = this.updateLectureSectionEndTime.bind(this);
    this.updateLectureSectionDays = this.updateLectureSectionDays.bind(this);
    this.createNewLectureSection = this.createNewLectureSection.bind(this);
    this.deleteLectureSection = this.deleteLectureSection.bind(this);

    this.updateLabSectionProfessor = this.updateLabSectionProfessor.bind(this);
    this.updateLabSectionStartTime = this.updateLabSectionStartTime.bind(this);
    this.updateLabSectionEndTime = this.updateLabSectionEndTime.bind(this);
    this.updateLabSectionDays = this.updateLabSectionDays.bind(this);
    this.createNewLabSection = this.createNewLabSection.bind(this);
    this.deleteLabSection = this.deleteLabSection.bind(this);
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
    let items = [];
    items = this.props.updateRankSelectOptions(this.props.id);
    return items;
  }

  updateLectureSectionProfessor(sectionId, professor) {
    this.props.updateLectureSectionProfessor(this.props.id, sectionId, professor);
  }

  updateLectureSectionStartTime(sectionId, startTime) {
    this.props.updateLectureSectionStartTime(this.props.id, sectionId, startTime);
  }

  updateLectureSectionEndTime(sectionId, endTime) {
    this.props.updateLectureSectionEndTime(this.props.id, sectionId, endTime);
  }

  updateLectureSectionDays(sectionId, day) {
    this.props.updateLectureSectionDays(this.props.id, sectionId, day);
  }

  createNewLectureSection() {
    this.props.createNewLectureSection(this.props.id);
  }

  deleteLectureSection(sectionId) {
    this.props.deleteLectureSection(this.props.id, sectionId);
  }

  updateLabSectionProfessor(sectionId, professor) {
    this.props.updateLabSectionProfessor(this.props.id, sectionId, professor);
  }

  updateLabSectionStartTime(sectionId, startTime) {
    this.props.updateLabSectionStartTime(this.props.id, sectionId, startTime);
  }

  updateLabSectionEndTime(sectionId, endTime) {
    this.props.updateLabSectionEndTime(this.props.id, sectionId, endTime);
  }

  updateLabSectionDays(sectionId, day) {
    this.props.updateLabSectionDays(this.props.id, sectionId, day);
  }

  createNewLabSection() {
    this.props.createNewLabSection(this.props.id);
  }

  deleteLabSection(sectionId) {
    this.props.deleteLabSection(this.props.id, sectionId);
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input placeholder="Course Name" inputProps={{ 'aria-label': 'description' }} 
            value={this.props.name} onChange={this.handleNameChange} 
          />
          <Input placeholder="Course ID" inputProps={{ 'aria-label': 'description' }}
            value={this.props.courseID} onChange={this.handleIDChange} 
          />
          <IconButton aria-label="delete" onClick={this.handleDeleteCourse}>
            <DeleteIcon />
          </IconButton>
          <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }}
            value={this.props.subject} onChange={this.handleSubjectChange} 
          />
          <TextField placeholder="Credits" value={this.props.credits} onChange={this.handleCreditsChange}
            InputProps={{ inputComponent: NumberFormatCustom }} 
          />
          <br/>
          <FormControl>
            <InputLabel>Rank</InputLabel>
            <Select onChange={this.handleRankChange} value={this.props.selected}>
              <MenuItem key="label" value="label" disabled>order courses by preference</MenuItem>
              {this.updateRankSelectOptions()}
            </Select>
          </FormControl>
          <FormControlLabel
            control={<Switch checked={this.props.isRequired} onChange={this.handleIsRequiredChange} />}
            label="Required"
          />
          <p>Lecture Sections</p>
          {this.props.lectureSections.map((lectureSection, index) => (
            <LectureSection
              id={index}
              key={index}
              lectureSection={lectureSection}
              professor={this.props.lectureSections[index].professor}
              startTime={this.props.lectureSections[index].startTime}
              endTime={this.props.lectureSections[index].endTime}
              sun={this.props.lectureSections[index].days.indexOf(0) !== -1}
              mon={this.props.lectureSections[index].days.indexOf(1) !== -1}
              tue={this.props.lectureSections[index].days.indexOf(2) !== -1}
              wed={this.props.lectureSections[index].days.indexOf(3) !== -1}
              thur={this.props.lectureSections[index].days.indexOf(4) !== -1}
              fri={this.props.lectureSections[index].days.indexOf(5) !== -1}
              sat={this.props.lectureSections[index].days.indexOf(6) !== -1}
              updateLectureSectionProfessor={this.updateLectureSectionProfessor}
              updateLectureSectionStartTime={this.updateLectureSectionStartTime}
              updateLectureSectionEndTime={this.updateLectureSectionEndTime}
              updateLectureSectionDays={this.updateLectureSectionDays}
              deleteLectureSection={this.deleteLectureSection}
            />))}
          <Button onClick={this.createNewLectureSection}>+ section</Button>
          <p>Lab / Recitation Sections (optional)</p>
          {this.props.labSections.map((labSection, index) => (
            <LabSection
              id={index}
              key={index}
              labSection={labSection}
              professor={this.props.labSections[index].professor}
              startTime={this.props.labSections[index].startTime}
              endTime={this.props.labSections[index].endTime}
              sun={this.props.labSections[index].days.indexOf(0) !== -1}
              mon={this.props.labSections[index].days.indexOf(1) !== -1}
              tue={this.props.labSections[index].days.indexOf(2) !== -1}
              wed={this.props.labSections[index].days.indexOf(3) !== -1}
              thur={this.props.labSections[index].days.indexOf(4) !== -1}
              fri={this.props.labSections[index].days.indexOf(5) !== -1}
              sat={this.props.labSections[index].days.indexOf(6) !== -1}
              updateLabSectionProfessor={this.updateLabSectionProfessor}
              updateLabSectionStartTime={this.updateLabSectionStartTime}
              updateLabSectionEndTime={this.updateLabSectionEndTime}
              updateLabSectionDays={this.updateLabSectionDays}
              deleteLabSection={this.deleteLabSection}
            />))}
          <Button onClick={this.createNewLabSection}>+ lab section</Button>
        </CardContent>
      </Card>
    );
  }
}

export default Course;
