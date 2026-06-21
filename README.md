# 🎬 StreamPulse: Studio Inventory & Billing Control

A premium, Netflix-style dark aesthetic desktop application built using **Java Swing** and **MySQL** to manage studio equipment inventory and generate dynamic real-time invoices.

## ✨ Features
- **Dynamic Inventory Cards:** Live stock status directly fetched from the database.
- **Quick Bill Generator:** Anti-error product dropdown menu.
- **Real-time Analytics:** Instant "Total Orders" and "Total Earnings" tracker cards.
- **Premium UI:** Fully customized dark cyberpunk layout.

## 🛠 Tech Stack
- **Language:** Java
- **UI Framework:** Java Swing
- **Database:** MySQL
- **Connector:** MySQL JDBC Driver

## 🚀 How to Run
1. Setup your MySQL database with the `studio_db` schema.
2. Ensure you have the `mysql-connector-j-x.x.x.jar` file in your project folder.
3. Compile the project:
   ```bash
   javac -cp ".;mysql-connector-j-9.6.0.jar" InventoryDashboard.java