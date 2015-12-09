package com.samatkinson.builder;

import com.samatkinson.WizChatApplication;
import com.samatkinson.data.UserStore;

public class ChatAppBuilder {
    public ChatAppBuilder withUserStore(UserStore store) {
        return null;
    }

    public WizChatApplication build() {
        return new WizChatApplication();
    }
}
