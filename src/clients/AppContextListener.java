package clients;

import beans.CursBean;
import beans.StudentBean;
import com.sun.org.apache.xerces.internal.xs.ShortList;
import com.sun.org.apache.xerces.internal.xs.XSException;
import entities.CursEntity;
import entities.StudentEntity;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;
@WebListener
public class AppContextListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //---------------------StudentJSP
        StudentBean studentBean = new StudentBean();
        CursBean cursBean = new CursBean();
        List<CursEntity> cursList = cursBean.findAllCs();
        servletContextEvent.getServletContext().setAttribute("cursList",cursList);

        List<StudentEntity> studentList = studentBean.findAllStud();
        servletContextEvent.getServletContext().setAttribute("studentList",studentList);

        List<StudentEntity> studentList2 = studentBean.findAllStud();
        servletContextEvent.getServletContext().setAttribute("studentList1",studentList2);
        List<StudentEntity> cursList2;
        //-------------------------
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
