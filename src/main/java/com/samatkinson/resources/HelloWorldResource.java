package com.samatkinson.resources;


import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.samatkinson.api.Saying;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final AtomicLong counter;

    public HelloWorldResource() {
        this.counter = new AtomicLong();
    }

    @GET
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), "Hello World");
    }
}
