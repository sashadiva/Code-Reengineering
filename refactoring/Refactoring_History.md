# Refactoring History

## 1. Refactoring God Class

### Ringkasan Perubahan

* Memisahkan logika persistence (simpan/muat data dari file) dari `ExpenseTracker` menjadi class baru `ExpenseStorage`.
* Menjadikan `ExpenseTracker` fokus pada user interaction dan business logic, sementara `ExpenseStorage` mengambil tanggung jawab `save/load` untuk `expenses` dan `categoryBudgets`.
* Mempertahankan behavior dan output program yang sama dengan pesan konsol dan mekanisme serialisasi yang tetap konsisten.

### Code Smell

* **God Class**

### Definisi

* Kelas yang melakukan terlalu banyak tanggung jawab sehingga menjadi besar, tidak modular, dan sulit dirawat.

### Alasan Implementasi Sebelumnya Termasuk Code Smell

* `ExpenseTracker` awalnya menggabungkan tiga concern utama:

  1. UI/interaction menu dan validasi input.
  2. Business logic pengelolaan expense summary, history, budget, dan konversi mata uang.
  3. Persistence file I/O untuk menyimpan dan memuat data expenses dan budgets.
* Banyaknya tanggung jawab berbeda dalam satu class menyebabkan pelanggaran terhadap Single Responsibility Principle (SRP).

### Perubahan yang Dilakukan

* Memindahkan seluruh logic persistence ke class baru `ExpenseStorage`.
* Mengubah `ExpenseTracker` agar menggunakan delegasi ke `ExpenseStorage`.

### Alasan Refactor Meningkatkan Kualitas

* **Maintainability:** Perubahan mekanisme penyimpanan cukup dilakukan di satu class.
* **Readability:** `ExpenseTracker` menjadi lebih fokus pada alur aplikasi.
* **Modularity:** Persistence dipisahkan dari business logic.
* **Testability:** Logic persistence dapat diuji secara terpisah.

### File yang Diubah

* `src/ExpenseTracker.java`

### File Baru yang Dibuat

* `src/ExpenseStorage.java`

### Method/Logic yang Dipindahkan

* `loadExpensesFromFile()` → `ExpenseStorage.loadExpenses()`
* `saveExpensesToFile()` → `ExpenseStorage.saveExpenses(List<Expense>)`
* `loadBudgetsFromFile()` → `ExpenseStorage.loadBudgets()`
* `saveBudgetsToFile()` → `ExpenseStorage.saveBudgets(Map<String, Double>)`

### Teknik Refactor yang Digunakan

* `Extract Class`
* `Move Method`
* `Replace Method with Delegate`

### Catatan Tambahan

* Refactor difokuskan hanya pada God Class.
