package clients;

import entities.CursEntity;
import entities.StudentEntity;
import interfaces.CursBean;
import interfaces.StudentBean;

import javax.ejb.EJB;
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

@WebServlet(name = "StudentServlet")
public class StudentServlet extends HttpServlet {
    //injectare EJB
    @EJB
    private StudentBean studentBean;
    @EJB
    private CursBean cursBean;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buton = request.getParameter("buton");
        String name = request.getParameter("NAME");
        int age = 0;
        int cnp = 0;
        try {
            age = Integer.parseInt(request.getParameter("VARSTA"));
            cnp = Integer.parseInt(request.getParameter("CNP"));
        }
        catch (Exception e)
        {
        }
        getServletContext().setAttribute("cursList",cursBean.findAllCs());
        getServletContext().setAttribute("studList",studentBean.findAllStud());
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();

        if (buton.equals("SAVE")) {

            String[] cursSelectedList = request.getParameterValues("CURS");
            if (cursSelectedList.length>0) {
                List<CursEntity> cursEntities = new ArrayList<>();

                for (String s : cursSelectedList) {
                    cursEntities.add(cursBean.convertStringToObject(s));
                    System.out.println(cursEntities.toString());
                }
                studentBean.insertStudent(name, age, cnp, cursEntities);
                printWriter.println("Element salvat cu succes " + name);
            }
            else{
                List<CursEntity> cursEntities = new ArrayList<>();
                studentBean.insertStudent(name, age, cnp, cursEntities);
                printWriter.println("Element salvat cu succes " + name);
            }

        }
        if (buton.equals("DELETE"))
        {
            String[] studentListS = request.getParameterValues("STUDENT");
            if (studentListS.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Student.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                studentBean.deleteStudent(studentBean.convertStringToObject(studentListS[0]));
            }
        }
        if (buton.equals("SELECT"))
        {
            String[] studentListS = request.getParameterValues("STUDENT");
            if (studentListS.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Student.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else {
                response.setContentType("text/html");
                StudentEntity studentEntity = studentBean.convertStringToObject(studentListS[0]);
                List<CursEntity> cursEntities = cursBean.findAllCursForSt(studentEntity.getId());
                for (CursEntity curs:cursEntities)
                {
                    printWriter.println(curs.getId()+" "+curs.getNume()+"</br>");
                }
            }

        }
        if (buton.equals("RESET")) {
            getServletContext().setAttribute("cursList", cursBean.findAllCs());
            getServletContext().setAttribute("studList", studentBean.findAllStud());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Student.jsp");
            requestDispatcher1.include(request,response);

        }
        if (buton.equals("CURS PAGE"))
        {
            getServletContext().setAttribute("cursList", cursBean.findAllCs());
            getServletContext().setAttribute("studList", studentBean.findAllStud());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Curs.jsp");
            requestDispatcher1.include(request,response);
        }
        printWriter.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
