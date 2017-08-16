package com.home.springboot;

import com.home.springboot.dao.Club;
import com.home.springboot.services.ClubService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClubService clubService;

    @Test
    public void initPageReturnPredefinedMessage() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Init page.")));
    }

    @Test
    public void givenPlayer_whenGettingFromServer_thenRightIsFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/player/messi"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString().contains("\"name\":\"messi\",\"position\":\"FW\"");
    }

    @Test
    public void givenClubMock_whenGettingClub_thenRightIsFound() throws Exception {
        Club fcb = new Club("FC Barcelona", "Barcelona", "Spain");
        Mockito.when(clubService.getClub("FC Barcelona")).thenReturn(fcb);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/club/FC Barcelona"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString().contains("\"name\":\"FC Barcelona\"");
    }
}
