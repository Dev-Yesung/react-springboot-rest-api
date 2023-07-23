package kr.co.prgrms.clone.song.domain;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.song.exception.impl.NoValidGenreException;

import java.util.Arrays;

public enum Genre {
    JAZZ(1, "Jazz"),
    RNB(2, "R&B"),
    CLASSIC(3, "Classic"),
    HIPHOP(4, "Hip-Hop"),
    ROCK(5, "Rock"),
    POP(6, "Pop"),
    ACOUSTIC(7, "Acoustic"),
    ALTERNATIVE(8, "Alternative");
    private final Integer id;
    private final String genre;

    Genre(Integer id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Integer getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public static String resolve(Integer id) {
        return Arrays.stream(values())
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoValidGenreException(ErrorCode.NO_VALID_GENRE))
                .getGenre();
    }
}
