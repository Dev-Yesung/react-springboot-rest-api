package kr.co.prgrms.clone.artist.presentation;

import kr.co.prgrms.clone.artist.application.ArtistService;
import kr.co.prgrms.clone.artist.application.dto.response.ArtistListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/artists")
public class ArtistController {
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<ArtistListResponse> findByName(@RequestParam
                                                         String name) {
        ArtistListResponse response = artistService.findByName(name);

        return ResponseEntity.ok(response);
    }
}
