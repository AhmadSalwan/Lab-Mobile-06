package com.example.tp3;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private static DataSource instance;
    private List<Book> books;

    private DataSource() {
        books = new ArrayList<>();
        // Using actual drawables found in project
        books.add(new Book("1", "Noise: A Flaw in Human Judgment", "Daniel Kahneman", 2021, "A ground-breaking investigation of why people make bad judgments.", "noise", false, "Psychology", 5.0f));
        books.add(new Book("2", "Hujan", "Tere Liye", 2016, "A story about friendship, love, and science fiction set in the future.", "hujann", false, "Fiction", 5.0f));
        books.add(new Book("3", "Marvel Encyclopedia", "Matt Forbeck", 2014, "The definitive guide to the characters of the Marvel Universe.", "marvel", false, "Encyclopedia", 5.0f));
//        books.add(new Book("4", "Madilog", "Tan Malaka", 1943, "Materialisme, Dialektika, dan Logika.", "madilog", false, "Philosophy", 4.5f));
        books.add(new Book("5", "Dune Messiah", "Frank Herbert", 1969, "The second novel in the Dune chronicles.", "dune", false, "Sci-Fi", 4.5f));
        books.add(new Book("6", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, "A young boy discovers he is a wizard.", "harry_potter", false, "Fantasy", 5.0f));
        books.add(new Book("7", "Atomic Habits", "James Clear", 2018, "An easy and proven way to build good habits.", "atomic_habits", false, "Self-Help", 5.0f));
        
        // Add more duplicates or generic ones to reach 15 if needed
//        books.add(new Book("8", "The Psychology of Money", "Morgan Housel", 2020, "Timeless lessons on wealth, greed, and happiness.", "money", false, "Finance", 4.8f));
//        books.add(new Book("9", "1984", "George Orwell", 1949, "Dystopian social science fiction.", "1984", false, "Sci-Fi", 4.7f));
//        books.add(new Book("10", "The Hobbit", "J.R.R. Tolkien", 1937, "Fantasy novel about Bilbo Baggins.", "hobbit", false, "Fantasy", 4.8f));
//        books.add(new Book("11", "Pride and Prejudice", "Jane Austen", 1813, "Romantic novel of manners.", "pride", false, "Romance", 4.6f));
//        books.add(new Book("12", "The Alchemist", "Paulo Coelho", 1988, "A novel about following your dream.", "alchemist", false, "Adventure", 4.5f));
//        books.add(new Book("13", "Brave New World", "Aldous Huxley", 1932, "A dystopian novel.", "brave", false, "Sci-Fi", 4.4f));
//        books.add(new Book("14", "Jane Eyre", "Charlotte Brontë", 1847, "A novel by English writer Charlotte Brontë.", "janeeyre", false, "Classic", 4.3f));
//        books.add(new Book("15", "The Da Vinci Code", "Dan Brown", 2003, "Mystery thriller novel.", "davinci", false, "Mystery", 4.1f));
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(0, book);
    }
}
