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

---

## 2. Refactoring Long Method

### Ringkasan Perubahan

* Memperpendek method `convertCurrency()` di `ExpenseTracker`.
* Menambahkan helper method:

  * `promptForTargetCurrency()`
  * `printConvertedExpenses(Currency targetCurrency)`

### Code Smell

* **Long Method**

### Definisi

* Method yang terlalu panjang atau memiliki terlalu banyak tanggung jawab.

### Alasan Implementasi Sebelumnya Termasuk Code Smell

* `convertCurrency()` sebelumnya menangani validasi input, parsing currency, formatting, konversi nilai, dan pencetakan output dalam satu method.

### Perubahan yang Dilakukan

* Mengekstrak logic validasi currency dan pencetakan hasil ke helper method terpisah.

### Alasan Refactor Meningkatkan Kualitas

* **Maintainability:** Perubahan pada input validation dan output conversion lebih terisolasi.
* **Readability:** Flow method utama lebih mudah dipahami.
* **Modularity:** Tanggung jawab dipisahkan ke method kecil.

### File yang Diubah

* `src/ExpenseTracker.java`

### File Baru yang Dibuat

* `-`

### Method/Logic yang Dipindahkan

* Logic validasi currency → `promptForTargetCurrency()`
* Logic konversi dan output → `printConvertedExpenses(Currency targetCurrency)`

### Teknik Refactor yang Digunakan

* `Extract Method`

### Catatan Tambahan

* Tidak ada perubahan behavior maupun output program.