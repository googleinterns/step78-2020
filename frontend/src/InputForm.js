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
    this.createNewTimePreference = this.createNewTimePreference.bind(this);
    
    // courses
    this.updateCourseName = this.updateCourseName.bind(this);
    this.updateCourseID = this.updateCourseID.bind(this);
    this.updateCourseSubject = this.updateCourseSubject.bind(this);
    this.updateCourseCredits = this.updateCourseCredits.bind(this);
    this.updateCourseIsRequired = this.updateCourseIsRequired.bind(this);
    this.updateSectionProfessor = this.updateSectionProfessor.bind(this);
    this.updateSectionStartTime = this.updateSectionStartTime.bind(this);
    this.updateSectionEndTime = this.updateSectionEndTime.bind(this);

    // criterion
    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
    
    // basic info
    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);

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
      sections: [{
        professor: "",
        startTime: "",
        endTime: ""
      }]
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.concat(defaultCourse)
    });
  }

  createNewSection(id) {
    const defaultSection = {
      professor: "",
      startTime: "",
      endTime: ""
    }

    this.setState(state => ({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...state.courses, 
            sections: this.state.courses[index].sections.concat(defaultSection)})
          : course)
    }))
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

  submit() {
    var json = JSON.stringify(this.state);
    console.log(json);
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
            updateCourseName={this.updateCourseName}
            updateCourseID={this.updateCourseID}
            updateCourseSubject={this.updateCourseSubject}
            updateCourseCredits={this.updateCourseCredits}
            updateCourseIsRequired={this.updateCourseIsRequired}
            updateSectionProfessor={this.updateSectionProfessor}
            updateSectionStartTime={this.updateSectionStartTime}
            updateSectionEndTime={this.updateSectionEndTime}
            createNewSection={this.createNewSection}/>))}
        <button onClick={this.createNewCourse}>Add Course</button>
        <h2>Preferences</h2>
        <Criterion 
          times={this.state.criterion.timePreferences}
          subject={this.state.criterion.preferredSubject}
          createNewTimePreference={this.createNewTimePreference}
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
        <button onClick={this.submit}>Submit</button>
      </div>)
  }
}

export default InputForm;
