package com.example.library_system.repositories.specs;

import com.example.library_system.entities.Book;
import com.example.library_system.entities.enums.BookStatus;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {
    public static Specification<Book> hasIsbn(String raw) {
        return (root, query, cb) -> {
            String normalized = raw.replace("-", "").replace(" ", "").trim();
            var isbnNoHyphen = cb.function("REPLACE", String.class, root.get("isbn"), cb.literal("-"), cb.literal(""));
            var isbnNoSpace  = cb.function("REPLACE", String.class, isbnNoHyphen, cb.literal(" "), cb.literal(""));
            return cb.equal(cb.lower(isbnNoSpace), normalized.toLowerCase());
        };
    }


    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) -> {
            var db = cb.lower(cb.trim(root.get("title")));
            var p  = "%" + title.trim().toLowerCase() + "%";
            return cb.like(db, p);
        };
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> {
            var db = cb.lower(cb.trim(root.get("author")));
            var p  = "%" + author.trim().toLowerCase() + "%";
            return cb.like(db, p);
        };
    }

    public static Specification<Book> hasPublisher(String publisher) {
        return (root, query, cb) -> {
            var db = cb.lower(cb.trim(root.get("publisher")));
            var p  = "%" + publisher.trim().toLowerCase() + "%";
            return cb.like(db, p);
        };
    }

    public static Specification<Book> hasStatus(BookStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
