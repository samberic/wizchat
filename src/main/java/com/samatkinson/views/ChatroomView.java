package com.samatkinson.views;

import com.samatkinson.api.Chat;
import io.dropwizard.views.View;

import java.util.List;

public class ChatroomView extends View {

    private String userOneName;
    private Chat currentChat;
    private List<ChatView> chats;
    private String userTwoName;


    public ChatroomView(String userOneName, String userTwoName, Chat currentChat, List<ChatView> chats) {
        super("chat.mustache");
        this.userOneName = userOneName;
        this.userTwoName = userTwoName;
        this.currentChat = currentChat;
        this.chats = chats;
    }

    public List<ChatView> getChats(){
        return chats;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }

    public String getCurrentUser() {
        return userOneName;
    }

    public String getOtherUser() {
        return userTwoName;
    }


}
