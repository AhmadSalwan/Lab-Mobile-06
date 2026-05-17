package com.example.libraryapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRepository {
    private static BookRepository instance;
    private List<Book> books;

    private BookRepository() {
        books = new ArrayList<>();
        initDummyData();
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private void initDummyData() {
        // 15+ dummy books with complete data
        books.add(new Book(
            "To Kill a Mockingbird", "Harper Lee", 1960,
            "A gripping, heart-wrenching, and wholly remarkable tale of coming-of-age in a South poisoned by virulent prejudice. Scout Finch's father defends a black man accused of rape in 1930s Alabama.",
            "Classic", 4.8f, "cover_mockingbird", 281, "English", "J. B. Lippincott & Co."
        ));
        books.add(new Book(
            "1984", "George Orwell", 1949,
            "Among the seminal texts of the 20th century, Nineteen Eighty-Four is a rare work that grows more haunting as its futuristic vision becomes more real. A startling and haunting vision of the world.",
            "Dystopia", 4.7f, "cover_1984", 328, "English", "Secker & Warburg"
        ));
        books.add(new Book(
            "The Great Gatsby", "F. Scott Fitzgerald", 1925,
            "The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted 'gin was the national drink and sex the national obsession'.",
            "Classic", 4.5f, "cover_gatsby", 180, "English", "Charles Scribner's Sons"
        ));
        books.add(new Book(
            "Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997,
            "Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal.",
            "Fantasy", 4.9f, "cover_hp1", 309, "English", "Bloomsbury"
        ));
        books.add(new Book(
            "The Hobbit", "J.R.R. Tolkien", 1937,
            "Bilbo Baggins is a hobbit who enjoys a comfortable, unambitious life, rarely travelling further than his pantry or cellar. But his contentment is disturbed when the wizard Gandalf and a company of dwarves arrive on his doorstep.",
            "Fantasy", 4.8f, "cover_hobbit", 310, "English", "George Allen & Unwin"
        ));
        books.add(new Book(
            "Dune", "Frank Herbert", 1965,
            "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an inhospitable world where the only thing of value is the spice melange.",
            "Sci-Fi", 4.7f, "cover_dune", 412, "English", "Chilton Books"
        ));
        books.add(new Book(
            "Atomic Habits", "James Clear", 2018,
            "No matter your goals, Atomic Habits offers a proven framework for improving every day. James Clear reveals practical strategies that will teach you exactly how to form good habits, break bad ones, and master the tiny behaviors that lead to remarkable results.",
            "Self-Help", 4.8f, "cover_atomic", 320, "English", "Avery"
        ));
        books.add(new Book(
            "Sapiens", "Yuval Noah Harari", 2011,
            "From a renowned historian comes a groundbreaking narrative of humanity's creation and evolution. One hundred thousand years ago, at least six different species of humans inhabited Earth. Today there is just one. Us.",
            "Non-Fiction", 4.6f, "cover_sapiens", 443, "English", "Harper"
        ));
        books.add(new Book(
            "The Alchemist", "Paulo Coelho", 1988,
            "Paulo Coelho's masterpiece tells the mystical story of Santiago, an Andalusian shepherd boy who yearns to travel in search of a worldly treasure. His quest will lead him to riches far different—and far more satisfying—than he ever imagined.",
            "Fiction", 4.6f, "cover_alchemist", 197, "Portuguese", "HarperOne"
        ));
        books.add(new Book(
            "Pride and Prejudice", "Jane Austen", 1813,
            "Since its immediate success in 1813, Pride and Prejudice has remained one of the most popular novels in the English language. Jane Austen called this brilliant work her own darling child.",
            "Classic", 4.7f, "cover_pride", 432, "English", "T. Egerton"
        ));
        books.add(new Book(
            "The Catcher in the Rye", "J.D. Salinger", 1951,
            "The hero-narrator of The Catcher in the Rye is an ancient child of sixteen, a native New Yorker named Holden Caulfield. Through circumstances that tend to preclude adult, secondhand description, he leaves his prep school in Pennsylvania.",
            "Classic", 4.3f, "cover_catcher", 277, "English", "Little, Brown and Company"
        ));
        books.add(new Book(
            "Brave New World", "Aldous Huxley", 1932,
            "Far in the future, the World Controllers have created the ideal society. Through clever use of genetic engineering, brainwashing and recreational sex and drugs, all the citizens are happy consumers.",
            "Dystopia", 4.4f, "cover_brave", 311, "English", "Chatto & Windus"
        ));
        books.add(new Book(
            "The Da Vinci Code", "Dan Brown", 2003,
            "An ingenious code hidden in the works of Leonardo da Vinci. A desperate race through the cathedrals and castles of Europe. An astonishing truth concealed for centuries. Will it surface at last?",
            "Thriller", 4.4f, "cover_davinci", 454, "English", "Doubleday"
        ));
        books.add(new Book(
            "Think and Grow Rich", "Napoleon Hill", 1937,
            "This book contains money-making secrets that can change your life. Inspired by Andrew Carnegie's magic formula for success, this book will teach you the secrets that could bring you a fortune.",
            "Self-Help", 4.5f, "cover_think", 238, "English", "The Ralston Society"
        ));
        books.add(new Book(
            "Bumi Manusia", "Pramoedya Ananta Toer", 1980,
            "Novel yang mengisahkan perjuangan Minke, pemuda Jawa terpelajar di zaman kolonial Belanda, dalam menemukan jati dirinya di tengah tekanan sistem kolonial yang tidak adil.",
            "Historical Fiction", 4.9f, "cover_bumi", 335, "Indonesian", "Hasta Mitra"
        ));
        books.add(new Book(
            "Laskar Pelangi", "Andrea Hirata", 2005,
            "Kisah 10 anak Belitung yang penuh dengan semangat untuk terus belajar demi meraih mimpi-mimpi mereka meskipun dalam keterbatasan. Sebuah novel yang menginspirasi jutaan pembaca Indonesia.",
            "Fiction", 4.8f, "cover_laskar", 529, "Indonesian", "Bentang Pustaka"
        ));
        books.add(new Book(
            "The Psychology of Money", "Morgan Housel", 2020,
            "Timeless lessons on wealth, greed, and happiness doing well with money isn't necessarily about what you know. It's about how you behave. And behavior is hard to teach, even to really smart people.",
            "Non-Fiction", 4.7f, "cover_psychology", 256, "English", "Harriman House"
        ));
    }

    public List<Book> getAllBooks() {
        // Return in reverse order (newest added = first shown)
        List<Book> reversed = new ArrayList<>(books);
        java.util.Collections.reverse(reversed);
        return reversed;
    }

    public List<Book> getLikedBooks() {
        List<Book> liked = new ArrayList<>();
        for (Book b : books) {
            if (b.isLiked()) liked.add(b);
        }
        return liked;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Book getBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("All");
        for (Book b : books) {
            if (!genres.contains(b.getGenre())) {
                genres.add(b.getGenre());
            }
        }
        return genres;
    }
}
