import model.*
import repository.*
import service.*

// --- INISIALISASI LAYER ---
// (Repo Anda, ditambah repo untuk Akun)
val bookRepo: Repository<Book> = InMemoryRepo()
val memberRepo: Repository<Member> = InMemoryRepo()
val borrowRepo: Repository<Borrowing> = InMemoryRepo()
val userAccountRepo: Repository<UserAccount> = InMemoryRepo() // <-- SUDAH DITAMBAHKAN

val catalogueservice = Catalogueservice(bookRepo)
// Berikan userAccountRepo ke MemberService
val memberservice = Memberservice(memberRepo, userAccountRepo) // <-- SUDAH DI-UPDATE
val reservationservice = Reservationservice()
val circulationservice = Circulationservice(bookRepo, memberRepo, borrowRepo, reservationservice)
val reportservice = Reportservice(borrowRepo, bookRepo, reservationservice)
val authservice = Authenticationservice(userAccountRepo) // (Inisialisasi Anda sudah benar)


// --- FUNGSI SETUP DATA AWAL ---
fun setupearlydata() {
    // Setup Buku (dari kode Anda)
    catalogueservice.addPrintedBook("Laskar Pelangi", "Andrea Hirata", 2005, "Novel", 529, 5)
    catalogueservice.addPrintedBook("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Sejarah", 535, 0) // Stok 0
    catalogueservice.addPrintedBook("Filosofi Teras", "Henry Manampiring", 2018, "Self-Help", 346, 3)
    catalogueservice.addDigitalBook("Clean Code", "Robert C. Martin", 2008, "Pemrograman", 22.5, "PDF")
    catalogueservice.addDigitalBook("Atomic Habits", "James Clear", 2018, "Self-Help", 5.3, "EPUB")

    // --- LOGIKA SETUP MEMBER BARU ---
    // (Sekarang memberservice akan OTOMATIS membuat AkunPengguna juga)
    memberservice.addMember("Andi", Tier.REGULAR)     // Akan mendapat ID: AG-0001
    memberservice.addMember("John", Tier.PREMIUM)    // Akan mendapat ID: AG-0002
    memberservice.addMember("Freddy", Tier.STAFF)    // Akan mendapat ID: AG-0003

    // Setup Akun Admin & Pustakawan (TETAP DI SINI)
    userAccountRepo.save(UserAccount("admin", "admin", "admin123", Role.ADMIN))
    userAccountRepo.save(UserAccount("pustakawan", "pustakawan", "perpus456", Role.LIBRARIAN))

    println("\n--- setup successful ---\n")
}

// --- FUNGSI TAMPILAN MENU (BARU) ---

