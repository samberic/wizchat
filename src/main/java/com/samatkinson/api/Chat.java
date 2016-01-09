package com.samatkinson.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class Chat {
    private List<String> chat;
    public String userOne;
    public String userTwo;

    public Chat() {
        // Jackson deserialization
    }

    public Chat(List<String> content, String userOne, String userTwo) {
        this.chat = content;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }

    public String getUserOne() {
        return userOne;
    }

    public String getUserTwo() {
        return userTwo;
    }


    @JsonProperty
    public List<String> getChat() {
        return chat;
    }

    public void addMessage(String s) {
        chat.add(s);
    }

    public boolean isBetween(String userOneName, String userTwoName) {
        return containsUser(userOneName) && containsUser(userTwoName);
    }

    public boolean containsUser(String userOneName) {
        return userOne.equals(userOneName) || userTwo.equals(userOneName);
    }

    public String getOtherUser(String userOneName) {
        return userOne.equals(userOneName) ? userTwo : userOne;
    }
}
