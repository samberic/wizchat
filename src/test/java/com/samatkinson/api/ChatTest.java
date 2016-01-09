package com.samatkinson.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ChatTest {
    @Test
    public void chatMatchesOnlyIfNamesAreExactMatch() throws Exception {
        Chat chat = new Chat(new ArrayList<>(), "bob", "fred");
        assertThat(chat.isBetween("bob", "fred"), is(true));
        assertThat(chat.isBetween("fred", "bob"), is(true));
        assertThat(chat.isBetween("Bob", "fred"), is(false));

    }
}
