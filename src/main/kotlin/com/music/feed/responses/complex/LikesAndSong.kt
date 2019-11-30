package com.music.feed.responses.complex

import com.music.feed.domain.Song

data class LikesAndSong(
        var likes : Set<String> = HashSet(),
        var songs : List<Song> =  ArrayList()
) {

}