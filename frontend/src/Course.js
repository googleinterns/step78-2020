import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Input, Card, CardContent, Switch } from '@material-ui/core';

class Course extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      courseID: "",
      subject: "",
      credits: 0.0,
      isRequired: false,
      section: [] //Will be updated to handle multiple sections later
    }

    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleIDChange = this.handleIDChange.bind(this);
  }

  handleNameChange(event) {
    this.setState({...this.state, name: event.target.value});
  }

  handleIDChange(event) {
    this.setState({...this.state, courseID: event.target.value});
  }

  handleSubjectChange(event) {
    this.setState({...this.state, subject: event.target.value});
  }

  handleCreditsChange(event) {
    this.setState({...this.state, credits: event.target.value});
  }

  handleisRequiredChange(event) {
    this.setState({...this.state, isRequired: event.target.checked})
  }

  render() {
    return (
      <Card>
        <CardContent>
          <Input id="name-input" placeholder="Course Name" 
            value={this.state.name} onChange={this.handleNameChange} />
          <Input id="id-input" placeholder="Course ID"
            value={this.state.courseID} onChange={this.handleIDChange} />
          <Input id="subject-input" placeholder="Subject"
            value={this.state.subject} onChange={this.handleSubjectChange} />

          //TODO: implement react-number-format (https://github.com/s-yadav/react-number-format)
          <Input id="credits-input" placeholder="Credits" 
            value={this.state.subject} onChange={this.handleSubjectChange} />
            
          <FormControlLabel>
            control = {<Switch id="isRequired-input" checked={this.state.isRequired} onChange={this.handleisRequiredChange} />}
            label = "Required"
          </FormControlLabel>

          //TODO: Implement proper section inputs
        </CardContent>
      </Card>
    );
  }
}
