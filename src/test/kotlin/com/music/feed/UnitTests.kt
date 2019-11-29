package com.music.feed

import com.music.feed.controller.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        UserTest::class,
        GenreTest::class,
        SongTest::class,
        MusicianTest::class,
        RatingTest::class,
        FavoriteTest::class
)
class UnitTests {

}
