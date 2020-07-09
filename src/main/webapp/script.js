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
function fetchUserCalendar() {
  document.getElementById("loading").style.display = "block";
  fetch('/handleSchedules').then(response => response.json()).then((calIds) => {
    idArray = calIds;
    displayCalendar(calIds[0]);
    showButtons();
  });
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
  for (var j = 0; j < idArray.length; j++) {
    if (j != currCalendarIndex) {
      fetch("/deleteCalendar?calId=" + idArray[j], {method: 'POST'});
      idArray.splice(j,1);
    }
  }
  displayCalendar(idArray[0]);
}
