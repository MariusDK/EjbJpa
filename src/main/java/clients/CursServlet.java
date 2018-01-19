package clients;


import entities.CursEntity;
import entities.StudentEntity;
import interfaces.CursBean;
import interfaces.StudentBean;


import javax.ejb.EJB;
import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@WebServlet(name = "CursServlet")
public class CursServlet extends HttpServlet
{
    //injectare EJB
    @EJB
    private StudentBean studentBean;
    @EJB
    private CursBean cursBean;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("NAME");
        String prof = request.getParameter("PROF_NAME");
        String dep = request.getParameter("DEPARTAMENT");
        String buton = request.getParameter("buton");
        getServletContext().setAttribute("cursList",cursBean.findAllCs());
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        if (buton.equals("SAVE"))
        {
            cursBean.insertCurs(name,0,prof,dep);
            getServletContext().setAttribute("cursList",cursBean.findAllCs());
            printWriter.println("Element salvat cu succes "+ name);

        }
        if (buton.equals("DELETE"))
        {

            String[] cursuri = request.getParameterValues("CURS");
            System.out.println(cursuri[0].toString());
            if (cursuri.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Curs.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                cursBean.deleteCurs(cursBean.convertStringToObject(cursuri[0]));
                getServletContext().setAttribute("cursList",cursBean.findAllCs());
            }
        }
        if (buton.equals("SELECT"))
        {
            String[] cursuri = request.getParameterValues("CURS");
            if (cursuri.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Curs.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                CursEntity cursEntity = cursBean.convertStringToObject(cursuri[0]);
                response.setContentType("text/html");
                if (cursEntity.getNumarStudenti()>0) {
                    List<StudentEntity> studentEntityList = studentBean.findStudFromCurs(cursEntity.getId());
                    for (StudentEntity studentEntity : studentEntityList) {
                        printWriter.println(studentEntity.getId()+" "+studentEntity.getName()+"</br>");
                    }

                }
                else
                {
                    printWriter.println("Cursul "+cursEntity.getNume()+" nu are nici un student inscris");
                }
            }
        }
        if (buton.equals("RESET"))
        {
            getServletContext().setAttribute("cursList",cursBean.findAllCs());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Curs.jsp");
            requestDispatcher1.include(request,response);
        }
        if (buton.equals("STUDENT PAGE"))
        {
            getServletContext().setAttribute("cursList",cursBean.findAllCs());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/Student.jsp");
            requestDispatcher1.include(request,response);
        }
        printWriter.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
