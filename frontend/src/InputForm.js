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
        credits: "",
        isRequired: false,
        rank: "",
        lectureSections: [{
          professor: "",
          startTime: "",
          endTime: "",
          days: []
        }],
        labSections: []
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
    this.createNewLectureSection = this.createNewLectureSection.bind(this);
    this.createNewLabSection = this.createNewLabSection.bind(this);
    this.createNewTimePreference = this.createNewTimePreference.bind(this);
    this.deleteCourse = this.deleteCourse.bind(this);
    this.deleteLectureSection = this.deleteLectureSection.bind(this);
    this.deleteLabSection = this.deleteLabSection.bind(this);
    this.deleteTimePreference = this.deleteTimePreference.bind(this);
    
    // courses
    this.updateCourseName = this.updateCourseName.bind(this);
    this.updateCourseID = this.updateCourseID.bind(this);
    this.updateCourseSubject = this.updateCourseSubject.bind(this);
    this.updateCourseCredits = this.updateCourseCredits.bind(this);
    this.updateCourseIsRequired = this.updateCourseIsRequired.bind(this);
    this.updateCourseRank = this.updateCourseRank.bind(this);
    this.updateRankSelectOptions = this.updateRankSelectOptions.bind(this);
    
    // lecture sections
    this.updateLectureSectionProfessor = this.updateLectureSectionProfessor.bind(this);
    this.updateLectureSectionStartTime = this.updateLectureSectionStartTime.bind(this);
    this.updateLectureSectionEndTime = this.updateLectureSectionEndTime.bind(this);
    this.updateLectureSectionDays = this.updateLectureSectionDays.bind(this);

    // lab sections
    this.updateLabSectionProfessor = this.updateLabSectionProfessor.bind(this);
    this.updateLabSectionStartTime = this.updateLabSectionStartTime.bind(this);
    this.updateLabSectionEndTime = this.updateLabSectionEndTime.bind(this);
    this.updateLabSectionDays = this.updateLabSectionDays.bind(this);

    // criterion
    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
    
    // basic info
    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);

    this.convertCourseLectureSections = this.convertCourseLectureSections.bind(this);
    this.convertCourseLabSections = this.convertCourseLabSections.bind(this);
    this.submit = this.submit.bind(this);
  }

  // used to keep track of which ranks have been selected, so no two courses have the same rank 
  selectedRanks = [];

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
    var previousSelected = this.state.courses[id].rank;
    if (previousSelected !== "") {
      var index = this.selectedRanks.indexOf(previousSelected);
      this.selectedRanks.splice(index, 1);
    }
    if (this.selectedRanks.indexOf(selected) === -1) {
      this.selectedRanks.push(selected);
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({ ...course, rank: selected })
          : this.state.courses[index].rank === selected
            ? ({...course, rank: ""})
            : course
        )
    });   
  }

  updateLectureSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...lectureSection, professor: sectionProfessor})
              : lectureSection)})
          : course)
    }))
  }

  updateLectureSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...lectureSection, startTime: sectionStartTime})
              : lectureSection)})
          : course)
    }))
  }

  updateLectureSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...lectureSection, endTime: sectionEndTime})
              : lectureSection)})
          : course)
    }))
  }

  updateLectureSectionDays(courseID, sectionID, day) {
    var days = [];
    var index = 0;
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? ({...course, lectureSections: this.state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
          sectionIndex === sectionID
            ? (
              days = [...this.state.courses[courseIndex].lectureSections[sectionIndex].days],
              days.includes(day)
                ? (
                  index = days.indexOf(day),
                  days.splice(index, 1)
                )
                : days = []
            )
            : lectureSection)})
        : course)

    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
            sectionIndex === sectionID
              ? (
                days.length > 0 
                  ? ({ ...lectureSection, days: days})
                  : ({ ...lectureSection, days: state.courses[courseIndex].lectureSections[sectionIndex].days.concat(day)})
              )
              : lectureSection)})
          : course)
    }))
  }

  updateLabSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...labSection, professor: sectionProfessor})
              : labSection)})
          : course)
    }))
  }

  updateLabSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...labSection, startTime: sectionStartTime})
              : labSection)})
          : course)
    }))
  }

  updateLabSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
            sectionIndex === sectionID
              ? ({...labSection, endTime: sectionEndTime})
              : labSection)})
          : course)
    }))
  }

  updateLabSectionDays(courseID, sectionID, day) {
    var days = [];
    var index = 0;
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? ({...course, labSections: this.state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
          sectionIndex === sectionID
            ? (
              days = [...this.state.courses[courseIndex].labSections[sectionIndex].days],
              days.includes(day)
                ? (
                  index = days.indexOf(day),
                  days.splice(index, 1)
                )
                : days = []
            )
            : labSection)})
        : course)

    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) =>
        courseIndex === courseID
          ? ({ ...course, labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
            sectionIndex === sectionID
              ? (
                days.length > 0 
                  ? ({ ...labSection, days: days})
                  : ({ ...labSection, days: state.courses[courseIndex].labSections[sectionIndex].days.concat(day)})
              )
              : labSection)})
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
      name: "",
      courseID: "",
      subject: "",
      credits: "",
      isRequired: false,
      rank: "",
      lectureSections: [{
        professor: "",
        startTime: "",
        endTime: "",
        days: []
      }],
      labSections: [{
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

  updateRankSelectOptions(id) {
    let items = [];   
    let numCourses = this.state.courses.length;     
    if (this.state.courses[id].rank !== "") {
      for (let j = 1; j <= numCourses; j++) {
        items.push(<MenuItem key={j} value={j}>{j}</MenuItem>)       
      }
      return items;
    } else {
      for (let j = 1; j <= numCourses; j++) {
        if (this.selectedRanks.indexOf(j) === -1) {
          items.push(<MenuItem key={j} value={j}>{j}</MenuItem>)
        } else {
          items.push(<MenuItem key={j} value={j} disabled>{j}</MenuItem>)
        }
      }
      return items;
    }
  }

  defaultSection = {
    professor: "",
    startTime: "",
    endTime: "",
    days: []
  }

  createNewLectureSection(id) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...course, lectureSections: this.state.courses[index].lectureSections.concat(this.defaultSection)})
          : course)
    });
  }

  createNewLabSection(id) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...course, labSections: this.state.courses[index].labSections.concat(this.defaultSection)})
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

  deleteLectureSection(courseID, sectionID) {
    var lectureSections = [];
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? (lectureSections = [...this.state.courses[courseIndex].lectureSections],
          this.state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) =>
            sectionIndex === sectionID
              ? (lectureSections.splice(sectionIndex, 1))
              : lectureSection)
        )
        : course)

    this.setState({
      ...this.state,
        courses: this.state.courses.map((course, index) =>
          index === courseID
            ? ({...course, lectureSections: lectureSections})
            : course)
    })
  }

  deleteLabSection(courseID, sectionID) {
    var labSections = [];
    this.state.courses.map((course, courseIndex) =>
      courseIndex === courseID
        ? (labSections = [...this.state.courses[courseIndex].labSections],
          this.state.courses[courseIndex].labSections.map((labSection, sectionIndex) =>
            sectionIndex === sectionID
              ? (labSections.splice(sectionIndex, 1))
              : labSection)
        )
        : course)

    this.setState({
      ...this.state,
        courses: this.state.courses.map((course, index) =>
          index === courseID
            ? ({...course, labSections: labSections})
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
    submitState.courses = submitState.courses.map((course) => this.convertCourseLectureSections(course));
    submitState.courses = submitState.courses.map((course) => this.convertCourseLabSections(course))

    submitState.criterion.timePreferences = submitState.criterion.timePreferences.map((times) => 
        this.timeToTimeRange(0, times.startTime, times.endTime));

    console.log(JSON.stringify(submitState))

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

  convertCourseLectureSections(course) {
    course['lectureSections'] = course.lectureSections.map((lectureSection) => {
      let newSection = {professor: lectureSection['professor'], meetingTimes: []};
      lectureSection.days.forEach(day => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, lectureSection.startTime, lectureSection.endTime));
      });
      return newSection;
    });
    
    return course;
  }

  convertCourseLabSections(course) {
    course['labSections'] = course.labSections.map((labSection) => {
      let newSection = {professor: labSection['professor'], meetingTimes: []};
      labSection.days.forEach(day => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, labSection.startTime, labSection.endTime));
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
            name={this.state.courses[index].name}
            courseID={this.state.courses[index].courseID}
            subject={this.state.courses[index].subject}
            credits={this.state.courses[index].credits}
            selected={this.state.courses[index].rank}
            isRequired={this.state.courses[index].isRequired}
            lectureSections={this.state.courses[index].lectureSections}
            labSections={this.state.courses[index].labSections}
            
            updateCourseName={this.updateCourseName}
            updateCourseID={this.updateCourseID}
            updateCourseSubject={this.updateCourseSubject}
            updateCourseCredits={this.updateCourseCredits}
            updateCourseIsRequired={this.updateCourseIsRequired}
            updateCourseRank={this.updateCourseRank}
            updateRankSelectOptions={this.updateRankSelectOptions}
            
            updateLectureSectionProfessor={this.updateLectureSectionProfessor}
            updateLectureSectionStartTime={this.updateLectureSectionStartTime}
            updateLectureSectionEndTime={this.updateLectureSectionEndTime}
            updateLectureSectionDays={this.updateLectureSectionDays}
            createNewLectureSection={this.createNewLectureSection}

            updateLabSectionProfessor={this.updateLabSectionProfessor}
            updateLabSectionStartTime={this.updateLabSectionStartTime}
            updateLabSectionEndTime={this.updateLabSectionEndTime}
            updateLabSectionDays={this.updateLabSectionDays}
            createNewLabSection={this.createNewLabSection}
            
            deleteCourse={this.deleteCourse}
            deleteLectureSection={this.deleteLectureSection}
            deleteLabSection={this.deleteLabSection}
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
