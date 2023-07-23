package kr.co.prgrms.clone.artist.application.dto.response;

import kr.co.prgrms.clone.artist.domain.Artist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ArtistResponse {
    private final String name;
    private final String description;

    public static List<ArtistResponse> getListOf(List<Artist> artists) {
        return artists.stream()
                .map(artist -> {
                    String artistName = artist.getName();
                    String artistDescription = artist.getDescription();

                    return new ArtistResponse(artistName, artistDescription);
                })
                .toList();
    }
}
