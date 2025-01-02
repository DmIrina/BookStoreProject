package com.example.bookshop.controllers;

import com.example.bookshop.models.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.storage.FileSystemStorageService;
import com.example.bookshop.storage.StorageFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BookController {
    private static Logger LOG = LoggerFactory.getLogger(BookController.class);

    private BookRepository bookRepository;
    private FileSystemStorageService storageService;

    @Autowired
    public void setStorageService(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/books")
    public String list(@PageableDefault(size = 8, direction = Sort.Direction.ASC, sort = "price") Pageable pageable, Model model) {
        model.addAttribute("books", bookRepository.findAll(pageable));
//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(BookController.class, "serveFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));
        LOG.info("All books");
        return "book/books";
    }

    /**
     * Save book to database.
     *
     * @param book
     * @return
     */
    @PostMapping(value = "book")
    public String saveBook(Book book, MultipartFile file, RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        book.setCoverImage(file.getOriginalFilename());
        bookRepository.save(book);

        LOG.info("Save book: " + book);
        return "redirect:/book/" + book.getId();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * View a specific book by its id.
     *
     * @param id
     * @param model
     * @return
     */

    @RequestMapping("book/{id}")
    public String showBook(@PathVariable Long id, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("book", optionalBook.get());
            LOG.info("Displaying book with ID: " + id);
        } else {
            LOG.error("Book not found with ID: " + id);
            return "error/book-not-found"; // Redirect or show a not found page
        }
        return "book/bookshow";
    }

    @RequestMapping("book/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            model.addAttribute("book", optionalBook.get());
            LOG.info("Editing book with ID: " + id);
        } else {
            LOG.error("Book not found with ID: " + id);
            return "error/book-not-found"; // Redirect or show a not found page
        }
        return "book/bookform";
    }


//    @RequestMapping("book/{id}")
//    public String showBook(@PathVariable Long id, Model model) {
//        model.addAttribute("book", bookRepository.findById(id));
//        LOG.info("Book id: " + id);
//        return "book/bookshow";
//    }
//
//    @RequestMapping("book/edit/{id}")
//    public String edit(@PathVariable Long id, Model model) {
//        model.addAttribute("book", bookRepository.findById(id));
//        LOG.info("Edited book id: " + id);
//        return "book/bookform";
//    }

    /**
     * New book.
     *
     * @param model
     * @return
     */
    @RequestMapping("book/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "book/bookform";
    }


    /**
     * Delete book by its id.
     *
     * @param id
     * @return
     */
    @RequestMapping("book/delete/{id}")
    public String delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        LOG.info("Deleted book id : " + id);
        return "redirect:/books";
    }
}