fun showAdminMenu(currentUser: UserAccount): UserAccount? {
    println("\n===== MENU ADMIN =====")
    println("1. Tambah Buku Cetak")
    println("2. Tambah Anggota Baru")
    println("3. Tampilkan Laporan")
    println("4. Tampilkan Semua Buku (Test ID)")
    println("5. Tampilkan Semua Anggota (Test ID)")
    println("6. Logout")
    print("Pilih menu Admin: ")

    when (readLine()) {
        "1" -> {
            print("Judul: "); val j = readLine()!!
            print("Penulis: "); val p = readLine()!!
            print("Tahun: "); val t = readLine()!!.toInt()
            print("Kategori: "); val k = readLine()!!
            print("Jml Halaman: "); val h = readLine()!!.toInt()
            print("Stok: "); val s = readLine()!!.toInt()
            catalogueservice.addPrintedBook(j, p, t, k, h, s)
        }
        "2" -> {
            print("Nama Anggota Baru: "); val n = readLine()!!
            print("Tier (REGULAR, PREMIUM, STAFF): "); val t = Tier.valueOf(readLine()!!.uppercase())
            memberservice.addMember(n, t) // Ini akan otomatis membuat akun login
        }
        "3" -> {
            reportservice.showactiveborrow()
            reportservice.showtopbook()
            reportservice.showqueuereservation()
        }
        "4" -> {
            println("--- Daftar Semua Buku ---")
            bookRepo.findAll().forEach { println(it.info()) }
        }
        "5" -> {
            println("--- Daftar Semua Anggota ---")
            memberRepo.findAll().forEach { println("ID: ${it.id} | Nama: ${it.name} | Tier: ${it.tier}") }
        }
        "6" -> {
            println("Anda telah logout.")
            return null // Mengembalikan null untuk logout
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser // Tetap login
}

fun showLibrarianMenu(currentUser: UserAccount): UserAccount? {
    println("\n===== MENU PUSTAKAWAN =====")
    println("1. Pinjam Buku")
    println("2. Kembalikan Buku")
    println("3. Tampilkan Laporan")
    println("4. Cari Buku")
    println("5. Tampilkan Antrian Reservasi")
    println("6. Tampilkan Semua Buku (Test ID)")
    println("7. Tampilkan Semua Anggota (Test ID)")
    println("8. Logout")
    print("Pilih menu Pustakawan: ")

    when (readLine()) {
        "1" -> {
            print("Masukkan ID Anggota: ")
            val idMember = readLine()!!
            print("Masukkan ID Buku: ")
            val idBook = readLine()!!
            circulationservice.borrowingbook(idMember, idBook)
        }
        "2" -> {
            print("Masukkan ID Anggota: ")
            val idMember = readLine()!!
            print("Masukkan ID Buku yang dikembalikan: ")
            val idBook = readLine()!!
            circulationservice.givebackbook(idBook, idMember)
        }
        "3" -> {
            reportservice.showactiveborrow()
            reportservice.showtopbook()
            reportservice.showqueuereservation()
            print("\nEkspor laporan pinjaman ke CSV? (y/n): ")
            if (readLine()?.equals("y", ignoreCase = true) == true) {
                reportservice.exportreportborrowtocsv()
            }
        }
        "4" -> {
            print("Masukkan judul/penulis/kategori untuk dicari: ")
            val query = readLine()!!
            val result = catalogueservice.findBook(query)
            println("--- Hasil Pencarian ---")
            if (result.isEmpty()) println("Buku tidak ditemukan.")
            else result.forEach { println(it.info()) }
        }
        "5" -> reportservice.showqueuereservation()
        "6" -> {
            println("--- Daftar Semua Buku ---")
            bookRepo.findAll().forEach { println(it.info()) }
        }
        "7" -> {
            println("--- Daftar Semua Anggota ---")
            memberRepo.findAll().forEach { println("ID: ${it.id} | Nama: ${it.name} | Tier: ${it.tier}") }
        }
        "8" -> {
            println("Anda telah logout.")
            return null // Logout
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser // Tetap login
}

fun showMemberMenu(currentUser: UserAccount): UserAccount? {
    // --- PERBAIKAN DI SINI ---
    // Ganti: val memberId = currentUser.associatedMemberId
    // Menjadi:
    val memberId = currentUser.idMemberrelated

    if (memberId == null) {
        println("Error: Akun anggota ini tidak terhubung dengan data member.")
        return null // Logout paksa jika ada error konfigurasi
    }

    println("\n===== MENU ANGGOTA (ID: $memberId) =====")
    println("1. Cari Buku")
    println("2. Pinjam Buku atau Reservasi")
    println("3. Lihat Peminjaman Saya")
    println("4. Logout")
    print("Pilih menu Anggota: ")

    when (readLine()) {
        "1" -> {
            print("Masukkan judul/penulis/kategori untuk dicari: ")
            val query = readLine()!!
            val result = catalogueservice.findBook(query)
            println("--- Hasil Pencarian ---")
            if (result.isEmpty()) println("Buku tidak ditemukan.")
            else result.forEach { println(it.info()) }
        }
        "2" -> {
            print("Masukkan ID Buku yang ingin direservasi: ")
            val idBook = readLine()!!
            circulationservice.borrowingbook(memberId, idBook)
        }
        "3" -> { // (Saya perbaiki typo "3.0" Anda menjadi "3")
            println("--- Peminjaman Aktif Saya ---")
            val myBorrows = borrowRepo.findAll().filter { it.idMember == memberId && it.dateBack == null }
            if (myBorrows.isEmpty()) {
                println("Anda tidak memiliki pinjaman aktif.")
            } else {
                myBorrows.forEach {
                    val book = bookRepo.findById(it.idBook)
                    println("- Judul: ${book?.title ?: "N/A"} | ID Buku: ${it.idBook} | Jatuh Tempo: ${it.deadline}")
                }
            }
        }
        "4" -> {
            println("Anda telah logout.")
            return null // Logout
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser // Tetap login
}


// --- FUNGSI MAIN (VERSI UPGRADE) ---
fun main() {
    var activeUser: UserAccount? = null // Ganti 'loggedIn' dengan 'activeUser'

    setupearlydata() // Panggil setup data di awal

    while (true) { // Loop utama aplikasi
        if (activeUser == null) {
            // --- Sesi Login ---
            print("Username: ")
            val username = readLine()!!
            print("Password: ")
            val password = readLine()!!
            activeUser = authservice.login(username, password)
        } else {
            // --- Logika Menu Berdasarkan Role ---
            activeUser = when (activeUser.role) {
                Role.ADMIN -> showAdminMenu(activeUser)
                Role.LIBRARIAN -> showLibrarianMenu(activeUser)
                Role.MEMBER -> showMemberMenu(activeUser)
            }
        }
    }
}