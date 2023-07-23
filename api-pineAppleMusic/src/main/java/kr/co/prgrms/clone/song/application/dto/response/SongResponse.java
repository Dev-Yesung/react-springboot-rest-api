package kr.co.prgrms.clone.song.application.dto.response;

import kr.co.prgrms.clone.song.domain.Genre;
import kr.co.prgrms.clone.song.domain.Song;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SongResponse {
    private final Integer id;
    private final String title;
    private final String genre;
    private final Float playTime;
    private final String lyrics;
    private final String url;
    private final Integer price;

    public static List<SongResponse> getListOf(List<Song> songs) {
        return songs.stream()
                .map(song -> {
                    Integer id = song.getId();
                    String songTitle = song.getTitle();
                    String genre = Genre.resolve(song.getGenreId());
                    Float playTime = song.getPlayTime();
                    String lyrics = song.getLyrics();
                    String url = song.getUrl();
                    Integer price = song.getPrice();

                    return new SongResponse(id, songTitle, genre, playTime,
                            lyrics, url, price);
                })
                .toList();
    }
}
