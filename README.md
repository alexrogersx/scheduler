
https://user-images.githubusercontent.com/51722825/131202483-6a9a9348-6d44-4aa1-9b36-d59b5b84632e.mov

# Appointment Scheduling Tool
> An Appointment and Customer Managment App Written in Java / JavaFX

## To Run 
1. [Install Java](https://www.java.com/en/download/) if not already installed.
2. Download or Clone this repo (or optionally just download the .jar file mentioned below)
3. Run .jar file located in the target directory 
4. Log into the app with "test" as the username and password

### [Documentation - JavaDocs](https://alexrogersdesign.github.io/scheduler/)

## About
This application is a customer appointment management solution designed for a small business who needs to keep track of
their customers and associated appointments. Data is stored in a mysql database and accessed via JDBC.

## Features
### UI
* Multilingual (English and French) based on user's computer language
* Time Zone adjusting by user's location
* User is notified when appointments are upcoming  
* Multiple options to sort Customer and Appointment directories
* Reports appointments by customer name, employee contact, and month.
* Appointments protected from overlapping or from being scheduled after business hours. 
* Form input validation 

### Application
* MySQL Database Integration
* CRUD functionality on both Appointment and Customer tables. 
* Database caching for scalability, requests are only made when necessary.
* Data Access Objects are created for organization and future expansion. 
* JavaDoc documentation with high coverage. 
* Logging for both user access and errors.  
* Zero configuration required.








