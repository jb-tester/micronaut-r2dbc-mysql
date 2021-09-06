package com.mytests.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller("/books")
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Transactional
    @Post("/")
    Mono<Book> create() {
        return Mono.from(
                Mono.from(authorRepository.save(new Author("Anton Chekhov")))
                        .flatMapMany(author -> bookRepository.save(new Book("Three Sisters", 50, author))));

    }


    @Get("/")
    List<Book> all() {
        return bookRepository.findAll().collectList().block();
    }

    @Get("/{id}")
    Mono<Book> show(Long id) {
        return Mono.from(bookRepository.findById(id));
    }


    @Delete("/{id}")
    Mono<HttpResponse<?>> delete(@NotNull Long id) {
        return Mono.from(bookRepository.deleteById(id))
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }

}
