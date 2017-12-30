package clients;


import entities.CursEntity;
import entities.StudentEntity;
import interfaces.CursBean;
import interfaces.StudentBean;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "CursServlet")
public class CursServlet extends HttpServlet
{
    @EJB
    private StudentBean studentBean;
    @EJB
    private CursBean cursBean;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("NAME");
        String prof = request.getParameter("PROF_NAME");
        String buton = request.getParameter("buton");

        if (buton.equals("SAVE"))
        {
            cursBean.insertCurs(name,0,prof);
        }
        if (buton.equals("DELETE"))
        {
            String[] cursuri = request.getParameterValues("cursList");
            if (cursuri.length>1)
            {
                //error
            }
            else
            {
                cursBean.deleteCurs(cursBean.convertStringToObject(cursuri[0]));
            }
        }
        if (buton.equals("SELECT"))
        {
            String[] cursuri = request.getParameterValues("cursList");
            if (cursuri.length>1)
            {
                //error
            }
            else
            {
                CursEntity cursEntity = cursBean.convertStringToObject(cursuri[0]);
                List<StudentEntity> studentEntityList = studentBean.findStudFromCurs(cursEntity.getId());
                response.setContentType("text/html");
                PrintWriter printWriter = response.getWriter();
                for (StudentEntity studentEntity:studentEntityList)
                {
                    printWriter.println(studentEntity.toString());
                }
                printWriter.close();

            }
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }














}
