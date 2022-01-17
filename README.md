## Tech Stack

Backend:
* **Groovy**
* **Spring Boot 2.6.^**
* **MongoDB**
* 
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
to make it easier to set up such project, as it is a tedious task.
The project comes shipped with some extra functionality both for the extension and the API.
Feel free to throw anything you don't need into the trash-bin!

The API is shipped with pre-configured database and a User.groovy class,
you can modify the security configuration in the SecurityConfig.groovy file,
there are 2 levels of security, api-key and jwt, api-key is meant for other services that interact with this API,
the JWT authentication is meant for the end-user running the browser extension

The Extension is shipped with a custon Navigator component since you can't use React Router in browser extensions,
I also pre-configured a homepage with a navigator-sidebar.

## Getting started
* First, install cookiecutter:
```sh
pip install cookiecutter
```
* Set up the project with cookiecutter:
```sh
cookiecutter .
```
* Install the necessary dependencies to compile the extension:
```sh
cd extension
npm install
```
Compile the extension, there are 2 modes dev/prod
Use dev when you want to debug your code as it provides source maps
Use prod when you want your code to be minified for shipping
```sh
npm run dev
```
Your extension is now in the /dist folder and can be loaded into chrome

* How to run the backend:
```sh
cd backend
sudo chmod ./scripts/deploy.sh
./scripts/deploy
```

