import React, {PureComponent} from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import Button from '@material-ui/core/Button';
import './classes.css';

export default class Calendar extends PureComponent {
  render() {
    return (
      <div className="fullCalendar">
        <Button onClick={
          this.props.selectPreviousSchedule
        }>Previous Schedule</Button>
        <Button onClick={
          this.props.selectNextSchedule
        }>Next Schedule</Button>
        {
          this.props.selectedScheduleId < this.props.scheduleList.length &&
          (
            <FullCalendar
              plugins={[dayGridPlugin, timeGridPlugin]}
              initialView="timeGridWeek"
              events={
                this.props.schedulesTimes[this.props.selectedScheduleId]
              }
            />
          )
        }
      </div>
    );
  }
}
