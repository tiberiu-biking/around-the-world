package master.pam.crosscutting.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {

    private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");

    public static <T> T getBean(Class<T> aClass) {
        return context.getBean(aClass);
    }

}
