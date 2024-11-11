# Aplikasi Data Karyawan

> Aplikasi untuk mengelola data karyawan dengan fitur CRUD menggunakan Spring Boot dan PostgreSQL.

## Table of Contents
1. [About the Project](#about-the-project)
2. [Features](#features)
3. [Tech Stack](#tech-stack)
4. [Prerequisites](#prerequisites)
5. [Installation](#installation)
6. [Usage](#usage)

## About the Project

Aplikasi Data Karyawan adalah sistem berbasis web yang dirancang untuk mengelola data karyawan. Dengan aplikasi ini, pengguna dapat melakukan operasi CRUD (Create, Read, Update, Delete) untuk menambah, melihat, memperbarui, dan menghapus data karyawan. Aplikasi ini dibangun menggunakan Spring Boot sebagai framework backend, dengan database PostgreSQL untuk menyimpan data karyawan.

Aplikasi ini dirancang agar mudah digunakan dan efisien, memungkinkan pengelolaan data karyawan dalam skala kecil hingga menengah dengan antarmuka frontend yang dapat diakses melalui URL yang disediakan.

## Features
- [x] **Create Employee**: Menambahkan data karyawan baru.
- [x] **Read Employees**: Melihat daftar karyawan dan melakukan pencarian berdasarkan NIK atau nama lengkap.
- [x] **Update Employee**: Memperbarui informasi karyawan.
- [x] **Delete Employee**: Menghapus data karyawan.
- [x] **CRUD Operations**: Semua operasi CRUD tersedia untuk manajemen data karyawan.
- [x] **Respons API dengan Status**: Setiap respon API dilengkapi dengan status kode HTTP dan pesan.

## Tech Stack
- **Java** - Java 21
- **Spring Boot** - Spring Boot 3.3.5
- **Database** - PostgreSQL
- **Maven** - Sebagai build tool

## Prerequisites
Pastikan Anda telah menginstal:
- **Java JDK 21** atau versi yang diperlukan
- **Maven** (untuk build dan dependency management)
- **Database** - PostgreSQL

## Installation
### 1. Clone Repository
```bash
git clone https://github.com/ardhiharry/employee-data-app.git
cd employee-data-app
```

### 2. Install Dependencies
```bash
mvn install
```

## Usage
Untuk menjalankan aplikasi, jalankan perintah berikut pada direktori aplikasi menggunakan Teminal:

MacOS/Linux:
```bash
./mvnw spring-boot:run
```

Windows:
```bash
mvnw.cmd spring-boot:run
```
