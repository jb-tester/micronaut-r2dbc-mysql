package com.mytests.micronaut;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@R2dbcRepository(dialect = Dialect.MYSQL) 
public interface AuthorRepository extends ReactiveStreamsCrudRepository<Author, Long> {
    @NonNull
    @Override
    Publisher<Author> findById(@NonNull @NotNull Long aLong); 

    @NonNull
    @Override
    Flux<Author> findAll();
}
