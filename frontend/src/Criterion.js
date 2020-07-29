import React, {PureComponent} from 'react';
import { Card, CardContent } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import SubjectPreference from './SubjectPreference';
import TimePreference from './TimePreference';

export default class Criterion extends PureComponent {
  render() {
    return (
      <Card>
        <CardContent>
          {this.props.times.map((timePreference, index) => (
            <TimePreference
              id={index}
              key={index}
              timePreference={timePreference}
              startTime={this.props.times[index].startTime}
              endTime={this.props.times[index].endTime}
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
