package com.samatkinson;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.samatkinson.builder.ChatAppBuilder;
import com.samatkinson.data.UserStore;
import org.junit.Test;

import static com.mashape.unirest.http.Unirest.get;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class End2EndChatTest {

    @Test
    public void noChatTextForANewChat() throws Exception {
        WizChatApplication chatApplication = new ChatAppBuilder().build();

        chatApplication.main(new String[]{"server"});

        HttpResponse<JsonNode> jsonResponse = get(chatApplication.url() + "/chat/jason/sookie").asJson();

        assertThat(jsonResponse.getBody().getObject().getString("chat"), is(""));

    }

}
