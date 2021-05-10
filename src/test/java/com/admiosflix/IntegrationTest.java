package com.admiosflix;

import com.admiosflix.dto.MovieDTO;
import com.admiosflix.dto.ResponseDTO;
import com.admiosflix.model.Genre;
import com.admiosflix.repository.MovieRepository;
import com.admiosflix.service.MovieManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {IntegrationTest.Initializer.class})
public class IntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withUsername("integrationUser")
            .withPassword("testPass");

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void contextLoads() {
    }

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieManagementService movieManagementService;

    @Test
    public void testGetMovies() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movies-management-api/v1/movies")).andExpect(status().isOk()).andReturn();
        assertNotNull(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetMovieById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movies-management-api/v1/movies/1")).andExpect(status().isOk()).andReturn();
        MovieDTO movieDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), MovieDTO.class);
        assertNotNull(movieDTO);
    }

    @Test
    public void testDeleteByID() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/movies-management-api/v1/movies/1")).andExpect(status().isOk()).andReturn();
        ResponseDTO responseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertEquals(responseDTO.getObjectId(), null);
        assertEquals(responseDTO.getMessage(), "Movie successfully deleted");
    }

    @Test
    public void testGetByGenre() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movies-management-api/v1/movies/genre/action")).andExpect(status().isOk()).andReturn();
        String jsonMoviesTag = getMovieContent(mvcResult.getResponse().getContentAsString());
        MovieDTO movieDTO = new ObjectMapper().readValue(jsonMoviesTag, MovieDTO.class);

        assertEquals(movieDTO.getGenre().getDescription(), "Action");
    }

    @Test
    public void testCreateMovie() throws Exception {
        MovieDTO movieDTO = MovieDTO.builder().title("Test").cast("Crew").cast("Cast").genre(Genre.builder().id(1).description("Action").build()).rating(5d).releaseDate(new Date()).build();
        MvcResult mvcResult = mockMvc.perform(post("/movies-management-api/v1/movies/")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(new ObjectMapper().writeValueAsString(movieDTO))
                                ).andExpect(status().isCreated()).andReturn();

        ResponseDTO responseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertNotNull(responseDTO.getObjectId());
        assertEquals(responseDTO.getMessage(), "Movie successfully created");
    }

    @Test
    public void testUpdateMovie() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/movies-management-api/v1/movies/1")).andExpect(status().isOk()).andReturn();
        MovieDTO movieToUpdateDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), MovieDTO.class);

        String title = movieToUpdateDTO.getTitle() + " Updated";
        movieToUpdateDTO.setTitle(title);

        MvcResult mvcResultPutRequest = mockMvc.perform(put("/movies-management-api/v1/movies/1")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(new ObjectMapper().writeValueAsString(movieToUpdateDTO))
                                        ).andExpect(status().isOk()).andReturn();

        ResponseDTO responseDTO = new ObjectMapper().readValue(mvcResultPutRequest.getResponse().getContentAsString(), ResponseDTO.class);
        assertNotNull(responseDTO.getObjectId());
        assertEquals(responseDTO.getMessage(), "Movie successfully updated");

        MvcResult mvcResultAfterUpdate = mockMvc.perform(get("/movies-management-api/v1/movies/1")).andExpect(status().isOk()).andReturn();
        MovieDTO movieUpdatedDTO = new ObjectMapper().readValue(mvcResultAfterUpdate.getResponse().getContentAsString(), MovieDTO.class);

        assertEquals(title, movieUpdatedDTO.getTitle());
    }

    private String getMovieContent(String json) {
        return json.substring(json.indexOf("[") + 1, json.indexOf("]"));
    }
}