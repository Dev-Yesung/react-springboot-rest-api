package kr.co.prgrms.clone.song.presentation;

import kr.co.prgrms.clone.song.application.SongService;
import kr.co.prgrms.clone.song.application.dto.response.SongResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SongResponse>> findAll() {
        List<SongResponse> all = songService.findAll();

        return ResponseEntity.ok(all);
    }

    @GetMapping("/title/{name}")
    public ResponseEntity<List<SongResponse>> findByTitle(@Validated @PathVariable
                                                          String name) {
        List<SongResponse> response = songService.findByTitle(name);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/{id}")
    public ResponseEntity<List<SongResponse>> findByGenre(@Validated @PathVariable
                                                          Integer id) {
        List<SongResponse> response = songService.findByGenre(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/artists/{name}")
    public ResponseEntity<List<SongResponse>> findByArtist(@Validated @PathVariable
                                                           String name) {
        List<SongResponse> response = songService.findByArtist(name);

        return ResponseEntity.ok(response);
    }
}
