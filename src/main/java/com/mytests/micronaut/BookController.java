package com.mytests.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Flowable;
import io.reactivex.Single;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller("/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

     
    @Post("/")
    Single<Book> create(@Valid Book book) {
        return Single.fromPublisher(bookRepository.save(book));
    }
    
   
   @Get("/")
   List<Book> all() {
       return bookRepository.findAll().collectList().block(); 
   }

    @Get("/{id}")
    Single<Book> show(Long id) {
        return Single.fromPublisher(bookRepository.findById(id)); 
    }
    
    @Put("/{id}")
    Single<Book> update(@NotNull Long id, @Valid Book book) {
        return Single.fromPublisher(bookRepository.update(book));
    }
    
    @Delete("/{id}")
    Single<HttpResponse<?>> delete(@NotNull Long id) {
        return Single.fromPublisher(bookRepository.deleteById(id))
                .map(deleted -> deleted > 0 ? HttpResponse.noContent() : HttpResponse.notFound());
    }
    
}
