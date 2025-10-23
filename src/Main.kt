import model.*
import repository.*
import service.*

// --- INISIALISASI LAYER ---
val bookRepo: Repository<Book> = InMemoryRepo()
val memberRepo: Repository<Member> = InMemoryRepo()
val borrowRepo: Repository<Borrowing> = InMemoryRepo()
val userAccountRepo: Repository<UserAccount> = InMemoryRepo()

val catalogueservice = Catalogueservice(bookRepo)
val memberservice = Memberservice(memberRepo, userAccountRepo)
val reservationservice = Reservationservice()
val circulationservice = Circulationservice(bookRepo, memberRepo, borrowRepo, reservationservice)
val reportservice = Reportservice(borrowRepo, bookRepo, reservationservice)
val authservice = Authenticationservice(userAccountRepo)

fun setupearlydata() {
    catalogueservice.addPrintedBook("Laskar Pelangi", "Andrea Hirata", 2005, "Novel", 529, 5)
    catalogueservice.addPrintedBook("Bumi Manusia", "Pramoedya Ananta Toer", 1980, "Sejarah", 535, 0)
    catalogueservice.addPrintedBook("Filosofi Teras", "Henry Manampiring", 2018, "Self-Help", 346, 3)
    catalogueservice.addDigitalBook("Clean Code", "Robert C. Martin", 2008, "Pemrograman", 22.5, "PDF")
    catalogueservice.addDigitalBook("Atomic Habits", "James Clear", 2018, "Self-Help", 5.3, "EPUB")

    memberservice.addMember("Andi", Tier.REGULAR)
    memberservice.addMember("John", Tier.PREMIUM)
    memberservice.addMember("Freddy", Tier.STAFF)

    userAccountRepo.save(UserAccount("admin", "admin", "admin123", Role.ADMIN))
    userAccountRepo.save(UserAccount("pustakawan", "pustakawan", "perpus456", Role.LIBRARIAN))

    println("\n--- setup successful ---\n")
}

