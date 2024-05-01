# Group 6 Skyline

This is the document of our project "Skylink".


# Iteration 1 functions 

Our app can work in both **online** and **offline** mode. We have moved some of our features and user stories to later iterations. Most of our code is under **app/app/src/main/java/com/example/skylink**. Listed below are features we have done for iteration 1.

## Flight Searching

We have created our fake database for flights and searching logic. We used depth-first search for available flights from the database, implemented in the java file " AirportPath". When searching for available flights, we first use this logic to filter flights for our user from the database, then return the results to the front end and show them on the screen. We also have the unit tests for our logic part called " AirportPathTest " to test the four methods in the "AirportPath" to ensure that our app works properly. The test covers over **80%** of the code in the logic layer. 
## Seat Selection
Moved to iteration 2.
## Flexible Date Ranges
We have created the UI for this feature, we are using fixed flight dates for now, users can select their preferred date from the main screen and will be able to search for flights accordingly.
## Architecture
Please see [**Architecture.jpg**](https://code.cs.umanitoba.ca/comp3350-winter2024/shadedragon-a02-6/-/blob/main/docs/iteration1_architecture.jpg)

### Presentation layer:

**MainActivity**
  - It is the home page of the application that takes users’ search parameters to display the resulting flights on the next activity.

**Flight_search**
- It displays resulting flights based on users’ input on a ListView using CustomFlightAdaptor. It also displays sorting filters that user can select to update the flight result list.


**CustomFlightAdaptor**
- The scrollable view in the Flight_search activity displays the resulting flights.


**User_info**
- The display asks travelers to fill in the information needed for flight booking.


## Business Logic:

**AirportPath**
- Responsible for searching and returning the list of flights based on destination, date, and other parameters the user has selected on MainActivity.

**BookingManager**
- Takes user information for booking flight.

**FlightSorting**
- Sorts resulting flights based on the option user has selected from spinner.

### Data:

**FlightDatabase**
- Stores flight route database.

**BookingDatabase**
- Stores user entered data for flight booking.

### Domain Specific Object:

**Flight**
- An object that stores information about a single flight like origin, destination, time, etc.


**Booking**
- An object that stores information about travelers trying to book the flight like name and destination.


**Flights**
- An object that stores the list of flights available based on user input.

## Completed User Stories

### 1: Basic Flight Searching Component
Fully functional.
Cost: 5 days
### 2: Flight Filters
Partially functional
Cost: 3 days
### 3:Show Flights for Flexible Date Range
Partially functional
Cost: 3 days
### 4:Categorize ticket price range using colours
Partially functional
Cost: 3 days

