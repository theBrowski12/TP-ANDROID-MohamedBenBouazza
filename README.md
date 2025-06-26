# TP-ANDROID-MohamedBenBouazza

Android Studio Project - Ecommerce App

---

## 📌 Overview

This is a sample Ecommerce Android application built with Jetpack Compose following a clean and modular architecture. The app allows users to browse products, view details, add products to cart, and manage user authentication with roles (guest, member, admin). Admin users can add, edit, and delete products.

---

## 🛠 Features

### User Features

- Browse product list with categories and search
- View detailed product information
- Add products to shopping cart (persisted locally)
- checkout products via Whatsapp (confirmation service)
- View and manage shopping cart contents
- User registration and login with email/password
- Profile management with editable user information
- Role-based access control (guest, member, admin)

### Admin Features

- Add new products with image, description, price, quantity
- Edit existing products
- Delete products
- View list of all users (admin only)
- Change user roles (admin only)

### Technical Features

- Jetpack Compose UI with Material3 components
- State management with ViewModel + Kotlin Flow
- Navigation with Jetpack Navigation Compose
- Clean architecture: separation of UI, ViewModel, repository layers
- Network communication with REST API backend (Node.js + MongoDB)
- Secure user authentication with JWT tokens
- Image loading via remote URLs
- Local cart persistence and sync on login/logout

---

## 🖼 Screenshots

### Home Screen (Product List with Categories and Search)


![WhatsApp Image 2025-06-26 at 17 06 34_5de431c3](https://github.com/user-attachments/assets/9d00609d-2d2f-4f26-a7e6-b4471d4ee8e0)

![WhatsApp Image 2025-06-26 at 17 06 34_778c16a4](https://github.com/user-attachments/assets/ef99f387-fd6f-4cc2-a91e-46aa4cfe797a)



### Product Details Screen

![image](https://github.com/user-attachments/assets/2a3e470a-2922-4957-9d10-665dbb538b6f)

### Shopping Cart Screen

![image](https://github.com/user-attachments/assets/122619b5-2e48-4a80-ab4f-c0ccbfb822ca)


### Checkout Via Whatsapp With predefined Message:

![WhatsApp Image 2025-06-26 at 17 18 19_db8200ff](https://github.com/user-attachments/assets/408fc23a-55e6-4fb9-bc9f-1d6d383fbf46)


### Login Screen

![WhatsApp Image 2025-06-26 at 17 21 30_ad0e3eb2](https://github.com/user-attachments/assets/be18ac16-1fa1-41c7-9191-5753fc2b1b19)

### Register Screen

![WhatsApp Image 2025-06-26 at 17 21 30_eba1e627](https://github.com/user-attachments/assets/7a605fb4-34e1-4039-a597-614099a95052)



### User Profile Screen

![WhatsApp Image 2025-06-26 at 17 05 06_7e865c3a](https://github.com/user-attachments/assets/bf289a13-41ef-4390-9468-4bcab1491370)

### Admin Home Screen (Admin Only with extra functionality ilke : Add Product, Edit Product , Delete Product)

![WhatsApp Image 2025-06-26 at 17 12 40_75b909b8](https://github.com/user-attachments/assets/3ef2395d-20d4-4b81-ad32-d61d41d5d47a)

### Admin Product Management Screen

![WhatsApp Image 2025-06-26 at 17 14 42_7dfc321b](https://github.com/user-attachments/assets/d3145744-f333-40fc-90bf-318377f76f7d)


### User Management Screen (Admin Only)

![WhatsApp Image 2025-06-26 at 17 15 23_0d1730c6](https://github.com/user-attachments/assets/f1e4e121-5e4c-48ae-a98c-d9783b1abfec)

![WhatsApp Image 2025-06-26 at 17 15 23_159d519f](https://github.com/user-attachments/assets/21619209-e19c-4e13-be13-de8a301a8c47)

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Flamingo or later
- Android device or emulator running API 23+
- Node.js backend server running with MongoDB (optional for full backend integration)

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/TP-ANDROID-MohamedBenBouazza.git
