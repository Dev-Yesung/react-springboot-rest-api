package kr.co.prgrms.clone.playlist.application.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaylistCreateRequest {
    private final String userEmail;
    private final String title;

    public PlaylistCreateRequest() {
        this.userEmail = null;
        this.title = null;
    }
}
