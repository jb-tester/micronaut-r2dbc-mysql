package com.mytests.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller("/authors")
public class AuthorController {
    private final AuthorRepository repository;
    private final AuthorService service;
    public AuthorController(AuthorRepository repository, AuthorService service) {
        this.repository = repository;
        this.service = service;
    }
    /*@Post
    Flowable<Void> setUp(){
        return Flowable.fromPublisher(service.setupData());
    }
    @Get
    Flowable<Author> all() { 
        
        return Flowable.fromPublisher(repository.findAll());
    }*/
    @Post
    Mono<Void> setUp(){
        return service.setupData();
    }
    @Get
    Flux<Author> all() {

        return repository.findAll();
    }

    @Get("/{id}")
    Author get(Long id) { 
        return Mono.from(repository.findById(id)).block();
    }
}
