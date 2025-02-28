# 💰 Personal Budget Manager

🚀 **Personal Budget Manager** is an application for managing personal budgets, tracking expenses and income, and analyzing finances over time.

---

## 📌 Features
✅ Create and manage a monthly budget  
✅ Add transactions (expenses and income)  
✅ Automatically update budget status  
✅ PostgreSQL database running in Docker  
✅ Full CRUD REST API (budgets, transactions)    
✅ Error handling and data validation  
✅ Unit and integration tests  

---

## 🛠 Technologies
🔹 **Java 21**  
🔹 **Spring Boot 3 (Spring MVC, Spring Data JPA)**  
🔹 **PostgreSQL (running in Docker)**  
🔹 **Hibernate**   
🔹 **JUnit 5 & Mockito**  
🔹 **Maven**  
🔹 **Docker**  

---

## 🏗 Running the Application Locally
### 🔹 **1. Clone the repository**
```sh
git clone https://github.com/PTR512/budget-manager.git
cd budget-manager
```
### 🔹 **2. Start PostgreSQL Database in Docker**
```sh
docker-compose up -d
```
📌 If you don’t have Docker, you can run PostgreSQL manually.

### 🔹 **3. Configure the application**
Edit the `src/main/resources/application.properties` file if you need to change database settings.

Default configuration:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/budget_db
spring.datasource.username=budget_user
spring.datasource.password=budget_pass
```

### 🔹 **4. Run the application**
```sh
mvn spring-boot:run
```
The application will start at `http://localhost:8080`.

---

## API Endpoints
🔹 Create a new Budget **POST** `/api/budgets`  
🔹 Get a budget for given month and year **GET** `/api/budgets/{year}/{month}`  
🔹 Update a budget **PUT** `/api/budgets/{id}`  
🔹 Delete a budget **DELETE** `/api/budgets/{id}`  
🔹 Get all transactions **GET** `/api/transactions`  
🔹 Get transactions by budget ID **GET** `/api/transactions/budget/{budgetId}`  
🔹 Get transaction by its ID **GET** `/api/transactions/{id}`  
🔹 Create a new transaction **POST** `/api/transactions`  
🔹 Update a transaction **PUT** `/api/transactions/{id}`  
🔹 Delete a transaction **DELETE** `/api/transactions/{id}`  

---

## 📡 Sample API Requests
### 🔹 **1. Create a New Budget**
📌 **POST** `/api/budgets`
```json
{
  "month": "2024-02",
  "limitAmount": 5000.00,
  "currentExpenses": 0.00
}
```
🔹 **Response:** `201 Created`

---

### 🔹 **2. Add a Transaction to a Budget**
📌 **POST** `/api/transactions?budgetId=1`
```json
{
  "amount": 150.00,
  "category": "Food",
  "type": "EXPENSE",
  "date": "2024-03-02"
}
```
🔹 **Response:** `201 Created`

---

## ✅ Running Tests
To execute unit and integration tests:
```sh
mvn test
```

---

## 📄 License
This project is licensed under the **MIT License** – feel free to modify and use it as needed.  

---

👨‍💻 **Author:** [Piotr Marszałek](https://github.com/PTR512)   
