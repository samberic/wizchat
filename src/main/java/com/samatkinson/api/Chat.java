package com.samatkinson.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class Chat {
    private long id;

    @Length(max = 3)
    private String chat;

    public Chat() {
        // Jackson deserialization
    }

    public Chat(long id, String content) {
        this.id = id;
        this.chat = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getChat() {
        return chat;
    }
}
