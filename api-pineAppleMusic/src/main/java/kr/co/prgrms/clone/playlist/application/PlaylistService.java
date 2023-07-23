package kr.co.prgrms.clone.playlist.application;

import kr.co.prgrms.clone.playlist.application.dto.request.PlaylistAddRequest;
import kr.co.prgrms.clone.playlist.application.dto.request.PlaylistCreateRequest;
import kr.co.prgrms.clone.playlist.application.dto.response.PlaylistResponse;
import kr.co.prgrms.clone.playlist.domain.Playlist;
import kr.co.prgrms.clone.playlist.exception.impl.PlaylistCreateException;
import kr.co.prgrms.clone.playlist.repository.PlaylistRepository;
import kr.co.prgrms.clone.song.application.dto.response.SongResponse;
import kr.co.prgrms.clone.song.domain.Song;
import kr.co.prgrms.clone.user.domain.User;
import kr.co.prgrms.clone.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.co.prgrms.clone.global.error.ErrorCode.USER_NOT_FOUND;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createPlaylist(PlaylistCreateRequest request) {
        String email = request.getUserEmail();
        String title = request.getTitle();
        Optional<User> response = userRepository.findByEmail(email);
        User user = response.orElseThrow(() -> new PlaylistCreateException(USER_NOT_FOUND));
        Integer userId = user.getId();
        playlistRepository.createPlaylist(userId, title);
    }

    public List<PlaylistResponse> findByUserId(Integer userId) {
        List<Playlist> playlists = playlistRepository.findByUserId(userId);

        return PlaylistResponse.getListOf(playlists);
    }

    public List<SongResponse> findAllSongsById(Integer playlistId) {
        List<Song> songs = playlistRepository.findAllSongById(playlistId);

        return SongResponse.getListOf(songs);
    }

    @Transactional
    public void addNewSong(Integer songId, Integer playlistId) {
        playlistRepository.addNewSong(songId, playlistId);
    }
}
