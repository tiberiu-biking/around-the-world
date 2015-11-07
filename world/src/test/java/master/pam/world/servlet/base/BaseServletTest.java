package master.pam.world.servlet.base;

import org.junit.Before;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BaseServletTest {

    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        writer = new PrintWriter(getClass().getCanonicalName());
        // CrudHelper.init();
    }

}
