# 🎓 Student Management System (Java Swing + MySQL)

A simple **Java GUI-based application** to manage student records with **marks, total, average, grade, and result**, integrated with a **MySQL database**.

---

## 🚀 Features

* 🖥️ User-friendly GUI using **Java Swing**
* 📊 Enter student marks (3 subjects)
* 🧮 Auto calculation of:

  * Total
  * Average
  * Grade
  * Result (Pass / Fail)
* 💾 Store data in **MySQL database**
* 📂 View all student records
* 🎨 Styled UI with **Nimbus Look & Feel**

---

## 🛠️ Technologies Used

* Java (Swing, AWT)
* MySQL (Database)
* JDBC (Database Connectivity)

---

## 📸 Application Modules

* ➤ Student Form Input
* ➤ Calculation Engine
* ➤ Database Insert
* ➤ Data Viewer

---

## 🗄️ Database Setup

### Step 1: Create Database

```sql
CREATE DATABASE YOUR_DB_NAME;
USE YOUR_DB_NAME;
```

### Step 2: Create Table

```sql
CREATE TABLE student (
    name VARCHAR(50),
    m1 INT,
    m2 INT,
    m3 INT,
    total INT,
    avg DOUBLE,
    grade VARCHAR(10),
    result VARCHAR(10)
);
```

---

## ⚙️ Configuration

Update your DB credentials in code:

```java
Connection con = DriverManager.getConnection(
    "DATABASE_CONNECTION_PATH",
    "USER_NAME",
    "USER_PASSWORD"
);
```

---
🖼️ Actual GUI

<img width="1920" height="1020" alt="image" src="https://github.com/user-attachments/assets/b66db5a5-7990-4711-93d1-c53858caf59f" />


## ▶️ How to Run

### 🔹 Compile

```bash
javac StudentGUI.java
```

### 🔹 Run

```bash
java StudentGUI
```

---

## 📦 Run as JAR

```bash
java -jar StudentApp.jar
```

👉 If using MySQL connector:

```bash
java -cp "StudentApp.jar;mysql-connector-j-8.x.x.jar" NewSwingAwt.StudentGUI
```

---

## ⚠️ Common Errors & Fixes

| Error                | Solution                          |
| -------------------- | --------------------------------- |
| DB connection failed | Check MySQL running & credentials |
| Column mismatch      | Ensure DB table has all 8 columns |
| No main manifest     | Fix manifest file                 |
| Class not found      | Add MySQL connector               |

---

## 🌟 Future Improvements

* 🔍 Search student by name
* ✏️ Update & Delete records
* 📊 JTable view (advanced UI)
* 🎨 Dark mode customization
* 📦 Convert to EXE installer

---

## 👨‍💻 Author

**IMMANUVEL REEGAN A**

---

## 📄 License

This project is for educational purposes.
