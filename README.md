# 🐷 PiggyBank System

The **PiggyBank System** is a financial and savings management application built with **Java 17**, **Spring Boot (Maven)**, and **PostgreSQL**.  
It allows users to create savings goals, manage their financial activities, and organize data based on **Rwanda’s administrative hierarchy**.

---

## 📘 Project Description

This system helps users:
- Create and manage personal savings goals (Ideas and Events).  
- Record financial actions like deposits and withdrawals.  
- Organize users and data by location (Province, District, Sector, Cell, Village).  
- Retrieve information using different filtering and pagination options.  

It includes complete **CRUD operations** for major entities and implements **One-to-Many**, **Many-to-Many**, and **Self-Referencing** relationships through **JPA**.

---

## 🧩 Main Entities
- **Users** – People using the system.  
- **Locations** – Represent Rwanda’s full administrative structure.  
- **Categories** – Used to group or classify user savings.  
- **Ideas & Ideas_Events** – Represent savings goals and events.  
- **IdeasActions** – Financial actions linked to events.  

---

## 🔗 Entity Relationships

| Relationship Type | Description | Example |
|--------------------|-------------|----------|
| **One-to-Many** | One record relates to many others | Location → Users, User → Ideas_Events, Ideas_Events → IdeasActions |
| **Many-to-Many** | Entities related through a junction table | Users ↔ Categories |
| **Self-Referencing** | Entity relates to itself | Location → Location (Province → District → Sector → Cell → Village) |

---

## 📊 Entity Relationship Diagram (ERD)

The diagram below shows how all entities are connected in the system.  
🖼️ *All images are stored inside the `photos` folder in this repository.*


---

## 🧾 API Demonstration Screenshots

Below are screenshots from **Postman** showing how the system’s APIs work.  
📸 *All screenshots are available in the `photos` folder.*

| Action | Screenshot |
|---------|-------------|
| **Create a New User** |
| **Get User by Email** |
| **Update User** |
| **Delete User** |
| **Get Locations by Type (with Pagination)** |
---

## 🧠 Summary

- Built using **Java 17**, **Spring Boot**, **PostgreSQL**, and **Maven**  
- Includes **6 main entities** with full CRUD support  
- Demonstrates **Rwanda’s location hierarchy**  
- Includes **Postman-tested API endpoints** for user and location management  
- Implements **One-to-Many**, **Many-to-Many**, and **Self-Referencing** relationships  

---

## ⚖️ License

This project is licensed under the **MIT License**.  
You are free to use, modify, and distribute it with proper attribution.

---

> _“PiggyBank System — simple, structured, and smart savings management.”_
