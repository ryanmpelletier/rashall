package com.pelletier.rashall.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

            List<String> result = getRequirements(handler.toString());

            String response = "Entry Number,Section,Shall Statement\n" +
                    result.stream().collect(Collectors.joining("\n"));
            exchange.getOut().setBody(response);
        }

    }

    protected List<String> getRequirements(String content){
        List<String> matches = getMatches(content);
        List<String> result = new ArrayList<>();
        int requirementNumber = 1;

        for(int i = 0; i < matches.size(); i++){
            log.info("Searching for requirements between {} and {}.",
                    matches.get(i), i + 1 == matches.size() ? "match end." : matches.get(i + 1));
            int startIndex = content.indexOf(matches.get(i) + "\n") + matches.get(i).length();
            int endIndex = i + 1 == matches.size() ? content.length() - 1 : content.indexOf(matches.get(i + 1) + "\n");
            log.info("Parsing content for requirements between {} and {}.", startIndex, endIndex);

            String parseSection = content.substring(startIndex, endIndex);
            log.info("Looking for requirements... \n\n {}", parseSection);

            String[] sentences = parseSection.split("\\.");
            log.info("Found {} sentences in section. Sentences are {}", sentences.length, Arrays.asList(sentences));

            final int index = i;
            for(int j = 0; j < sentences.length; j++){
                if(sentences[j].contains("shall")){
                    String requirement = buildRequirement(requirementNumber, matches.get(index), sentences[j]);
                    log.info("Found requirement {}", requirement);
                    result.add(requirement);
                    requirementNumber++;
                }
            }

        }
        log.info("Found {} requirements!", result.size());
        return result;

    }

    protected String buildRequirement(int number, String section, String sentence){
        return number + "," + section.trim() + "," + sentence.trim();
    }

    protected List<String> getMatches(String content){
        List<String> matches = new ArrayList<>();
        String pattern = "(Section ([0-9]{1,})((\\.)[0-9]{1,}){0,})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(content);
        while(m.find()){
            String match = m.group(0);
            log.info("Found section {}", match);
            matches.add(match);
        }
        return matches;
    }

}
