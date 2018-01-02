package jndi;

import interfaces.CursBeanR;
import interfaces.StudentBeanR;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class JbossJNDI implements JNDI{
    private String JNDICurs = "TemaEJB-1.0-SNAPSHOT/CursEJB!interfaces.CursBeanR";
    private String JNDIStudent = "TemaEJB-1.0-SNAPSHOT/StudentEJB!interfaces.StudentBeanR";
    private CursBeanR cursBeanR;
    private StudentBeanR studentBeanR;

    public JbossJNDI() throws NamingException
    {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jboss.naming.remote.client.InitialContextFactory");
        properties.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
        properties.put(Context.SECURITY_PRINCIPAL,"root");
        properties.put(Context.SECURITY_CREDENTIALS,"root");
        properties.put("jboss.naming.client.ejb.context",true);
        Context context = null;
        context = new InitialContext(properties);
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
