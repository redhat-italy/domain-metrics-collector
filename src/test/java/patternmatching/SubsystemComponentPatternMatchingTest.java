/**
 * 
 */
package patternmatching;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Battaglia
 *
 */
public class SubsystemComponentPatternMatchingTest {
    
    private static final Pattern pattern=Pattern.compile("((singleton)|((stateless|stateful)-session))-bean");

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSLSBMatch() {
        assertTrue(pattern.matcher("stateless-session-bean").matches());
        assertTrue(pattern.matcher("stateful-session-bean").matches());
        assertTrue(pattern.matcher("singleton-bean").matches());
    }

}
