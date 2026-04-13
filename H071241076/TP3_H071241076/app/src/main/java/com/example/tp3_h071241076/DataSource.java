package com.example.tp3_h071241076;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Book> books = new ArrayList<>();

    public static void initDummyData() {
        if (books.isEmpty()) {
            books.add(new Book("1", "Laskar Pelangi", "Andrea Hirata", "2005", "Kisah perjuangan 10 anak di Belitung.", R.drawable.laskarpelangi, 4.8f, "Novel"));
            books.add(new Book("2", "Bumi Manusia", "Pramoedya A. Toer", "1980", "Kisah Minke di era kolonial.", R.drawable.bumimanusia, 4.9f, "Sejarah"));
            books.add(new Book("3", "Negeri 5 Menara", "A. Fuadi", "2009", "Perjalanan Alif di pesantren.", R.drawable.negeri5menara, 4.7f, "Novel"));
            books.add(new Book("4", "Ayat-Ayat Cinta", "Habiburrahman E. S.", "2004", "Kisah cinta Fahri di Mesir.", R.drawable.ayatayatcinta, 4.6f, "Romansa"));
            books.add(new Book("5", "Perahu Kertas", "Dee Lestari", "2009", "Kisah persahabatan dan cinta.", R.drawable.perahukertas, 4.5f, "Fiksi"));
            books.add(new Book("6", "Filosofi Kopi", "Dee Lestari", "2006", "Pencarian kopi terbaik.", R.drawable.filosofikopi, 4.4f, "Fiksi"));
            books.add(new Book("7", "Gadis Kretek", "Ratih Kumala", "2012", "Sejarah kretek dan cinta.", R.drawable.gadiskretek, 4.6f, "Fiksi Sejarah"));
            books.add(new Book("8", "Hujan", "Tere Liye", "2016", "Tentang persahabatan dan melupakan.", R.drawable.hujan, 4.7f, "Sci-Fi"));
            books.add(new Book("9", "Bumi", "Tere Liye", "2014", "Petualangan Raib di dunia paralel.", R.drawable.bumi, 4.8f, "Fantasi"));
            books.add(new Book("10", "Pulang", "Leila S. Chudori", "2012", "Eksil politik Indonesia.", R.drawable.pulang, 4.8f, "Sejarah"));
            books.add(new Book("11", "Laut Bercerita", "Leila S. Chudori", "2017", "Kisah aktivis yang dihilangkan.", R.drawable.lautbercerita, 4.9f, "Fiksi Sejarah"));
            books.add(new Book("12", "Dilan 1990", "Pidi Baiq", "2014", "Kisah romansa masa SMA.", R.drawable.dilan, 4.5f, "Romansa"));
            books.add(new Book("13", "Ronggeng Dukuh Paruk", "Ahmad Tohari", "1982", "Kisah penari ronggeng.", R.drawable.ronggeng, 4.7f, "Budaya"));
            books.add(new Book("14", "Cantik Itu Luka", "Eka Kurniawan", "2002", "Kisah keluarga yang tragis.", R.drawable.cantikituluka, 4.8f, "Fiksi"));
            books.add(new Book("15", "O", "Eka Kurniawan", "2016", "Kisah tentang seekor monyet.", R.drawable.o, 4.3f, "Fiksi"));
        }
    }
}