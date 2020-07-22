import React from 'react';
import { Card, CardContent } from '@material-ui/core';
import SubjectPreference from './SubjectPreference';
import TimePreference from './TimePreference';

class Criterion extends React.Component {
  constructor(props) {
    super(props);

    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
  }

  updateTimeStartPreference(id, startTime) {
    this.props.updateTimeStartPreference(id, startTime);
  }

  updateTimeEndPreference(id, endTime) {
    this.props.updateTimeEndPreference(id, endTime);
  }
  
  updateSubjectPreference(subject) {
    this.props.updateSubjectPreference(subject);
  }
  
  render() {
    return (
      <Card>
        <CardContent>
          <Card>
            <CardContent>
              {this.props.times.map((timePreference, index) => (
              <TimePreference
                id={index}
                key={index}
                timePreference={timePreference}
                updateTimeStartPreference={this.updateTimeStartPreference}
                updateTimeEndPreference={this.updateTimeEndPreference}/>))}
            </CardContent>
          </Card>
          <button onClick={this.props.createNewTimePreference}>Add time-block</button>
          <SubjectPreference 
            subject={this.props.subject}
            updateSubjectPreference={this.updateSubjectPreference}/>
        </CardContent>
      </Card>
    )
  }
}

export default Criterion;
