## Tech Stack

Backend:
* **Groovy**
* **Spring Boot 2.6.^**
* **MongoDB**

Extension:
* **React**
* **Typescript**
* **Tailwind**

## Requirements
This project might work with older versions of Java/Gradle and NodeJS, 
I haven't verified for myself, the versions you see below are the ones I used when making this template

* **Python 2.7 or later** (Project setup)
* **Java 17 & Gradle 7.2 or later** (Backend)
* **Docker & Docker compose** (Backend)
* **NodeJS 16.13.^ & npm/yarn** (Chrome extension)

## Description
After working with Spring APIs and Chrome extensions I wanted to make a cookiecutter template
to make it easier to set up such a project, as it is a tedious task.
The project comes shipped with some functionality that is always useful and 
in one way or another we always end up implementing as the application scales.
Feel free to throw away anything you don't need into the trash-bin!

The API is shipped with pre-configured database and security.
You can modify the security configuration in the SecurityConfig.groovy file, there are 2 levels of security: 
* **api-key**
This is meant for other web-services or admins that want to interact with the API
* **jwt security**
The JWT authentication is meant for the end-user running the browser extension, I hope the end-points are self-explanatory!
Feel free to ask for clarification if needed.

The Extension is shipped with a custon Navigator component since you can't use React Router in browser extensions,
I also pre-configured the Popup component with a Sidebar and Navigator.
I have to thank [Fireship](https://www.youtube.com/c/Fireship) for the TailwindCSS styles for the sidebar-buttons

## Getting started
First, install cookiecutter:
```sh
pip install cookiecutter
```
Set up the project with cookiecutter:
```sh
cookiecutter .
```
Install the necessary dependencies to compile the extension:
```sh
cd extension
npm install
```
Compile the extension, there are 2 modes dev/prod.
Use dev when you want to debug your code as it provides source maps.
Use prod when you want your code to be minified for shipping.
```sh
npm run dev
```
Your extension is now in the /dist folder and can be loaded into chrome.

How to run the backend:
**Make sure you have Java and Gradle installed**
```sh
cd backend
sudo chmod ./scripts/deploy.sh
./scripts/deploy
```

