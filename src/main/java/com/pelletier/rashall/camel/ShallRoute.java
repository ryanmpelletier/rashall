package com.pelletier.rashall.camel;

import com.pelletier.rashall.camel.process.ShallProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ShallRoute extends RouteBuilder {


    @Value("${rashall.in.directory}")
    String in;

    @Value("${rashall.out.directory}")
    String out;

    @Override
    public void configure() {
        from("file://" + in)
                .process(new ShallProcessor())
                .to("file://" + out);
    }
}
