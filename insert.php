<?php
    include "koneksi.php";

    // Pemeriksaan input
    $id = isset($_POST['id']) ? $_POST['id'] : '';
    $nik = isset($_POST['nik']) ? $_POST['nik'] : '';
    $nama = isset($_POST['nama']) ? $_POST['nama'] : '';
    $alamat = isset($_POST['alamat']) ? $_POST['alamat'] : '';


    // Pemeriksaan input tambahan (opsional)
    if (empty($nama) || empty($nik) || empty($alamat)) { 
        echo "Kolom isian tidak boleh kosong"; 
        exit; // Menghentikan eksekusi skrip jika input tidak valid
    }

    if (empty($id)) {
        $query = mysqli_query($con,"INSERT INTO pengguna (id, nik, nama, alamat) VALUES (0, '$nik', '$nama', '$alamat')");
        $pesanSukses = "Data berhasil di simpan";
        $pesanGagal = "Error simpan Data";
    } else {
        $query = mysqli_query($con,"UPDATE pengguna SET nik = '$nik', nama = '$nama', alamat = '$alamat' WHERE id = '$id'");
        $pesanSukses = "Data berhasil di ubah";
        $pesanGagal = "Error ubah Data";
    }

    // Pesan sukses atau gagal
    if ($query) {
        echo $pesanSukses;
    } else {
        echo $pesanGagal . ": " . mysqli_error($con); // Menampilkan pesan error dari database
    }

    // Tutup koneksi database
    mysqli_close($con);
?>
