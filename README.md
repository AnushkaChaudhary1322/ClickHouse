Sure! Here's an updated and more detailed version of your `README.md` that includes **setup, usage, features**, and other relevant information for your ClickHouse data ingestion assignment:

---

````markdown
# ClickHouse Data Ingestion Tool

A Spring Boot-based web application for **bidirectional data ingestion** between **ClickHouse** and **Flat Files (CSV)**. This tool allows users to export data from ClickHouse to a CSV file and import CSV files into a ClickHouse table with optional column filtering.

---

## Features

- **Bidirectional Transfer**: ClickHouse ⟷ CSV file
- **CSV Upload/Download**: Import/export via file browser
- **Column Selection**: Choose which columns to include when importing
- **JWT Authentication**: Secure endpoints with token-based login
- **Record Counting**: Display total data rows being processed
- **Unit Tests**: Tests for services and full ingestion pipeline
- **In-Memory Table Creation**: Auto-generates test table during dev/test

---

## Setup Instructions

1. **Install Requirements**:

   - Java 21+
   - Maven 3.9+
   - ClickHouse Server (running)

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/clickhouse-ingestion-tool.git
   cd clickhouse-ingestion-tool
   ```
````

3. **Configure ClickHouse Connection**:
   Edit `src/main/resources/application.properties`:

   ```properties
   clickhouse.url=jdbc:clickhouse://localhost:8123/default
   clickhouse.username=default
   clickhouse.password=
   ```

4. **Run the App**:
   ```bash
   mvn spring-boot:run
   ```

---

## 🧪 Running Tests

To run all unit tests:

```bash
mvn test
```

## 🖥️ Endpoints (Web UI)

- `http://localhost:8080/` — Main UI
- Supports upload/download of CSV
- Select table name, columns, and target file

---

## Project Structure

```
src/
 └── main/
     └── java/com/anushka/
         ├── → IngestionController, MainAplllication.java
         ├── → CsvService, ClickHouseService
         └── MainApplication.java
 └── test/
     └── java/com/anushka/
         └── AppTest.java
resources/
 └── templates/                   → index.html
 └── static/js/                   → app.js, styles.css
 └── application.properties
```

---
