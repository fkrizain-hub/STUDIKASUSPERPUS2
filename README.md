# STUDIKASUSPERPUS2
STUDIKASUSPERPUS2 adalah proyek yang dibuat untuk **memenuhi tugas kelompok** dalam mata kuliah Pemrograman Berorientasi Objek.  
Proyek ini ditulis menggunakan bahasa **Kotlin** dan mensimulasikan sistem sederhana untuk mengelola data perpustakaan, seperti anggota, buku, dan transaksi peminjaman.

## Fitur Utama
- **Manajemen Anggota**  
  Menambahkan, menampilkan, dan menghapus data anggota perpustakaan.

- **Manajemen Buku**  
  Menyimpan data buku cetak, digital, dan audio dengan pewarisan (inheritance) antar kelas.

- **Peminjaman Buku**  
  Simulasi peminjaman dan pengembalian buku oleh anggota.

- **Penerapan Konsep OOP**  
  Menerapkan konsep **class, inheritance, polymorphism, dan encapsulation** dalam kode.

## Teknologi yang Digunakan
- **Bahasa:** Kotlin  
- **IDE:** IntelliJ IDEA  

## Struktur Proyek
src/

├── model/

│   ├── AudioBook.kt

│   ├── Book.kt

│   ├── Borrowing.kt

│   ├── DigitalBook.kt

│   ├── Member.kt

│   ├── PrintedBook.kt

│   ├── Reservation.kt

│   ├── Role.kt

│   ├── Tier.kt

│   └── UserAccount.kt

│

├── repository/

│   ├── InMemoryRepo.kt

│   └── Repository.kt

│

├── service/

│   ├── AuthenticationService.kt

│   ├── CatalogueService.kt

│   ├── CirculationService.kt

│   ├── FineStrategy.kt

│   ├── MemberService.kt

│   ├── ReportService.kt

│   └── ReservationService.kt

│

├── util/

│   └── IdGenerator.kt

│

└── Main.kt

## Cara Menjalankan Program
1.	Clone repository ini: git clone https://github.com/fkrizain-hub/STUDIKASUSPERPUS2
2.	Buka folder proyek di IntelliJ IDEA.
3.	Jalankan file Main.kt pada direktori src/main/kotlin/.
4.	Ikuti instruksi yang muncul di terminal.

## Pembagian Tugas
1.	Anggota 1: Analisis & desain class diagram (OOP structure + UML)

	Membuat Use Case Diagram dan menjelaskan kebutuhan sistem.
3.	Anggota 2: Implementasi model & repository

	Mendesain Class Diagram dan relasi antar objek.
5.	Anggota 3: Implementasi service logic (peminjaman, pengembalian, reservasi)

	Mengimplementasikan model serta logika dasar pada sistem.
7.	Anggota 4: Implementasi antarmuka CLI & laporan

	Mengembangkan logic lanjutan dan melakukan implementasi interface (IML).

## Output Yang Diharapkan
C:\Users\jovan\.jdks\openjdk-24.0.2+12-54\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA 2025.2\lib\idea_rt.jar=53757" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath C:\Users\jovan\IdeaProjects\perpus2\out\production\perpus2;C:\Users\jovan\.m2\repository\org\jetbrains\kotlin\kotlin-stdlib-jdk8\2.1.21\kotlin-stdlib-jdk8-2.1.21.jar;C:\Users\jovan\.m2\repository\org\jetbrains\kotlin\kotlin-stdlib\2.1.21\kotlin-stdlib-2.1.21.jar;C:\Users\jovan\.m2\repository\org\jetbrains\annotations\13.0\annotations-13.0.jar;C:\Users\jovan\.m2\repository\org\jetbrains\kotlin\kotlin-stdlib-jdk7\2.1.21\kotlin-stdlib-jdk7-2.1.21.jar MainKt

Book Laskar Pelangi added successfully
Book Bumi Manusia added successfully
Book Filosofi Teras added successfully
Book Clean Code added successfully
Book Atomic Habits added successfully
 New Member Andi with ID: AG-0001 tier REGULAR added successfully
Account login for Andi has been made (Username: andi,Pass: 123456).
 New Member John with ID: AG-0002 tier PREMIUM added successfully
Account login for John has been made (Username: john,Pass: 123456).
 New Member Freddy with ID: AG-0003 tier STAFF added successfully
Account login for Freddy has been made (Username: freddy,Pass: 123456).

--- setup successful ---

Username: admin
Password: admin123\
Username or password does not match username or password! Try Again

Username: admin
Password: admin123

 Login successful! welcome to adminADMIN!

===== MENU ADMIN =====
1. Tambah Anggota Baru
2. Tampilkan Laporan
3. Tampilkan Semua Buku (Test ID)
4. Tampilkan Semua Anggota (Test ID)
5. Logout
Pilih menu Admin: 1
Nama Anggota Baru: jovan
Tier (REGULAR, PREMIUM, STAFF): STAFF
 New Member jovan with ID: AG-0004 tier STAFF added successfully
Account login for jovan has been made (Username: jovan,Pass: 123456).

===== MENU ADMIN =====
1. Tambah Anggota Baru
2. Tampilkan Laporan
3. Tampilkan Semua Buku (Test ID)
4. Tampilkan Semua Anggota (Test ID)
5. Logout
Pilih menu Admin: 4
--- Daftar Semua Anggota ---
ID: AG-0001 | Nama: Andi | Tier: REGULAR
ID: AG-0002 | Nama: John | Tier: PREMIUM
ID: AG-0003 | Nama: Freddy | Tier: STAFF
ID: AG-0004 | Nama: jovan | Tier: STAFF

===== MENU ADMIN =====
1. Tambah Anggota Baru
2. Tampilkan Laporan
3. Tampilkan Semua Buku (Test ID)
4. Tampilkan Semua Anggota (Test ID)
5. Logout
Pilih menu Admin: 5
Anda telah logout.
Username: pustakawan
Password: perpus456

 Login successful! welcome to pustakawanLIBRARIAN!

===== MENU PUSTAKAWAN =====
1. Tambah Buku 
2. Pinjam Buku
3. Kembalikan Buku
4. Tampilkan Laporan
5. Cari Buku
6. Tampilkan Antrian Reservasi
7. Tampilkan Semua Buku (Test ID)
8. Tampilkan Semua Anggota (Test ID)
9. Logout
Pilih menu Pustakawan: 1

Pilih Kategori Buku yang Ingin Ditambahkan:
1. Buku Cetak
2. Buku Digital
3. Buku Audio
Masukkan pilihan (1-3): 1

--- Tambah Buku Cetak ---
Judul: Belajar Mengiklhaskan
Penulis: Jose Situmeang
Tahun: 2025
Kategori: Cetak
Jumlah Halaman: 34
Stok: 4
Book Belajar Mengiklhaskan added successfully
✅ Buku cetak berhasil ditambahkan.

===== MENU PUSTAKAWAN =====
1. Tambah Buku 
2. Pinjam Buku
3. Kembalikan Buku
4. Tampilkan Laporan
5. Cari Buku
6. Tampilkan Antrian Reservasi
7. Tampilkan Semua Buku (Test ID)
8. Tampilkan Semua Anggota (Test ID)
9. Logout
Pilih menu Pustakawan:
