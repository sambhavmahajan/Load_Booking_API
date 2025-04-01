# Load Booking API
This is an API given as an assignment by Liveasy Logistic for Software Engineering Intern round. This API is built using spring boot for managing load bookings in the transportation domain. It enables shippers and transporters to create, update, and retrieve booking information.

## Table of Contents

- [Setup Instructions](#setup-instructions)
- [API Usage](#api-usage)
- [Assumptions](#assumptions)
- [Technologies](#technologies)

## Setup Instructions

### Prerequisites

Before setting up the project, make sure you have the following installed:
- **Java 21** (or newer)
- **Maven**
- **PostgreSQL**
- **IntelliJ IDEA** (or your preferred IDE)
- **Postman** (for API testing)

### Clone the Repository

Clone the repository to your local machine using Git:
```bash
git clone https://github.com/sambhavmahajan/Load_Booking_API.git
cd load-booking-api
```

### Configure PostgreSQL

1. **Create a Database**:  
   Open your PostgreSQL client and create a database for the API. For example:
   ```sql
   CREATE DATABASE logistic;
   ```

2. **Update Database Settings**:  
   In the project, open the `src/main/resources/application.properties` file and update the database configuration:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/logistic
   spring.datasource.username=postgres
   spring.datasource.password=12345678
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### Import Project in IntelliJ IDEA

1. **Open IntelliJ IDEA** and select **File > Open...**.
2. Navigate to the cloned repository and open the project folder.
3. IntelliJ should automatically recognize the Maven project. If not, right-click the `pom.xml` file and choose **Add as Maven Project**.
4. Let Maven import the dependencies.

### Build and Run

1. **Build the Project**:  
   You can build the project using Maven:
   ```bash
   mvn clean install
   ```
2. **Run the Application**:  
   Either run the project from IntelliJ by right-clicking the main class and selecting **Run**, or use the Maven command:
   ```bash
   mvn spring-boot:run
   ```
   The API will start and run on `http://localhost:8080`. No port is mentioned in properties, it can be changed by server.port=9000 (eg)

## BookingController API Specification

## Base URL
```
http://localhost:8080/booking
```

## Endpoints

### 1. **Create Booking**

- **Endpoint**: `POST /booking`
- **Description**: Creates a new booking for a given load.
- **Request Body**:
  ```json
  {
    "load": "UUID-of-load",
    "transporterId": "transporter-id",
    "comment": "Any additional comments",
    "proposedRate": 1000
  }
  ```
- **Response**:
  - **Status**: `201 Created`
  - **Response Body**: None
  - **Error Responses**:
    - `400 Bad Request`: If the load is already cancelled or the provided data is invalid.
    - `404 Not Found`: If the load does not exist.

---

### 2. **Get Bookings**

- **Endpoint**: `GET /booking`
- **Description**: Fetches all bookings based on the provided shipper and transporter IDs.
- **Query Parameters**:
  - `shipperId` (string): The unique identifier of the shipper.
  - `transporterId` (string): The unique identifier of the transporter.
  
- **Example Request**:
  ```
  GET /booking?shipperId=12345&transporterId=67890
  ```
- **Response**:
  - **Status**: `200 OK`
  - **Response Body**:
    ```json
    [
      {
        "bookingId": "UUID-of-booking",
        "load": "UUID-of-load",
        "transporterId": "transporter-id",
        "comment": "Some comment",
        "proposedRate": 1000,
        "status": "BOOKED"
      }
    ]
    ```
  - **Error Responses**:
    - `400 Bad Request`: If the query parameters are invalid.

---

### 3. **Get Booking by ID**

- **Endpoint**: `GET /booking/{bookingId}`
- **Description**: Retrieves a booking by its unique booking ID.
- **Path Parameters**:
  - `bookingId` (UUID): The unique identifier of the booking.
  
- **Example Request**:
  ```
  GET /booking/123e4567-e89b-12d3-a456-426614174000
  ```
- **Response**:
  - **Status**: `200 OK`
  - **Response Body**:
    ```json
    {
      "bookingId": "UUID-of-booking",
      "load": "UUID-of-load",
      "transporterId": "transporter-id",
      "comment": "Some comment",
      "proposedRate": 1000,
      "status": "BOOKED"
    }
    ```
  - **Error Responses**:
    - `404 Not Found`: If the booking with the given ID does not exist.

---

### 4. **Update Booking**

- **Endpoint**: `PUT /booking/{bookingId}`
- **Description**: Updates the details of an existing booking.
- **Path Parameters**:
  - `bookingId` (UUID): The unique identifier of the booking to be updated.
  
- **Request Body**:
  ```json
  {
    "load": "UUID-of-load",
    "transporterId": "new-transporter-id",
    "comment": "Updated comments",
    "proposedRate": 1200
  }
  ```
- **Response**:
  - **Status**: `200 OK`
  - **Response Body**: None
  - **Error Responses**:
    - `400 Bad Request`: If the load is already cancelled or invalid data is provided.
    - `404 Not Found`: If the booking with the given ID does not exist.
    
## LoadController API Specification

## Base URL
```
http://localhost:8080/load
```

## Endpoints

### 1. **Create Load**

- **Method:** `POST`
- **Endpoint:** `/load`
- **Description:** Creates a new load.
- **Request Body:**
  ```json
  {
    "shipperId": "string",
    "facility": {
      "facilityId": 1,
      "loadingPoint": "Point A",
      "unloadingPoint": "Point B",
      "loadingDate": "2025-05-01T10:00:00Z",
      "unloadingDate": "2025-05-02T10:00:00Z"
    },
    "productType": "string",
    "truckType": "string",
    "noOfTrucks": 2,
    "weight": 1500.0,
    "comment": "Additional details",
    "status": "PENDING"
  }
  ```
- **Success Response:**
  - **Status:** `201 Created`
  - **Response Body:** *None*
- **Error Responses:**
  - `400 Bad Request` if the provided data is invalid.

---

### 2. **Fetch Loads**

- **Method:** `GET`
- **Endpoint:** `/load`
- **Description:** Retrieves a list of loads filtered by shipper ID and truck type.
- **Query Parameters:**
  - `shipperId` (string): The shipper's unique identifier.
  - `truckType` (string): The truck type.
- **Example Request:**
  ```
  GET /load?shipperId=shipper123&truckType=Flatbed
  ```
- **Success Response:**
  - **Status:** `200 OK`
  - **Response Body:** An array of load objects.
  ```json
  [
    {
      "loadId": "UUID-of-load",
      "shipperId": "shipper123",
      "facility": {
        "facilityId": 1,
        "loadingPoint": "Point A",
        "unloadingPoint": "Point B",
        "loadingDate": "2025-05-01T10:00:00Z",
        "unloadingDate": "2025-05-02T10:00:00Z"
      },
      "productType": "Electronics",
      "truckType": "Flatbed",
      "noOfTrucks": 2,
      "weight": 1500.0,
      "comment": "Handle with care",
      "status": "PENDING"
    }
  ]
  ```
- **Error Responses:**
  - `400 Bad Request` if query parameters are missing or invalid.

---

### 3. **Fetch Load by ID**

- **Method:** `GET`
- **Endpoint:** `/load/{loadId}`
- **Description:** Retrieves details of a specific load by its unique ID.
- **Path Parameter:**
  - `loadId` (UUID): The unique identifier of the load.
- **Example Request:**
  ```
  GET /load/917db17a-89bf-4da3-bf46-02817cb81011
  ```
- **Success Response:**
  - **Status:** `200 OK`
  - **Response Body:** A load object.
  ```json
  {
    "loadId": "917db17a-89bf-4da3-bf46-02817cb81011",
    "shipperId": "shipper123",
    "facility": {
      "facilityId": 1,
      "loadingPoint": "Point A",
      "unloadingPoint": "Point B",
      "loadingDate": "2025-05-01T10:00:00Z",
      "unloadingDate": "2025-05-02T10:00:00Z"
    },
    "productType": "Electronics",
    "truckType": "Flatbed",
    "noOfTrucks": 2,
    "weight": 1500.0,
    "comment": "Handle with care",
    "status": "PENDING"
  }
  ```
- **Error Responses:**
  - `404 Not Found` if a load with the specified ID does not exist.

---

### 4. **Update Load**

- **Method:** `PUT`
- **Endpoint:** `/load/{loadId}`
- **Description:** Updates an existing load with new details.
- **Path Parameter:**
  - `loadId` (UUID): The unique identifier of the load to update.
- **Request Body:**
  ```json
  {
    "shipperId": "shipper123",
    "facility": {
      "facilityId": 1,
      "loadingPoint": "Updated Point A",
      "unloadingPoint": "Updated Point B",
      "loadingDate": "2025-05-01T10:00:00Z",
      "unloadingDate": "2025-05-02T10:00:00Z"
    },
    "productType": "Electronics",
    "truckType": "Flatbed",
    "noOfTrucks": 3,
    "weight": 2000.0,
    "comment": "Updated comment",
    "status": "UPDATED"
  }
  ```
- **Success Response:**
  - **Status:** `200 OK`
  - **Response Body:** *None*
- **Error Responses:**
  - `400 Bad Request` if provided data is invalid.
  - `404 Not Found` if a load with the specified ID does not exist.

---

### 5. **Delete Load**

- **Method:** `DELETE`
- **Endpoint:** `/load/{loadId}`
- **Description:** Deletes a specific load.
- **Path Parameter:**
  - `loadId` (UUID): The unique identifier of the load to delete.
- **Example Request:**
  ```
  DELETE /load/917db17a-89bf-4da3-bf46-02817cb81011
  ```
- **Success Response:**
  - **Status:** `200 OK`
  - **Response Body:** *None*
- **Error Responses:**
  - `404 Not Found` if a load with the specified ID does not exist.

## Error Codes

- `400 Bad Request`: Invalid data or request parameters.
- `404 Not Found`: Resource (booking, load) not found.

## Assumptions

- **Data Integrity**:  
  It is assumed that the `load` provided in the booking request exists and is valid. The API will check for a valid load ID and its status before creating a booking.

- **Status Management**:  
  Once a booking is created, the corresponding load's status is updated to `BOOKED`. If a load is already `CANCELLED`, it cannot be booked.

- **Database Schema**:  
  The project assumes a PostgreSQL database is used, and the schema is managed by Hibernate's `ddl-auto` feature (set to `update`).

- **Error Handling**:  
  The API uses `ResponseStatusException` to return appropriate HTTP status codes and messages when errors occur (e.g., invalid input, resource not found).

- **Security**:  
  This version of the API does not include authentication or authorization. It is assumed that the API is either secured through network controls or will be extended with a security module in the future.

---

## Technologies

- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **IntelliJ IDEA**

---
