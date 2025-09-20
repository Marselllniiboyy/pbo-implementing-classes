package infrastructure;

import java.math.BigDecimal;

import application.dto.*;
import application.service.*;
import domain.entity.AccountCardEntity;
import domain.entity.AccountEntity;
import domain.entity.CardTypeEntity;
import domain.entity.CustomerEntity;
import domain.exception.*;
import domain.value.AccountType;
import infrastructure.container.AppContainer;
import infrastructure.container.DefaultAppContainer;

public final class Application {
    // Layanan-layanan
    private AccountService accountService;
    private CardTypeService cardTypeService;
    private CustomerService customerService;
    private TransactionService transactionService;
    private LogService log;

    public void setup() {
        AppContainer container = new DefaultAppContainer();
        setup(container);
    }

    public void setup(AppContainer container) {
        // Inisialisasi layanan log terlebih dahulu
        log = container.getLogService();

        // Memuat semua layanan
        log.system("Loading module <AccountService>");
        accountService = container.getAccountService();

        log.system("Loading module <CardTypeService>");
        cardTypeService = container.getCardTypeService();

        log.system("Loading module <CustomerService>");
        customerService = container.getCustomerService();

        log.system("Loading module <TransactionService>");
        transactionService = container.getTransactionService();

        log.system("Aplikasi berhasil disiapkan");
        log.system("");
        log.system("");
    }

