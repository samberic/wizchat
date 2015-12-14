package com.samatkinson.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Chat {
    private String chat;
    public String userOne;
    public String userTwo;

    public Chat() {
        // Jackson deserialization
    }

    public Chat(String content, String userOne, String userTwo) {
        this.chat = content;
        this.userOne = userOne;
        this.userTwo = userTwo;
    }


    @JsonProperty
    public String getChat() {
        return chat;
    }

    public void addMessage(String s) {
        chat += s;
    }

    public boolean isBetween(String s, String s1) {
        return userOne.equals(s) && userTwo.equals(s1) || userOne.equals(s1) && userTwo.equals(s);
    }
}
