package kr.co.prgrms.clone.song.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Song {
    private final Integer id;
    private final String title;
    private final Integer genreId;
    private final Float playTime;
    private final String lyrics;
    private final String url;
    private final Integer price;
}
