<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MariusDK
  Date: 12/30/2017
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post"  action="CursServlet"/>
<p>ADAUGA</p>
Denumire:         <input type="text" name="NAME"/>
</br>
Profesor titular: <input type="text" name="PROF_NAME"/>
</br>
Departament:      <input type="text" name="DEPARTAMENT"/>
</br>

<input type="submit" name = "buton" value="SAVE"/>

<p>STERGE SI AFISARE</p>
<br>Alege curs:</br>
<c:forEach items="${cursList}" var="curs">
   <br> <input type="checkbox" name="CURS" value="${curs}"/>${curs}</<br>
</c:forEach>
<br>
<input type="submit" name = "buton" value="DELETE"/>
<input type="submit" name = "buton" value="SELECT"/>
</br>
<p>
   <input type="submit" name="buton" value="RESET"/>
   <input type="submit" name="buton" value="CURS PAGE"/>
</p>

</from>