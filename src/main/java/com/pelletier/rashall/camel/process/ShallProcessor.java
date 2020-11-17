package com.pelletier.rashall.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class ShallProcessor implements Processor {

    Logger log = LoggerFactory.getLogger(ShallProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("ShallProcessor begin processing Exchange.");

        BodyContentHandler handler = new BodyContentHandler();

        AutoDetectParser parser = new AutoDetectParser();

        Metadata metadata = new Metadata();

        try (InputStream stream = exchange.getIn().getBody(InputStream.class)) {
            parser.parse(stream, handler, metadata);

            log.info("Content = {}", handler.toString());
        }

    }

}
