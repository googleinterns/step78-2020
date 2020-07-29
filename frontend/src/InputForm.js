import React from 'react';
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

  updateLectureSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...lectureSection};
            }

            return {...lectureSection, professor: sectionProfessor};
          })
        }
      })
    }));
  }

  updateLectureSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...lectureSection};
            }

            return {...lectureSection, startTime: sectionStartTime};
          })
        }
      })
    }));
  }

  updateLectureSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...lectureSection};
            }

            return {...lectureSection, endTime: sectionEndTime};
          })
        }
      })
    }));
  }

  updateLectureSectionDays(courseID, sectionID, day) {
    var days = [];
    var index = 0;

    this.state.courses.map((course, courseIndex) => {
      if (courseIndex !== courseID) {
        return {...course};
      }

      return {
        ...course,
        lectureSections: this.state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
          if (sectionIndex !== sectionID) {
            return {...lectureSection};
          }

          days = [...this.state.courses[courseIndex].lectureSections[sectionIndex].days];
          if (days.includes(day)) {
            index = days.indexOf(day);
            return days.splice(index, 1);
          }

          return days.push(day);
        })
      }
    });

    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          lectureSections: state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...lectureSection};
            }

            return {...lectureSection, days: days}
          })
        }
      })
    }));
  }

  updateLabSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...labSection};
            }

            return {...labSection, professor: sectionProfessor};
          })
        }
      })
    }));
  }

  updateLabSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...labSection};
            }

            return {...labSection, startTime: sectionStartTime};
          })
        }
      })
    }));
  }

  updateLabSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...labSection};
            }

            return {...labSection, endTime: sectionEndTime};
          })
        }
      })
    }));
  }

  updateLabSectionDays(courseID, sectionID, day) {
    var days = [];
    var index = 0;

    this.state.courses.map((course, courseIndex) => {
      if (courseIndex !== courseID) {
        return {...course};
      }

      return {
        ...course,
        labSections: this.state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
          if (sectionIndex !== sectionID) {
            return {...labSection};
          }

          days = [...this.state.courses[courseIndex].labSections[sectionIndex].days];
          if (days.includes(day)) {
            index = days.indexOf(day);
            return days.splice(index, 1);
          }

          return days.push(day);
        })
      }
    });

    this.setState(state => ({
      ...this.state,
      courses: state.courses.map((course, courseIndex) => {
        if (courseIndex !== courseID) {
          return {...course};
        }

        return {
          ...course,
          labSections: state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
            if (sectionIndex !== sectionID) {
              return {...labSection};
            }

            return {...labSection, days: days}
          })
        }
      })
    }));
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
    }));
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
    }));
  }

  updateSubjectPreference(preferredSubject) {
    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        preferredSubject: preferredSubject
      }
    }));
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
    }));
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
    }));
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
    }));
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
    }));
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
      labSections: []
    }

    this.setState({
      ...this.state,
      courses: this.state.courses.concat(defaultCourse)
    });
  }

  createNewLectureSection(id) {
    const defaultSection = {
      professor: "",
      startTime: "",
      endTime: "",
      days: []
    }; 
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...course, lectureSections: this.state.courses[index].lectureSections.concat(defaultSection)})
          : course)
    });
  }

  createNewLabSection(id) {
    const defaultSection = {
      professor: "",
      startTime: "",
      endTime: "",
      days: []
    };

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id
          ? ({...course, labSections: this.state.courses[index].labSections.concat(defaultSection)})
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
    }));
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

    this.state.courses.map((course, courseIndex) => {
      if (courseIndex !== courseID) {
        return {...course};
      }

      lectureSections = [...this.state.courses[courseIndex].lectureSections];
      return {
        ...course, 
        lectureSections: this.state.courses[courseIndex].lectureSections.map((lectureSection, sectionIndex) => {
          if (sectionIndex !== sectionID) {
            return {...lectureSection};
          }
        
          return lectureSections.splice(sectionIndex, 1);
        })
      }
    });

    this.setState({
      ...this.state,
        courses: this.state.courses.map((course, index) =>
          index === courseID
            ? ({...course, lectureSections: lectureSections})
            : course)
    });
  }

  deleteLabSection(courseID, sectionID) {
    var labSections = [];

    this.state.courses.map((course, courseIndex) => {
      if (courseIndex !== courseID) {
        return {...course};
      }

      labSections = [...this.state.courses[courseIndex].labSections];
      return {
        ...course, 
        labSections: this.state.courses[courseIndex].labSections.map((labSection, sectionIndex) => {
          if (sectionIndex !== sectionID) {
            return {...labSection};
          }
        
          return labSections.splice(sectionIndex, 1);
        })
      }
    });
    
    this.setState({
      ...this.state,
        courses: this.state.courses.map((course, index) =>
          index === courseID
            ? ({...course, labSections: labSections})
            : course)
    });
  }

  deleteTimePreference(id) {
    var timePreferences = [...this.state.criterion.timePreferences];
    this.state.criterion.timePreferences.map((timePreference, index) =>
      index === id
        ? (timePreferences.splice(index, 1))
        : timePreference);

    this.setState(state => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: timePreferences
      }
    }));
  }

  submit() {
    let submitState = JSON.parse(JSON.stringify(this.state));
    submitState.courses = submitState.courses.map((course) => this.convertCourseSections(course));
    
    let inputtedCourseScores = {}; 
    submitState.courses.forEach((course) => {
      inputtedCourseScores[course.name] = course.rank;
    });
    submitState.criterion['courseScores'] = inputtedCourseScores;

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
    course['lectureSections'] = course.lectureSections.map((lectureSection) => {
      let newSection = {professor: lectureSection['professor'], meetingTimes: []};

      lectureSection.days.forEach(day => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, lectureSection.startTime, lectureSection.endTime));
      });
      return newSection;
    });

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
