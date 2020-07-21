import React from 'react';
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
        sections: [{
          professor: "",
          startTime: "",
          endTime: ""
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
    
    // courses
    this.updateCourseName = this.updateCourseName.bind(this);
    this.updateCourseID = this.updateCourseID.bind(this);
    this.updateCourseSubject = this.updateCourseSubject.bind(this);
    this.updateCourseCredits = this.updateCourseCredits.bind(this);

    // criterion
    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
    
    // basic info
    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);
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

  updateTimeStartPreference(id, startTime) {
    this.setState({
      ...this.state,
      timePreferences: this.state.criterion.timePreferences.map((timePreference, index) =>
        index === id
          ? ({ ...timePreference, startTime: startTime })
          : timePreference)
    });
  }

  updateTimeEndPreference(id, endTime) {
    this.setState({
      ...this.state,
      timePreferences: this.state.criterion.timePreferences.map((timePreference, index) =>
        index === id
          ? ({ ...timePreference, endTime: endTime })
          : timePreference)
    });
  }

  updateSubjectPreference(subject) {
    this.setState({...this.state, preferredSubject: subject});
  }
  
  updateMinCredits(minCred) {
    this.setState({...this.state, minCredits: 654321});
  }

  updateMaxCredits(maxCredits) {
    this.setState({...this.state, maxCredits: maxCredits});
  }

  updateTermStartDate(termStartDate) {
    this.setState({...this.state, startDate: termStartDate});
  }

  updateTermEndDate(termEndTime) {
    this.setState({...this.state, endDate: termEndTime});
  }

  createNewCourse() {
    const defaultCourse = {
      name: "course name",
      courseID: "",
      subject: "",
      credits: 0,
      isRequired: false,
      section: {
        professor: "",
        startTime: "",
        endTime: ""
      }
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.concat(defaultCourse)
    });
  }

  createNewSection() {
    const defaultSection = {
      professor: "",
      startTime: "",
      endTime: ""
    }

    this.setState({
      ...this.state,
      sections: this.state.courses[0].sections.concat(defaultSection)
    });
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
            updateCourseName={this.updateCourseName}
            updateCourseID={this.updateCourseID}
            updateCourseSubject={this.updateCourseSubject}
            updateCourseCredits={this.updateCourseCredits}/>))}
        <button onClick={this.createNewCourse}>Add Course</button>
        <h2>Preferences</h2>
        <Criterion 
          subject={this.state.criterion.preferredSubject}

          updateSubjectPreference={this.updateSubjectPreference}
          updateTimeStartPreference={this.updateTimeStartPreference}
          updateTimeEndPreference={this.updateTimeEndPreference}/>
        <h2>Other info</h2>
        <BasicInfo 
          minCredits={this.state.basicInfo.credits.minCredits}
          maxCredits={this.state.basicInfo.credits.maxCredits}
          startDate={this.state.basicInfo.termDates.startDate}
          endDate={this.state.basicInfo.termDates.endDate}

          updateMinCredits={this.updateMinCredits}
          updateMaxCredits={this.updateMaxCredits}
          updateTermStartDate={this.updateTermStartDate}
          updateTermEndDate={this.updateTermEndDate}/>
      </div>)
  }
}

export default InputForm;
