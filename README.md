
# QuitQ - Full Stack E-Commerce Application (React + Spring Boot + MySQL)

This is a case study project developed as part of the **Hexaware Java+React FSD Training Program**. The project demonstrates a full-stack **E-Commerce Application** with essential functionalities like product management, cart, wishlist, order placement, and user authentication, built using **React**, **Spring Boot**, and **MySQL**.

---

## Table of Contents

- [Features](#features)  
- [Technologies Used](#technologies-used)  
- [Project Structure](#project-structure)  
- [Setup Instructions](#setup-instructions)  
- [Schema Design](#schema-design)  
- [Developed By](#developed-by)  

---

## Features

- **User Authentication**: Secure JWT-based login and registration.  
- **Product Management**: Sellers can add, view, and manage products.  
- **Cart Management**: Users can add or remove products from their cart.  
- **Wishlist Functionality**: Save products for future purchase.  
- **Order Management**: Place orders, track orders, and manage order details.  
- **Shipping Address Management**: Save and select addresses during checkout.  
- **Payment Handling**: Basic payment information storage and management.  
- **Role-based Access**: Distinct access levels for users and sellers.  

---

## Technologies Used

- **Frontend**: React, Vite, Axios, React-Bootstrap  
- **Backend**: Spring Boot, Spring Security with JWT, JPA, Hibernate  
- **Database**: MySQL  
- **Additional Tools**: Lombok, ModelMapper  

---

## Project Structure

```
CaseStudy-QuitQ/
├── QuitQ/           # Spring Boot backend project
└── quitq-frontend/  # React frontend project
```

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yuva-r/CaseStudy---QuitQ.git  
cd CaseStudy-QuitQ  
```

---

### 2. Backend Setup (Spring Boot)

- Open the `QuitQ` folder in your preferred IDE (VS Code, Eclipse, IntelliJ).  
- Configure the database credentials in `src/main/resources/application.properties`:  

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quitq  
spring.datasource.username=root  
spring.datasource.password=yourpassword  
```

- Run the application. Spring Boot will automatically create the required tables if JPA is properly configured.  

---

### 3. Frontend Setup (React)

```bash
cd quitq-frontend  
npm install  
npm run dev  
```

The frontend will be accessible at [http://localhost:5173](http://localhost:5173).

---

### 4. Database Setup

Create the database in MySQL:

```sql
CREATE DATABASE quitq;
```

Tables will be generated automatically by Spring Boot using JPA.

---

## Schema Design (Key Tables Overview)

- **users** — User credentials and role information  
- **products** — Product details linked to sellers  
- **categories** — Product categories  
- **cart**, **cart_items** — Shopping cart functionality  
- **wishlist** — Wishlist management  
- **orders**, **order_items** — Order processing  
- **shipping_address** — Shipping address information  
- **payment** — Basic payment details  

---

## Developed By

- **Yuvasheee R**  
  Trainee @ Hexaware Technologies  
