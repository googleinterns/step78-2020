// Copyright 2020 Google LLC
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

package com.google.sps.data;

public class Section {
    private String professor;
    private TimeRange[] meetingTimes;
    /*
    * meeting times is an array
    * Where the 0th index is Sunday and the 6th is Saturday
    * [_,_,_,_,_,_,_] no meeting times
    * [_,*,_,*,_,*,_] class MWF
    * [_,_,*,_,*,_,_] class TTh
    * [*,_,_,_,_,_,*] class SatSun
    */
    public Section(String professor, TimeRange[] meetingTimes){
        this.professor = professor;
        this.meetingTimes = meetingTimes;
    }
}