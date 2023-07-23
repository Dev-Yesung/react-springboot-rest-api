package kr.co.prgrms.clone.song.repository;

import kr.co.prgrms.clone.artist.domain.Artist;
import kr.co.prgrms.clone.song.domain.Song;

import java.util.List;

public interface SongRepository {
    List<Song> findAll();

    List<Song> findByTitle(String title);

    List<Song> findByGenre(Integer genreId);

    List<Song> findByArtist(List<Artist> artist);
}
