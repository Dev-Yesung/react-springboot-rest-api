package kr.co.prgrms.clone.song.application;

import kr.co.prgrms.clone.artist.domain.Artist;
import kr.co.prgrms.clone.artist.repository.ArtistRepository;
import kr.co.prgrms.clone.song.application.dto.response.SongResponse;
import kr.co.prgrms.clone.song.domain.Song;
import kr.co.prgrms.clone.song.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongService(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    public List<SongResponse> findAll() {
        List<Song> all = songRepository.findAll();

        return SongResponse.getListOf(all);
    }

    public List<SongResponse> findByTitle(String title) {
        List<Song> songs = songRepository.findByTitle(title);

        return SongResponse.getListOf(songs);
    }

    public List<SongResponse> findByGenre(Integer genreId) {
        List<Song> songs = songRepository.findByGenre(genreId);

        return SongResponse.getListOf(songs);
    }

    public List<SongResponse> findByArtist(String artistName) {
        List<Artist> artists = artistRepository.findByName(artistName);
        List<Song> songs = songRepository.findByArtist(artists);

        return SongResponse.getListOf(songs);
    }
}
