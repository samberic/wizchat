package com.samatkinson;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.samatkinson.config.HelloWorldConfiguration;
import com.samatkinson.data.Chats;
import com.samatkinson.resources.ChatResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

public class WizChatApplication extends Application<HelloWorldConfiguration> {
    private final MetricRegistry metrics = new MetricRegistry();
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
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets", "/assets"));
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        this.environment = environment;
        environment.jersey().register(new ChatResource(metrics, new Chats()));

        environment.healthChecks().register("chatz", new MessageCanSendHealthCheck(url()));

        ConsoleReporter reporter = ConsoleReporter
            .forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

        reporter.start(10, TimeUnit.SECONDS);

    }

    public String url() {
        return "http://localhost:8080";
    }

    public void shutdown() throws Exception {
        environment.getApplicationContext().getServer().stop();
    }
}
