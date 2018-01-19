package clients;

import dtos.CursDTO;
import dtos.StudentDTO;
import interfaces.CursBean;
import interfaces.CursBeanR;
import interfaces.StudentBean;
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
import java.util.List;

@WebServlet(name = "CursServlerJNDI")
public class CursServletJNDI extends HttpServlet {
    private JNDI jndiProp;

    private StudentBeanR studentBean;
    private CursBeanR cursBean;
    // conexiunea cu bean-uri și interfețe remote
    public CursServletJNDI() {
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
        String name = request.getParameter("NAME");
        String prof = request.getParameter("PROF_NAME");
        String dep = request.getParameter("DEPARTAMENT");
        String buton = request.getParameter("buton");
        getServletContext().setAttribute("cursList",cursBean.findAllCsR());
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        if (buton.equals("SAVE"))
        {
            cursBean.insertCursR(name,0,prof,dep);
            getServletContext().setAttribute("cursList",cursBean.findAllCsR());
            printWriter.println("Element salvat cu succes "+ name);

        }
        if (buton.equals("DELETE"))
        {

            String[] cursuri = request.getParameterValues("CURS");
            System.out.println(cursuri[0].toString());
            if (cursuri.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/CursJNDI.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                cursBean.deleteCursR(cursBean.convertStringToObjectR(cursuri[0]));
                getServletContext().setAttribute("cursList",cursBean.findAllCsR());
            }
        }
        if (buton.equals("SELECT"))
        {
            String[] cursuri = request.getParameterValues("CURS");
            if (cursuri.length>1)
            {
                RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/CursJNDI.jsp");
                requestDispatcher1.include(request,response);
                printWriter.append("<font color='red'><b>Eroare: Prea multe optiuni alese!</b></fond> ");
            }
            else
            {
                CursDTO cursDTO = cursBean.convertStringToObjectR(cursuri[0]);
                response.setContentType("text/html");
                if (cursDTO.getNumarStudenti()>0) {
                    List<StudentDTO> studentEntityList = studentBean.findStudFromCursR(cursDTO.getId());
                    for (StudentDTO studentEntity : studentEntityList) {
                        printWriter.println(studentEntity.getId()+" "+studentEntity.getName()+"</br>");
                    }

                }
                else
                {
                    printWriter.println("Cursul "+cursDTO.getNume()+" nu are nici un student inscris");
                }
            }
        }
        if (buton.equals("RESET"))
        {
            getServletContext().setAttribute("cursList",cursBean.findAllCsR());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/CursJNDI.jsp");
            requestDispatcher1.include(request,response);
        }
        if (buton.equals("STUDENT PAGE"))
        {
            getServletContext().setAttribute("cursList",cursBean.findAllCsR());
            RequestDispatcher requestDispatcher1 = request.getRequestDispatcher("/StudentJNDI.jsp");
            requestDispatcher1.include(request,response);
        }
        printWriter.close();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
