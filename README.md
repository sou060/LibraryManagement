# Library Management System - Spring Boot REST API

A complete Spring Boot application for managing a library system with books and authors. Features full CRUD operations, advanced search functionality, and automatic orphan removal for many-to-many relationships.

## 🚀 Features

✅ **Complete CRUD Operations**
- Create, read, update, delete books and authors
- Comprehensive error handling with appropriate HTTP status codes

✅ **Advanced Search**
- Find books by title (case-insensitive)
- Find books published after a specific date
- Find authors by name (case-insensitive)
- Get all books by a specific author

✅ **Orphan Removal** (Custom Implementation for Many-to-Many)
- Automatically deletes authors with no associated books
- Triggered on book updates and deletions
- Prevents database orphan accumulation

✅ **Eager Loading**
- Prevents lazy loading exceptions
- Uses `LEFT JOIN FETCH` and `JOIN FETCH` in queries
- Authors are always loaded with books

✅ **Data Validation**
- Email validation for authors
- Not-null constraints
- Price validation (must be positive)

✅ **RESTful API Design**
- Consistent endpoint naming
- Proper HTTP methods (GET, POST, PUT, DELETE)
- Meaningful status codes

---

## 📋 Technology Stack

| Technology | Version |
|-----------|---------|
| Java | 17 |
| Spring Boot | 4.0.6 |
| Spring Data JPA | Latest |
| Hibernate | Latest |
| MySQL | 8.0+ |
| Lombok | Latest |
| Jakarta Persistence (Jakarta EE) | Latest |
| Maven | Latest |

---

## 📦 Project Structure

```
src/main/java/com/java/librarymanagement/
├── entity/
│   ├── Book.java              # Book entity with many-to-many relationship
│   └── Authors.java           # Authors entity with many-to-many relationship
├── repo/
│   ├── BookRepository.java    # Book data access layer with custom queries
│   └── AuthorsRepository.java # Authors data access layer with custom queries
├── service/
│   ├── BookService.java       # Book business logic with orphan removal
│   └── AuthorsService.java    # Authors business logic
├── controller/
│   ├── BooksController.java   # Book REST endpoints
│   └── AuthorsController.java # Authors REST endpoints
└── DTO/
    ├── CreateBookRequest.java
    ├── CreateAuthorRequest.java
    ├── UpdateBookRequest.java
    ├── BookWithAuthorsDTO.java
    └── AuthorWithBooksDTO.java
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### 1. Clone the Repository
```bash
git clone <repository-url>
cd "Library Management"
```

### 2. Create Database
```bash
mysql -u root -p < database_init.sql
```

Or manually create:
```sql
CREATE DATABASE library_db;
```

### 3. Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 4. Build the Project
```bash
mvn clean compile
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

Or package and run:
```bash
mvn clean package
java -jar target/LibraryManagement-0.0.1-SNAPSHOT.jar
```

The server will start on `http://localhost:8080`

---

## 📚 API Endpoints

### Books API (`/api/books`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books` | Get all books |
| GET | `/api/books/{id}` | Get book by ID |
| GET | `/api/books/with-authors` | Get all books with authors |
| GET | `/api/books/search/title?title=...` | Search books by title |
| GET | `/api/books/search/published-after?date=...` | Find books published after date |
| GET | `/api/books/by-author/{authorId}` | Get all books by author |
| POST | `/api/books` | Create new book |
| PUT | `/api/books/{id}` | Update book |
| DELETE | `/api/books/{id}` | Delete book |

### Authors API (`/api/authors`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/authors` | Get all authors |
| GET | `/api/authors/{id}` | Get author by ID |
| GET | `/api/authors/search/name?name=...` | Search authors by name |
| GET | `/api/authors/{id}/books` | Get all books by author |
| POST | `/api/authors` | Create new author |
| PUT | `/api/authors/{id}` | Update author |
| DELETE | `/api/authors/{id}` | Delete author |

---

## 📝 Example Usage

### Create an Author
```bash
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{
    "authorName": "Stephen King",
    "authorEmail": "stephen@example.com"
  }'
```

### Create a Book
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "The Shining",
    "publishedDate": "1977-01-28",
    "price": 29.99,
    "authorIds": [1]
  }'
