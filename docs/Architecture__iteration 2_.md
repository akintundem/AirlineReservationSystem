# Group 6 Skylink

This is the document of our project, "Skylink."

[Iteration 2 Architecture](https://code.cs.umanitoba.ca/comp3350-winter2024/shadedragon-a02-6/-/blob/main/docs/iteration2_architecture.png)


# Iteration 2 functions 

Our app can work in both **online** and **offline** modes. We have moved some of our features and user stories to later iterations. Most of our codes are under **app/app/src/main/java/com/example/skylink**. Below are features we have done for iteration 2.

## Flight Searching

We have created our actual database using HSQLDB for data storage. We also improved our methods of searching for available flights from the database; it was implemented in the Java file " AirportPath." We also have unit and integration tests for our business/logic layer.
## Seat Selection
This feature enables users to select seats before they proceed to payment; users can view both available and unavailable seats in the app and confirm their preferred seat selection.
## Payment System
We now have a fully functional payment system; firstly, after seat selection, there will be a page that takes the user's credit card information and billing information; after the user submits the payment, the payment successful page will pop up, and it will show the details of the payment. At the bottom, a button will take the user back to the flight searching page.
## User Authentication
It includes a sign-in page and a sign-up page; if the user already has an account, they can enter the email and password and log in to the app. If the user does not have an account, then the sign-up page will enable the user to create an account; it will collect various information about the user, including phone number, billing name, email, and other information needed for account management and user authentication.


## Presentation layer:

**FlightDisplay**
- It displays resulting flights based on users’ input on a ListView using CustomFlightAdaptor. It also displays sorting filters that the user can select to update the flight result list.

**FlightSearch**
  - The page takes users’ search parameters to display the resulting flights on the next activity.

**CreditCardPaymentActivity and PaymentSuccessfulActivity**
- These are for the payment system; credit card payment activity will take the user's payment information, and after the payment is successfully made, it will take the user to the payment success page.

**CustomFlightAdaptor**
- The scrollable view in the Flight_search activity displays the resulting flights.

**Seat Select**
- After a user has chosen the flight and entered the passenger information, the app will bring the user to the seat selection page. The user can view seats and make selections based on preference. (Includes inbound activity and outbound activity)

**User Auth**
- Users can fill in the information needed for flight booking. It also enables users to create and manage accounts.

**Session**
- We use sessions to get different information when the app runs and store temporary data.

## Business Logic:

**AirportPath**
- Responsible for searching and returning the list of flights based on destination, date, and other parameters the user has selected.

**FlightSorting**
- Sorts resulting flights based on the option the user has selected from the spinner.

**PaymentHandler**
- Collect payment information and display it on the screen.

**PlaneConfiguration**
- This provides information about the planes, such as the number of seats per row.


**User Handler**
- This is used for managing user actions, including creating profiles, verifying user information, and logging in.

**PassengerDataManager**
- This is used for managing user information.

## Data Layer:

**FlightHSQLDB**
- Stores flight routes in the database.

**BookingDatabase**
- Stores user entered data for flight booking.

**CitiesRepository**
- Stores cities in the database

**PaymentHSQLDB**
- Process data related to payments.

**BookingStub, FlightStub,PaymentStub,UserStub**
- These are all for unit tests and have similar functions to the actual database.

## Domain Specific Object:

**Flight**
- An object that stores information about a single flight, like origin, destination, time, etc.

**Flights**
- An object that stores the list of flights available based on user input.

**Aircraft**
- It contains the aircraft's name and the business and economy class seat configuration information.

**City**
- It contains the city names and codes and provides a way to obtain city information.

**FlightSearch**
- Flight search information, including the departure place, destination, departure date, return date, total number of passengers and whether it is a one-way flight.

**PassengerData**
- The code implements a class that represents passenger information, including the passenger's title, first name, last name, phone number and email address, and provides corresponding get and set methods.

**TripInvoice**
- The code implements a class representing a trip invoice containing the user ID and total amount.

**UserProperties**
- This class contains information such as the user's name, email, password, gender, address, phone number, date of birth, and country/region and provides a method to verify this information.

## Features

Finished:
- Creating and Managing User Profile

- Interactive Seat Map

- Payment System

Add-ons has been moved to iteration 3.

## User stories

**Finished**


**Payment System**

- Description: As a user, I want to be able to use a credit card to pay in the app.


**Creating and Managing User Profile**

- Description: As a user, I want to be able to register an account and store my information for future use.




 **Creating Econ and Business Seat Distinction**

- Description: As a user, I want to know what area the seats of different classes are in when choosing seats.



 **Interactive Seat Map**


- Description: As a user, I want to view an interactive seat map with available seats I can choose from because I would like to make an informed decision that provides me maximum comfort as I travel.




 **Prior Seat Selection**

- Description: As a user, I want to be able to book for my family to have adjacent seats in advance because I need to ensure we are together during the flight.


