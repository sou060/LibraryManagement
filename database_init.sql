-- Library Management System - Database Initialization Script

-- Create Database
CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

-- Drop existing tables (if any)
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS authors;

-- Create Authors Table
CREATE TABLE authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(255) NOT NULL,
    author_email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create Books Table
CREATE TABLE book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_name VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    price DOUBLE NOT NULL CHECK (price > 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_book_name (book_name),
    INDEX idx_published_date (published_date)
);

-- Create Book-Author Join Table (Many-to-Many)
CREATE TABLE book_author (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE,
    INDEX idx_author_id (author_id)
);

-- Sample Data - Authors
INSERT INTO authors (author_name, author_email) VALUES
('Stephen King', 'stephen.king@example.com'),
('J.K. Rowling', 'jk.rowling@example.com'),
('George R. R. Martin', 'grr.martin@example.com'),
('J.R.R. Tolkien', 'jrr.tolkien@example.com'),
('Neil Gaiman', 'neil.gaiman@example.com');

-- Sample Data - Books
INSERT INTO book (book_name, published_date, price) VALUES
('The Shining', '1977-01-28', 29.99),
('IT', '1986-09-15', 34.99),
('The Hobbit', '1937-09-21', 24.99),
('The Stand', '1978-10-03', 32.99),
('American Gods', '2001-06-19', 28.99),
('Harry Potter and the Philosopher''s Stone', '1998-06-26', 15.99),
('A Game of Thrones', '1996-08-06', 39.99),
('Misery', '1987-06-08', 26.99),
('The Lord of the Rings', '1954-07-29', 45.99),
('Good Omens', '1990-05-01', 22.99);

-- Sample Data - Book-Author Relationships
INSERT INTO book_author (book_id, author_id) VALUES
(1, 1),  -- The Shining by Stephen King
(2, 1),  -- IT by Stephen King
(3, 4),  -- The Hobbit by J.R.R. Tolkien
(4, 1),  -- The Stand by Stephen King
(5, 5),  -- American Gods by Neil Gaiman
(6, 2),  -- Harry Potter by J.K. Rowling
(7, 3),  -- A Game of Thrones by George R. R. Martin
(8, 1),  -- Misery by Stephen King
(9, 4),  -- The Lord of the Rings by J.R.R. Tolkien
(10, 5); -- Good Omens by Neil Gaiman

-- Create Indexes for Better Query Performance
CREATE INDEX idx_book_published_date ON book(published_date);
CREATE INDEX idx_authors_email ON authors(author_email);

-- Display Summary
SELECT 'Authors created:' as summary;
SELECT COUNT(*) as total_authors FROM authors;

SELECT 'Books created:' as summary;
SELECT COUNT(*) as total_books FROM book;

SELECT 'Book-Author relationships created:' as summary;
SELECT COUNT(*) as total_relationships FROM book_author;

-- Sample Query: Books by Author
SELECT 'Sample: Books by Stephen King' as query;
SELECT b.id, b.book_name, b.published_date, b.price
FROM book b
JOIN book_author ba ON b.id = ba.book_id
JOIN authors a ON ba.author_id = a.id
WHERE a.author_name = 'Stephen King'
ORDER BY b.published_date DESC;

-- Sample Query: Authors by Book
SELECT 'Sample: Authors of The Hobbit' as query;
SELECT a.id, a.author_name, a.author_email
FROM authors a
JOIN book_author ba ON a.id = ba.author_id
JOIN book b ON ba.book_id = b.id
WHERE b.book_name = 'The Hobbit';

