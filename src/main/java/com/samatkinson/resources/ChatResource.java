package com.samatkinson.resources;


import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.google.common.base.Optional;
import com.samatkinson.data.Chats;
import com.samatkinson.views.ChatView;
import com.samatkinson.views.ChatroomView;
import org.eclipse.jetty.http.HttpParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.codahale.metrics.MetricRegistry.name;
import static java.util.stream.Collectors.toList;

@Path("/chat/{userOne}/{userTwo}")
@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON})
public class ChatResource {
    private final Meter requests;

    private final Chats chats;
    private final Timer responses;

    public ChatResource(MetricRegistry metrics, Chats chats) {
        requests = metrics.meter("requests");
        responses = metrics.timer(name(ChatResource.class, "responses"));
        this.chats = chats;
    }

    @GET
    public ChatroomView chatBetween(@PathParam("userOne") final Optional<String> userOne,
                                    @PathParam("userTwo") final Optional<String> userTwo) {
        requests.mark();
        final Timer.Context context = responses.time();

        try {
            String userOneName = userOne.get();
            String userTwoName = userTwo.get();
            return new ChatroomView(userOneName, userTwoName, chats.chatBetween(userOneName, userTwoName),
                chats.belongingTo(userOneName)
                    .stream()
                    .map(c -> new ChatView(userOneName, c))
                    .collect(toList()));
        } finally {
            context.stop();
        }

    }

    @POST
    public ChatroomView newMessage(
        @PathParam("userOne") Optional<String> from,
        @PathParam("userTwo") Optional<String> to,
        @FormParam("message") Optional<String> message
    ) {
        requests.mark();
        String formattedMessage = from.get() + ": " + message.get() + "";
        chats.addMessageToChat(from.get(), to.get(), formattedMessage);
        return chatBetween(from, to);

    }

}
