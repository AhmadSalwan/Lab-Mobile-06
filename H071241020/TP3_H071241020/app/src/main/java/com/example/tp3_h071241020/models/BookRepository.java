package com.example.tp3_h071241020.models;

import com.example.tp3_h071241020.R;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static List<Book> bookList = new ArrayList<>();

    static {
        bookList.add(new Book("B1", "Strategi Transformasi Bangsa Menuju Indonesia Emas 2045", "Prabowo Subianto", "2023", "Paparan komprehensif mengenai visi transformasi ekonomi dan sosial Indonesia menjadi kekuatan besar dunia.", "Politik & Ekonomi", "Edisi Visi Negara", "Team Prabowo", 4.9f, R.drawable._1, null));
        bookList.add(new Book("B2", "Lucunya Prabowo", "Kurniawan", "2014", "Kumpulan sisi humanis, cerita unik, dan humor dari perjalanan karir politik Prabowo Subianto.", "Biografi & Humor", "Cetakan Kedua", "Media Presindo", 4.2f, R.drawable._2, null));
        bookList.add(new Book("B3", "Jokowi Menuju Cahaya", "Alberthiene Endah", "2018", "Biografi resmi yang mengisahkan perjalanan hidup Jokowi dari masa kecil hingga memimpin bangsa.", "Biografi", "Edisi Revisi", "Gramedia", 4.7f, R.drawable._3, null));
        bookList.add(new Book("B4", "Bahlil: Koboi dari Timur", "Fentiny Muhtar", "2022", "Kisah inspiratif Bahlil Lahadalia yang bertransformasi dari sopir angkot menjadi menteri investasi.", "Biografi", "Edisi Pertama", "Gramedia", 4.5f, R.drawable._4, null));
        bookList.add(new Book("B5", "Gibran: The Next President?", "Anindita S. Tayo", "2023", "Analisis mendalam mengenai fenomena politik dan potensi Gibran Rakabuming Raka dalam kancah nasional.", "Politik", "Edisi Digital", "Pustaka Rakyat", 4.0f, R.drawable._5, null));
        bookList.add(new Book("B6", "Jokowi Pemimpin Rakyat Berjiwa Rocker", "Yon Thayrun", "2012", "Melihat sisi unik Jokowi yang menyelaraskan jiwa kepemimpinan rakyat dengan kecintaannya pada musik metal.", "Biografi", "Edisi Kolektor", "Expose", 4.3f, R.drawable._6, null));
        bookList.add(new Book("B7", "Ahmad Sahroni: Meraih Mimpi", "Fentiny Muhtar", "2020", "Kisah sukses 'Crazy Rich Tanjung Priok' yang memulai segalanya dari nol sebagai tukang semir sepatu.", "Biografi", "Edisi Hardcover", "Gramedia", 4.6f, R.drawable._7, null));
        bookList.add(new Book("B8", "Pilpres 2024 dan Cawe-cawe Presiden Jokowi", "Pengamat Politik", "2023", "Diskusi kritis mengenai peran kepemimpinan presiden dalam menjamin transisi kekuasaan yang stabil.", "Politik", "Edisi Analisis", "Pustaka Bangsa", 4.1f, R.drawable._8, null));
        bookList.add(new Book("B9", "Gado-gado Kerikil Jokowi", "Akmal Nasery Basral", "2014", "Kumpulan catatan jurnalistik mengenai dinamika kepemimpinan Jokowi saat masih menjabat di Solo.", "Sosial", "Edisi Perdana", "Republika", 4.2f, R.drawable._9, null));
        bookList.add(new Book("B10", "10 Dosa Besar Suharto", "Wimanjaya Liotohe", "1998", "Buku kontroversial yang mengkritisi berbagai kebijakan di era Orde Baru pasca reformasi.", "Sejarah", "Edisi Klasik", "Yayasan Muttaqin", 3.8f, R.drawable._10, null));
        bookList.add(new Book("B11", "Paradoks Indonesia dan Solusinya", "Prabowo Subianto", "2017", "Gagasan kritis mengenai ketimpangan ekonomi dan solusi untuk kedaulatan pangan dan energi.", "Ekonomi", "Edisi Revisi", "Garnida", 4.7f, R.drawable._11, null));
        bookList.add(new Book("B12", "Islam ala Prabowo", "Budi Purnomo", "2018", "Menelusuri kedekatan Prabowo dengan nilai-nilai religius dan ormas Islam di Indonesia.", "Religi", "Edisi Pemilu", "Indie Press", 4.0f, R.drawable._12, null));
        bookList.add(new Book("B13", "Jokowi: Si Tukang Kayu", "G. Dwipayana", "2014", "Narasi perjalanan hidup Jokowi dari pengusaha mebel kayu hingga menjadi Presiden Republik Indonesia.", "Biografi", "Edisi Populer", "Pustaka Indonesia", 4.4f, R.drawable._13, null));
        bookList.add(new Book("B14", "Luhut Binsar menurut kita-kita", "Peter F. Gontha", "2022", "Kumpulan testimoni dari para sahabat dan kolega mengenai karakter Luhut Binsar Pandjaitan.", "Biografi", "Edisi Mewah", "Penerbit Inspirasi", 4.6f, R.drawable._14, null));
        bookList.add(new Book("B15", "Perkebunan Kelapa Sawit Cepat Panen", "Paiman", "2021", "Panduan teknis dan praktis bagi para petani untuk mengelola kebun kelapa sawit secara efisien.", "Pertanian", "Edisi Panduan", "Lily Publisher", 4.5f, R.drawable._15, null));
    }

    public static List<Book> getBooks() { return bookList; }

    public static void addBook(Book book) {
        bookList.add(0, book);
    }

    public static void deleteBook(String id) {
        bookList.removeIf(b -> b.getId().equals(id));
    }

    public static void updateBook(Book updated) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId().equals(updated.getId())) {
                bookList.set(i, updated);
                break;
            }
        }
    }

    public static Book getBookById(String id) {
        for (Book b : bookList) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }
}