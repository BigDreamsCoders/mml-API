package com.music.feed.rating

import com.music.feed.BaseTest
import org.junit.Assert
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

class RatingTest : BaseTest(){
@Test
    fun getAllFavorites() {
        val uri = "rating/favorites"

        val mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(base + uri)
                .header("Authorization", defaultToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn()
        val status = mvcResult.response.status
        val content = mvcResult.response.contentAsString
        Assert.assertEquals(200, status)
        Assert.assertNotNull(content)
    }
}