package kr.co.prgrms.clone.playlist.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Playlist {
    private final Integer id;
    private final Integer userId;
    private final String title;
}
