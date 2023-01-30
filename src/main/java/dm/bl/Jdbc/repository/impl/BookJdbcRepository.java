package dm.bl.Jdbc.repository.impl;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.entity.Book;
import dm.bl.Jdbc.exceptions.ResourceNotFoundException;
import dm.bl.Jdbc.mapper.BookWithAuthorRowMapper;
import dm.bl.Jdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookJdbcRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Book entity) {
        String sql = """
                INSERT INTO book(name) VALUES(?)
                """;
        return jdbcTemplate.update(sql, entity.name());
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = """
                SELECT book.id AS book_id, book.name AS book_name, author.id AS author_id, author.name AS author_name
                FROM book
                LEFT JOIN books_author ON book.id = book_id
                LEFT JOIN author ON books_author.author_id = author.id
                WHERE book.id = ?
                """ ;

        List<Book> books = jdbcTemplate.query(sql, new BookWithAuthorRowMapper(), id);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public int update(Book entity) {
        String sql = """
                UPDATE book SET name=?
                WHERE id=?
                """;
        return jdbcTemplate.update(sql, entity.name(),entity.id());
    }

    @Override
    public int deleteById(Long id) {
        String sql = """
                DELETE book WHERE id=?
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Book> findAll() {
        String sql = """
                SELECT book.id AS book_id, book.name AS book_name, author.id AS author_id, author.name AS author_name
                FROM book
                LEFT JOIN books_author ON book.id = book_id
                LEFT JOIN author ON books_author.author_id = author.id           
                """ ;

        return jdbcTemplate.query(sql, new BookWithAuthorRowMapper());

    }

    @Override
    public Optional<Book> findByName(String name) {
        String sql = """
                SELECT book.id AS book_id, book.name AS book_name, author.id AS author_id, author.name AS author_name
                FROM book
                LEFT JOIN books_author ON book.id = book_id
                LEFT JOIN author ON books_author.author_id = author.id
                WHERE book.name = ?
                """ ;
        List<Book> books = jdbcTemplate.query(sql, new BookWithAuthorRowMapper(), name);
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public boolean existBookByName(String name) {
        String sql = """
                SELECT EXISTS(SELECT * FROM book WHERE name=?)
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, name));
    }
    public boolean existBookById(Long id) {
        String sql = """
                SELECT EXISTS(SELECT * FROM book WHERE id=?)
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    @Override
    public int addAuthorToBook(Long idBook, Author author) {
        if (!existBookById(idBook)){
            throw new ResourceNotFoundException("Not Found");
        }
        String sql = """
                INSERT INTO books_author(book_id, author_id) 
                SELECT ?, id FROM author WHERE name=?              
                """;
        return jdbcTemplate.update(sql, idBook, author.name());
    }
}
