package kr.co.prgrms.clone.playlist.presentation;

import kr.co.prgrms.clone.playlist.application.PlaylistService;
import kr.co.prgrms.clone.playlist.application.dto.request.PlaylistAddRequest;
import kr.co.prgrms.clone.playlist.application.dto.request.PlaylistCreateRequest;
import kr.co.prgrms.clone.playlist.application.dto.response.PlaylistResponse;
import kr.co.prgrms.clone.song.application.dto.response.SongResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/playlists")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Void> createPlaylist(@RequestBody PlaylistCreateRequest request) {
        playlistService.createPlaylist(request);

        return ResponseEntity
                .status(CREATED)
                .build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<PlaylistResponse>> findByUserId(@PathVariable
                                                               Integer id) {
        List<PlaylistResponse> response = playlistService.findByUserId(id);

        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<List<SongResponse>> findAllSongsById(@PathVariable
                                                               Integer playlistId) {
        List<SongResponse> response = playlistService.findAllSongsById(playlistId);

        return ResponseEntity
                .ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<Void> addNewSong(@RequestBody PlaylistAddRequest request) {
        Integer playlistId = request.getPlaylistId();
        Integer songId = request.getSongId();
        playlistService.addNewSong(songId, playlistId);

        return ResponseEntity
                .status(CREATED)
                .build();
    }
}
