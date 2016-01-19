package com.samatkinson;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mashape.unirest.http.Unirest.post;

public class Utilities {

    private static final ChatBots chatBots = new ChatBots("Sam", "Mike", "Dan", "Jon", "Fabrice");

    public static void main(String[] args) throws Exception {
        chatBots.startChatting();
    }

    public static void submitChat(String message, String userOne, String userTwo, String url) throws UnirestException {
        post(url + "/chat/" + userOne + "/" + userTwo)
                .header("accept", "application/json")
                .field("message", message)
                .asJson();
    }

    private static class ChatBots {
        private String[] names;

        public ChatBots(String... names) {
            this.names = names;
        }

        public void startChatting() throws Exception {

            ExecutorService executorService = Executors.newScheduledThreadPool(4);
            executorService.submit(() -> this.randomChats());
            executorService.submit(() -> this.randomChats());
            executorService.submit(() -> this.randomChats());
            executorService.submit(() -> this.randomChats());
            randomChats();
        }

        private void randomChats() {
            while (true) {

                Random random = new Random();
                String userOne = names[random.nextInt(names.length - 1)];
                String userTwo = names[random.nextInt(names.length - 1)];

                try {
                    submitChat(
                        RandomStringUtils.random(random.nextInt(140)),
                        userOne,
                        userTwo,
                        "http://localhost:8080"
                    );
                    Thread.sleep(random.nextInt(5000));

                } catch (UnirestException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
