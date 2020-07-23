import React from 'react';
import { Card, CardContent } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import SubjectPreference from './SubjectPreference';
import TimePreference from './TimePreference';

class Criterion extends React.Component { 
  render() {
    return (
      <Card>
        <CardContent>
          {this.props.times.map((timePreference, index) => (
            <TimePreference
              id={index}
              key={index}
              timePreference={timePreference}
              updateTimeStartPreference={this.props.updateTimeStartPreference}
              updateTimeEndPreference={this.props.updateTimeEndPreference}
              deleteTimePreference={this.props.deleteTimePreference}
            />))}
          <Button onClick={this.props.createNewTimePreference}>+ time-block</Button>
          <SubjectPreference 
            subject={this.props.subject}
            updateSubjectPreference={this.props.updateSubjectPreference}
          />
        </CardContent>
      </Card>
    )
  }
}

export default Criterion;
