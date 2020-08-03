import React from 'react';
import {CardContent, Input} from '@material-ui/core';
import Typography from '@material-ui/core/Typography';

class SubjectPreference extends React.Component {
  constructor(props) {
    super(props);

    this.handleSubjectChange = this.handleSubjectChange.bind(this);
  }

  handleSubjectChange(event) {
    this.props.updateSubjectPreference(event.target.value);
  }

  render() {
    return (
      <CardContent>
        <Typography variant="subtitle1" gutterBottom>
          Preferred Subject: 
        </Typography>
        <Input placeholder="Subject" value={this.props.subject} onChange={this.handleSubjectChange}
        />
      </CardContent>
    );
  }
}

export default SubjectPreference;
