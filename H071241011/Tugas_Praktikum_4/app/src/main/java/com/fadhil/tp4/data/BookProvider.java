package com.fadhil.tp4.data;

import com.fadhil.tp4.R;
import com.fadhil.tp4.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookProvider {
    private static BookProvider instance;
    private List<Book> books;

    private BookProvider() {
        books = new ArrayList<>();
        // 15 dummy books
        books.add(new Book("1", "The Android Handbook", "Fadhil", 2024, "A comprehensive guide to Android Development.", R.drawable.ic_dummy_cover, "Education", 4.5f));
        books.add(new Book("2", "Java Programming 101", "John Doe", 2022, "Learn Java from scratch to advanced.", R.drawable.ic_dummy_cover, "Education", 4.8f));
        books.add(new Book("3", "UI/UX Mastery", "Jane Smith", 2023, "Mastering the art of user interfaces.", R.drawable.ic_dummy_cover, "Design", 4.2f));
        books.add(new Book("4", "Kotlin for Beginners", "Alex Lee", 2021, "The modern way to program Android.", R.drawable.ic_dummy_cover, "Education", 4.9f));
        books.add(new Book("5", "Clean Architecture", "Uncle Bob", 2017, "Software structure and design.", R.drawable.ic_dummy_cover, "Programming", 5.0f));
        books.add(new Book("6", "Design Patterns", "GoF", 1994, "Elements of Reusable Object-Oriented Software.", R.drawable.ic_dummy_cover, "Programming", 4.7f));
        books.add(new Book("7", "The Pragmatic Programmer", "Andrew Hunt", 1999, "Your journey to mastery.", R.drawable.ic_dummy_cover, "Programming", 4.8f));
        books.add(new Book("8", "Effective Java", "Joshua Bloch", 2018, "Best practices for Java.", R.drawable.ic_dummy_cover, "Programming", 4.9f));
        books.add(new Book("9", "Head First Design Patterns", "Eric Freeman", 2004, "A brain-friendly guide.", R.drawable.ic_dummy_cover, "Programming", 4.6f));
        books.add(new Book("10", "Clean Code", "Robert C. Martin", 2008, "A handbook of agile software craftsmanship.", R.drawable.ic_dummy_cover, "Programming", 4.9f));
        books.add(new Book("11", "Refactoring", "Martin Fowler", 2018, "Improving the design of existing code.", R.drawable.ic_dummy_cover, "Programming", 4.7f));
        books.add(new Book("12", "Code Complete", "Steve McConnell", 2004, "A practical handbook of software construction.", R.drawable.ic_dummy_cover, "Programming", 4.8f));
        books.add(new Book("13", "Mythical Man-Month", "Frederick P. Brooks Jr.", 1975, "Essays on software engineering.", R.drawable.ic_dummy_cover, "Management", 4.5f));
        books.add(new Book("14", "Continuous Delivery", "Jez Humble", 2010, "Reliable software releases.", R.drawable.ic_dummy_cover, "DevOps", 4.6f));
        books.add(new Book("15", "The DevOps Handbook", "Gene Kim", 2016, "How to create world-class agility, reliability, and security in technology organizations.", R.drawable.ic_dummy_cover, "DevOps", 4.7f));
    }

    public static BookProvider getInstance() {
        if (instance == null) {
            instance = new BookProvider();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(0, book); // Add to top
    }
}
