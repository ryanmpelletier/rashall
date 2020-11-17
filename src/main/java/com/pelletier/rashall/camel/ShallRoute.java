package com.pelletier.rashall.camel;

import com.pelletier.rashall.camel.process.ShallProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShallRoute extends RouteBuilder {

    Logger log = LoggerFactory.getLogger(ShallRoute.class);

    @Value("${rashall.in.directory}")
    String in;

    @Value("${rashall.out.directory}")
    String out;

    @Override
    public void configure() throws Exception {
        from("file://" + in)
                .convertBodyTo(String.class)
                .process(new ShallProcessor())
                .to("file://" + out);
    }
}
