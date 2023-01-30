package dm.bl.Jdbc.service;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.exceptions.DuplicateResourceException;
import dm.bl.Jdbc.exceptions.ResourceNotFoundException;
import dm.bl.Jdbc.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public  void  addNewAuthor(Author author){
        if (authorRepository.existBookByName(author.name())){
            throw new DuplicateResourceException(String.format("Book with name=%s exist", author.name()));
        }
        int result = authorRepository.save(author);
        if (result != 1){
            throw new RuntimeException();
        }
        log.info("{}, created", author);
    }

    public Author getAuthor(Long id){
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with id=%s not found", id)));
    }

    public Author getAuthor(String name){
        return authorRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Author with name=%s not found", name)));
    }

    public void deleteAuthor(Long id){
        authorRepository.deleteById(id);
        log.info("Delete book with id={}", id);
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
}
