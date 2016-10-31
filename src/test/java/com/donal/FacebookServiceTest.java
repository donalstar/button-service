package com.donal;

import com.donal.model.PostData;
import com.donal.model.Result;
import com.donal.model.UserData;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.json.JsonObject;
import com.restfb.types.Post;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by donal on 10/30/16.
 */
public class FacebookServiceTest {

    private FacebookService facebookService;

    private FacebookClient facebookClient;

    private Connection<Post> connection;

    @Before
    public void tearUp() {
        facebookClient = Mockito.mock(FacebookClient.class);

        connection = (Connection<Post>) Mockito.mock(Connection.class);

        Mockito.when(connection.getData()).thenReturn(getTestPosts());

        Mockito.when(this.facebookClient.fetchObject("me", JsonObject.class)).thenReturn(getTestUser());

        Mockito.when(this.facebookClient.fetchConnection("me/feed", Post.class)).thenReturn(connection);

        facebookService = new FacebookService();
    }

    @Test
    public void testGetUser() {
        Response response = facebookService.getUser(facebookClient);

        assertNotNull(response);

        UserData result = (UserData) response.getEntity();

        assertEquals("TEST_USER", result.getName());
    }

    @Test
    public void testGetAllPosts() {
        Response response = facebookService.getPosts(null, null, facebookClient);

        List<PostData> results = ((Result) response.getEntity()).getPosts();

        assertNotNull(response);

        assertEquals(2,  results.size());
    }


    @Test
    public void testGetPostsByInvalidDate() {
        Response response = facebookService.getPosts("BOGUS_DATE", null, facebookClient);

        assertEquals(500, response.getStatus());

        assertEquals("Invalid search date BOGUS_DATE", response.getEntity());
//        response.getStatus()

                System.out.println("RESP " + response + " " + response.getStatus());



        System.out.println("res " + response.getEntity());
    }


    private JsonObject getTestUser() {
        return new JsonObject("{ \"name\": \"TEST_USER\" }");
    }

    private List<Post> getTestPosts() {

        List<Post> list = new ArrayList<Post>();

        Post post = new Post();

        post.setName("POST");

        list.add(post);

        post = new Post();

        post.setName("POST2");

        list.add(post);

        return list;
    }

}

