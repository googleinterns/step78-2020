import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Criterion from './Criterion';
import Course from './Course';
import BasicInfo from './BasicInfo';

ReactDOM.render(
  <React.StrictMode>
    <h2>Courses</h2>
    <Course />
    <h2>Preferences</h2>
    <Criterion />
    <h2>Other Information</h2>
    <BasicInfo />
  </React.StrictMode>,
  document.getElementById('root')
)