// --- MENU ADMIN (SIMPel) ---
fun showAdminMenu(currentUser: UserAccount): UserAccount? {
    println("\n===== MENU ADMIN =====")
    println("1. Tambah Anggota Baru")
    println("2. Tampilkan Laporan")
    println("3. Tampilkan Semua Buku (Test ID)")
    println("4. Tampilkan Semua Anggota (Test ID)")
    println("5. Logout")
    print("Pilih menu Admin: ")

    when (readLine()) {
        "1" -> {
            print("Nama Anggota Baru: "); val n = readLine()!!
            print("Tier (REGULAR, PREMIUM, STAFF): "); val t = Tier.valueOf(readLine()!!.uppercase())
            memberservice.addMember(n, t)
        }
        "2" -> {
            reportservice.showactiveborrow()
            reportservice.showtopbook()
            reportservice.showqueuereservation()
        }
        "3" -> {
            println("--- Daftar Semua Buku ---")
            bookRepo.findAll().forEach { println(it.info()) }
        }
        "4" -> {
            println("--- Daftar Semua Anggota ---")
            memberRepo.findAll().forEach { println("ID: ${it.id} | Nama: ${it.name} | Tier: ${it.tier}") }
        }
        "5" -> {
            println("Anda telah logout.")
            return null
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser
}

// --- MENU PUSTAKAWAN (TAMBAH OPSI TAMBAH BUKU) ---
fun showLibrarianMenu(currentUser: UserAccount): UserAccount? {
    println("\n===== MENU PUSTAKAWAN =====")
    println("1. Tambah Buku ")
    println("2. Pinjam Buku")
    println("3. Kembalikan Buku")
    println("4. Tampilkan Laporan")
    println("5. Cari Buku")
    println("6. Tampilkan Antrian Reservasi")
    println("7. Tampilkan Semua Buku (Test ID)")
    println("8. Tampilkan Semua Anggota (Test ID)")
    println("9. Logout")
    print("Pilih menu Pustakawan: ")

    when (readLine()) {
        "1" -> {
            // Pilih kategori buku dulu
            println("\nPilih Kategori Buku yang Ingin Ditambahkan:")
            println("1. Buku Cetak")
            println("2. Buku Digital")
            println("3. Buku Audio")
            print("Masukkan pilihan (1-3): ")
            val pilihanKategori = readLine()

            if (pilihanKategori == "1") {
                println("\n--- Tambah Buku Cetak ---")
                print("Judul: "); val title = readLine()!!
                print("Penulis: "); val author = readLine()!!
                print("Tahun: "); val year = readLine()!!.toInt()
                print("Kategori: "); val category = readLine()!!
                print("Jumlah Halaman: "); val pages = readLine()!!.toInt()
                print("Stok: "); val stock = readLine()!!.toInt()

                catalogueservice.addPrintedBook(title, author, year, category, pages, stock)
                println("✅ Buku cetak berhasil ditambahkan.")

            } else if (pilihanKategori == "2") {
                println("\n--- Tambah Buku Digital ---")
                print("Judul: "); val title = readLine()!!
                print("Penulis: "); val author = readLine()!!
                print("Tahun: "); val year = readLine()!!.toInt()
                print("Kategori: "); val category = readLine()!!
                print("Ukuran File (MB): "); val sizeFileMB = readLine()!!.toDouble()
                print("Format File (contoh: PDF, EPUB, MOBI): "); val format = readLine()!!

                catalogueservice.addDigitalBook(title, author, year, category, sizeFileMB, format)
                println("✅ Buku digital berhasil ditambahkan.")

            } else if (pilihanKategori == "3") {
                println("\n--- Tambah Buku Audio ---")
                print("Judul: "); val title = readLine()!!
                print("Narator: "); val narrator = readLine()!!
                print("Tahun: "); val year = readLine()!!.toInt()
                print("Kategori: "); val category = readLine()!!
                print("Durasi (menit): "); val duration = readLine()!!.toInt()

                catalogueservice.addAudioBook(title, narrator, year, category, duration, narrator)
                println("✅ Buku audio berhasil ditambahkan.")

            } else {
                println("⚠️ Pilihan kategori tidak valid. Kembali ke menu utama.")
            }
        }

        "2" -> {
            print("Masukkan ID Anggota: "); val idMember = readLine()!!
            print("Masukkan ID Buku: "); val idBook = readLine()!!
            circulationservice.borrowingbook(idMember, idBook)
        }
        "3" -> {
            print("Masukkan ID Anggota: "); val idMember = readLine()!!
            print("Masukkan ID Buku yang dikembalikan: "); val idBook = readLine()!!
            circulationservice.givebackbook(idBook, idMember)
        }
        "4" -> {
            reportservice.showactiveborrow()
            reportservice.showtopbook()
            reportservice.showqueuereservation()
            print("\nEkspor laporan pinjaman ke CSV? (y/n): ")
            if (readLine()?.equals("y", ignoreCase = true) == true) {
                reportservice.exportreportborrowtocsv()
            }
        }
        "5" -> {
            print("Masukkan judul/penulis/kategori untuk dicari: ")
            val query = readLine()!!
            val result = catalogueservice.findBook(query)
            println("--- Hasil Pencarian ---")
            if (result.isEmpty()) println("Buku tidak ditemukan.") else result.forEach { println(it.info()) }
        }
        "6" -> reportservice.showqueuereservation()
        "7" -> {
            println("--- Daftar Semua Buku ---")
            bookRepo.findAll().forEach { println(it.info()) }
        }
        "8" -> {
            println("--- Daftar Semua Anggota ---")
            memberRepo.findAll().forEach { println("ID: ${it.id} | Nama: ${it.name} | Tier: ${it.tier}") }
        }
        "9" -> {
            println("Anda telah logout.")
            return null
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser
}

// --- MENU ANGGOTA ---
fun showMemberMenu(currentUser: UserAccount): UserAccount? {
    val memberId = currentUser.idMemberrelated
    if (memberId == null) {
        println("Error: Akun anggota ini tidak terhubung dengan data member.")
        return null
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
            if (result.isEmpty()) println("Buku tidak ditemukan.") else result.forEach { println(it.info()) }
        }
        "2" -> {
            print("Masukkan ID Buku yang ingin direservasi: ")
            val idBook = readLine()!!
            circulationservice.borrowingbook(memberId, idBook)
        }
        "3" -> {
            println("--- Peminjaman Aktif Saya ---")
            val myBorrows = borrowRepo.findAll().filter { it.idMember == memberId && it.dateBack == null }
            if (myBorrows.isEmpty()) println("Anda tidak memiliki pinjaman aktif.")
            else myBorrows.forEach {
                val book = bookRepo.findById(it.idBook)
                println("- Judul: ${book?.title ?: "N/A"} | ID Buku: ${it.idBook} | Jatuh Tempo: ${it.deadline}")
            }
        }
        "4" -> {
            println("Anda telah logout.")
            return null
        }
        else -> println("Pilihan tidak valid.")
    }
    return currentUser
}

// --- FUNGSI MAIN ---
fun main() {
    var activeUser: UserAccount? = null
    setupearlydata()

    while (true) {
        if (activeUser == null) {
            print("Username: "); val username = readLine()!!
            print("Password: "); val password = readLine()!!
            activeUser = authservice.login(username, password)
        } else {
            activeUser = when (activeUser.role) {
                Role.ADMIN -> showAdminMenu(activeUser)
                Role.LIBRARIAN -> showLibrarianMenu(activeUser)
                Role.MEMBER -> showMemberMenu(activeUser)
            }
        }
    }
}
