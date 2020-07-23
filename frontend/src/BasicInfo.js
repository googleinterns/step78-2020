import React from 'react';
import { Card } from '@material-ui/core';
import Credits from './Credits';
import TermDates from './TermDates';

class BasicInfo extends React.Component {  
  render() {
    return (
      <Card>
        <Credits 
          minCredits={this.props.minCredits}
          maxCredits={this.props.maxCredits}
          updateMinCredits={this.props.updateMinCredits}
          updateMaxCredits={this.props.updateMaxCredits}
        />
        <TermDates 
          startDate={this.props.startDate}
          endDate={this.props.endDate}
          updateTermStartDate={this.props.updateTermStartDate}
          updateTermEndDate={this.props.updateTermEndDate}
        />
      </Card>
    )
  }
}

export default BasicInfo;
