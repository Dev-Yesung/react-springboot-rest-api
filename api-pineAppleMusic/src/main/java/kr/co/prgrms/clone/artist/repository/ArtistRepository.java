package kr.co.prgrms.clone.artist.repository;

import kr.co.prgrms.clone.artist.domain.Artist;

import java.util.List;

public interface ArtistRepository {

    List<Artist> findByName(String artistName);
}
