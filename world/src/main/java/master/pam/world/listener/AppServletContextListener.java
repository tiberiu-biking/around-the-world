package master.pam.world.listener;

import master.pam.crosscutting.spring.SpringContext;
import master.pam.server.api.server.IServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppServletContextListener implements ServletContextListener {

    private IServer serverIf = SpringContext.getBean(IServer.class);

    @Override
    public void contextDestroyed(ServletContextEvent aServletContextEvent) {
        serverIf.shutdown();
    }

    @Override
    public void contextInitialized(ServletContextEvent aServletContextEvent) {
        serverIf.startup();
    }
}