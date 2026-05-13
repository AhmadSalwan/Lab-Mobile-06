package com.example.library_app.data;

import com.example.library_app.R;
import com.example.library_app.model.Book;
import com.example.library_app.utils.BookRepository;

public class DummyData {

    public static void generateBooks() {

        if (!BookRepository.bookList.isEmpty()) {
            return;
        }

        BookRepository.bookList.add(new Book(
                "Atomic Habits",
                "James Clear",
                "2018",
                "Panduan membangun kebiasaan baik.",
                R.drawable.atomic_habits,
                false,
                "Self Improvement",
                4.8f
        ));

        BookRepository.bookList.add(new Book(
                "Harry Potter",
                "J.K Rowling",
                "2001",
                "Petualangan dunia sihir Hogwarts.",
                R.drawable.hp,
                false,
                "Fantasy",
                4.9f
        ));

        BookRepository.bookList.add(new Book(
                "Rich dad Poor Dad",
                "Robert Kiyosaki",
                "2001",
                "It dismantles the myth that earning a high income makes you wealthy",
                R.drawable.rich_dad,
                false,
                "Personal Finance, Self-Help, and Business",
                4.9f
        ));
    }
}