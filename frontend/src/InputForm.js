import React from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import Button from '@material-ui/core/Button';
import Course from './Course';
import Criterion from './Criterion';
import BasicInfo from './BasicInfo';

class InputForm extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      courses: [{
        name: "",
        courseID: "",
        subject: "",
        credits: 0,
        isRequired: false,
        rank: "",
        sections: [{
          professor: "",
          startTime: "",
          endTime: "",
          days: []
        }]
      }],
      criterion: {
        timePreferences: [{
          startTime: "",
          endTime: ""
        }],
        preferredSubject: ""
      },
      basicInfo: {
        credits: {
          minCredits: 12,
          maxCredits: 18
        },
        termDates: {
          startDate: "",
          endDate: ""
        }
      }
    };

    this.createNewCourse = this.createNewCourse.bind(this);
    this.createNewSection = this.createNewSection.bind(this);
    this.createNewTimePreference = this.createNewTimePreference.bind(this);
    this.deleteCourse = this.deleteCourse.bind(this);
    this.deleteSection = this.deleteSection.bind(this);
    this.deleteTimePreference = this.deleteTimePreference.bind(this);
    
    // courses
    this.updateCourseName = this.updateCourseName.bind(this);
    this.updateCourseID = this.updateCourseID.bind(this);
    this.updateCourseSubject = this.updateCourseSubject.bind(this);
    this.updateCourseCredits = this.updateCourseCredits.bind(this);
    this.updateCourseIsRequired = this.updateCourseIsRequired.bind(this);
    this.updateCourseRank = this.updateCourseRank.bind(this);
    this.updateRankSelectOptions = this.updateRankSelectOptions.bind(this);
    
    // sections
    this.updateSectionProfessor = this.updateSectionProfessor.bind(this);
    this.updateSectionStartTime = this.updateSectionStartTime.bind(this);
    this.updateSectionEndTime = this.updateSectionEndTime.bind(this);
    this.updateSectionDays = this.updateSectionDays.bind(this);

    // criterion
    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
    
    // basic info
    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);

    this.convertCourseSections = this.convertCourseSections.bind(this);
    this.submit = this.submit.bind(this);
  }

  updateCourseName(id, courseName) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, name: courseName })
          : course)
    });
  }

  updateCourseID(id, courseID) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, courseID: courseID })
          : course)
    });
  }

  updateCourseSubject(id, courseSubject) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, subject: courseSubject })
          : course)
    });
  }

  updateCourseCredits(id, courseCredits) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, credits: courseCredits })
          : course)
    });
  }

  updateCourseIsRequired(id) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, isRequired: !this.state.courses[index].isRequired })
          : course)
    });
  }

  updateCourseRank(id, selected) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, rank: selected })
          : course)
    });   
  }

  updateSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, sections: state.courses[courseIndex].sections.map((section, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...section, professor: sectionProfessor})
              : section)})
          : course)
    }))
  }

  updateSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, sections: state.courses[courseIndex].sections.map((section, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...section, startTime: sectionStartTime})
              : section)})
          : course)
    }))
  }

  updateSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, sections: state.courses[courseIndex].sections.map((section, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...section, endTime: sectionEndTime})
              : section)})
          : course)
    }))
  }

  updateSectionDays(courseID, sectionID, day) {
    var days = [];
    var index = 0;
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? ({...course, sections: this.state.courses[courseIndex].sections.map((section, sectionIndex) =>
          sectionIndex === sectionID
            ? (
              days = [...this.state.courses[courseIndex].sections[sectionIndex].days],
              days.includes(day)
                ? (
                  index = days.indexOf(day),
                  days.splice(index, 1)
                )
                : days = []
            )
            : section)})
        : course)

    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, sections: state.courses[courseIndex].sections.map((section, sectionIndex) =>
            sectionIndex === sectionID
              ? (
                days.length > 0 
                  ? ({ ...section, days: days})
                  : ({ ...section, days: state.courses[courseIndex].sections[sectionIndex].days.concat(day)})
              )
              : section)})
          : course)
    }))
  }

  updateTimeStartPreference(id, timePreferenceStartTime) {
    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: this.state.criterion.timePreferences.map((timePreference, index) =>
          index === id
            ? ({...timePreference, startTime: timePreferenceStartTime})
            : timePreference)
      }
    }))
  }

  updateTimeEndPreference(id, timePreferenceEndTime) {
    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: this.state.criterion.timePreferences.map((timePreference, index) =>
          index === id
            ? ({...timePreference, endTime: timePreferenceEndTime})
            : timePreference)
      }
    }))
  }

  updateSubjectPreference(preferredSubject) {
    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        preferredSubject: preferredSubject
      }
    }))
  }
  
  updateMinCredits(minCredits) {
    this.setState(state => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        credits: {
          ...state.basicInfo.credits, 
          minCredits: minCredits
        }
      }
    }))
  }

  updateMaxCredits(maxCredits) {
    this.setState(state => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        credits: {
          ...state.basicInfo.credits, 
          maxCredits: maxCredits
        }
      }
    }))
  }

  updateTermStartDate(termStartDate) {
    this.setState(state => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        termDates: {
          ...state.basicInfo.termDates, 
          startDate: termStartDate
        }
      }
    }))
  }

  updateTermEndDate(termEndDate) {
    this.setState(state => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        termDates: {
          ...state.basicInfo.termDates, 
          endDate: termEndDate
        }
      }
    }))
  }

  createNewCourse() {
    const defaultCourse = {
      name: "course name",
      courseID: "",
      subject: "",
      credits: 0,
      isRequired: false,
      rank: "",
      sections: [{
        professor: "",
        startTime: "",
        endTime: "",
        days: []
      }]
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.concat(defaultCourse)
    });
  }

  updateRankSelectOptions() {
     let items = [];   
     let numCourses = this.state.courses.length;     
     for (let i = 1; i <= numCourses; i++) {             
          items.push(<MenuItem key={i} value={i}>{i}</MenuItem>);   
     }
     return items;
  }

  createNewSection(id) {
    const defaultSection = {
      professor: "",
      startTime: "",
      endTime: "",
      days: []
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...course, sections: this.state.courses[index].sections.concat(defaultSection)})
          : course)
    });
  }

  createNewTimePreference() {
    const defaultTimePreference = {
      startTime: "",
      endTime: ""
    }

    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: this.state.criterion.timePreferences.concat(defaultTimePreference)
      }
    }))
  }

  deleteCourse(id) {
    var courses = [...this.state.courses];
    this.state.courses.map((course, index) =>
      index === id
        ? (courses.splice(index, 1),
          this.setState({...this.state, courses: courses}))
        : course)
  }

  deleteSection(courseID, sectionID) {
    var sections = [];
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? (sections = [...this.state.courses[courseIndex].sections],
          this.state.courses[courseIndex].sections.map((section, sectionIndex) =>
            sectionIndex === sectionID
              ? (sections.splice(sectionIndex, 1))
              : section)
        )
        : course)

    this.setState({
      ...this.state,
        courses: this.state.courses.map((course, index) =>
          index === courseID
            ? ({...course, sections: sections})
            : course)
    })
  }

  deleteTimePreference(id) {
    var timePreferences = [...this.state.criterion.timePreferences];
    this.state.criterion.timePreferences.map((timePreference, index) =>
      index === id
        ? (timePreferences.splice(index, 1))
        : timePreference)

    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: timePreferences
      }
    }))
  }

  submit() {
    let submitState = JSON.parse(JSON.stringify(this.state));
    submitState.courses = submitState.courses.map((course) => this.convertCourseSections(course));

    submitState.criterion.timePreferences = submitState.criterion.timePreferences.map((times) => 
        this.timeToTimeRange(0, times.startTime, times.endTime));

    var scheduleList;
    fetch("/handleUserInput", {
      method:"POST",
      headers: {'Content-Type':'application/json'},
      body: JSON.stringify(submitState)
    }).then(response => response.json())
      .then(responseSchedules => {
        scheduleList = responseSchedules;
        console.log(JSON.stringify(scheduleList));
      });
  }

  convertCourseSections(course) {
    course['sections'] = course.sections.map((section) => {
      let newSection = {professor: section['professor'], meetingTimes: []};
      section.days.forEach(day => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, section.startTime, section.endTime));
      });
      return newSection;
    });
    
    return course;
  }

  timeToTimeRange(day, startTime, endTime) {
    let startHourMin = startTime.split(':');
    let startHour = parseInt(startHourMin[0]);
    let startMin = parseInt(startHourMin[1]);

    let endHourMin = endTime.split(':');
    let endHour = parseInt(endHourMin[0]);
    let endMin = parseInt(endHourMin[1]);

    let start = (startHour * 60) + startMin;
    let end = (endHour * 60) + endMin;
    let duration = start < end ? end - start 
        : (end + 24 * 60) - start;
    
    start += day * 24 * 60;

    return {start: start, duration: duration}
  }

  render() {
    return (
      <div>
        <h2>Courses</h2>
        {this.state.courses.map((course, index) => (
          <Course
            id={index}
            key={index}
            course={course}
            sections={this.state.courses[index].sections}
            isRequired={this.state.courses[index].isRequired}
            selected={this.state.courses[index].rank}
            
            updateCourseName={this.updateCourseName}
            updateCourseID={this.updateCourseID}
            updateCourseSubject={this.updateCourseSubject}
            updateCourseCredits={this.updateCourseCredits}
            updateCourseIsRequired={this.updateCourseIsRequired}
            updateCourseRank={this.updateCourseRank}
            updateRankSelectOptions={this.updateRankSelectOptions}
            
            updateSectionProfessor={this.updateSectionProfessor}
            updateSectionStartTime={this.updateSectionStartTime}
            updateSectionEndTime={this.updateSectionEndTime}
            updateSectionDays={this.updateSectionDays}
            createNewSection={this.createNewSection}
            deleteCourse={this.deleteCourse}
            deleteSection={this.deleteSection}
          />))}
        <Button onClick={this.createNewCourse}>+ Course</Button>
        <h2>Preferences</h2>
        <Criterion 
          times={this.state.criterion.timePreferences}
          subject={this.state.criterion.preferredSubject}
          createNewTimePreference={this.createNewTimePreference}
          updateSubjectPreference={this.updateSubjectPreference}
          updateTimeStartPreference={this.updateTimeStartPreference}
          updateTimeEndPreference={this.updateTimeEndPreference}
          deleteTimePreference={this.deleteTimePreference}
        />
        <h2>Other info</h2>
        <BasicInfo 
          minCredits={this.state.basicInfo.credits.minCredits}
          maxCredits={this.state.basicInfo.credits.maxCredits}
          startDate={this.state.basicInfo.termDates.startDate}
          endDate={this.state.basicInfo.termDates.endDate}
          updateMinCredits={this.updateMinCredits}
          updateMaxCredits={this.updateMaxCredits}
          updateTermStartDate={this.updateTermStartDate}
          updateTermEndDate={this.updateTermEndDate}
        />
        <Button onClick={this.submit}>Submit</Button>
      </div>)
  }
}

export default InputForm;
