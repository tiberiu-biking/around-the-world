package master.pam.world.servlet.base;

import master.pam.server.api.request.RequestConstants;
import master.pam.world.servlet.impl.user.SignInServlet;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.IOException;

public class LoginServletTest extends BaseServletTest {

    @Test
    public void testLoginSuccessfull() throws IOException, ServletException {

        Mockito.when(request.getParameter(RequestConstants.USERNAME)).thenReturn("ale@ale.com");
        Mockito.when(request.getParameter(RequestConstants.PASSWORD)).thenReturn("ale");
        Mockito.when(response.getWriter()).thenReturn(writer);
        new SignInServlet().doPost(request, response);

        writer.flush();
    }

    @Test
    public void testLoginUnsuccessfull() throws IOException, ServletException {
        Mockito.when(request.getParameter(RequestConstants.USERNAME)).thenReturn("ale@ale.com");
        Mockito.when(request.getParameter(RequestConstants.PASSWORD)).thenReturn("wrong password");
        Mockito.when(response.getWriter()).thenReturn(writer);

        new SignInServlet().doPost(request, response);
        writer.flush();
    }
}
