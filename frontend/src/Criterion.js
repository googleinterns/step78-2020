import React from 'react';
import { Card, CardContent } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import SubjectPreference from './SubjectPreference';
import TimePreference from './TimePreference';

class Criterion extends React.Component {
  constructor(props) {
    super(props);

    this.updateTimeStartPreference = this.updateTimeStartPreference.bind(this);
    this.updateTimeEndPreference = this.updateTimeEndPreference.bind(this);
    this.updateSubjectPreference = this.updateSubjectPreference.bind(this);
    this.deleteTimePreference = this.deleteTimePreference.bind(this);
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

  deleteTimePreference(id) {
    this.props.deleteTimePreference(id);
  }
  
  render() {
    return (
      <Card>
        <CardContent>
          {this.props.times.map((timePreference, index) => (
          <TimePreference
            id={index}
            key={index}
            timePreference={timePreference}
            updateTimeStartPreference={this.updateTimeStartPreference}
            updateTimeEndPreference={this.updateTimeEndPreference}
            deleteTimePreference={this.deleteTimePreference}/>))}
          <Button onClick={this.props.createNewTimePreference}>+ time-block</Button>
          <SubjectPreference 
            subject={this.props.subject}
            updateSubjectPreference={this.updateSubjectPreference}/>
        </CardContent>
      </Card>
    )
  }
}

export default Criterion;
