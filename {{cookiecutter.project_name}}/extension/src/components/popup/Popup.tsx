import React, { FC, useContext } from 'react';
import ReactDOM from 'react-dom';
import '../../App.css';
import { PopupNavigationContext, PopupNavigationProvider } from './PopupNavigation';
import PopupSidebar from './PopupSidebar';

const Popup: FC = () => {
  const { page, pageComponents } = useContext(PopupNavigationContext)
  const PageComponent = pageComponents.get(page)

  return (
    <div className='popup-container'>
      <PopupSidebar />
      <PageComponent />
    </div>
  )
}

export default function display() {
  const root = document.getElementById('root')
  ReactDOM.render(
    <PopupNavigationProvider>
      <Popup />
    </PopupNavigationProvider>,
    root
  )
}
