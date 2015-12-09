package com.samatkinson.resources;


import com.google.common.base.Optional;
import com.samatkinson.api.Chat;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/chat/{userOne}/{userTwo}")
@Produces(MediaType.APPLICATION_JSON)
public class ChatRoomResource {
    private final AtomicLong counter;

    public ChatRoomResource() {
        this.counter = new AtomicLong();
    }

    @GET
    public Chat chatBetween(@PathParam("userOne") Optional<String> userOne,
                       @PathParam("userTwo") Optional<String> userTwo) {
        return new Chat(counter.incrementAndGet(), "");
    }

}
