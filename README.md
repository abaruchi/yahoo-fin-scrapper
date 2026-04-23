# Yahoo Finance Scrapper

A Spring Boot application designed to scrape and manage financial data from Yahoo Finance via RapidAPI. It supports tracking stocks across different markets (Brazil, USA, Australia), cryptocurrencies, and currency exchange rates.

## Features

- **Stock Tracking:** Supports multiple markets with configurable tickers.
- **Crypto Tracking:** Monitores popular cryptocurrencies like BTC, ETH, and LTC.
- **Currency Exchange:** Tracks and backfills USD exchange rates.
- **Database Integration:** Persists data using PostgreSQL with JPA/Hibernate.
- **External Integration:** Uses OpenFeign to communicate with Yahoo Finance API via RapidAPI.

## Prerequisites

- **Java 26** or higher
- **Maven** (bundled `mvnw` is included)
- **PostgreSQL** database

## Installation & Configuration

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd yahoo-fin-scrapper
   ```

2. **Database Setup:**
   Create a PostgreSQL database named `scrapper_db`.

3. **Environment Configuration:**
   Create or update the `.env` file in the root directory with your credentials:
   ```properties
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/scrapper_db
   SPRING_DATASOURCE_USERNAME=your_username
   SPRING_DATASOURCE_PASSWORD=your_password
   SPRING_DATASOURCE_DRIVER=org.postgresql.Driver

   DEFAULT_TIMEZONE=UTC

   RAPID_API_KEY=your_rapidapi_key
   RAPID_API_HOST=yh-finance.p.rapidapi.com
   RAPID_API_VERSION=v2
   ```

## How to Run

You can run the application using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application uses the `application.properties` and `application-local.properties` files. It also imports properties from the `.env` file.

## Running Tests

To execute the unit tests, run:

```bash
./mvnw test
```

## Technologies Used

- **Spring Boot 4.0.5**
- **Spring Data JPA**
- **Spring Cloud OpenFeign**
- **Lombok**
- **PostgreSQL**
- **Azure Container Apps** (Deployment support)
