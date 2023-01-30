package dm.bl.Jdbc.service;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.entity.Book;
import dm.bl.Jdbc.exceptions.DuplicateResourceException;
import dm.bl.Jdbc.exceptions.ResourceNotFoundException;
import dm.bl.Jdbc.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public void addNewBook(Book book){
        if (bookRepository.existBookByName(book.name())){
            throw new DuplicateResourceException(String.format("Book with name=%s exist", book.name()));
        }
        int result = bookRepository.save(book);
        if (result != 1){
            throw new RuntimeException();
        }
        log.info("{}, created",book);
    }

    public Book getBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with id=%s not found", id)));
    }

    public Book getBook(String name){
        return bookRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book with name=%s not found", name)));
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
        log.info("Delete book with id={}", id);
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public void addAuthorToBook(Long idBook, Author author){
        bookRepository.addAuthorToBook(idBook, author);
        log.info("Added author with name={} to book with id={}",author.name(), idBook);
    }

}
