package academy.devdojo.springwebfluxessentials.service;

import academy.devdojo.springwebfluxessentials.domain.Anime;
import academy.devdojo.springwebfluxessentials.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Flux<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Mono<Anime> findById(int id) {
        return animeRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    private Mono<Anime> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found."));
    }

    public Mono<Anime> save(Anime anime) {
        return animeRepository.save(anime);
    }

    public Mono<Void> update(Anime anime) {
        return animeRepository.findById(anime.getId())
                .map(animeFound -> anime.withId(animeFound.getId())) //withId() do lombok, retorna um objeto.
                .flatMap(animeRepository::save)
                .thenEmpty(Mono.empty());
    }

    public Mono<Void> delete(int id) {
        return animeRepository.findById(id)
                .flatMap(animeRepository::delete);
    }
}
