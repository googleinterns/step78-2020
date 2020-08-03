import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import Button from '@material-ui/core/Button';

export default class Calendar extends React.Component {
  constructor(props) {
    super(props);

    this.selectPreviousSchedule = this.selectPreviousSchedule.bind(this);
    this.selectNextSchedule = this.selectNextSchedule.bind(this);

    this.state = {
      selectedScheduleId: 0, // Current schedule
    };
  }

  selectNextSchedule() {
    if (this.state.selectedScheduleId < this.props.schedulesTimes.length - 1) {
      this.setState({selectedScheduleId: this.state.selectedScheduleId + 1});
    }
  }

  selectPreviousSchedule() {
    if (this.state.selectedScheduleId > 0) {
      this.setState({selectedScheduleId: this.state.selectedScheduleId - 1});
    }
  }

  render() {
    return (
      <div>
        <Button onClick={
          this.selectPreviousSchedule
        }>Previous Schedule</Button>
        <Button onClick={
          this.selectNextSchedule
        }>Next Schedule</Button>
        {
          this.state.selectedScheduleId < this.props.scheduleList.length &&
          (
            <FullCalendar
              plugins={[dayGridPlugin, timeGridPlugin]}
              initialView="timeGridWeek"
              events={
                this.props.schedulesTimes[this.state.selectedScheduleId]
              }
            />
          )
        }
      </div>
    );
  }
}
