package com.samatkinson.api;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatTest {
    @Test
    public void chatMatchesOnlyIfNamesAreExactMatch() throws Exception {
        Chat chat = new Chat("", "bob", "fred");
        assertThat(chat.isBetween("bob", "fred"), is(true));
        assertThat(chat.isBetween("fred", "bob"), is(true));
        assertThat(chat.isBetween("Bob", "fred"), is(false));

    }
}
