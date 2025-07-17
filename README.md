# Job‑Shop Accounting Database System

A Java application for managing job‑shop manufacturing operations with Azure SQL Database.  
Tracks customers, assemblies, processes, jobs, and cost transactions through a console interface and web application.

## Features
- Complete manufacturing workflow management  
- Cost tracking across assemblies, processes, and departments  
- Console application with 18 menu options  
- Web interface for customer management and search  
- Data import/export functionality  
- Comprehensive error handling

## Tech Stack
- **Backend:** Java 11 + JDBC  
- **Database:** Azure SQL Database  
- **Web:** JSP + HTML/CSS  
- **Server:** Apache Tomcat

## Getting Started

### Prerequisites
- Java 11 or later  
- Azure SQL Database account  
- Apache Tomcat 9+

### Setup
1. Clone the repo  
2. Set up an Azure SQL Database  
3. Run the SQL scripts in the `sql/` folder to create tables  
4. Update the database connection details in the Java code  
5. Compile and run `JobShopAccountingSystem.java`  
6. Deploy the JSP files in `web/` to your Tomcat server

## Database Design
The system manages relationships between:
- Customers ↔ their assembly orders  
- Assemblies ↔ multiple manufacturing steps  
- Jobs ↔ individual work units  
- Cost transactions ↔ multiple account types

**Key features**:  
- Three process types (Paint, Fit, Cut) with specialized attributes  
- Full cost accounting across all operations

## Usage

**Console Application**  
WELCOME TO THE JOB‑SHOP ACCOUNTING DATABASE SYSTEM
(1) Enter a new customer
…
(18) Quit

**Web Interface**  
- Add new customers: `/addCustomer.jsp`  
- Search customers by category: `/listCustomers.jsp`

## Project Structure
├── src/
│ ├── JobShopAccountingSystem.java
│ └── database/
├── sql/
│ ├── create_tables.sql
│ └── queries.sql
├── web/
│ ├── addCustomer.jsp
│ ├── listCustomers.jsp
│ └── WEB-INF/web.xml
├── lib/
└── docs/

## License
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

## Contact
Naeem Shahabi Sani – shahabi@ou.edu  
Project homepage: https://github.com/naeemshb/job-shop-accounting
