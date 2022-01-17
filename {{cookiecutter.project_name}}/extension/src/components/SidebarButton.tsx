import React, { FC } from 'react';
import { FaQuestionCircle } from 'react-icons/fa';


interface SidebarButton {
  icon?: React.ReactNode
  text?: string
  onClick?: () => void
}

const SidebarButton: FC<SidebarButton> = ({ icon = <FaQuestionCircle />, text = 'tooltip 💡', onClick = null }) => (
  <button className="sidebar-button group" onClick={onClick}>
    {icon}
    <span className="sidebar-tooltip group-hover:scale-100">
      {text}
    </span>
  </button>
);


const Divider: FC = () => <hr className="sidebar-hr" />;

export default SidebarButton;
