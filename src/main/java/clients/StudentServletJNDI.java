package clients;

import dtos.CursDTO;
import dtos.StudentDTO;
import interfaces.CursBeanR;
import interfaces.StudentBeanR;
import jndi.GlassFishJNDI;
import jndi.JNDI;
import jndi.JbossJNDI;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
@WebServlet(name = "StudentServlerJNDI")
public class StudentServletJNDI extends HttpServlet{
    private JNDI jndiProp;
    private StudentBeanR studentBean;
    private CursBeanR cursBean;
    public StudentServletJNDI()
    {
        try {
            jndiProp = new JbossJNDI();
        } catch (NamingException e) {
            try {
                jndiProp = new GlassFishJNDI();
            } catch (NamingException e1) {
                e1.printStackTrace();
            }
        }
        studentBean = jndiProp.getStudentBean();
        cursBean = jndiProp.getCursBean();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buton = request.getParameter("buton");
        String name = request.getParameter("NAME");
        int age = 0;
        int cnp = 0;
        try {
            age = Integer.parseInt(request.getParameter("VARSTA"));
            cnp = Integer.parseInt(request.getParameter("CNP"));
        }
        catch (Exception e){}

        getServletContext().setAttribute("cursList",cursBean.findAllCsR());
        getServletContext().setAttribute("studList",studentBean.findAllStudR());
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();

        if (buton.equals("SAVE")) {

            String[] cursSelectedList = request.getParameterValues("CURS");
            if (cursSelectedList.length>0) {
                List<CursDTO> cursDTOS = new ArrayList<>();

                for (String s : cursSelectedList) {
                    cursDTOS.add(cursBean.convertStringToObjectR(s));
                    System.out.println(cursDTOS.toString());
                }
                studentBean.insertStudentR(name, age, cnp, cursDTOS);
                printWriter.println("Element salvat cu succes " + name);
            }
            else{
                List<CursDTO> cursDTOS = new ArrayList<>();
                studentBean.insertStudentR(name, age, cnp, cursDTOS);
                printWriter.println("Element salvat cu succes " + name);
            }

        }
        if (buton.equals("DELETE"))
        {
            String[] studentListS = request.getParameterValues("STUDENT");
            if (studentListS.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/StudentJNDI.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                studentBean.deleteStudentR(studentBean.convertStringToObjectR(studentListS[0]));
            }
        }
        if (buton.equals("SELECT"))
        {
            String[] studentListS = request.getParameterValues("STUDENT");
            if (studentListS.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/StudentJNDI.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else {
                response.setContentType("text/html");
                StudentDTO studentDTO = studentBean.convertStringToObjectR(studentListS[0]);
                List<CursDTO> cursDTOS = cursBean.findAllCursForStR(studentDTO.getId());
                for (CursDTO curs:cursDTOS)
                {
                    printWriter.println(curs.getId()+" "+curs.getNume()+"</br>");
                }
            }

        }
        if (buton.equals("RESET")) {
            getServletContext().setAttribute("cursList", cursBean.findAllCsR());
            getServletContext().setAttribute("studList", studentBean.findAllStudR());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/StudentJNDI.jsp");
            requestDispatcher1.include(request,response);

        }
        if (buton.equals("CURS PAGE"))
        {
            getServletContext().setAttribute("cursList", cursBean.findAllCsR());
            getServletContext().setAttribute("studList", studentBean.findAllStudR());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/CursJNDI.jsp");
            requestDispatcher1.include(request,response);
        }
        printWriter.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