    public void execute() {
        // Menampilkan header aplikasi
        log.info("====================== Aplikasi Perbankan ========================");
        log.info(
                """
                        Aplikasi ini dibuat untuk memenuhi tugas pada matkul PBO
                        Tim kami terdiri dari:
                          1. Gede Dhanu Purnayasa (2415091092)
                          2. Made Marsel Biliana Wijaya (2415091090)
                        """
        );
        log.system("");
        log.system("");

        // ==================== CERITA KEHIDUPAN NYATA ====================
        log.info("🎬 KISAH TIGA SAHABAT - PERJALANAN KEUANGAN");
        log.info("=============================================");
        log.info("");
        log.info("🌴 Di sebuah desa kecil di Bali, tiga sahabat tumbuh bersama");
        log.info("dan memulai perjalanan keuangan mereka yang penuh tantangan.");
        log.info("");
        log.info("👨‍💻 DHANU - Si jenius IT yang baru lulus");
        log.info("🎨 MARSEL - Si seniman kreatif yang ambisius");
        log.info("🎓 SINDHU - Si mahasiswa yang penuh semangat");
        log.info("");
        log.info("💥 KONFLIK: Dhanu mendapat tawaran kerja di Jakarta");
        log.info("dengan gaji tinggi, tapi harus meninggalkan teman-temannya.");
        log.info("");
        log.info("🎭 AKT 1: PENGENALAN KARAKTER");
        log.info("=============================");

        // ==================== PENGATURAN JENIS KARTU ====================
        log.info("🏦 BANK BALI MANDIRI - SOLUSI KEUANGAN");
        log.info("=====================================");
        log.info("");
        log.info("Bank menyiapkan berbagai jenis kartu untuk memenuhi kebutuhan");
        log.info("keuangan yang berbeda dari setiap nasabah.");
        log.info("");
        
        CardTypeEntity cardDiamond = cardTypeService.createCardType(
                new CreateCardTypeDto(
                        "Diamond",
                        "Kartu premium untuk profesional dengan gaji tinggi",
                        new BigDecimal("50000"),        // hargaBulanan
                        new BigDecimal("50000000"),     // batasTransferHarian
                        new BigDecimal("100000000"),    // batasPenarikanHarian
                        new BigDecimal("40000000"),     // batasDepositHarian
                        new BigDecimal("200000")        // saldoMinimum
                )
        );
        log.info("💎 KARTU DIAMOND - Rp 50k/bulan, limit 50M/hari");

        CardTypeEntity cardGold = cardTypeService.createCardType(
                new CreateCardTypeDto(
                        "Gold",
                        "Kartu untuk freelancer dan pekerja menengah",
                        new BigDecimal("50000"),        // hargaBulanan
                        new BigDecimal("20000000"),     // batasTransferHarian
                        new BigDecimal("10000000"),     // batasPenarikanHarian
                        new BigDecimal("15000000"),     // batasDepositHarian
                        new BigDecimal("100000")        // saldoMinimum
                )
        );
        log.info("🥇 KARTU GOLD - Rp 50k/bulan, limit 20M/hari");

        CardTypeEntity cardSilver = cardTypeService.createCardType(
                new CreateCardTypeDto(
                        "Silver",
                        "Kartu untuk mahasiswa dan pemula",
                        new BigDecimal("15000"),        // hargaBulanan
                        new BigDecimal("5000000"),      // batasTransferHarian
                        new BigDecimal("2000000"),      // batasPenarikanHarian
                        new BigDecimal("3000000"),      // batasDepositHarian
                        new BigDecimal("50000")         // saldoMinimum
                )
        );
        log.info("🥈 KARTU SILVER - Rp 15k/bulan, limit 5M/hari");
        log.info("");

        // ==================== PENGATURAN PELANGGAN ====================
        log.info("🎭 AKT 2: KEPUTUSAN BESAR");
        log.info("=========================");
        log.info("");
        log.info("Dhanu mendapat tawaran kerja di Jakarta dengan gaji 15 juta,");
        log.info("tapi harus meninggalkan teman-temannya di Bali.");
        log.info("Setelah diskusi panjang, mereka memutuskan untuk tetap bersama");
        log.info("dan membuka rekening di bank yang sama untuk saling mendukung.");
        log.info("");
        log.info("👨‍💻 DHANU - Si Jenius IT yang Dilema");
        log.info("   💰 Gaji: Rp 8 juta (Bali) vs Rp 15 juta (Jakarta)");
        log.info("   🎯 Keputusan: Tetap di Bali, buka rekening bersama teman");
        CustomerEntity customerDhanu = customerService.createCustomer(
                new CreateCustomerDto(
                        "Gede Dhanu Purnayasa",
                        "dhanu@gmail.com",
                        "628293984854",
                        "Singaraja, Bali",
                        "2003-04-30"
                )
        );
        log.info("✅ Dhanu mendaftar sebagai nasabah");

        log.info("");
        log.info("🎨 MARSEL - Si Seniman yang Setia");
        log.info("   💰 Pendapatan: Rp 4-6 juta/bulan (freelancer)");
        log.info("   🎯 Mimpi: Studio desain sendiri");
        CustomerEntity customerMarsel = customerService.createCustomer(
                new CreateCustomerDto(
                        "Made Marsel Biliana Wijaya",
                        "marsel@gmail.com",
                        "6384939485",
                        "Bangli, Bali",
                        "2003-03-12"
                )
        );
        log.info("✅ Marsel mendaftar sebagai nasabah");

        log.info("");
        log.info("🎓 SINDHU - Si Mahasiswa yang Optimis");
        log.info("   💰 Pendapatan: Rp 0 (bergantung orang tua)");
        log.info("   🎯 Mimpi: Lulus cum laude, dapat kerja impian");
        CustomerEntity customerSindhu = customerService.createCustomer(
                new CreateCustomerDto(
                        "Kadek Sindhu Arta",
                        "sindhu@gmail.com",
                        "6287861637978",
                        "Gianyar, Bali",
                        "2004-05-20"
                )
        );
        log.info("✅ Sindhu mendaftar sebagai nasabah");
        log.info("");

        // ==================== PENGATURAN REKENING BANK ====================
        log.info("🏦 AKT 3: PEMBUKAAN REKENING");
        log.info("=============================");
        log.info("");
        log.info("Ketiga sahabat membuka rekening tabungan dengan saldo awal yang berbeda.");
        log.info("Ini adalah langkah pertama menuju kemandirian finansial mereka.");
        log.info("");
        log.info("🎓 SINDHU - Rekening Pertama");
        log.info("   💰 Saldo awal: Rp 0 (masih menunggu kiriman orang tua)");
        AccountEntity accountSindhu = accountService.createAccount(
                new CreateAccountDto(
                        customerSindhu,
                        new BigDecimal("0"),
                        AccountType.SAVINGS
                )
        );
        log.info("✅ Rekening Sindhu berhasil dibuat");

        log.info("");
        log.info("🎨 MARSEL - Rekening dengan Uang Hasil Kerja");
        log.info("   💰 Saldo awal: Rp 500,000 (hasil proyek desain)");
        AccountEntity accountMarsel = accountService.createAccount(
                new CreateAccountDto(
                        customerMarsel,
                        new BigDecimal("500000"),
                        AccountType.SAVINGS
                )
        );
        log.info("✅ Rekening Marsel berhasil dibuat");

        log.info("");
        log.info("👨‍💻 DHANU - Rekening dengan Gaji Pertama");
        log.info("   💰 Saldo awal: Rp 1,000,000 (gaji pertama + bonus)");
        AccountEntity accountDhanu = accountService.createAccount(
                new CreateAccountDto(
                        customerDhanu,
                        new BigDecimal("1000000"),
                        AccountType.SAVINGS
                )
        );
        log.info("✅ Rekening Dhanu berhasil dibuat");
        log.info("");

        // ==================== PENGATURAN KARTU PELANGGAN ====================
        log.info("💳 AKT 4: PEMILIHAN KARTU ATM");
        log.info("=============================");
        log.info("");
        log.info("Ketiga sahabat memilih kartu ATM sesuai dengan kebutuhan dan kemampuan mereka.");
        log.info("");
        log.info("💎 DHANU - Kartu Diamond (Premium)");
        log.info("   💰 Biaya bulanan: Rp 50,000");
        log.info("   🚀 Limit transfer: Rp 50 juta/hari");
        log.info("   🔒 PIN: 129394");
        AccountCardEntity cardDhanu = accountService.assignCard(
                new AssignCardDto(
                        accountDhanu,
                        cardDiamond,
                        129394
                )
        );
        log.info("✅ Kartu Diamond berhasil diterbitkan untuk Dhanu");

        log.info("");
        log.info("🥇 MARSEL - Kartu Gold (Creative Professional)");
        log.info("   💰 Biaya bulanan: Rp 50,000");
        log.info("   🎨 Limit transfer: Rp 20 juta/hari");
        log.info("   🔒 PIN: 120504");
        AccountCardEntity cardMarsel = accountService.assignCard(
                new AssignCardDto(
                        accountMarsel,
                        cardGold,
                        120504
                )
        );
        log.info("✅ Kartu Gold berhasil diterbitkan untuk Marsel");

        log.info("");
        log.info("🥈 SINDHU - Kartu Silver (Student Friendly)");
        log.info("   💰 Biaya bulanan: Rp 15,000");
        log.info("   🎓 Limit transfer: Rp 5 juta/hari");
        log.info("   🔒 PIN: 120504");
        AccountCardEntity cardSindhu = accountService.assignCard(
                new AssignCardDto(
                        accountSindhu,
                        cardSilver,
                        120504
                )
        );
        log.info("✅ Kartu Silver berhasil diterbitkan untuk Sindhu");
        log.info("");

        // ==================== OPERASI PELANGGAN ====================
        log.info("📝 AKT 5: UPDATE PROFIL");
        log.info("=======================");
        log.info("");
        log.info("Dhanu baru saja lulus dengan predikat cum laude dan mendapat gelar S.Kom.");
        log.info("Dia memutuskan untuk update data profil di bank dengan informasi terbaru.");
        log.info("");
        log.info("🔄 DHANU - Update Profil dengan Prestasi Baru");
        log.info("   🎓 Nama baru: Gede Dhanu Purnayasa, S. Kom");
        log.info("   📱 Telepon: 087861234567 (nomor baru)");
        log.info("   🏠 Alamat: Banjar Pule, Desa Padangsambian, Negaro");
        try {
            customerDhanu = customerService.updateCustomer(
                    customerDhanu.id(),
                    new UpdateCustomerDto(
                            "Gede Dhanu Purnayasa, S. Kom",
                            "dhanu@gmail.com",
                            "087861234567",
                            "Banjar Pule, Desa Padangsambian, Negaro",
                            "12-12-2012"
                    )
            );
            log.info("✅ Update profil Dhanu berhasil!");
            log.info("   📧 Email: " + customerDhanu.email());
            log.info("   👤 Nama: " + customerDhanu.name());
            log.info("   📱 Telepon: " + customerDhanu.phoneNumber());
        } catch (CustomerException.CustomerNotFound e) {
            log.info("❌ Gagal update profil: " + e.getMessage());
        }
        log.info("");

        // ==================== OPERASI KARTU ====================
        log.info("🔐 AKT 6: UPDATE PIN");
        log.info("===================");
        log.info("");
        log.info("Dhanu menyadari bahwa PIN kartunya terlalu mudah ditebak.");
        log.info("Dia memutuskan untuk mengubah PIN dari 129394 ke 120504 untuk keamanan yang lebih baik.");
        log.info("");
        log.info("🔒 DHANU - Update PIN untuk Keamanan");
        log.info("   🔐 PIN lama: 129394");
        log.info("   🔐 PIN baru: 120504");
        try {
            accountService.updatePin(accountDhanu, 129394, 120504);
            log.info("✅ PIN berhasil diubah!");
            log.info("   🔐 PIN baru: 120504");
            log.info("   🛡️ Kartu sekarang lebih aman");
        } catch (CardException.InvalidPin e) {
            log.error("❌ Penggantian PIN gagal: " + e.getUserMessage());
            log.error("🔍 Detail teknis: " + e.getTechnicalMessage());
            log.error("📋 Context: " + e.getContext());
        } catch (CardException.CardNotFound e) {
            log.error("❌ Kartu tidak ditemukan: " + e.getUserMessage());
            log.error("🔍 Detail teknis: " + e.getTechnicalMessage());
        }
        log.info("");

        // ==================== OPERASI TRANSAKSI ====================
        log.info("💰 AKT 7: PERTOLONGAN SAHABAT");
        log.info("=============================");
        log.info("");
        log.info("Marsel mendapat proyek desain logo untuk startup unicorn,");
        log.info("tapi butuh tablet desain premium seharga 250rb.");
        log.info("Dhanu memutuskan untuk membantu dengan transfer via kartu ATM.");
        log.info("");
        log.info("💸 TRANSFER 1: Dhanu → Marsel (Rp 250,000)");
        log.info("   💰 Saldo Dhanu saat ini: " + accountDhanu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 250,000");
        log.info("   💳 Via: Kartu ATM (Diamond)");
        log.info("   🔒 PIN: 120504");
        try {
            transactionService.sendMoneyUsingCard(
                    accountDhanu.accountNumber(),
                    accountMarsel.accountNumber(),
                    new BigDecimal("250000"),
                    120504
            );
            accountDhanu = accountService.reloadAccount(accountDhanu);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Transfer berhasil!");
            log.info("   💸 Jumlah: Rp 250,000");
            log.info("   💰 Biaya transfer: Rp 2,500 (1%)");
            log.info("   💰 Total yang terpotong: Rp 252,500");
            log.info("   💰 Sisa saldo Dhanu: " + accountDhanu.getFormattedBalance());
            log.info("   💰 Saldo Marsel sekarang: " + accountMarsel.getFormattedBalance());
        } catch (AccountException.InsufficientBalance e) {
            log.error("❌ Transfer gagal - Saldo tidak mencukupi: " + e.getUserMessage());
            log.error("💰 Saldo saat ini: " + e.getContext().get("currentBalance"));
            log.error("💸 Jumlah yang dibutuhkan: " + e.getContext().get("requiredAmount"));
        } catch (TransactionException.DailyLimitExceeded e) {
            log.error("❌ Transfer gagal - Batas harian terlampaui: " + e.getUserMessage());
            log.error("📊 Total harian saat ini: " + e.getContext().get("currentTotal"));
            log.error("🚫 Batas maksimal: " + e.getContext().get("limit"));
        } catch (TransactionException.SameAccountTransfer e) {
            log.error("❌ Transfer gagal: " + e.getUserMessage());
        } catch (AccountException.AccountNotFound e) {
            log.error("❌ Transfer gagal - Rekening tidak ditemukan: " + e.getUserMessage());
        } catch (CardException.CardNotFound e) {
            log.error("❌ Transfer gagal - Kartu tidak ditemukan: " + e.getUserMessage());
        } catch (CardTypeException.CardTypeNotFound e) {
            log.error("❌ Transfer gagal - Jenis kartu tidak ditemukan: " + e.getUserMessage());
        }
        log.info("");

        log.info("Beberapa hari kemudian, tablet yang dibeli Marsel rusak.");
        log.info("Dia membutuhkan tablet yang lebih bagus seharga 100rb.");
        log.info("Dhanu memutuskan untuk membantu lagi dengan transfer kedua.");
        log.info("");
        log.info("💸 TRANSFER 2: Dhanu → Marsel (Rp 100,000)");
        log.info("   💰 Saldo Dhanu saat ini: " + accountDhanu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 100,000");
        log.info("   💳 Via: Kartu ATM (Diamond)");
        log.info("   🔒 PIN: 120504");
        try {
            transactionService.sendMoneyUsingCard(
                    accountDhanu.accountNumber(),
                    accountMarsel.accountNumber(),
                    new BigDecimal("100000"),
                    120504
            );
            accountDhanu = accountService.reloadAccount(accountDhanu);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Transfer berhasil!");
            log.info("   💸 Jumlah: Rp 100,000");
            log.info("   💰 Biaya transfer: Rp 1,000 (1%)");
            log.info("   💰 Total yang terpotong: Rp 101,000");
            log.info("   💰 Sisa saldo Dhanu: " + accountDhanu.getFormattedBalance());
            log.info("   💰 Saldo Marsel sekarang: " + accountMarsel.getFormattedBalance());
        } catch (Exception e) {
            log.error("❌ Transfer kedua gagal: " + e.getMessage());
        }
        log.info("");

        log.info("Sindhu mendapat kiriman dari orang tua 100rb.");
        log.info("Dia memutuskan untuk membantu Dhanu dengan transfer via teller.");
        log.info("");
        log.info("💸 TRANSFER 3: Sindhu → Dhanu (Rp 100,000)");
        log.info("   💰 Saldo Sindhu saat ini: " + accountSindhu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 100,000");
        log.info("   🏦 Via: Teller Bank (tanpa biaya)");
        try {
            transactionService.sendMoneyViaTeller(
                    accountSindhu.accountNumber(),
                    accountDhanu.accountNumber(),
                    new BigDecimal("100000")
            );
            accountSindhu = accountService.reloadAccount(accountSindhu);
            accountDhanu = accountService.reloadAccount(accountDhanu);
            log.info("✅ Transfer via teller berhasil!");
            log.info("   💸 Jumlah: Rp 100,000");
            log.info("   💰 Biaya transfer: Rp 0 (gratis via teller)");
            log.info("   💰 Saldo Sindhu sekarang: " + accountSindhu.getFormattedBalance());
            log.info("   💰 Saldo Dhanu sekarang: " + accountDhanu.getFormattedBalance());
        } catch (AccountException.InsufficientBalance e) {
            log.error("❌ Transfer via teller gagal - Saldo tidak mencukupi: " + e.getUserMessage());
        } catch (Exception e) {
            log.error("❌ Transfer via teller gagal: " + e.getMessage());
        }
        log.info("");

        // ==================== SITUASI OUT OF LIMIT ====================
        log.info("🚫 AKT 7.6: MOMEN MENEGANGKAN - BATAS HARIAN TERLAMPAUI!");
        log.info("=====================================================");
        log.info("");
        log.info("Dhanu mendapat proyek besar dan ingin transfer gaji ke rekening investasi.");
        log.info("Dia tidak menyadari bahwa sudah mencapai batas transfer harian.");
        log.info("");
        log.info("💸 DHANU - Percobaan Transfer Melebihi Batas Harian");
        log.info("   💰 Saldo Dhanu saat ini: " + accountDhanu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 30,000,000 (30 juta)");
        log.info("   💳 Via: Kartu ATM (Diamond)");
        log.info("   🔒 PIN: 120504");
        log.info("   🎯 Tujuan: Dhanu ingin investasi ke reksadana");
        log.info("   ⚠️ Batas harian Kartu Diamond: Rp 50,000,000");
        log.info("   📊 Total transfer hari ini: Rp 25,000,000 (dari transfer sebelumnya)");
        log.info("   🚫 Sisa batas: Rp 25,000,000 (tidak cukup untuk 30 juta)");
        try {
            transactionService.sendMoneyUsingCard(
                    accountDhanu.accountNumber(),
                    accountMarsel.accountNumber(), // Transfer ke Marsel sebagai contoh
                    new BigDecimal("30000000"), // 30 juta
                    120504
            );
            log.info("✅ Transfer berhasil!");
        } catch (TransactionException.DailyLimitExceeded e) {
            log.error("❌ Transfer gagal - Batas harian terlampaui!");
            log.error("   🔍 Pesan: " + e.getUserMessage());
            log.error("   📊 Total harian saat ini: " + e.getContext().get("currentTotal"));
            log.error("   🚫 Batas maksimal: " + e.getContext().get("limit"));
            log.error("   😰 Dhanu menyadari bahwa dia sudah mencapai batas transfer harian");
            log.error("   💭 Dhanu berpikir bahwa dia harus menunggu besok atau mengurangi jumlah transfer");
            log.error("   💡 Dhanu memutuskan untuk mencoba transfer 20 juta saja dulu");
        } catch (Exception e) {
            log.error("❌ Transfer gagal: " + e.getMessage());
        }
        log.info("");
        log.info("Dhanu mencoba lagi dengan jumlah yang lebih kecil.");
        log.info("");
        log.info("💸 DHANU - Percobaan Transfer dengan Jumlah yang Disesuaikan");
        log.info("   💰 Saldo Dhanu saat ini: " + accountDhanu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 20,000,000 (20 juta)");
        log.info("   💳 Via: Kartu ATM (Diamond)");
        log.info("   🔒 PIN: 120504");
        log.info("   🎯 Tujuan: Dhanu memutuskan untuk mengurangi jumlah transfer menjadi 20 juta");
        try {
            transactionService.sendMoneyUsingCard(
                    accountDhanu.accountNumber(),
                    accountMarsel.accountNumber(),
                    new BigDecimal("20000000"), // 20 juta
                    120504
            );
            accountDhanu = accountService.reloadAccount(accountDhanu);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Transfer berhasil!");
            log.info("   💸 Jumlah: Rp 20,000,000");
            log.info("   💰 Biaya transfer: Rp 200,000 (1%)");
            log.info("   💰 Total yang terpotong: Rp 20,200,000");
            log.info("   💰 Saldo Dhanu sekarang: " + accountDhanu.getFormattedBalance());
            log.info("   💰 Saldo Marsel sekarang: " + accountMarsel.getFormattedBalance());
            log.info("   😊 Dhanu senang karena transfer berhasil dan dia belajar untuk memperhatikan batas harian");
        } catch (Exception e) {
            log.error("❌ Transfer kedua gagal: " + e.getMessage());
        }
        log.info("");

        // ==================== SITUASI PIN INVALID ====================
        log.info("🔐 AKT 7.5: MOMEN MENEGANGKAN - PIN SALAH!");
        log.info("=============================================");
        log.info("");
        log.info("Sindhu mencoba melakukan transfer via kartu ATM untuk pertama kalinya.");
        log.info("Dia lupa PIN kartunya dan mencoba menebak-nebak.");
        log.info("");
        log.info("💳 SINDHU - Percobaan Transfer dengan PIN Salah");
        log.info("   💰 Saldo Sindhu saat ini: " + accountSindhu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 50,000");
        log.info("   💳 Via: Kartu ATM (Silver)");
        log.info("   🔒 PIN yang dicoba: 123456 (PIN salah!)");
        log.info("   😰 Sindhu mengakui bahwa dia lupa PIN-nya dan mencoba menebak");
        try {
            transactionService.sendMoneyUsingCard(
                    accountSindhu.accountNumber(),
                    accountDhanu.accountNumber(),
                    new BigDecimal("50000"),
                    123456  // PIN salah
            );
            log.info("✅ Transfer berhasil!");
        } catch (CardException.InvalidPin e) {
            log.error("❌ Transfer gagal - PIN salah!");
            log.error("   🔍 Pesan: " + e.getUserMessage());
            log.error("   📋 Context: " + e.getContext());
            log.error("   😰 Sindhu panik karena PIN-nya salah");
            log.error("   💭 Sindhu menyadari bahwa dia harus mengingat PIN yang benar");
        } catch (Exception e) {
            log.error("❌ Transfer gagal: " + e.getMessage());
        }
        log.info("");
        log.info("Sindhu mencoba lagi dengan PIN yang benar setelah mengingatnya.");
        log.info("");
        log.info("💳 SINDHU - Percobaan Transfer dengan PIN Benar");
        log.info("   💰 Saldo Sindhu saat ini: " + accountSindhu.getFormattedBalance());
        log.info("   💸 Jumlah transfer: Rp 50,000");
        log.info("   💳 Via: Kartu ATM (Silver)");
        log.info("   🔒 PIN yang dicoba: 120504 (PIN benar)");
        log.info("   😌 Sindhu merasa lega karena sekarang dia ingat PIN-nya");
        try {
            transactionService.sendMoneyUsingCard(
                    accountSindhu.accountNumber(),
                    accountDhanu.accountNumber(),
                    new BigDecimal("50000"),
                    120504  // PIN benar
            );
            accountSindhu = accountService.reloadAccount(accountSindhu);
            accountDhanu = accountService.reloadAccount(accountDhanu);
            log.info("✅ Transfer berhasil!");
            log.info("   💸 Jumlah: Rp 50,000");
            log.info("   💰 Biaya transfer: Rp 500 (1%)");
            log.info("   💰 Total yang terpotong: Rp 50,500");
            log.info("   💰 Saldo Sindhu sekarang: " + accountSindhu.getFormattedBalance());
            log.info("   💰 Saldo Dhanu sekarang: " + accountDhanu.getFormattedBalance());
            log.info("   😊 Sindhu senang karena transfer berhasil");
        } catch (Exception e) {
            log.error("❌ Transfer kedua gagal: " + e.getMessage());
        }
        log.info("");

        // ==================== OPERASI PENARIKAN ====================
        log.info("💸 AKT 8: PENARIKAN UANG TUNAI");
        log.info("=============================");
        log.info("");
        log.info("Marsel mendapat proyek desain mendesak dan butuh software premium.");
        log.info("Dia memutuskan untuk menarik uang tunai dari ATM untuk membeli software.");
        log.info("");
        log.info("🏧 MARSEL - Penarikan Uang Tunai");
        log.info("   💰 Saldo Marsel saat ini: " + accountMarsel.getFormattedBalance());
        log.info("   💸 Jumlah penarikan: Rp 100,000");
        log.info("   🏧 Via: ATM Bank");
        log.info("   🔒 PIN: 120504");
        try {
            transactionService.withdrawMoneyUsingCard(
                    accountMarsel.accountNumber(),
                    new BigDecimal("100000"),
                    120504
            );
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Penarikan via kartu berhasil!");
            log.info("   💸 Jumlah: Rp 100,000");
            log.info("   💰 Sisa saldo Marsel: " + accountMarsel.getFormattedBalance());
        } catch (TransactionException.InvalidTransactionAmount e) {
            log.info("❌ Penarikan tidak valid: " + e.getMessage());
        } catch (AccountException.AccountNotFound e) {
            log.info("❌ " + e.getMessage());
        } catch (CardException.CardNotFound e) {
            log.info("❌ Penarikan tidak valid: " + e.getMessage());
        } catch (CardException.InvalidPin e) {
            log.info("❌ Pin Invalid: " + e.getMessage());
        } catch (CardTypeException.CardTypeNotFound e) {
            log.info("❌ " + e.getMessage());
        } catch (AccountException.InsufficientBalance e) {
            log.info("❌ " + e.getMessage());
        } catch (TransactionException.DailyLimitExceeded e) {
            log.info("❌ " + e.getMessage());
        }
        log.info("");

        // ==================== OPERASI BIAYA BULANAN ====================
        log.info("📅 AKT 9: BIAYA BULANAN KARTU");
        log.info("=============================");
        log.info("");
        log.info("Akhir bulan tiba dan bank mulai memotong biaya bulanan kartu.");
        log.info("Dhanu dikenakan biaya bulanan Rp 50,000 untuk Kartu Diamond.");
        log.info("");
        log.info("💳 BANK - Memproses Biaya Bulanan Kartu Diamond");
        log.info("   💰 Saldo Dhanu sebelum potongan: " + accountDhanu.getFormattedBalance());
        log.info("   💸 Biaya bulanan Kartu Diamond: Rp 50,000");
        try {
            transactionService.applyCardMonthlyCharge(accountDhanu.accountNumber());
            accountDhanu = accountService.reloadAccount(accountDhanu);
            log.info("✅ Biaya bulanan kartu berhasil diterapkan!");
            log.info("   💰 Saldo Dhanu setelah potongan: " + accountDhanu.getFormattedBalance());
        } catch (AccountException.InsufficientBalance e) {
            log.error("❌ Biaya bulanan gagal - Saldo tidak mencukupi: " + e.getUserMessage());
            log.error("💰 Saldo saat ini: " + e.getContext().get("currentBalance"));
            log.error("💸 Biaya yang dibutuhkan: " + e.getContext().get("requiredAmount"));
        } catch (AccountException.AccountNotFound e) {
            log.error("❌ Biaya bulanan gagal - Rekening tidak ditemukan: " + e.getUserMessage());
        } catch (CardException.CardNotFound e) {
            log.error("❌ Biaya bulanan gagal - Kartu tidak ditemukan: " + e.getUserMessage());
        } catch (CardTypeException.CardTypeNotFound e) {
            log.error("❌ Biaya bulanan gagal - Jenis kartu tidak ditemukan: " + e.getUserMessage());
        }

        // ==================== TAMPILAN SALDO REKENING ====================
        log.info("📊 AKT 9: LAPORAN KEUANGAN PERTENGAHAN BULAN");
        log.info("=============================================");
        log.info("");
        
        log.info("📱 Ketiga sahabat mengadakan meeting virtual untuk review keuangan");
        log.info("   💭 Dhanu mengusulkan untuk melihat kondisi keuangan masing-masing");
        log.info("   💭 Marsel setuju agar mereka tahu siapa yang perlu bantuan");
        log.info("   💭 Sindhu juga ingin tahu progress keuangan teman-temannya");
        log.info("");
        
        // Reload data
        accountDhanu = accountService.reloadAccount(accountDhanu);
        accountMarsel = accountService.reloadAccount(accountMarsel);
        accountSindhu = accountService.reloadAccount(accountSindhu);

        // Tampilkan saldo
        log.info("💰 LAPORAN SALDO SAAT INI:");
        log.info("   👨‍💻 Dhanu (Kartu Diamond): " + accountDhanu.getFormattedBalance());
        log.info("   🎨 Marsel (Kartu Gold): " + accountMarsel.getFormattedBalance());
        log.info("   🎓 Sindhu (Kartu Silver): " + accountSindhu.getFormattedBalance());
        log.info("");
        log.info("📈 ANALISIS KEUANGAN:");
        log.info("   💎 Dhanu: Masih punya saldo cukup untuk kebutuhan");
        log.info("   🥇 Marsel: Saldo menipis, butuh proyek baru");
        log.info("   🥈 Sindhu: Saldo kosong, masih bergantung orang tua");
        log.info("");

        // ==================== OPERASI DEPOSIT ====================
        log.info("💰 AKT 10: DEPOSIT UANG");
        log.info("=======================");
        log.info("");
        log.info("Dhanu mendapat bonus dari kantor dan melakukan deposit.");
        log.info("");
        try {
            transactionService.depositMoneyUsingCard(accountDhanu.accountNumber(), new BigDecimal("100000"), 120504);
            accountDhanu = accountService.reloadAccount(accountDhanu);
            log.info("✅ Deposit via kartu berhasil: Rp 100,000");
            log.info("   💰 Saldo Dhanu sekarang: " + accountDhanu.getFormattedBalance());
        } catch (TransactionException.DailyLimitExceeded e) {
            log.error("❌ Deposit gagal - Batas harian terlampaui: " + e.getUserMessage());
        } catch (Exception e) {
            log.error("❌ Deposit pertama gagal: " + e.getMessage());
        }

        log.info("");
        log.info("Marsel mendapat pembayaran proyek dan melakukan deposit.");
        log.info("");
        try {
            transactionService.depositMoneyUsingCard(accountMarsel.accountNumber(), new BigDecimal("150000"), 120504);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Deposit via kartu berhasil: Rp 150,000");
            log.info("   💰 Saldo Marsel sekarang: " + accountMarsel.getFormattedBalance());
        } catch (Exception e) {
            log.error("❌ Deposit kedua gagal: " + e.getMessage());
        }

        log.info("");
        log.info("Sindhu mendapat kiriman dari orang tua dan melakukan deposit.");
        log.info("");
        try {
            transactionService.depositMoneyUsingCard(accountSindhu.accountNumber(), new BigDecimal("150000"), 120504);
            accountSindhu = accountService.reloadAccount(accountSindhu);
            log.info("✅ Deposit via kartu berhasil: Rp 150,000");
            log.info("   💰 Saldo Sindhu sekarang: " + accountSindhu.getFormattedBalance());
        } catch (Exception e) {
            log.error("❌ Deposit ketiga gagal: " + e.getMessage());
        }
        log.info("");

        // ==================== OPERASI PENARIKAN AKHIR ====================
        log.info("💸 AKT 11: PENARIKAN AKHIR");
        log.info("==========================");
        log.info("");
        log.info("Marsel mendapat proyek desain lagi dan butuh uang tunai 25rb.");
        log.info("Dia memutuskan untuk menarik uang dari ATM.");
        log.info("");
        try {
            transactionService.withdrawMoneyUsingCard(accountMarsel.accountNumber(), new BigDecimal("25000"), 120504);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            log.info("✅ Withdraw via kartu berhasil: Rp 25,000");
            log.info("   💰 Sisa saldo Marsel: " + accountMarsel.getFormattedBalance());
        } catch (AccountException.InsufficientBalance e) {
            log.error("❌ Withdraw gagal - Saldo tidak mencukupi: " + e.getUserMessage());
            log.error("💰 Saldo saat ini: " + e.getContext().get("currentBalance"));
            log.error("💸 Jumlah yang dibutuhkan: " + e.getContext().get("requiredAmount"));
        } catch (TransactionException.DailyLimitExceeded e) {
            log.error("❌ Withdraw gagal - Batas harian terlampaui: " + e.getUserMessage());
        } catch (Exception e) {
            log.error("❌ Withdraw gagal: " + e.getMessage());
        }
        log.info("");

        // ==================== TAMPILAN SALDO AKHIR ====================
        log.info("🎬 AKT 12: EPILOG");
        log.info("=================");
        log.info("");
        log.info("Ketiga sahabat mengadakan meeting akhir bulan untuk review keuangan.");
        log.info("");
        try {
            accountDhanu = accountService.reloadAccount(accountDhanu);
            accountMarsel = accountService.reloadAccount(accountMarsel);
            accountSindhu = accountService.reloadAccount(accountSindhu);

            log.info("💰 LAPORAN SALDO AKHIR BULAN:");
            log.info("   👨‍💻 Dhanu (Kartu Diamond): " + accountDhanu.getFormattedBalance());
            log.info("   🎨 Marsel (Kartu Gold): " + accountMarsel.getFormattedBalance());
            log.info("   🎓 Sindhu (Kartu Silver): " + accountSindhu.getFormattedBalance());
            log.info("");
            
            log.info("🏦 Sistem perbankan ini berjalan dengan baik!");
            log.info("Semua transaksi berhasil diproses dengan aman dan efisien.");
            
        } catch (AccountException.AccountNotFound e) {
            log.error("❌ Gagal reload akun: " + e.getUserMessage());
        }
    }
}