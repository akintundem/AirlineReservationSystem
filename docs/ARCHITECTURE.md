# Group 6 Skylink

  

This is the document of our project, "Skylink."

  

[Iteration 3 Architecture](https://code.cs.umanitoba.ca/comp3350-winter2024/shadedragon-a02-6/-/blob/main/docs/iteration3architecture.png)

  
  

# Iteration 3 functions

  

Our app can work in both **online** and **offline** modes. We have moved some of our features and user stories to later iterations. Most of our codes are under **app/app/src/main/java/com/example/skylink**. Below are features we have done for iteration 3.

  

## Add ons

  

This feature enables users to select add-ons before they proceed to payment; users can choose their desired add-on products and the price will be calculated during checkout.

  

## View Confirmed Bookings

  

Users will be able to view their booked flights in the app, through hamburger menu.

  

## System tests for the project

  

End to end system tests for our project.

  
  

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

  

**Add-ons**

  

- It enables users to select add-on products and it will be stored in the booking information.

  

  

**Update userProfileActivity**

  

- Users will be able to update their profile information after they create their accounts.

  

  

**BookingsDisplay**

  

- Users will be able to view their booked flights.

  

## Business Logic:

**FlightBookingHandler**

- Logic for managing bookings.
  

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
  

**Booking info**

- This enables us to process user information.

  

**FlightInfo**

- This enables us to process flight information.


