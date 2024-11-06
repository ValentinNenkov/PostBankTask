# Currency Exchange Rates Services
## Overview
This project consists of two independent microservices, Service A and Service B, which work with currency exchange rate information from an external source. They use Spring Boot and WebSocket communication to send and receive real-time currency rate updates.

# Table of Contents
## General Information
## Service A
## Description
## Main Components
## Service B
## Description
## Main Components

# General Information
## The project includes:

Service A - Fetches currency exchange rate data from an external source, processes it, and stores it in a database. It sends the currency updates to Service B via WebSocket.
Service B - Receives data from Service A via WebSocket and stores it in its own database.

# Service A
## Description of Service A
Service A is designed to fetch currency exchange rates from an external source (e.g., Central Bank), process the information, and store it in a database. Additionally, it sends the currency rate updates to Service B via WebSocket.

## Main Components of Service A
CurrencyCourseController: A REST controller that handles API requests related to currency rates.

CurrencyCourseService and CurrencyCourseServiceImpl: The service layer that handles the logic for retrieving, transforming, and storing currency exchange rate data.

WebSocketSender: A class for sending updates via a WebSocket connection.

CurrencyCourseRepository and CurrencyCourseRowRepository: Repositories that manage CRUD operations for currency rates and their rows in the database.


# Service B
## Description of Service B
Service B is designed to receive currency exchange rate updates from Service A via WebSocket and store them in its database. 

## Main Components of Service B
WebSocketConfig and WebSocketServerHandler: Configure and handle WebSocket messages for receiving updates from Service A.

CurrencyCourseService and CurrencyCourseServiceImpl: The service layer that handles logic for storing and processing currency rates.

CurrencyCourseRepository and CurrencyCourseRowRepository: Repositories responsible for saving currency rates and their rows to the database.