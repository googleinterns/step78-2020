import React from 'react';
import { CardContent, Input } from '@material-ui/core';

class SubjectPreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleSubjectChange = this.handleSubjectChange.bind(this);
  }

  handleSubjectChange(event) {
    this.props.updateSubjectPreference(event.target.value)
  }

  render() {
    return (
      <CardContent>
        <div id="label">Preferred subject: </div>
        <Input placeholder="Subject" inputProps={{ 'aria-label': 'description' }} 
          value={this.props.subject} onChange={this.handleSubjectChange} />
      </CardContent>
    );
  }
}

export default SubjectPreference;