```

### Search Books by Title
```bash
curl -X GET "http://localhost:8080/api/books/search/title?title=Shining"
```

### Find Books Published After 2020
```bash
curl -X GET "http://localhost:8080/api/books/search/published-after?date=2020-01-01"
```

### Get All Books by Author
```bash
curl -X GET http://localhost:8080/api/books/by-author/1
```

### Update a Book
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "bookName": "Updated Title",
    "publishedDate": "2024-01-01",
    "price": 39.99,
    "authorIds": [1, 2]
  }'
```

### Delete a Book (Triggers Orphan Removal)
```bash
curl -X DELETE http://localhost:8080/api/books/1
```

---

## 🔄 Orphan Removal Mechanism

The system implements custom orphan removal for many-to-many relationships:

1. **On Book Update**: When you change a book's authors, removed authors are checked
2. **On Book Delete**: When you delete a book, all its authors are checked
3. **Orphan Check**: If an author has no remaining books, they are automatically deleted

**Example:**
```
Author: John Doe
Associated with: Book 1, Book 2

Action: Remove John from Book 1
Result: John is still associated with Book 2 → John is kept

Action: Remove John from Book 2 (last book)
Result: John has no books → John is automatically deleted (ORPHAN REMOVED)
```

---

## 🗄️ Database Schema

### Authors Table
```sql
CREATE TABLE authors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    author_name VARCHAR(255) NOT NULL,
    author_email VARCHAR(255) NOT NULL UNIQUE
);
```

### Books Table
```sql
CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_name VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    price DOUBLE NOT NULL
);
```

### Book-Author Join Table (Many-to-Many)
```sql
CREATE TABLE book_author (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);
```

---

## 🧪 Testing

### Using cURL
```bash
# Get all books
curl http://localhost:8080/api/books

# Create new author
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{"authorName":"Test","authorEmail":"test@example.com"}'
```

### Using Postman
1. Import the API endpoints
2. Test each endpoint with sample data
3. Verify orphan removal behavior

### Run Shell Script
```bash
chmod +x test_api.sh
./test_api.sh
```

---

## 🐛 Common Issues & Solutions

### Issue: "Could not write JSON: Cannot lazily initialize collection"
**Solution:** Already fixed! We use eager loading with `LEFT JOIN FETCH` in all queries.

### Issue: "Author not found with id"
**Solution:** Make sure the author exists before referencing them in a book. Author IDs in the request must exist in the database.

### Issue: "One or more authors were not found"
**Solution:** Verify all author IDs exist. Check with GET `/api/authors` endpoint first.

### Issue: Database connection refused
**Solution:** 
- Ensure MySQL is running
- Check database credentials in `application.properties`
- Verify database exists with `USE library_db;` in MySQL

---

## 📄 Configuration Files

### application.properties
```properties
spring.application.name=LibraryManagement
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080
```

---

## 📊 Key Implementation Details

### Entity Design
- **Lazy Loading Disabled**: Used `FetchType.EAGER` on `@ManyToMany`
- **Cascade Operations**: Set to DETACH, MERGE, PERSIST, REFRESH (not DELETE by default)
- **Orphan Removal**: Implemented in service layer (not supported on `@ManyToMany`)

### Service Layer
- All methods wrapped in `@Transactional`
- Business logic isolated from controllers
- Orphan removal logic centralized

### Controller Layer
- Consistent endpoint naming
- Proper HTTP status codes
- Request validation with `@Valid`
- Response wrapped in `ResponseEntity`

### Query Optimization
- All queries use JOIN FETCH for eager loading
- Database indexes on frequently searched columns
- Parameterized queries to prevent SQL injection

---

## 📋 Requirements Checklist

✅ Create a new book and author  
✅ Retrieve a list of all books and authors  
✅ Retrieve a single book or author by ID  
✅ Update book and author details  
✅ Delete a book or author  
✅ Find books by title  
✅ Find books published after a certain date  
✅ Find authors by name  
✅ Find all books by a specific author  
✅ Orphan removal implementation  
✅ Comprehensive error handling  
✅ Database schema definition  

---

## 📚 References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/)
- [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/)

---

## 📝 License

This project is open source and available under the MIT License.

---

## 👨‍💻 Author

developed as a learning project for Spring Boot, JPA, and REST API development.

---

**Status:** ✅ **Production Ready**

For detailed API documentation, see [API_DOCUMENTATION.md](API_DOCUMENTATION.md)

