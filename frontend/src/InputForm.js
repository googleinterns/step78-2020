import React from 'react';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Course from './Course';
import Criterion from './Criterion';
import BasicInfo from './BasicInfo';
import './classes.css';

class InputForm extends React.Component {
  constructor(props) {
    super(props);
    this.state =
      JSON.parse(window.localStorage.getItem('inputFormState')) ||
      {
        courses: [{
          name: '',
          courseID: '',
          subject: '',
          credits: '',
          isRequired: false,
          rank: '',
          lectureSections: [{
            professor: '',
            startTime: '',
            endTime: '',
            days: [],
          }],
          labSections: [],
        }],
        criterion: {
          timePreferences: {
            timeBefore: '',
            timeAfter: '',
          },
          preferredSubject: '',
        },
        basicInfo: {
          credits: {
            minCredits: 12,
            maxCredits: 18,
          },
          termDates: {
            startDate: '',
            endDate: '',
          },
        },
      };

    this.createNewCourse = this.createNewCourse.bind(this);
    this.createNewLectureSection = this.createNewLectureSection.bind(this);
    this.createNewLabSection = this.createNewLabSection.bind(this);
    this.deleteCourse = this.deleteCourse.bind(this);
    this.deleteLectureSection = this.deleteLectureSection.bind(this);
    this.deleteLabSection = this.deleteLabSection.bind(this);

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
    this.updateTimeBeforePreference = this.updateTimeBeforePreference.bind(this);
    this.updateTimeAfterPreference = this.updateTimeAfterPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);

