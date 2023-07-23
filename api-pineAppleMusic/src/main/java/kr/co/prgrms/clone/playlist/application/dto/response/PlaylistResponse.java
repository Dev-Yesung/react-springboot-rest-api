package kr.co.prgrms.clone.playlist.application.dto.response;

import kr.co.prgrms.clone.playlist.domain.Playlist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PlaylistResponse {
    private final Integer id;
    private final String title;

    public static List<PlaylistResponse> getListOf(List<Playlist> playlists) {
        return playlists.stream()
                .map(response -> new PlaylistResponse(response.getId(), response.getTitle()))
                .toList();
    }
}
