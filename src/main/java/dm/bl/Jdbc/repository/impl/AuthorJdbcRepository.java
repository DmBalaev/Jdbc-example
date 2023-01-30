package dm.bl.Jdbc.repository.impl;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorJdbcRepository implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Author entity) {
        String sql = """
                INSERT author(name) VALUES(?)
                """;
        return jdbcTemplate.update(sql, entity.name());
    }

    @Override
    public Optional<Author> findById(Long id) {
        String sql = """
                SELECT * FROM author WHERE id=?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Author.class), id)
                .stream().findFirst();
    }

    @Override
    public int update(Author entity) {
        String sql = """
                UPDATE author SET name=?
                """;
        return jdbcTemplate.update(sql, entity.name());
    }

    @Override
    public int deleteById(Long id) {
        String sql = """
                DELETE author WHERE id=?
                """;
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Author> findAll() {
        String sql = """
                SELECT * FROM author
                LIMIT 20
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Author.class));
    }

    @Override
    public boolean existBookByName(String name) {
        String sql = """
                SELECT EXIST(SELECT * FROM author WHERE name=?)
                """;
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, name));
    }

    @Override
    public Optional<Author> findByName(String name) {
        String sql = """
                SELECT * FROM author WHERE name=?
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Author.class), name)
                .stream().findFirst();
    }
}
