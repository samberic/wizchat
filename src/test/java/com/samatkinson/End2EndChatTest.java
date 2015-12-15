package com.samatkinson;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class End2EndChatTest {

    private static WizChatApplication chatApplication;

    @Before
    public void setup() throws Exception {
        chatApplication = startServer();
    }

    @After
    public void after() throws Exception {
        chatApplication.shutdown();
    }

    @Test
    public void noChatTextForPeopleWhoHaventSpokenBefore() throws Exception {
        HttpResponse<JsonNode> jsonResponse = get(chatApplication.url() + "/chat/jason/sookie").asJson();

        assertThat(extractChat(jsonResponse), is(""));
    }

    @Test
    public void chatReturnsMessagesThatHaveBeenSent() throws Exception {
        String message = "Hey Sookie";
        post(chatApplication.url() + "/chat/jason/sookie")
                .field("message", message)
                .asJson();

        HttpResponse<JsonNode> jsonResponse = get(chatApplication.url() + "/chat/jason/sookie").asJson();

        assertThat(extractChat(jsonResponse), is("jason: " + message));

    }


    @Test
    public void userWithMultipleChatsCanAccessAllChats() throws Exception {
        String message = "This a chat between bob and sue";
        post(chatApplication.url() + "/chat/bob/sue")
                .field("message", message)
                .asJson();

        String message2 = "This is a chat between Mike and Bob";

        post(chatApplication.url() + "/chat/mike/bob")
                .field("message", message2)
                .asJson();

        HttpResponse<JsonNode> jsonResponseBobSue = get(chatApplication.url() + "/chat/bob/sue").asJson();
        HttpResponse<JsonNode> jsonResponseMikeDan = get(chatApplication.url() + "/chat/mike/bob").asJson();

        assertThat(extractChat(jsonResponseBobSue), is("bob: " + message));
        assertThat(extractChat(jsonResponseMikeDan), is("mike: " + message2));

    }



    @Test
    public void chatCanBeAccessedByBothUsers() throws Exception {
        String message = "Hey Sookie";
        post(chatApplication.url() + "/chat/jason/sookie")
                .queryString("a", "b")
                .field("message", message)
                .asJson();

        HttpResponse<JsonNode> jasonResponse = get(chatApplication.url() + "/chat/jason/sookie").asJson();
        HttpResponse<JsonNode> sookieResponse = get(chatApplication.url() + "/chat/sookie/jason").asJson();

        assertThat(extractChat(jasonResponse), is(extractChat(sookieResponse)));

    }

    private static WizChatApplication startServer() throws Exception {
        WizChatApplication chatApplication = new WizChatApplication();
        chatApplication.run(new String[]{"server"});
        return chatApplication;
    }

    private String extractChat(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getBody().getObject().getString("chat");
    }


}
