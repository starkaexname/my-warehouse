package info.riabokon.mywarehouse;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MyWarehouseApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-h2.properties")
@Sql({"/schema.sql", "/data.sql"})
class StatisticsControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getAllTest() throws Exception {
        mvc.perform(get("/statistic").param("start", "00:00-01/01/2020").param("end", "23:59-31/12/2025"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].open", Matchers.is(1)))
                .andExpect(jsonPath("$[0].inProgress", Matchers.is(1)))
                .andExpect(jsonPath("$[0].finished", Matchers.is(1)));
    }

    @Test
    void getAllMissingParamsTest() throws Exception {
        mvc.perform(get("/statistic"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.cause", Matchers.is("MissingServletRequestParameterException")));
    }

    @Test
    void getByDatetimeInvalidRangeTest() throws Exception {
        mvc.perform(get("/statistic").param("start", "23:59-31/12/2025").param("end", "00:00-01/01/2020"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.cause", Matchers.is("TimeRangeException")));
    }
}
