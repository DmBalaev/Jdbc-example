package dm.bl.Jdbc.repository;

import dm.bl.Jdbc.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends Repository<Author> {
    boolean existBookByName(String name);

    Optional<Author> findByName(String name);
}
