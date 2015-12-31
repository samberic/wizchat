package com.samatkinson;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.samatkinson.config.HelloWorldConfiguration;
import com.samatkinson.resources.ChatRoomResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WizChatApplication extends Application<HelloWorldConfiguration> {
    private Environment environment;

    public static void main(String[] args) throws Exception {
        new WizChatApplication().run("server");
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
        this.environment = environment;
        ChatRoomResource resource = new ChatRoomResource();
        environment.jersey().register(resource);

        environment.healthChecks().register("chatz", new MessageCanSendHealthCheck(url()));
    }

    public String url() {
        return "http://localhost:8080";
    }

    public void shutdown() throws Exception {
        environment.getApplicationContext().getServer().stop();
    }
}
