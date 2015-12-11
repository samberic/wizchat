package com.samatkinson.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Chat {
    @Length(max = 3)
    private String chat;

    public Chat() {
        // Jackson deserialization
    }

    public Chat(String content) {
        this.chat = content;
    }


    @JsonProperty
    public String getChat() {
        return chat;
    }
}
