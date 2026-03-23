# 🚜 Wheel Share

> A web platform for renting farming and heavy machinery — built with Spring Boot, MySQL, and plain HTML/CSS/JS.

---

## 📌 Project Overview

Wheel Share connects equipment owners with farmers and contractors who need temporary access to heavy machinery. Users can browse available equipment, view rental pricing, manage their cart, and track rental requests — all from a localhost-hosted web interface.

This is a full-stack project running entirely on `localhost:8080` with a Spring Boot backend, MySQL database, and vanilla frontend pages.

---

## ✅ Current Features

| Feature | Status | URL |
|---|---|---|
| Equipment Listing | ✅ Working | `/equipments.html` |
| User Registration | ✅ Working | `/register.html` |
| User Login | ✅ Working | `/login.html` |
| User Dashboard (cart + rentals) | ✅ Working | `/user-dashboard.html` |
| Add Equipment (owner) | ✅ Working | `/add-equipment.html` |
| Owner Dashboard (rental requests) | 🚧 In Progress | `/dashboard.html` |
| Rent Now (booking flow) | 🚧 In Progress | via equipment page |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.1.3 |
| ORM | Spring Data JPA + Hibernate |
| Database | MySQL 8.x |
| Frontend | HTML5, CSS3, Vanilla JavaScript |
| Build Tool | Maven (via `mvnw`) |
| Server | Embedded Tomcat on port `8080` |

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/business/
│   │   ├── controllers/
│   │   │   ├── EquipmentController.java      # Seeds + serves GET /api/equipments
│   │   │   ├── WheelShareController.java     # User auth, rentals, equipment POST
│   │   │   └── UserController.java           # Legacy user management
│   │   ├── entities/
│   │   │   ├── Equipment.java                # Equipment table entity
│   │   │   ├── User.java                     # User table entity
│   │   │   └── RentalRequest.java            # Rental request entity (PENDING/APPROVED/REJECTED)
│   │   ├── repositories/
│   │   │   ├── EquipmentRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── RentalRequestRepository.java  # Includes findByUserId()
│   │   └── services/
│   │       └── UserServices.java
│   └── resources/
│       ├── application.properties            # DB config, port
│       ├── static/
│       │   ├── equipments.html               # Equipment listing page
│       │   ├── user-dashboard.html           # User dashboard (cart + rentals)
│       │   ├── dashboard.html                # Owner dashboard
│       │   ├── register.html
│       │   ├── login.html
│       │   ├── add-equipment.html
│       │   └── Images/                       # Local equipment images
│       └── templates/                        # Thymeleaf templates (legacy)
```

---
## 🖼️ Screenshots
<img width="1010" height="537" alt="Screenshot 2026-03-23 221112" src="https://github.com/user-attachments/assets/21837153-47f0-4dd8-9473-1c26e74a2f35" />
<img width="1728" height="927" alt="Screenshot 2026-03-23 221203" src="https://github.com/user-attachments/assets/44adce34-3acb-433c-ad0b-3e0e12435ef4" />
<img width="1007" height="524" alt="Screenshot 2026-03-23 221312" src="https://github.com/user-attachments/assets/1391c5d0-19b0-4867-af2e-ca19b9c17ec6" />
<img width="1661" height="857" alt="Screenshot 2026-03-23 221817" src="https://github.com/user-attachments/assets/408f0832-544d-4299-9bac-b85c1e32d4b2" />
<img width="1658" height="857" alt="Screenshot 2026-03-23 221854" src="https://github.com/user-attachments/assets/2071ac3c-23f5-4bcb-b454-b7c4eb02935e" />
<img width="1664" height="859" alt="Screenshot 2026-03-23 221931" src="https://github.com/user-attachments/assets/b5c5a541-4dd9-49f8-84a3-b42060cc1080" />

---

## ⚙️ Setup Instructions

### Prerequisites

- Java 17+
- MySQL 8.x running locally
- Maven (or use the included `mvnw` wrapper)

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/wheel-share.git
cd wheel-share
```

### 2. Create the MySQL Database

```sql
CREATE DATABASE businessproject;
```

### 3. Configure Database Credentials

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/businessproject
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
server.port=8080
```

### 4. Run the Application

**On Windows:**
```bash
mvnw.cmd spring-boot:run
```

**On Linux/macOS:**
```bash
./mvnw spring-boot:run
```

### 5. Open in Browser

| Page | URL |
|---|---|
| Equipment Listing | http://localhost:8080/equipments.html |
| Register | http://localhost:8080/register.html |
| Login | http://localhost:8080/login.html |
| User Dashboard | http://localhost:8080/user-dashboard.html |
| Owner Dashboard | http://localhost:8080/dashboard.html |
| Add Equipment | http://localhost:8080/add-equipment.html |

> **Note:** Register a user first, then login before accessing the User Dashboard.

---

## 🔌 API Endpoints

### Users

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users/register` | Register a new user |
| `POST` | `/api/users/login` | Login and receive user object |

**Register request body:**
```json
{
  "name": "Rajesh Kumar",
  "email": "rajesh@example.com",
  "password": "pass123",
  "phone": "9876543210"
}
```

**Login request body:**
```json
{
  "email": "rajesh@example.com",
  "password": "pass123"
}
```

---

### Equipment

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/equipments` | Fetch all available equipment |
| `POST` | `/api/equipments` | Add a new equipment listing |

**Add equipment request body:**
```json
{
  "name": "John Deere 5310",
  "type": "Tractor",
  "pricePerDay": 1500,
  "imageUrl": "/Images/tractor.jpeg"
}
```

---

### Rentals

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/rentals` | Create a new rental request |
| `GET` | `/api/rentals` | Get all rental requests (owner view) |
| `GET` | `/api/rentals/user/{userId}` | Get rentals for a specific user |
| `POST` | `/api/rentals/{id}/status` | Update rental status (APPROVED / REJECTED) |

**Create rental request body:**
```json
{
  "userId": 1,
  "equipmentId": 3
}
```

**Update status request body:**
```json
{
  "status": "APPROVED"
}
```

---

## 🗄️ Database Tables

| Table | Description |
|---|---|
| `user` | Registered users (name, email, password, phone) |
| `equipment` | Equipment listings (name, type, pricePerDay, imageUrl) |
| `rental_requests` | Rental requests with status tracking |

Hibernate auto-creates tables on first run (`ddl-auto=update`). No manual SQL migration required.

---

## 🧪 Testing the API (PowerShell)

```powershell
# Register
Invoke-RestMethod -Uri http://localhost:8080/api/users/register -Method POST `
  -ContentType "application/json" `
  -Body '{"name":"Test User","email":"test@test.com","password":"pass123"}'

# Get all equipment
Invoke-RestMethod -Uri http://localhost:8080/api/equipments -Method GET

# Create rental
Invoke-RestMethod -Uri http://localhost:8080/api/rentals -Method POST `
  -ContentType "application/json" `
  -Body '{"userId":1,"equipmentId":1}'
```

---



## 🚀 Future Improvements

- [ ] JWT-based authentication (replace `localStorage` session)
- [ ] Real payment gateway integration (Razorpay / Stripe)
- [ ] Equipment availability calendar (date-range booking)
- [ ] Owner profile and equipment management panel
- [ ] Email notifications on rental approval/rejection
- [ ] Search and filter equipment by location and type
- [ ] Mobile-responsive UI improvements

---

## 👥 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---



