package kr.ac.kumoh.s20220518.w25w11_mongodb_backend.service

import kr.ac.kumoh.s20220518.w25w11_mongodb_backend.model.Song
import kr.ac.kumoh.s20220518.w25w11_mongodb_backend.repository.SongRepository
import org.springframework.stereotype.Service

@Service
class SongService(
    private val repository: SongRepository
) {
    // Create
    fun addSong(song: Song): Song = repository.save(song)

    // Read (Retrieve)
    fun getAllSongs(): List<Song> = repository.findAll()
    fun getSongById(id: String): Song? = repository.findById(id).orElse(null)
    fun getSongBySinger(singer: String): List<Song> = repository.findBySinger(singer)

    // Update
    fun updateSong(id: String, song: Song): Song? {
        val songTarget = repository.findById(id)

        return if (songTarget.isPresent) {
            val oldSong = songTarget.get()
            val updatedSong = oldSong.copy(
                title = song.title,
                singer = song.singer,
                rating = song.rating,
                lyrics = song.lyrics
            )
            repository.save(updatedSong)
        } else {
            null
        }
    }

    // Delete
    fun deleteSong(id: String): Boolean {
        return if (repository.existsById(id)) {
            repository.deleteById(id)
            true
        } else {
            false
        }
    }
}