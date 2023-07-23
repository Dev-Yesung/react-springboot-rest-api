package kr.co.prgrms.clone.artist.application;

import kr.co.prgrms.clone.artist.application.dto.response.ArtistListResponse;
import kr.co.prgrms.clone.artist.application.dto.response.ArtistResponse;
import kr.co.prgrms.clone.artist.domain.Artist;
import kr.co.prgrms.clone.artist.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public ArtistListResponse findByName(String name) {
        List<Artist> artists = artistRepository.findByName(name);
        List<ArtistResponse> artistResponse = ArtistResponse.getListOf(artists);

        return new ArtistListResponse(artistResponse);
    }
}
