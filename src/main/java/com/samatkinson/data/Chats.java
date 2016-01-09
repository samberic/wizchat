package com.samatkinson.data;

import com.samatkinson.api.Chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Chats {

    List<Chat> chats = new ArrayList<>();

    public Chat chatBetween(String userOneName, String userTwoName) {
        return chats.stream()
                .filter(chat -> chat.isBetween(userOneName, userTwoName))
                .findFirst()
                .orElse(new Chat(new ArrayList<>(), userOneName, userTwoName));
    }

    public void addMessageToChat(
            String from, String to, String formattedMessage) {
        for (Chat chat : chats) {
            if (chat.isBetween(from, to)) {
                chat.addMessage(formattedMessage);
                return;
            }
        }
        chats.add(new Chat(new LinkedList<>(Arrays.asList(formattedMessage)), from, to));
    }

    public List<Chat> of(String userOneName) {
        return chats.stream()
                .filter(chat -> chat.containsUser(userOneName))
                .collect(toList());
    }
}
