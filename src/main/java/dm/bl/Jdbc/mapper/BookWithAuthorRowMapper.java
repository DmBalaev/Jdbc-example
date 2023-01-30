package dm.bl.Jdbc.mapper;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.entity.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookWithAuthorRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author(
                rs.getLong("author_id"),
                rs.getString("author_name"));

        return new Book(
                rs.getLong("book_id"),
                rs.getString("book_name"),
                author
        );
    }
}
