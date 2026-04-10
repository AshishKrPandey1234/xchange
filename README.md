# XCHANGE. 🔄

**Xchange** is a high-performance Full-Stack Skill Barter System designed to solve the "Double Coincidence of Wants" in a community. Unlike simple peer-to-peer apps, Xchange uses a **Graph-based engine** to find multi-party trade loops.

---

## 🚀 The Core Innovation
Traditional bartering fails when I have what you want, but you *don't* have what I want. Xchange solves this by mapping users as nodes in a directed graph and executing a **DFS (Depth-First Search)** algorithm to detect cycles (e.g., A → B → C → A), allowing three or more people to trade skills simultaneously.

## 🛠️ Tech Stack
This project demonstrates a **Distributed Database Architecture**:

* **Backend:** Java 21, Spring Boot 3.x, Spring Data JPA
* **Relational Database (MySQL):** Handles persistent user profiles, authentication, and credit systems.
* **NoSQL Database (MongoDB):** Manages dynamic skill nodes and trade requirements for high scalability.
* **Frontend:** HTML5, Tailwind CSS, JavaScript (ES6+).
* **API:** RESTful architecture with cross-origin resource sharing (CORS) enabled.

## 🏗️ System Architecture
The system follows a modular Monolith pattern:
- **Models:** Java entities mapped to MySQL and Document models for MongoDB.
- **Repositories:** Decoupled data access layers for both SQL and NoSQL.
- **Controllers:** REST endpoints for real-time CRUD operations.
- **Trade Engine:** Logic layer responsible for graph construction and cycle detection.



## 📋 Features
- **Smart Dashboard:** Live view of the community network.
- **Dual-DB Persistence:** Seamless integration of SQL and NoSQL in a single transaction flow.
- **Trade Simulation:** Interactive "Compute Match" functionality to initiate algorithmic trade detection.
- **Responsive UI:** Modern, clean interface designed with Tailwind CSS for a premium user experience.

## ⚙️ Setup & Installation
1. Clone the repository.
2. Ensure **MySQL** (Port 3306) and **MongoDB** (Port 27017) are running.
3. Update `application.properties` with your database credentials.
4. Run the Spring Boot application via IntelliJ or Maven.
5. Open `index.html` in your browser.

---

### **Author**
**Ashish Kumar Pandey** *B.Tech in Computer Science & Engineering* *Galgotias University | Class of 2027*
