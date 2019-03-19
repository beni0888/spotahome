package com.spotahome.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotahome.application.GetHomes;
import com.spotahome.application.GetHomesQuery;
import com.spotahome.domain.Home;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GetHomes getHomes;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenListHomesApiIsCalled_thenItReturnsAListOfHomes() throws Exception {
        List<Home> homes = asList(
                aHome(1L, "home 1", "Madrid", "http://foo.com/home1", "http://foo.com/home1"),
                aHome(2L, "home 2", "Madrid", "http://foo.com/home2", "http://foo.com/home2")
        );
        GetHomesQuery query = new GetHomesQuery("title");
        when(getHomes.execute(query)).thenReturn(homes);

        mvc.perform(get("/api/homes?sortedBy=title"))
                .andExpect(jsonPath("$[*]", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(homes.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(homes.get(0).getTitle())))
                .andExpect(jsonPath("$[0].city", is(homes.get(0).getCity())))
                .andExpect(jsonPath("$[0].url", is(homes.get(0).getUrl().toString())))
                .andExpect(jsonPath("$[0].picture", is(homes.get(0).getPicture().toString())))

                .andExpect(jsonPath("$[1].id", is(homes.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].title", is(homes.get(1).getTitle())))
                .andExpect(jsonPath("$[1].city", is(homes.get(1).getCity())))
                .andExpect(jsonPath("$[1].url", is(homes.get(1).getUrl().toString())))
                .andExpect(jsonPath("$[1].picture", is(homes.get(1).getPicture().toString())))

                .andExpect(status().isOk());

    }

    @Test
    public void whenDownloadApiIsCalled_thenItDownloadsAJsonFile() throws Exception {
        List<Home> homes = asList(
                aHome(1L, "home 1", "Madrid", "http://foo.com/home1", "http://foo.com/home1"),
                aHome(2L, "home 2", "Madrid", "http://foo.com/home2", "http://foo.com/home2")
        );
        GetHomesQuery query = new GetHomesQuery("title");
        when(getHomes.execute(query)).thenReturn(homes);

        MvcResult result = mvc.perform(get("/api/download?sortedBy=title"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(objectMapper.writeValueAsString(homes));
        assertThat(result.getResponse().getContentType()).isEqualTo(APPLICATION_OCTET_STREAM.toString());
    }

    private Home aHome(Long id, String title, String city, String url, String picture) {
        return Home.create(id, title, city, URI.create(url), URI.create(picture));
    }
}