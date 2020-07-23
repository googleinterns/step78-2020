import React from 'react';
import { Card } from '@material-ui/core';
import Credits from './Credits';
import TermDates from './TermDates';

class BasicInfo extends React.Component {
  constructor(props) {
    super(props);

    this.updateMinCredits = this.updateMinCredits.bind(this);
    this.updateMaxCredits = this.updateMaxCredits.bind(this);
    this.updateTermStartDate = this.updateTermStartDate.bind(this);
    this.updateTermEndDate = this.updateTermEndDate.bind(this);
  }

  updateMinCredits(minCredits) {
    this.props.updateMinCredits(minCredits);
  }

  updateMaxCredits(maxCredits) {
    this.props.updateMaxCredits(maxCredits);
  }

  updateTermStartDate(startDate) {
    this.props.updateTermStartDate(startDate);
  }

  updateTermEndDate(endDate) {
    this.props.updateTermEndDate(endDate);
  }
  
  render() {
    return (
      <Card>
        <Credits 
          minCredits={this.props.minCredits}
          maxCredits={this.props.maxCredits}
          updateMinCredits={this.updateMinCredits}
          updateMaxCredits={this.updateMaxCredits}
        />
        <TermDates 
          startDate={this.props.startDate}
          endDate={this.props.endDate}
          updateTermStartDate={this.updateTermStartDate}
          updateTermEndDate={this.updateTermEndDate}
        />
      </Card>
    )
  }
}

export default BasicInfo;
