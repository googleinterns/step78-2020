import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Criterion from './Criterion';
import Course from './Course';

ReactDOM.render(
  <React.StrictMode>
    <Course />
    <Criterion />
  </React.StrictMode>,
  document.getElementById('root')
)
