package com.samatkinson.resources;

import com.samatkinson.data.Chats;
import com.samatkinson.views.ChatroomView;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.base.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ChatResourceTest {

    private Chats chats = new Chats();

    @Before
    public void setup(){
        chats.addMessageToChat("Ben", "Fred", "Hola");
        chats.addMessageToChat("Mike", "Ben", "Hola");
        chats.addMessageToChat("Jon", "Ben", "Hola");
    }

    @Test
    public void returnsAUsersChatsWithUserAsUserOne() throws Exception {
        ChatResource chatResource = new ChatResource(chats);
        ChatroomView chatroomView = chatResource.chatBetween(of("Ben"), of("Jon"));

        assertThat(chatroomView.getChats().size(), is(3));
        chatroomView.getChats().forEach(view -> assertThat(view.getUserOne(), is("Ben")));
    }


}
