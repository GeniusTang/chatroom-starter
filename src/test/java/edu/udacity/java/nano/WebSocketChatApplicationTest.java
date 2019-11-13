package edu.udacity.java.nano;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest
//@SpringBootTest
public class WebSocketChatApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void loginTest() throws Exception {
        mvc.perform(get("/index").param("username", "Genius"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("chat"))
        .andExpect(model().attribute("username", equalTo("Genius")));

        mvc.perform(get("/index").param("username", ""))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("chat"))
                .andExpect(model().attribute("username", equalTo("Anonymous")));
    }
}