# Tugas Kecil I Strategi Algoritma
> Dibuat oleh Rafen Max Alessandro, K1, 13523031 sebagai pemenuhan tugas mata kuliah IF2211 Strategi Algoritma

## IQ Puzzler Pro Solver
IQ Puzzler Pro Solver merupakan permainan logika berbasis puzzle dengan tujuan untuk mengisi sebuah papan 2 dimensi kosong menggunakan potongan puzzle dengan bentuk unik. Permainan dikatakan berhasil diselesaikan ketika pemain menemukan kombinasi peletakan puzzle sehingga papan terisi penuh menggunakan seluruh blok puzzle. 

Menggunakan algoritma _brute force_, program ini dapat mencari penyelesaian dari permainan IQ Puzzler Pro. Program memanfaatkan iterasi setiap kemungkinan kombinasi peletakan puzzle dan _backtracking_ untuk memberikan salah satu penyelesaian yang mungkin, atau menyatakan bahwa tidak ada penyelesaian sama sekali. 

## Requirements
Sebelum menjalankan program, pastikan perangkat Anda sudah memiliki:
1. **JDK 17 atau lebih baru** (Download: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html))
2. **JavaFX SDK (versi yang sesuai)** (Download: [Gluon JavaFX](https://gluonhq.com/products/javafx/))  
Serta pastikan bahwa JavaFX telah terkonfigurasi ke dalam IDE yang Anda gunakan.

## _Compile_ Program
1. Lakukan _clone_ terhadap repositori ini menggunakan _code_ berikut
```shell
git clone https://github.com/rafenmaxxx/Tucil1_13523031.git
```
2. Lacak direktori letak JavaFX disimpan, lalu salin _path_ menuju folder lib yang telah di-_install_
3. Ubah direktori pada terminal menjadi direktori hasil _clone_ repositori ini
```shell
cd <Tucil1_13523031>
```
4. Jalankan program dengan mengeksekusikan kedua _command_ berikut
```shell
javac --module-path "<path-to-installed-JavaFX-library-folder>" --add-modules javafx.controls,javafx.fxml -d bin src/Main.java src/utility/*.java
```
```shell
java --module-path "<path-to-installed-JavaFX-library-folder>" --add-modules javafx.controls,javafx.fxml -cp bin src.Main
```
5. _Graphical User Interface_ (GUI) telah berhasil dijalankan dan program siap untuk Anda gunakan! ٩(^ᗜ^ )و ´-


## Menjalankan Program
Program dapat menerima _input_ berupa file dengan ekstensi .txt melalui tombol **Pilih File** atau menerima input manual dengan mengetikan konfigurasi puzzle pada **textbox** yang tersedia. Setelah memasukkan konfigurasi, pengguna dapat menekan tombol **Mulai Proses** untuk menjalankan algoritma _brute force_ dan mencari penyelesaian puzzle. Jika ditemukan penyelesaian, pengguna dapat menekan tombol **Simpan solusi** untuk menyimpan penyelesaian ke dalam file berekstensi .txt dalam _folder_ test dalam direktori repositori.
<img src="https://github.com/user-attachments/assets/f8226e1b-0e6e-4ccf-a79e-2bde030d8f8b" width="500"> <img src="https://github.com/user-attachments/assets/92e8daf6-eddb-4b9e-8c63-9aacc5c62bb3" width="500">

