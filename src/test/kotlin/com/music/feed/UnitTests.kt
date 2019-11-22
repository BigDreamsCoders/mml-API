package com.music.feed

import com.music.feed.genre.GenreTest
import com.music.feed.musician.MusicianTest
import com.music.feed.rating.RatingTest
import com.music.feed.song.SongTest
import com.music.feed.user.UserTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        UserTest::class,
        GenreTest::class,
        SongTest::class,
        MusicianTest::class,
        RatingTest::class
)
class UnitTests {

}
