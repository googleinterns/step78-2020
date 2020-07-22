import React from 'react';
import TextField from '@material-ui/core/TextField';
import { CardContent } from '@material-ui/core';
import { NumberFormatCustom } from './NumberFormat';

class Credits extends React.Component {
  constructor(props) {
    super(props);

    this.handleMinCreditsChange = this.handleMinCreditsChange.bind(this);
    this.handleMaxCreditsChange = this.handleMaxCreditsChange.bind(this);
  }

  handleMinCreditsChange(event) {
    this.props.updateMinCredits(event.target.value);
  }

  handleMaxCreditsChange(event) {
    this.props.updateMaxCredits(event.target.value);
  }

  render() {
    return (
      <CardContent>
        <TextField label="Minimum credits:" value={this.props.minCredits} 
          onChange={this.handleMinCreditsChange} InputProps={{ inputComponent: NumberFormatCustom }} />
        <TextField label="Maximum credits:" value={this.props.maxCredits} 
          onChange={this.handleMaxCreditsChange} InputProps={{ inputComponent: NumberFormatCustom }} />
      </CardContent>
    );
  }
}

export default Credits;
