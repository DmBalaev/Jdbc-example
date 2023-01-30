package dm.bl.Jdbc.repository;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.entity.Book;

import java.util.Optional;

public interface BookRepository extends Repository<Book>{

    Optional<Book> findByName(String name);

    boolean existBookByName(String name);

    int addAuthorToBook(Long idBook, Author author);

}
