package com.wind.restapp.knight.restws;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.wind.restapp.knight.domain.Knight;
import com.wind.restapp.knight.form.KnightForm;
import com.wind.restapp.knight.form.KnightStatus;
import jetwang.test.RestTest;
import junit.framework.Assert;
import org.junit.Test;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class KnightRestServiceTest extends RestTest {
    @Test
    public void testFindKnight() throws Exception {
        testUpdateKnight();
        WebResource webResource = client().resource("http://localhost:9998/rs/knight").path("quote").path("10").path("1").path("s");
        String s = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        System.out.println(s);
        List<Knight> knights = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<List<Knight>>() {
        });
        Assert.assertNotNull(knights);
        Assert.assertFalse(knights.size() < 1);
    }

    @Test
    public void testGetKnight() throws Exception {
        try {
            client().resource("http://localhost:9998/rs/knight").path("15665656").accept(MediaType.APPLICATION_JSON_TYPE).get(Knight.class);
        } catch (UniformInterfaceException e) {
            Assert.assertEquals(404, e.getResponse().getStatus());
            return;
        }
        Assert.fail("There should be an exception");
    }

    @Test
    public void testCheckKnight() throws Exception {
        try {
            client().resource("http://localhost:9998/rs/knight").path("check").accept(MediaType.APPLICATION_JSON_TYPE).post(new KnightForm());
        } catch (UniformInterfaceException e) {
            Assert.assertEquals(404, e.getResponse().getStatus());
            return;
        }
        Assert.fail("There should be an exception");
    }

    @Test
    public void testUpdateKnight() throws Exception {
        KnightForm form = new KnightForm();
        form.setName("test name");
        form.setStatus(KnightStatus.King);
        form.setKnightId("test knight id");
        ClientResponse clientResponse = client().resource("http://localhost:9998/rs/knight").path("update").entity(form, MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class);
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), clientResponse.getStatus());
        Assert.assertNotNull(clientResponse.getLocation().toURL().toString());
    }
}
