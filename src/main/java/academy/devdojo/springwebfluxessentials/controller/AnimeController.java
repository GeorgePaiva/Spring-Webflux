package academy.devdojo.springwebfluxessentials.controller;

import academy.devdojo.springwebfluxessentials.domain.Anime;
import academy.devdojo.springwebfluxessentials.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animes")
public class AnimeController {
    private final AnimeService animeService;

    @GetMapping
    public Flux<Anime> listAll() {
        return animeService.findAll();
    }

    @GetMapping(path = "{id}")
    public Mono<Anime> findById(@PathVariable int id) {
        return animeService.findById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Anime> save(@Valid @RequestBody Anime anime) { //@Valid add as validacoes que está na class Anime.
        return animeService.save(anime);
    }

    @PutMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable int id, @Valid @RequestBody Anime anime) {
        return animeService.update(anime.withId(id));
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable int id) {
        return animeService.delete(id);
    }

}
