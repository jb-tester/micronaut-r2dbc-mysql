package com.mytests.micronaut;

import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.Arrays;

@Singleton
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) { 
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        
    }

    @Transactional 
    Mono<Void> setupData() {
        return Mono.from(authorRepository.save(new Author("Lev Tolstoy")))
                .flatMapMany((author -> bookRepository.saveAll(Arrays.asList(
                        new Book("Anna Karenina", 1000, author),
                        new Book("War And Peace", 4000, author)
                ))))
                .then(Mono.from(authorRepository.save(new Author("Fedor Dostoyevsky"))))
                .flatMapMany((author ->
                        bookRepository.save(new Book("Idiot", 500, author))
                )).then();
    }
}
