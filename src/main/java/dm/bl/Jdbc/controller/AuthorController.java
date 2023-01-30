package dm.bl.Jdbc.controller;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<Author> allBooks(){
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public Author authorById(@PathVariable Long id){
        return authorService.getAuthor(id);
    }

    @GetMapping("/name/{name}")
    public Author authorByName(@PathVariable String name){
        return authorService.getAuthor(name);
    }

    @PostMapping
    public void addBook(@RequestBody Author author){
        authorService.addNewAuthor(author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
    }

}