    // basic info
    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);

    this.submit = this.submit.bind(this);
  }

  componentDidUpdate() {
    window.localStorage.setItem(
      'inputFormState',
      JSON.stringify(this.state),
    );
  }

  updateCourseName(id, courseName) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, name: courseName}) :
          course),
    });
  }

  updateCourseID(id, courseID) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, courseID: courseID}) :
          course),
    });
  }

  updateCourseSubject(id, courseSubject) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, subject: courseSubject}) :
          course),
    });
  }

  updateCourseCredits(id, courseCredits) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, credits: courseCredits}) :
          course),
    });
  }

  updateCourseIsRequired(id) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, isRequired: !this.state.courses[index].isRequired}) :
          course),
    });
  }

  updateCourseRank(id, selected) {
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, rank: selected}) :
          course),
    });
  }

  updateLectureSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLectureSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLectureSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLectureSectionDays(courseID, sectionID, day) {
    let days = [];
    let index = 0;

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
        }),
      };
    });

    this.setState((state) => ({
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

            return {...lectureSection, days: days};
          }),
        };
      }),
    }));
  }

  updateLabSectionProfessor(courseID, sectionID, sectionProfessor) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLabSectionStartTime(courseID, sectionID, sectionStartTime) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLabSectionEndTime(courseID, sectionID, sectionEndTime) {
    this.setState((state) => ({
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
          }),
        };
      }),
    }));
  }

  updateLabSectionDays(courseID, sectionID, day) {
    let days = [];
    let index = 0;

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
        }),
      };
    });

    this.setState((state) => ({
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

            return {...labSection, days: days};
          }),
        };
      }),
    }));
  }

  updateTimeBeforePreference(timeBeforePreference) {
    this.setState((state) => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: {
          ...state.criterion.timePreferences,
          timeBefore: timeBeforePreference,
        },
      },
    }));
  }

  updateTimeAfterPreference(timeAfterPreference) {
    this.setState((state) => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        timePreferences: {
          ...state.criterion.timePreferences,
          timeAfter: timeAfterPreference,
        },
      },
    }));
  }

  updateSubjectPreference(preferredSubject) {
    this.setState((state) => ({
      ...this.state,
      criterion: {
        ...state.criterion,
        preferredSubject: preferredSubject,
      },
    }));
  }

  updateMinCredits(minCredits) {
    this.setState((state) => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        credits: {
          ...state.basicInfo.credits,
          minCredits: minCredits,
        },
      },
    }));
  }

  updateMaxCredits(maxCredits) {
    this.setState((state) => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        credits: {
          ...state.basicInfo.credits,
          maxCredits: maxCredits,
        },
      },
    }));
  }

  updateTermStartDate(termStartDate) {
    this.setState((state) => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        termDates: {
          ...state.basicInfo.termDates,
          startDate: termStartDate,
        },
      },
    }));
  }

  updateTermEndDate(termEndDate) {
    this.setState((state) => ({
      ...this.state,
      basicInfo: {
        ...state.basicInfo,
        termDates: {
          ...state.basicInfo.termDates,
          endDate: termEndDate,
        },
      },
    }));
  }

  createNewCourse() {
    const defaultCourse = {
      name: '',
      courseID: '',
      subject: '',
      credits: '',
      isRequired: false,
      rank: '',
      lectureSections: [{
        professor: '',
        startTime: '',
        endTime: '',
        days: [],
      }],
      labSections: [],
    };

    this.setState({
      ...this.state,
      courses: this.state.courses.concat(defaultCourse),
    });
  }

  createNewLectureSection(id) {
    const defaultSection = {
      professor: '',
      startTime: '',
      endTime: '',
      days: [],
    };
    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, lectureSections: this.state.courses[index].lectureSections.concat(defaultSection)}) :
          course),
    });
  }

  createNewLabSection(id) {
    const defaultSection = {
      professor: '',
      startTime: '',
      endTime: '',
      days: [],
    };

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === id ?
          ({...course, labSections: this.state.courses[index].labSections.concat(defaultSection)}) :
          course),
    });
  }

  deleteCourse(id) {
    const courses = [...this.state.courses];
    this.state.courses.map((course, index) =>
      index === id ?
        (courses.splice(index, 1),
        this.setState({...this.state, courses: courses})) :
        course);
  }

  deleteLectureSection(courseID, sectionID) {
    let lectureSections = [];

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
        }),
      };
    });

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === courseID ?
          ({...course, lectureSections: lectureSections}) :
          course),
    });
  }

  deleteLabSection(courseID, sectionID) {
    let labSections = [];

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
        }),
      };
    });

    this.setState({
      ...this.state,
      courses: this.state.courses.map((course, index) =>
        index === courseID ?
          ({...course, labSections: labSections}) :
          course),
    });
  }

  submit() {
    const submitState = JSON.parse(JSON.stringify(this.state));
    submitState.courses = submitState.courses.map((course) => this.convertCourseSections(course));

    const inputtedCourseScores = {};
    submitState.courses.forEach((course) => {
      inputtedCourseScores[course.name] = course.rank;
    });
    submitState.criterion['courseScores'] = inputtedCourseScores;

    submitState.criterion.timePreferences.timeBefore = this.timeToTimeRange(0, '00:01', submitState.criterion.timePreferences.timeBefore);
    submitState.criterion.timePreferences.timeAfter = this.timeToTimeRange(0, submitState.criterion.timePreferences.timeAfter, '23:59');

    fetch('/handleUserInput', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(submitState),
    }).then((response) => response.json())
      .then((responseSchedules) => {
        this.props.setScheduleList(responseSchedules);
        const {startDate, endDate} = this.state.basicInfo.termDates;
        this.props.setTermDates(startDate, endDate);
        this.props.handleNext();
      });
  }

  convertCourseSections(course) {
    course['lectureSections'] = course.lectureSections.map((lectureSection) => {
      const newSection = {professor: lectureSection['professor'], meetingTimes: []};

      lectureSection.days.forEach((day) => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, lectureSection.startTime, lectureSection.endTime));
      });
      return newSection;
    });

    course['labSections'] = course.labSections.map((labSection) => {
      const newSection = {professor: labSection['professor'], meetingTimes: []};
      labSection.days.forEach((day) => {
        newSection.meetingTimes.push(this.timeToTimeRange(day, labSection.startTime, labSection.endTime));
      });
      return newSection;
    });

    return course;
  }

  timeToTimeRange(day, startTime, endTime) {
    const startHourMin = startTime.split(':');
    const startHour = parseInt(startHourMin[0]);
    const startMin = parseInt(startHourMin[1]);

    const endHourMin = endTime.split(':');
    const endHour = parseInt(endHourMin[0]);
    const endMin = parseInt(endHourMin[1]);

    let start = (startHour * 60) + startMin;
    const end = (endHour * 60) + endMin;
    const duration = start < end ? end - start :
      (end + 24 * 60) - start;

    start += day * 24 * 60;

    return {start: start, duration: duration};
  }

  render() {
    return (
      <div className="gridRoot">
        <Typography variant="h5" gutterBottom>
          Courses:
        </Typography>

        <Grid container direction="row" justify="flex-start" alignItems="flex-start">
          {this.state.courses.map((course, index) => (
            <Grid item xs>
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
              />
            </Grid>))}
          <Button onClick={this.createNewCourse}>+ Course</Button>
        </Grid>
        <Typography variant="h5" gutterBottom>
          Other Information:
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs>
            <Criterion
              times={this.state.criterion.timePreferences}
              subject={this.state.criterion.preferredSubject}
              updateSubjectPreference={this.updateSubjectPreference}
              updateTimeBeforePreference={this.updateTimeBeforePreference}
              updateTimeAfterPreference={this.updateTimeAfterPreference}
            />
          </Grid>
          <Grid item xs>
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
          </Grid>
        </Grid>
        <Button size="large" variant="contained" color="primary" onClick={this.submit}>Submit</Button>
      </div>);
  }
}

export default InputForm;
