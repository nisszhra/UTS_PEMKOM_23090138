/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p_uts_23090138_c_2025;

import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

import java.util.Scanner;

/**
 *
 * @author SPECTRE
 */
public class CRUD_23090138_C_2025 {

    static Scanner scanner = new Scanner(System.in);
    static MongoCollection<Document> collection = MongoConnection.getCustomerCollection();

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n=== Aplikasi Manajemen Inventaris Barang ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Tampilkan Semua Barang");
            System.out.println("3. Ubah Data Barang");
            System.out.println("4. Hapus Barang");
            System.out.println("5. Cari Barang");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahBarang();
                    break;
                case 2:
                    tampilkanBarang();
                    break;
                case 3:
                    ubahBarang();
                    break;
                case 4:
                    hapusBarang();
                    break;
                case 5:
                    cariBarang();
                    break;
                case 6:
                    System.out.println("Keluar program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilihan != 6);
    }

    // Menambahkan data ke MongoDB
    public static void tambahBarang() {
        System.out.print("Kode Barang: ");
        String kode = scanner.nextLine();
        System.out.print("Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Jumlah: ");
        int jumlah = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Apakah barang dalam kondisi baik? (ya/tidak): ");
        String kondisiInput = scanner.nextLine();
        boolean kondisiBaik = kondisiInput.equalsIgnoreCase("ya");

        // Simpan ke MongoDB
        Document doc = new Document("kode", kode)
                .append("nama", nama)
                .append("jumlah", jumlah)
                .append("kondisiBaik", kondisiBaik); // simpan boolean
        collection.insertOne(doc);

        System.out.println("Barang berhasil ditambahkan.");
    }

    // Menampilkan semua data
    public static void tampilkanBarang() {
        FindIterable<Document> barangList = collection.find();
        System.out.println("\nDaftar Barang:");
        for (Document doc : barangList) {
            System.out.println("Kode: " + doc.getString("kode")
                    + ", Nama: " + doc.getString("nama")
                    + ", Jumlah: " + doc.getInteger("jumlah")
                    + ", Kondisi Baik: " + (doc.getBoolean("kondisiBaik") ? "Ya" : "Tidak"));
        }
    }

    // Mengubah data berdasarkan kode
    public static void ubahBarang() {
        System.out.print("Masukkan kode barang yang ingin diubah: ");
        String kode = scanner.nextLine();

        Document barang = collection.find(eq("kode", kode)).first();
        if (barang == null) {
            System.out.println("Barang tidak ditemukan.");
            return;
        }

        System.out.print("Nama baru: ");
        String namaBaru = scanner.nextLine();
        System.out.print("Jumlah baru: ");
        int jumlahBaru = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Apakah kondisi sekarang baik? (ya/tidak): ");
        String kondisiInput = scanner.nextLine();
        boolean kondisiBaikBaru = kondisiInput.equalsIgnoreCase("ya");

        Document updateDoc = new Document("$set", new Document("nama", namaBaru)
                .append("jumlah", jumlahBaru)
                .append("kondisiBaik", kondisiBaikBaru));

        collection.updateOne(eq("kode", kode), updateDoc);
        System.out.println("Barang berhasil diperbarui.");
    }

    // Menghapus data berdasarkan kode
    public static void hapusBarang() {
        System.out.print("Masukkan kode barang yang ingin dihapus: ");
        String kode = scanner.nextLine();

        long deleted = collection.deleteOne(eq("kode", kode)).getDeletedCount();
        if (deleted > 0) {
            System.out.println("Barang berhasil dihapus.");
        } else {
            System.out.println("Barang tidak ditemukan.");
        }
    }

    // Mencari barang berdasarkan nama
    public static void cariBarang() {
        System.out.print("Masukkan nama barang yang dicari: ");
        String nama = scanner.nextLine();

        FindIterable<Document> hasil = collection.find(eq("nama", nama));
        boolean ditemukan = false;

        for (Document doc : hasil) {
            System.out.println("Kode: " + doc.getString("kode")
                    + ", Nama: " + doc.getString("nama")
                    + ", Jumlah: " + doc.getInteger("jumlah")
                    + ", Kondisi Baik: " + (doc.getBoolean("kondisiBaik") ? "Ya" : "Tidak"));
            ditemukan = true;
        }

        if (!ditemukan) {
            System.out.println("Barang tidak ditemukan.");
        }
    }
}
