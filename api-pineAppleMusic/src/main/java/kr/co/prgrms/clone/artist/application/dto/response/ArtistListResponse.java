package kr.co.prgrms.clone.artist.application.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ArtistListResponse {
    private final List<ArtistResponse> artists;
}
