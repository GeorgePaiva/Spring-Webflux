package academy.devdojo.springwebfluxessentials.util;

import academy.devdojo.springwebfluxessentials.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToSaved() {
        return Anime.builder()
                .name("Tensei Shitara Slime  Datta Ken")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shitara Slime  Datta Ken")
                .build();
    }

    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .id(1)
                .name("Tensei Shitara Slime  Datta Ken 2")
                .build();
    }
}
