package com.samatkinson;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

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
        HttpResponse<JsonNode> jsonResponse =
                get(chatApplication.url() + "/chat/jason/sookie")
                        .header("accept", "application/json")
                        .asJson();

        assertThat(extractChat(jsonResponse), is(empty()));
    }

    @Test
    public void chatReturnsMessagesThatHaveBeenSent() throws Exception {
        String message = "Hey Sookie";
        String messageTwo = "Hey Jason";
        post(chatApplication.url() + "/chat/jason/sookie")
                .header("accept", "application/json")
                .field("message", message)
                .asJson();
        post(chatApplication.url() + "/chat/sookie/jason")
                .header("accept", "application/json")
                .field("message", messageTwo)
                .asJson();

        HttpResponse<JsonNode> jsonResponse = get(chatApplication.url() + "/chat/jason/sookie")
                .header("accept", "application/json")
                .asJson();

        List<String> result = extractChat(jsonResponse);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), is("jason: " + message));
        assertThat(result.get(1), is("sookie: " + messageTwo));

    }


    @Test
    public void userWithMultipleChatsCanAccessAllChats() throws Exception {
        String message = "This a chat between bob and sue";
        post(chatApplication.url() + "/chat/bob/sue")
                .header("accept", "application/json")
                .field("message", message)
                .asJson();

        String message2 = "This is a chat between Mike and Bob";

        post(chatApplication.url() + "/chat/mike/bob")
                .header("accept", "application/json")
                .field("message", message2)
                .asJson();

        HttpResponse<JsonNode> jsonResponseBobSue = get(chatApplication.url() + "/chat/bob/sue")
                .header("accept", "application/json").
                        asJson();
        HttpResponse<JsonNode> jsonResponseMikeDan = get(chatApplication.url() + "/chat/mike/bob")
                .header("accept", "application/json")
                .asJson();

        assertThat(flatChat(extractChat(jsonResponseBobSue)), is("bob: " + message));
        assertThat(flatChat(extractChat(jsonResponseMikeDan)), is("mike: " + message2));

    }

    @Test
    public void chatCanBeAccessedByBothUsers() throws Exception {
        String message = "Hey Sookie";
        post(chatApplication.url() + "/chat/jason/sookie")
                .header("accept", "application/json")
                .field("message", message)
                .asJson();

        HttpResponse<JsonNode> jasonResponse = get(chatApplication.url() + "/chat/jason/sookie")
                .header("accept", "application/json").asJson();
        HttpResponse<JsonNode> sookieResponse = get(chatApplication.url() + "/chat/sookie/jason")
                .header("accept", "application/json").asJson();

        assertThat(extractChat(jasonResponse), is(extractChat(sookieResponse)));

    }


    private static WizChatApplication startServer() throws Exception {
        WizChatApplication chatApplication = new WizChatApplication();
        chatApplication.run(new String[]{"server"});
        return chatApplication;
    }

    private List<String> extractChat(HttpResponse<JsonNode> jsonResponse) {
        JSONArray jsonArray = jsonResponse.getBody().getObject().getJSONObject("currentChat").getJSONArray("chat");
        List<String> result = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++)
            result.add(jsonArray.getString(i));

        return result;
    }

    private String flatChat(List<String> strings) {
        return strings.stream().collect(joining(""));
    }


}
