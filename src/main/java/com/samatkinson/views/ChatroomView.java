package com.samatkinson.views;

import com.samatkinson.api.Chat;
import io.dropwizard.views.View;

import java.util.List;

public class ChatroomView extends View {

    private final List<ChatView> allChatsForThisUser;
    private final String currentUser;
    private final Chat currentChat;
    private final String otherUser;


    public ChatroomView(String currentUser, String otherUser, Chat currentChat, List<ChatView> allChatsForThisUser) {
        super("chat.mustache");
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.currentChat = currentChat;
        this.allChatsForThisUser = allChatsForThisUser;
    }

    public List<ChatView> getChats(){
        return allChatsForThisUser;
    }

    public Chat getCurrentChat() {
        return currentChat;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getOtherUser() {
        return otherUser;
    }
}
