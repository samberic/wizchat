package com.samatkinson.resources;


import com.google.common.base.Optional;
import com.samatkinson.api.Chat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Path("/chat/{userOne}/{userTwo}")
@Produces(MediaType.APPLICATION_JSON)
public class ChatResource {

    List<Chat> chats = new LinkedList<Chat>();

    @GET
    public Chat chatBetween(@PathParam("userOne") final Optional<String> userOne,
                            @PathParam("userTwo") final Optional<String> userTwo) {
        String userOneName = userOne.get();
        String userTwoName = userTwo.get();
        return chats.stream()
                .filter(chat -> chat.isBetween(userOneName, userTwoName))
                .findFirst()
                .orElse(new Chat("", userOneName, userTwoName));
    }

    @POST
    public void newMessage(
            @PathParam("userOne") Optional<String> from,
            @PathParam("userTwo") Optional<String> to,
            @FormParam("message") Optional<String> message
    ) {
        String formattedMessage = from.get() + ": " + message.get() +"\n";
        for (Chat chat : chats) {
            if (chat.isBetween(from.get(), to.get())) {
                chat.addMessage(formattedMessage);
                return;
            }
        }
        chats.add(new Chat(formattedMessage, from.get(), to.get()));

    }

}
