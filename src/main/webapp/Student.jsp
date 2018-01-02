<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MariusDK
  Date: 12/30/2017
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post"  action="StudentServlet"/>
<p>ADAUGA</p>
Nume: <input type="text" name="NAME"/>
</br>
Varsta:  <input type="text" name="VARSTA"/>
</br>
CNP:  <input type="text" name="CNP"/>
</br>
<br>Alege cursuri:</br>
    <c:forEach items="${cursList}" var="curs">
       <br> <input type="checkbox" name="CURS" value="${curs}"/>${curs}</br>
    </c:forEach>
    <input type="submit" name = "buton" value="SAVE"/>

    <p>STERGE SI AFISARE</p>
    Alege studenti:</br>
    <c:forEach items="${studList}" var="student">
    <br><input type="checkbox" name="STUDENT" value="${student}"/>${student}</br>
    </c:forEach>
    <input type="submit" name = "buton" value="DELETE"/>
    <input type="submit" name = "buton" value="SELECT"/>
    <p>
    <input type="submit" name="buton" value="RESET"/>
        <input type="submit" name="buton" value="STUDENT PAGE"/>
    </p>
</from>