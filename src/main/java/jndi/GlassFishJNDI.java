package jndi;

import interfaces.CursBeanR;
import interfaces.StudentBeanR;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class GlassFishJNDI implements JNDI{
    private String JNDICurs = "java:global/TemaEJB-1.0-SNAPSHOT/CursBean!interfaces.CursBeanR";
    private String JNDIStudent = "java:global/TemaEJB-1.0-SNAPSHOT/StudentBean!interfaces.StudentBeanR";
    private CursBeanR cursBeanR;
    private StudentBeanR studentBeanR;
    public GlassFishJNDI() throws NamingException
    {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("org.omg.CORBA.ORBInitialHost","localhost");
        properties.setProperty("org.omg.CORBA.ORBInitialPort","3700");
        InitialContext context = new InitialContext(properties);
        cursBeanR = (CursBeanR) context.lookup(JNDICurs);
        studentBeanR = (StudentBeanR) context.lookup(JNDIStudent);
    }



    @Override
    public CursBeanR getCursBean() {
        return cursBeanR;
    }

    @Override
    public StudentBeanR getStudentBean() {
        return studentBeanR;
    }
}
