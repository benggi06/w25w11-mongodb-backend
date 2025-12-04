package kr.ac.kumoh.s20220518.w25w11_mongodb_backend.controller

import kr.ac.kumoh.s20220518.w25w11_mongodb_backend.model.Song
import kr.ac.kumoh.s20220518.w25w11_mongodb_backend.service.SongService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = ["http://localhost:5173","https://benggi06.github.io",])
class SongController(
    private val service: SongService
) {
    // Create
    @PostMapping
    fun addSong(
        @RequestBody song: Song
    ): ResponseEntity<Song> {
        val createdSong = service.addSong(song)
        // 201 Created
        // 생성된 리소스의 URI 반환
        return ResponseEntity
            .created(URI("/api/songs/${createdSong.id}"))
            .body(createdSong)
    }

    // Read (Retrieve)
    @GetMapping
    fun getAllSongs(): ResponseEntity<List<Song>> {
        val songs = service.getAllSongs()
        if (songs.isEmpty()) {
            // 204 No Content
            return ResponseEntity.noContent().build()
        }

        // 200 OK
        return ResponseEntity.ok(songs)
    }

    @GetMapping("/{id}")
    fun getSongById(
        @PathVariable id: String
    ): ResponseEntity<Song> {
        val song = service.getSongById(id)

        // 있으면 200 OK, 없으면 404 Not Found
        return song?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/singer/{singer}")
    fun getSongBySinger(
        @PathVariable singer: String
    ): ResponseEntity<List<Song>> {
        val songs = service.getSongBySinger(singer)

        if (songs.isEmpty()) {
            // 204 No Content
            return ResponseEntity.noContent().build()
        }

        // 200 OK
        return ResponseEntity.ok(songs)
    }

    // Update
    @PutMapping("/{id}")
    fun updateSong(
        @PathVariable id: String,
        @RequestBody songDetails: Song
    ): ResponseEntity<Song> {
        val updatedSong = service.updateSong(id, songDetails)

        // 성공시 200 OK와 업데이트된 객체 반환, 실패시 404 Not Found
        return updatedSong?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    // Delete
    @DeleteMapping("/{id}")
    fun deleteSong(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        return if (service.deleteSong(id)) {
            // 204 No Content
            ResponseEntity.noContent().build()
        } else {
            // 404 Not Found
            ResponseEntity.notFound().build()
        }
    }
}