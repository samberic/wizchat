package com.samatkinson;

import com.samatkinson.config.HelloWorldConfiguration;
import com.samatkinson.resources.ChatRoomResource;
import com.samatkinson.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WizChatApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new WizChatApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        ChatRoomResource resource = new ChatRoomResource();
        environment.jersey().register(resource);
    }

    public String url() {
        return "http://localhost:8080";
    }
}
