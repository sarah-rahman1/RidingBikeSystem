#  RidingBikeSystem

(A Corporate Fleet & Ride-Hailing Management System)

An enterprise-ready, console-driven Ride Allocation and Booking Platform built on Java. This architecture leverages Hibernate Object-Relational Mapping (ORM) to handle persistence logic over a local PostgreSQL database instance. The application provides comprehensive controls for orchestrating users, managing driver groups, deploying vehicles, executing real-time trip booking workflows, updating wallets, and aggregating analytical system logs.

## 📌 Architectural Features & Capabilities

The system exposes a secure, structured Command Line Interface (CLI) partitioned into clear functional modules:

### 1. Consumer Directory Management
* Registration Engine:Provision new accounts complete with runtime email pattern validations.
* Global Inventory: Fetch structured registries of all consumers currently saved inside the schema.
* Information Upgrades: Hot-swap or update user metadata profiles dynamically (Names, Contact Lines, Electronic Mail addresses).
* Profile Deprovisioning:Safely delete consumers from the active lookup arrays.
* Targeted Lookups: Index individual profile rows directly via primary sequence identifier keys.

### 2. Service-Provider Operations
* Driver Onboarding: Inject certified service providers into the persistent storage engine.
* Availability Tracking: Track real-time driver state transitions through structural indicators (`AVAILABLE`, `BUSY`, `OFFLINE`).
* Operator Updates: Modify, edit, or strip driver entities dynamically out of the environment.

### 3. Fleet Assets & Logistics
* Vehicle Onboarding: Track and catalog company vehicles categorized across multiple deployment tiers (`BIKE`, `AUTO`, `CAB`).
* Dynamic Linking: Bind and balance vehicles directly to independent drivers via strict transactional relationships.

### 4. Trip Orchestration Engine
* Matching Logic: Process ride inquiries by evaluating geographic parameters against active, unassigned operator sets.
* Trip Lifecycle Termination: Automated systems to calculate fares, drop vehicle status flags back to active availability arrays, and trigger financial workflows.
* Lifecycle Interruption: Safely cancel pending or active requests while rolling back associated entity states.

### 5. Financial Ledger & Diagnostics
* Automated Bookkeeping: Generates structured immutable financial entries the exact moment a trip is finalized.
* Analytical Reporting: Generate isolated consumer trip statements, isolate complete records matching specific operator keys, track audit trails, or sort operators utilizing availability criteria.

---

## 🛠 Integrated Technology Stack

* Platform Environment: Java 17 Development Kit or higher
* Relational Database Engine: PostgreSQL (Supported via native JDBC Driver version 42.7.7)
* Persistence Layer (ORM): Hibernate Framework 6.4.4.Final (Adhering to Jakarta Persistence JPA 3.1 specifications)
* Data Ingestion Constraints: Hibernate Validator 8.0.2.Final
* Dependency & Dependency Management: Apache Maven

### Structural Relational Entity Model
* Users: Maps demographic profiles, unique identifier columns, strict contact strings, and real-time wallet balances.
* Driver: Manages personal data fields, dynamic operator status parameters, and a explicit 1-to-1 data link mapping to specific fleet machinery.
* Vehicle: Holds license parameters, internal manufacturing designations, category flags, and a back-reference owner binding to a specific operator.
* Ride: Contains geographic points, fare totals, transactional lifecycle indicators, associated consumers, and field references to assigned drivers.
* Transaction: Encapsulates unique record tracking arrays, payment mode markers, transaction sums, calendar timestamps, and target consumer handles.

---

## ⚙️ Initial Startup & Local Topology Setup

### Workspace Requirements
1. Java Development Kit (JDK) version 17+ configured within system environment arrays.
2. Apache Maven build tool compiled and discoverable via command terminal paths.
3. A local PostgreSQL database engine active and listening on standard local ports.

### 1. Schema Provisioning
Connect to your native database client and initialize the core structural layer:
```sql
CREATE DATABASE "rideBookingSystem";
```

### 2. Configuration Settings
Navigate to the resources package array and launch `persistence.xml` within a plain-text editor to provide local endpoint security details:
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/rideBookingSystem"/>
<property name="jakarta.persistence.jdbc.user" value="postgres"/>
<property name="jakarta.persistence.jdbc.password" value="YOUR_PASSWORD"/>
```
*System Notice: The environment relies on the automated schema updates engine (`update`), meaning physical relational tables and primary arrays deploy on the initial compilation cycle automatically.*

---

## 🏃 Execution Commands

### Compile and Resolve Project Packages
To download dependencies and run compiler optimizations over your code workspace:
```bash
mvn clean compile
```

### Bootstrap the Application Loop
To launch the core engine runtime and trigger the entry point thread class, invoke:
```bash
mvn exec:java -Dexec.mainClass="com.ride.main.RideDriver"
```

---

## 📂 Logical Workspace Directories

```text
RidingBikeSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ride/
│   │   │       ├── dao/          # Storage Objects managing baseline CRUD statements
│   │   │       ├── entity/       # Structural JPA database mapping records
│   │   │       ├── enums/        # System category sets and state variables
│   │   │       ├── service/      # Transactional rules and calculation engine
│   │   │       └── main/         # Application bootstrap thread and configuration loaders
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml  # JPA driver routing configurations
│   └── test/                     # Functional evaluation layers
├── pom.xml                       # Dependency management descriptors
└── .gitignore                    # Automation ignore arrays
```
