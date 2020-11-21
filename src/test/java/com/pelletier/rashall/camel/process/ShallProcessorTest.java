package com.pelletier.rashall.camel.process;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShallProcessorTest {

    Logger log = LoggerFactory.getLogger(ShallProcessorTest.class);

    final String content = "Section 1.2.3\n"
            + "Your mama is so fat she went outside with a bowl and a spoon.\n"
            + "Section 1.232.31\n"
            + "Your mama is so old her birth certificate is expired.\n"
            + "Section 112.4.83\n"
            + "Your mom is so fat the scale said 'to be continued'.\n"
            + "Section 1\n"
            + "You shall not pass.\n";

    @Test
    public void testRegexMatch(){
        ShallProcessor shallProcessor = new ShallProcessor();
        log.info("Match = {}",shallProcessor.getMatches(content));
    }

    @Test
    public void testParseContent(){
        ShallProcessor shallProcessor = new ShallProcessor();
        log.info("{}",shallProcessor.getRequirements(content));
    }



}
