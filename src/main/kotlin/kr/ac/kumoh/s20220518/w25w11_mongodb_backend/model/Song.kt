package kr.ac.kumoh.s20220518.w25w11_mongodb_backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "songs")
data class Song(
    @Id val id: String? = null,
    val title: String,
    val singer: String,
    val rating: Int,
    val lyrics: String,
)
