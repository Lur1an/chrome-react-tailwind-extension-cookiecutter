import React, { Children, Context, ContextType, createContext, FC, FunctionComponent, useState } from "react";

/**
 * Generic navigation provider
 * @generic P page to render
 * @param pageComponents Map of possible pages
 * @param setPage function to set page
 */
interface NavigationContextType<P> {
  page: P
  pageComponents: Map<P, FunctionComponent>
  setPage: (page: P) => void
}

function createNavigationContext<P>(defaultValue: NavigationContextType<P> = null) {
  const context = createContext<NavigationContextType<P>>(defaultValue)
  return context
}

interface NavigationProviderProps<P> {
  NavigationContext: Context<NavigationContextType<P>>
  pageComponents: Map<P, FunctionComponent>
  defaultPage: P
  children: React.ReactNode
}

function NavigationProvider<P= string>({ NavigationContext, pageComponents, defaultPage, children }: NavigationProviderProps<P>) {
  const [page, setPage] = useState(defaultPage)

  const navigationContextValue: NavigationContextType<P> = {
    page: page,
    setPage: setPage,
    pageComponents: pageComponents
  }
  return (
    <NavigationContext.Provider value={navigationContextValue}>
      {children}
    </NavigationContext.Provider>
  );
}

export { NavigationProvider, createNavigationContext };

