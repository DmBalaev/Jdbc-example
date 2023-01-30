package dm.bl.Jdbc.controller;

import dm.bl.Jdbc.entity.Author;
import dm.bl.Jdbc.entity.Book;
import dm.bl.Jdbc.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> allBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book bookById(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @GetMapping("/name/{name}")
    public Book bookByName(@PathVariable String name){
        return bookService.getBook(name);
    }

    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.addNewBook(book);
    }


    @PostMapping("/{id}")
    public void addAuthorToBook(@PathVariable Long id, @RequestBody Author author){
        bookService.addAuthorToBook(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

}
