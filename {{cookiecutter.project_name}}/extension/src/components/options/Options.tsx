import React, { FC } from 'react';
import ReactDOM from 'react-dom';
import { MemoryRouter, Route } from 'react-router';
import '../../App.css'

const Options: FC = () => {
  return (
    <div className='background-style w-screen h-screen'>
      <p>Stuff</p>
    </div>);
}

export default function display() {
  const root = document.getElementById('root')
  ReactDOM.render(<Options />, root)
}
