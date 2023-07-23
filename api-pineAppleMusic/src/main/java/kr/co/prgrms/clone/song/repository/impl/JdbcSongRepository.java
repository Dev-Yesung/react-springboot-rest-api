package kr.co.prgrms.clone.song.repository.impl;

import kr.co.prgrms.clone.artist.domain.Artist;
import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.song.domain.Song;
import kr.co.prgrms.clone.song.exception.impl.SongNotFoundException;
import kr.co.prgrms.clone.song.repository.SongRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static kr.co.prgrms.clone.global.error.ErrorCode.NOT_FOUND_SONG;

@Repository
public class JdbcSongRepository implements SongRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String FIND_BY_TITLE = "SELECT * FROM songs WHERE title = :title";
    private static final String FIND_BY_GENRE_ID = "SELECT * FROM songs WHERE genre_id = :genreId";
    private static final String FIND_BY_ARTIST =
            "SELECT B.id, B.title, B.genre_id, B.play_time, B.lyrics, B.url, B.price" +
                    "FROM songs_artists AS A JOIN songs AS B " +
                    "ON A.artist_id = B.id WHERE A.artist_id = :artistId";

    public JdbcSongRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Song> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM songs", songRowMapper);
        } catch (DataAccessException e) {
            throw new SongNotFoundException(NOT_FOUND_SONG);
        }
    }

    @Override
    public List<Song> findByTitle(String title) {
        var paramMap
                = Collections.singletonMap("title", title);
        try {
            return jdbcTemplate.query(FIND_BY_TITLE, paramMap, songRowMapper);
        } catch (DataAccessException e) {
            throw new SongNotFoundException(NOT_FOUND_SONG);
        }
    }

    @Override
    public List<Song> findByGenre(Integer genreId) {
        var paramMap
                = Collections.singletonMap("genreId", genreId);
        try {
            return jdbcTemplate.query(FIND_BY_GENRE_ID, paramMap, songRowMapper);
        } catch (DataAccessException e) {
            throw new SongNotFoundException(NOT_FOUND_SONG);
        }
    }

    @Override
    public List<Song> findByArtist(List<Artist> artist) {
        List<Integer> ids = artist
                .stream()
                .map(Artist::getId)
                .toList();
        try {
            return getSongs(ids);
        } catch (DataAccessException e) {
            throw new SongNotFoundException(NOT_FOUND_SONG);
        }
    }

    private List<Song> getSongs(List<Integer> ids) {
        List<Song> songs = new ArrayList<>();
        for (Integer id : ids) {
            var paramMap = Collections.singletonMap("artistId", id);
            List<Song> response = jdbcTemplate.query(FIND_BY_ARTIST, paramMap, songRowMapper);
            songs.addAll(response);
        }
        return songs;
    }

    RowMapper<Song> songRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int genreId = rs.getInt("genre_id");
        float playTime = rs.getFloat("play_time");
        String lyrics = rs.getString("lyrics");
        String url = rs.getString("url");
        int price = rs.getInt("price");

        return new Song(id, title, genreId, playTime, lyrics, url, price);
    };
}
