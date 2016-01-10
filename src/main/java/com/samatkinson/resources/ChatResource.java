package com.samatkinson.resources;


import com.google.common.base.Optional;
import com.samatkinson.data.Chats;
import com.samatkinson.views.ChatView;
import com.samatkinson.views.ChatroomView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static java.util.stream.Collectors.toList;

@Path("/chat/{userOne}/{userTwo}")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class ChatResource {
    private final Chats chats;

    public ChatResource(Chats chats) {
        this.chats = chats;
    }

    @GET
    public ChatroomView chatBetween(@PathParam("userOne") final Optional<String> userOne,
                                    @PathParam("userTwo") final Optional<String> userTwo) {
        String userOneName = userOne.get();
        String userTwoName = userTwo.get();
        return new ChatroomView(userOneName, userTwoName, chats.chatBetween(userOneName, userTwoName),
                chats.belongingTo(userOneName)
                        .stream()
                        .map(c -> new ChatView(userOneName, c))
                        .collect(toList()));
    }

    @POST
    public ChatroomView newMessage(
            @PathParam("userOne") Optional<String> from,
            @PathParam("userTwo") Optional<String> to,
            @FormParam("message") Optional<String> message
    ) {
        String formattedMessage = from.get() + ": " + message.get() + "";
        chats.addMessageToChat(from.get(), to.get(), formattedMessage);
        return chatBetween(from, to);

    }

}
