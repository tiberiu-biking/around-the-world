package master.pam.test.log;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Logger log = LoggerFactory.getLogger(LogTest.class);
        log.debug("DEBUG");
        log.trace("TRACE");
        log.info("INFO");
        log.error("ERROR");

    }
}
