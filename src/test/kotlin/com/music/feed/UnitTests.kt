package com.music.feed

import com.music.feed.controller.GenreTest
import com.music.feed.controller.MusicianTest
import com.music.feed.controller.RatingTest
import com.music.feed.controller.SongTest
import com.music.feed.controller.UserTest
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
