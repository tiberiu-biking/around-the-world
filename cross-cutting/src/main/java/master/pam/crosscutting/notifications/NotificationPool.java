package master.pam.crosscutting.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NotificationPool {

    protected final Logger logger = LoggerFactory.getLogger(NotificationPool.class);

    private static final NotificationPool pool = new NotificationPool();

    private List<String> notifications = new ArrayList<String>();

    private boolean isPoolOpen;

    public static final NotificationPool getPool() {
        return pool;
    }

    public boolean isPoolOpen() {
        return isPoolOpen;
    }

    public void setPoolOpen(boolean aIsPoolOpen) {
        isPoolOpen = aIsPoolOpen;
    }

    public String getNotification() {
        String result = null;
        if (notifications.size() > 0) {
            logger.debug("Notification removed from the pool!");
            result = notifications.get(0);
            notifications.remove(0);

        }
        return result;
    }

    public void addNotification(String aNotification) {
        logger.debug("Added notification to the notification pool!");
        notifications.add(aNotification);
    }

}
