package kr.co.prgrms.clone.artist.repository.impl;

import kr.co.prgrms.clone.artist.domain.Artist;
import kr.co.prgrms.clone.artist.exception.impl.ArtistNotFoundException;
import kr.co.prgrms.clone.artist.repository.ArtistRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static kr.co.prgrms.clone.global.error.ErrorCode.NOT_FOUND_ARTIST;

@Repository
public class JdbcArtistRepository implements ArtistRepository {
    private static final String FIND_BY_NAME = "SELECT id, name, description FROM artists WHERE name LIKE CONCAT('%', :name, '%')";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcArtistRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Artist> findByName(String name) {
        var paramMap = Collections.singletonMap("name", name);
        try {
            return jdbcTemplate.query(FIND_BY_NAME, paramMap, artistRowMapper);
        } catch (DataAccessException e) {
            throw new ArtistNotFoundException(NOT_FOUND_ARTIST);
        }
    }

    RowMapper<Artist> artistRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");

        return new Artist(id, name, description);
    };
}
