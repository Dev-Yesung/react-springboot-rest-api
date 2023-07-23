package kr.co.prgrms.clone.artist.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Artist {
    private final Integer id;
    private final String name;
    private final String description;
}
