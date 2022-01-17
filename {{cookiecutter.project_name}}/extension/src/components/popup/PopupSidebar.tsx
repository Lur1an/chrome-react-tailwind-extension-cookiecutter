import React, { useContext } from "react";
import { FC, FunctionComponent } from "react";
import { BsFillLightningFill, BsGearFill, BsPlus } from 'react-icons/bs'
import { FaCog, FaHorseHead, FaPoo } from 'react-icons/fa'
import { MdHome } from 'react-icons/md'
import SidebarButton from "../SidebarButton";
import { PopupNavigationContext, PopupPage } from "./PopupNavigation";

interface PopupSidebarProps {

}

const PopupSidebar: FC<PopupSidebarProps> = () => {
  const { setPage } = useContext(PopupNavigationContext)
  return (
    <div className='sidebar' >
      <SidebarButton icon={<MdHome size="28" />} onClick={() => setPage(PopupPage.HOME)} text='Home' />
      <SidebarButton icon={<BsGearFill size="22" />} onClick={() => setPage(PopupPage.SETTINGS)} text='Settings' />
    </div>
  )
}

export default PopupSidebar;