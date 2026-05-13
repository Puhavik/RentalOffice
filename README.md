# RentalOffice

A study project for managing a vehicle rental business with an **Oracle database**, a **PHP web interface**, and **Java utilities** for CLI operations and test data generation.

## Project Overview

This repository combines:
- **Database schema and SQL scripts** for rental offices, customers, vehicles, reservations, employees, and deliveries.
- **PHP CRUD pages** to manage rental offices and customers from a browser.
- **Java helper classes** to insert records, run CLI flows, and generate bulk sample data.

The project is useful as a compact example of a multi-layered database application with relational integrity and basic UI.

## Tech Stack

- **Database:** Oracle SQL / PL/SQL
- **Backend scripts:** PHP
- **Utilities / Data seeding:** Java (Maven project)
- **Frontend:** Simple HTML + CSS

## Repository Structure

- `create.sql` — full schema creation (tables, keys, triggers, sequence, stored procedure)
- `drop.sql` — object cleanup script
- `insert.sql` — manual seed data
- `DBSHelper.php` — reusable database helper for PHP scripts
- `index.php` — main web page with forms for CRUD actions
- `add*.php`, `read*.php`, `update*.php`, `delete*.php` — action handlers for entities
- `showCustomersInOffice.php` — procedure-based customer count by office
- `DBS_Helper.java` — Java DB helper with insert and auto-insert methods
- `Main.java` — entry point for batch auto-insert scenarios
- `cli.java` — interactive command-line input flow
- `pom.xml` — Maven configuration

## Data Model (High-Level)

Main entities:
- `rental_office`
- `customer`
- `friends` (many-to-many customer relation)
- `car`
- `truck` and `passenger_car` (specialization by `plate_number`)
- `reservation`
- `employee`
- `delivery`

Key behavior implemented in SQL:
- Cascade deletes between related entities
- Employee ID sequence + trigger
- Trigger for logging inserted rental office data
- Stored procedure for counting customers in a rental office

## Prerequisites

Before running the project, make sure you have:

1. **Oracle Database** instance accessible from your environment.
2. **PHP** runtime (recommended PHP 7.4+).
3. **Java JDK** (8+ recommended) and **Maven**.
4. Correct database credentials configured in Java and PHP helper files.

## Setup Instructions

### 1) Initialize the database

Run SQL scripts in this order:

1. `create.sql`
2. `insert.sql` (optional but recommended for demo data)

If you need a clean reset, run `drop.sql` first and then recreate.

### 2) Configure database connections

Update connection settings in:
- `DBSHelper.php`
- `DBS_Helper.java`

Set host, port, service/SID, username, and password to your local Oracle setup.

### 3) Run the PHP web interface

Use any local PHP server from the repository root, for example:

```bash
php -S localhost:8000
```

Then open:

```text
http://localhost:8000/index.php
```

### 4) Run Java utilities

Build and run with Maven/Java after configuring DB access.

- `Main.java` — for bulk auto-insert methods.
- `cli.java` — for manual insertion via terminal prompts.

> Note: Some methods in Java files are intentionally toggled by uncommenting specific lines.

## Features

### Web UI (PHP)

From `index.php`, you can:
- Add, update, read, and delete rental offices
- Add, update, read, and delete customers
- Show number of customers in a rental office

### Java CLI and Seeder

- Insert individual entities (office, customer, friends, car, reservation, employee, delivery)
- Generate random sample data in bulk for stress testing and demos

## Known Notes / Caveats

- There are minor naming inconsistencies in some variables and prompts (e.g., `reantal` typo) that do not change the conceptual flow.
- Trigger/procedure behavior depends on Oracle-specific SQL and may require Oracle-compatible tooling (`sqlplus`, SQL Developer, etc.).
- Input validation and security hardening (e.g., stricter sanitization) can be improved for production usage.

## Suggested Improvements

If you plan to evolve this project:
- Add centralized config files for DB credentials
- Introduce prepared statements everywhere and stronger validation
- Add unit/integration tests for Java helpers and PHP handlers
- Containerize Oracle XE + PHP app for reproducible local startup
- Add role-based access and authentication to the web interface

## License

No explicit license is currently defined in this repository.
If you want open usage, add a `LICENSE` file (e.g., MIT or Apache-2.0).
