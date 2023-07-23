package kr.co.prgrms.clone.playlist.repository;

import kr.co.prgrms.clone.playlist.domain.Playlist;
import kr.co.prgrms.clone.song.domain.Song;

import java.util.List;

public interface PlaylistRepository {
    void createPlaylist(Integer userId, String title);

    List<Playlist> findByUserId(Integer userId);

    void addNewSong(Integer songId, Integer playlistId);

    List<Song> findAllSongById(Integer playlistId);
}
