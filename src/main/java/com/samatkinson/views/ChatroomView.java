package com.samatkinson.views;

import com.samatkinson.api.Chat;
import io.dropwizard.views.View;

import java.util.List;

public class ChatroomView extends View {

    private Chat currentChat;
    private List<Chat> chats;


    public ChatroomView(Chat currentChat, List<Chat> chats) {
        super("chat.mustache");
        this.currentChat = currentChat;
        this.chats = chats;
    }

    public List<Chat> getChats(){
        return chats;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }
}
