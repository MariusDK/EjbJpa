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
    @EJB
    private StudentBean studentBean;
    @EJB
    private CursBean cursBean;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String buton = request.getParameter("buton");
        PrintWriter printWriter = response.getWriter();

        if (buton == "SAVE") {
            String name = request.getParameter("NAME");
            String[] cursSelectedList = request.getParameterValues("CURS");

            List<CursEntity> cursEntities = new ArrayList<>();

            for (String s : cursSelectedList) {
                cursEntities.add(cursBean.convertStringToObject(s));
            }
            studentBean.insertStudent(name, cursEntities);
        }
        if (buton == "DELETE")
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
        if (buton == "SELECT")
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
                    printWriter.println(curs.toString());
                }
                }
            }
            printWriter.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
