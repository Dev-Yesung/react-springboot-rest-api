package kr.co.prgrms.clone.playlist.application.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaylistAddRequest {
    private final Integer songId;
    private final Integer playlistId;

    public PlaylistAddRequest() {
        this.songId = null;
        this.playlistId = null;
    }
}
