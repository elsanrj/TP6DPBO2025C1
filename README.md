# Janji
Saya Elsa Nurjanah dengan NIM 2306026 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
## 1. App.java
`main(String[] args)`: Launch **MainMenu** menggunakan `SwingUtilities.invokeLater()`.

## 2. MainMenu.java
Menampilkan layar menu utama yang terdiri dari logo game dan tombol mulai.
- **Atribut**:
  - **startButton**: Tombol untuk memulai game.
  - **logoLabel**: Sebuah label yang menampilkan logo game.
  - **backgroundImage**: Gambar latar belakang untuk menu.
  - **startIcon**, **logoIcon**: Ikon untuk tombol mulai dan gambar logo.
  - **frameWidth**, **frameHeight**: Dimensi jendela menu utama.
  - **startIconWidth**, **startIconHeight**, **logoWidth**, **logoHeight**: Ukuran untuk ikon dan logo.
- **Metode**:
  - `MainMenu()`: Menginisialisasi UI menu utama, memuat gambar, dan menyusun layout.
  - `startGame()`: Menutup menu utama dan membuka layar game.
  - `main(String[] args)`: Titik masuk untuk menu utama (launch frame `MainMenu`).

## 3. Pipe.java
Kelas objek pipa dalam game (upperPipe / lowerPipe).
- **Atribut**:
  - **posX**, **posY**: Posisi saat ini dari pipa.
  - **width**, **height**: Dimensi dari pipa.
  - **image**: Gambar yang digunakan untuk merender pipa.
  - **velocityX**: Kecepatan pipa, yang memindahkannya ke kiri.
  - **passed**: Flag boolean yang menentukan apakah pipa telah melewati pemain untuk tujuan pencatatan skor.

## 4. Player.java
Kelas objek pemain (burung) dalam game.
- **Atribut**:
  - **posX**, **posY**: Posisi saat ini dari burung.
  - **width**, **height**: Dimensi dari burung.
  - **image**: Gambar yang digunakan untuk merender burung.
  - **velocityY**: Kecepatan vertikal burung, digunakan untuk mensimulasikan gravitasi dan lompatan.

## 5. FlappyBird.java
Panel utam game yang menangani mekanisme dan tampilan game.
- **Atribut**:
  - **frameWidth**, **frameHeight**: Dimensi dari jendela game (360x640).
  - **backgroundImage**, **birdImage**, **lowerPipeImage**, **upperPipeImage**, **gameOverImage**, **restartImage**: Gambar yang digunakan untuk merender komponen game yang berbeda (latar belakang, burung, pipa, game over, dll.).
  - **player**: Sebuah instance dari kelas `Player` yang mewakili burung.
  - **pipes**: Sebuah `ArrayList<Pipe>` yang menyimpan semua pipa (upperPipe / lowerPipe).
  - **gameOver**: Sebuah flag boolean yang menunjukkan apakah game telah berakhir.
  - **gameLoop**: Sebuah `Timer` untuk menggerakkan loop game pada 60 FPS.
  - **pipesCooldown**: Sebuah `Timer` untuk menghasilkan pipa setiap 4,5 detik.
  - **gravity**, **score**: Gravitasi yang memengaruhi burung dan penghitung skor.
  - **scoreLabel**: Sebuah JLabel untuk menampilkan skor di layar.
- **Metode**:
  - `draw(Graphics g)`: Merender komponen game (latar belakang, burung, pipa, dll.).
  - `paintComponent(Graphics g)`: Memanggil metode `draw()` untuk merender komponen.
  - `move()`: Memperbarui posisi burung, menerapkan gravitasi, memindahkan pipa, dan mengecek penambahan skor (burung melewati pipa).
  - `boxCollision()`: Memeriksa apakah ada tabrakan antara burung dengan pipa atau tanah, jika ada maka game over.
  - `placePipes()`: Menghasilkan sepasang pipa baru pada ketinggian acak.
  - `actionPerformed(ActionEvent e)`: Dijalankan setiap frame (60 FPS), memanggil `move()`, `boxCollision()`, dan `repaint()`.
  - `keyPressed(KeyEvent e)`: Menanggapi penekanan tombol, terutama spasi untuk burung melompat dan "R" untuk restart.
  - `restart()`: Mengatur ulang status game (posisi pemain, pipa, skor) untuk memulai game baru.

# Alur Program
1. **Menu Utama**: Saat game dimulai, **MainMenu** ditampilkan, memungkinkan pengguna untuk memulai game dengan mengklik tombol "Start".
2. **Mulai Game**: Panel **FlappyBird** ditampilkan, menampilkan latar belakang, burung, dan pipa yang bergerak dari kanan ke kiri.
3. **Interaksi Pemain**: Pemain dapat mengendalikan burung menggunakan tombol spasi untuk membuatnya "terbang" dan melompat. Tujuannya adalah untuk menghindari pipa sambil mengumpulkan poin.
4. **Pipa dan Pencatatan Skor**: Pipa dihasilkan secara berkala, dan jika burung melewati pipa, skor bertambah.
5. **Game Over**: Jika burung bertabrakan dengan pipa atau tanah, game berakhir dan layar "Game Over" ditampilkan dengan tombol restart menggunakan tombol "R".

# Fitur Utama Program
## 1. Main Menu

<img src="https://github.com/elsanrj/TP6DPBO2025C1/blob/master/Dokumentasi/mainMenu.png" width="300">

Saat aplikasi dijalankan, MainMenu akan memunculkan antarmuka berisi latar belakang, logo, dan tombol "Start". Ketika tombol ini ditekan, metode startGame() akan dipanggil. Di dalam metode tersebut, jendela main menu akan ditutup (dispose()), kemudian jendela permainan baru akan dibuat dan menampilkan instance dari kelas FlappyBird, sehingga permainan dimulai.
## 2. Game Over

<img src="https://github.com/elsanrj/TP6DPBO2025C1/blob/master/Dokumentasi/gameOver_fall.png" width="300"> <img src="https://github.com/elsanrj/TP6DPBO2025C1/blob/master/Dokumentasi/gameOver_pipeCollision.png" width="300">

Dalam metode `boxCollision()`, dibuat bounding box untuk burung dan setiap pipa. Jika ada intersection (tabrakan) antara burung dan pipa, maka status gameOver diubah menjadi true. Selain itu, jika posisi vertikal burung melewati batas bawah layar (menyentuh tanah), maka gameOver juga akan menjadi true.
## 3. Restart
Saat game over, game mengalami freeze dan user dapat menekan tombol R pada keyboard. Metode restart() akan dipanggil dari dalam keyPressed(). Di dalam restart(), posisi burung direset ke awal, kecepatan vertikalnya diatur ke nol, daftar pipa dikosongkan, skor di-reset ke nol, dan status gameOver diubah kembali menjadi false. Hal ini memungkinkan permainan dimulai kembali dari awal.
## 4. Score
Label skor (scoreLabel) dibuat dan ditampilkan di pojok kiri atas layar dalam konstruktor. Peningkatan skor terjadi dalam metode move(), yaitu ketika burung berhasil melewati pipa bagian atas (posisi horizontal burung melewati posisi horizontal pipa yang persis ada di depannya). Saat kondisi ini terpenuhi, skor bertambah satu dan label diperbarui dengan skor terbaru. Setiap pipa yang sudah dilewati akan ditandai dengan flag passed agar tidak dihitung lagi.

# Dokumentasi
[![YouTube](http://i.ytimg.com/vi/bTOuBws4RBE/hqdefault.jpg)](https://www.youtube.com/watch?v=bTOuBws4RBE)
