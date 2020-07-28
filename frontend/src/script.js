// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

// fetch the secondary calendar with the schedule (just one right now, will be multiple later)
var currCalendarIndex = 0; 
var idArray;

export async function fetchUserCalendar() {
  // change back to /fullCalendar
  const response = await fetch('/fullCalendar');
  const responseJson = await response.json();
  return responseJson;
}

// hide / show the appropriate elements on the page 
function showButtons() {
  document.getElementById("schedules").style.display = "none";
  document.getElementById("previous").style.display = "inline-block";
  document.getElementById("next").style.display = "inline-block";
  document.getElementById("loading").style.display = "none";
  document.getElementById("chosenCal").style.display = "block";
}

// display the next calendar schedule
function displayNextCalendar() {
  if (currCalendarIndex < idArray.length - 1) {
    currCalendarIndex++;
    displayCalendar(idArray[currCalendarIndex]);
  }
}

// display the previous calendar schedule
function displayPreviousCalendar() {
  if (currCalendarIndex > 0) {
    currCalendarIndex--;
    displayCalendar(idArray[currCalendarIndex]);
  }
}

// display the secondary calendar 
function displayCalendar(calId) {
  const url = "https://calendar.google.com/calendar/embed?src=" + calId;
  document.getElementById("calendar").src = url;
}

// delete all secondary calendars created, except for the chosen one 
function deleteUnchosenCalendars() {
  idArray
      .filter((id, index) => index !== currCalendarIndex)
      .forEach(id => deleteCalendar(id));

  const chosenCalendarId = idArray[currCalendarIndex];
  currCalendarIndex = 0;
  idArray = [chosenCalendarId];

  displayCalendar(chosenCalendarId);
}

function deleteCalendar(calendarId) {
  fetch("/deleteCalendar?calId=" + calendarId, { method: 'POST' });
}

function login() {
  window.location.href = "/auth";
}