import React, { FunctionComponent, FC} from "react";
import { createNavigationContext, NavigationProvider } from "../NavigationContext";
import Home from "./Home";
import Settings from "./Settings";

enum PopupPage {
  HOME,
  SETTINGS
}

interface PopupNavigationProviderProps {
  children?: React.ReactNode
}

const PopupNavigationContext = createNavigationContext<PopupPage>(null)

const pageComponents = new Map<PopupPage, FunctionComponent>()
pageComponents.set(PopupPage.HOME, Home)
pageComponents.set(PopupPage.SETTINGS, Settings)

const PopupNavigationProvider: FC<PopupNavigationProviderProps> = ({ children }) => {

  return (
    <NavigationProvider NavigationContext={PopupNavigationContext} pageComponents={pageComponents} defaultPage={PopupPage.HOME}>
      {children}
    </NavigationProvider>
  )
}

export { PopupNavigationProvider, PopupNavigationContext, PopupPage };

