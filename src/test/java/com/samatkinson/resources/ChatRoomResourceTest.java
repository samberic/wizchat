package com.samatkinson.resources;

import com.google.common.base.Optional;
import org.junit.Test;

import static com.google.common.base.Optional.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class ChatRoomResourceTest {

    private final String danMikeChat = "Chat between Mike and Dan";
    private final Optional<String> fred = of("Fred");
    private final Optional<String> jan = of("Jan");
    private final String fredJonChat = "Chat with Fred and Jan";
    private Optional<String> mike = of("Mike");
    private Optional<String> dan = of("Dan");

    @Test
    public void test() throws Exception {
        ChatResource resource = new ChatResource();
        resource.newMessage(
                mike,
                dan,
                of(danMikeChat));

        resource.newMessage(
                fred,
                jan,
                of(fredJonChat));

        assertThat(resource.chatBetween(mike, dan).getCurrentChat().getChat().trim(), is("Mike: " + danMikeChat));
        assertThat(resource.chatBetween(dan, mike).getCurrentChat().getChat().trim(), is("Mike: " + danMikeChat));
        assertThat(resource.chatBetween(fred, jan).getCurrentChat().getChat().trim(), is("Fred: " + fredJonChat));
        assertThat(resource.chatBetween(jan, fred).getCurrentChat().getChat().trim(), is("Fred: " + fredJonChat));


    }
}
