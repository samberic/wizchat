package com.samatkinson.data;

import com.google.common.base.Optional;
import com.samatkinson.api.Chat;

import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;

public class Chats {

    List<Chat> chats = new ArrayList<>();

    public Chat chatBetween(String userOneName, String userTwoName) {
        return chats.stream()
                .filter(chat -> chat.isBetween(userOneName, userTwoName))
                .findFirst()
                .orElse(new Chat("", userOneName, userTwoName));
    }

    public List<Chat> asList() {
        return chats;
    }

    public void addMessageToChat(
            String from, String to, String formattedMessage) {
        for (Chat chat : chats) {
            if (chat.isBetween(from, to)) {
                chat.addMessage(formattedMessage);
                return;
            }
        }
        chats.add(new Chat(formattedMessage, from, to));
    }
}
