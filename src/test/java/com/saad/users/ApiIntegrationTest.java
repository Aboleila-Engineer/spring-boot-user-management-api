package com.saad.users;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("demo")
class ApiIntegrationTest {
 @Autowired MockMvc mvc;
 @Autowired UserRepository users;
 
 @BeforeEach void reset(){ users.deleteAll();}
 @Test void servesHome() throws Exception {mvc.perform(get("/")).andExpect(status().isOk());}
 @Test void userLifecycleAndPasswordPrivacy() throws Exception {
 var response=mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"saad.demo\",\"fullName\":\"Demo User\",\"password\":\"example-only-123\"}"))
 .andExpect(status().isCreated()).andExpect(jsonPath("$.password").doesNotExist()).andExpect(jsonPath("$.passwordHash").doesNotExist()).andReturn();
 User user=users.findAll().get(0);assertThat(user.passwordHash).startsWith("$2");assertThat(user.passwordHash).doesNotContain("example-only");long id=user.id;
 mvc.perform(get("/api/users/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("saad.demo"));
 mvc.perform(put("/api/users/"+id).contentType(MediaType.APPLICATION_JSON).content("{\"fullName\":\"Updated User\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.fullName").value("Updated User"));
 mvc.perform(delete("/api/users/"+id)).andExpect(status().isNoContent());
 mvc.perform(get("/api/users/"+id)).andExpect(status().isNotFound());
 }
 @Test void rejectsInvalidAndDuplicateUsers() throws Exception {
 mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("{}")).andExpect(status().isBadRequest());
 String data="{\"username\":\"tester\",\"fullName\":\"Tester\",\"password\":\"example-only-123\"}";
 mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(data)).andExpect(status().isCreated());
 mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(data)).andExpect(status().isConflict());
 }
 @Test void rejectsMalformedJson() throws Exception {mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("{")).andExpect(status().isBadRequest());}
}
