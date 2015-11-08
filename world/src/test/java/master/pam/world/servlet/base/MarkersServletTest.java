package master.pam.world.servlet.base;

import master.pam.world.servlet.impl.marker.GetMarkersServlet;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class MarkersServletTest extends BaseServletTest {

  @Test
  public void getListOfMarkersTest() throws IOException, ServletException {

    Mockito.when(response.getWriter()).thenReturn(writer);
    new GetMarkersServlet().doPost(request, response);

    writer.flush();
    assertTrue(FileUtils.readFileToString(new File("markers-response.json"), "UTF-8").contains("My Expected String"));
  }

}
