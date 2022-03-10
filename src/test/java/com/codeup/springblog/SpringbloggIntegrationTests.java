package com.codeup.springblog;

import com.codeup.springblog.models.User;
import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringblogApplication.class)
@AutoConfigureMockMvc
public class SpringbloggIntegrationTests {
    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userDao;

    @Autowired
    PostRepository postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        testUser = userDao.findByUsername("testUser");

        // Creates the test user if not exists
        if (testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }
        //Throws post request to login and expects a redirect to index page after login.
        httpSession = this.mvc.perform(post("/login").with(csrf())
                        .param("username", "testUser")
                        .param("password", "password"))
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/posts"))
                .andReturn()
                .getRequest()
                .getSession();
    }

    // SANITY TESTS

    @Test
    public void contextLoads() {
        // Sanity Test, just to make sure the MVC bean is working.
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() {
        // It makes sure the returned session is not null.
        assertNotNull(httpSession);
    }

    // CRUD FUNCTIONALITY TESTING


    // CREATE
    @Test
    public void testCreatePost() throws Exception {
        // Makes a post request to /posts/create and expects redirection to the post.
        this.mvc.perform(MockMvcRequestBuilders.get("/posts/create").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("title", "Eats")
                        .param("body", "BBQ!"))
                .andExpect(status().is3xxRedirection());
    }

    // READ
    @Test
    public void testShowPost() throws Exception {
        Post existingPost = postDao.findAll().get(0);

        // Makes a Get request to /ads/{id} and expect a redirection to the Ad show page
        this.mvc.perform(MockMvcRequestBuilders.get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                // Test the dynamic content of the page
                .andExpect(MockMvcResultMatchers.content().string(containsString(existingPost.getBody())));
    }

    @Test
    public void testPostsIndex() throws Exception {
//        List<Post> posts = postDao.findAll();
        Post posts = postDao.findAll().get(0);
        this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk())
                // checking static content of the page.
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("All the Posts")));
        for (Post post : posts) {
            this.mvc.perform(MockMvcRequestBuilders.get("/posts"))
                    // checking dynamic content of the page.
                    .andExpect(MockMvcResultMatchers.content().string(containsString(post.getTitle())));
//                .andExpect(MockMvcResultMatchers.content().string(containsString(post.getBody())));
        }
    }
        // UPDATE

        @Test
        public void testEditPost() throws Exception {
            Post existingPost = postDao.findAll().get(0);

            // Makes a post request to /posts/{id}/edit and expect a redirection to the Post show page
            this.mvc.perform(post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("title", "edited title"))
                    .andExpect(status().is3xxRedirection());
            // Makes a get request to /posts/{id}/edit and expect a redirection to the post show page.
            this.mvc.perform(get("/posts/" + existingPost.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("edited title")));
        }
    }

    // DELETE
    @Test
    public void testDeletePost() throws Exception {
        if(postDao.findPostByTitle("testPost") == null) {
            this.mvc.perform(post("/posts/create").with(csrf())
                            .session((MockHttpSession) httpSession)
                            .param("title", "testPost"))
                    .andExpect(status().is3xxRedirection());
        }
        Post postToDelete = postDao.findPostByTitle("testPost");
        this.mvc.perform(get("/posts/" + postToDelete.getId() + "/delete").with(csrf())
                        .session((MockHttpSession) httpSession)
                        .param("id", String.valueOf(postToDelete.getId())))
                .andExpect(status().is3xxRedirection());
    }
}
