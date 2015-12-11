package com.samatkinson.resources;


import com.google.common.base.Optional;
import com.samatkinson.api.Chat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Path("/chat/{userOne}/{userTwo}")
@Produces(MediaType.APPLICATION_JSON)
public class ChatRoomResource {
    private List<String> messages = new ArrayList<String>();
    private Map<String, Map<String, String>> chats = new HashMap<String, Map<String, String>>();


    @GET
    public Chat chatBetween(@PathParam("userOne") Optional<String> userOne,
                            @PathParam("userTwo") Optional<String> userTwo) {
        return new Chat(String.join("\n", messages));
    }

    @POST
    public void newMessage(
            @PathParam("userOne") Optional<String> from,
            @PathParam("userTwo") Optional<String> to,
            @FormParam("message") Optional<String> message,
            @Context HttpServletRequest httpReques
    ) {

        messages.add(from.get() + ": " + message.get());

    }

}
