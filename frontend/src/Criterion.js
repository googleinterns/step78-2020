import React, {PureComponent} from 'react';
import {Card, CardContent, CardHeader} from '@material-ui/core';
import SubjectPreference from './SubjectPreference';
import TimePreference from './TimePreference';

export default class Criterion extends PureComponent {
  render() {
    return (
      <Card>
        <CardHeader title="Preferences"/>
        <CardContent>
          <TimePreference
            timeBefore={this.props.times.timeBefore}
            timeAfter={this.props.times.timeAfter}
            updateTimeBeforePreference={this.props.updateTimeBeforePreference}
            updateTimeAfterPreference={this.props.updateTimeAfterPreference}
          />
          <SubjectPreference
            subject={this.props.subject}
            updateSubjectPreference={this.props.updateSubjectPreference}
          />
        </CardContent>
      </Card>
    );
  }
}
