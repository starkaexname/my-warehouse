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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MyWarehouseApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-h2.properties")
@Sql({"/schema.sql", "/data.sql"})
class MaintenanceJobControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getAllTest() throws Exception {
        mvc.perform(get("/maintenance-job"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].status", Matchers.is("FINISHED")))
                .andExpect(jsonPath("$[1].status", Matchers.is("IN_PROGRESS")))
                .andExpect(jsonPath("$[2].status", Matchers.is("OPEN")));
    }

    @Test
    void getByOperatorTest() throws Exception {
        mvc.perform(get("/maintenance-job").param("operator", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status", Matchers.is("FINISHED")))
                .andExpect(jsonPath("$[1].status", Matchers.is("OPEN")));
    }

    @Test
    void getByAreaTest() throws Exception {
        mvc.perform(get("/maintenance-job").param("area", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", Matchers.is("IN_PROGRESS")));
    }

    @Test
    void getByDatetimeTest() throws Exception {
        mvc.perform(get("/maintenance-job").param("startTime", "00:00-01/01/2020").param("endTime", "23:59-31/12/2025"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].status", Matchers.is("FINISHED")))
                .andExpect(jsonPath("$[1].status", Matchers.is("IN_PROGRESS")))
                .andExpect(jsonPath("$[2].status", Matchers.is("OPEN")));
    }

    @Test
    void getByDatetimeInvalidRangeTest() throws Exception {
        mvc.perform(get("/maintenance-job").param("startTime", "23:59-31/12/2025").param("endTime", "00:00-01/01/2020"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.cause", Matchers.is("TimeRangeException")));
    }

    @Test
    void getByInvalidOperatorTypeTest() throws Exception {
        mvc.perform(get("/maintenance-job").param("operator", "nonNumeric"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.cause", Matchers.is("MethodArgumentTypeMismatchException")));
    }
}
