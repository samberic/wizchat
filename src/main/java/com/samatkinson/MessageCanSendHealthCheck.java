package com.samatkinson;

import com.codahale.metrics.health.HealthCheck;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import static com.codahale.metrics.health.HealthCheck.Result.healthy;
import static com.codahale.metrics.health.HealthCheck.Result.unhealthy;
import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageCanSendHealthCheck extends HealthCheck {

    private String applicationUrl;

    public MessageCanSendHealthCheck(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    protected Result check() throws Exception {
        String message = "Test Message " + System.currentTimeMillis();
        String chatroomUrl = applicationUrl + "/chat/healthbot1/healthbot2";
        post(chatroomUrl)
                .field("message", message)
                .asJson();

        HttpResponse<JsonNode> jasonResponse = get(chatroomUrl).asJson();

        return extractChat(jasonResponse).contains(message) ?
                healthy() :
                unhealthy("Chat does not contain previously sent message\n" + message);
    }

    private String extractChat(HttpResponse<JsonNode> jsonResponse) {
        return jsonResponse.getBody().getObject().getString("chat");
    }

}
