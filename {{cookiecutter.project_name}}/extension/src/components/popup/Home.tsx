import React, { useContext } from "react";
import { FC } from "react";
import { PopupNavigationContext } from "./PopupNavigation";
interface HomeProps {

}

const Home: FC<HomeProps> = () => {
  return (
    <div>
      <h1 className="font-bold text-3xl text-center">HOME</h1>
    </div>
  );
}

export default Home;