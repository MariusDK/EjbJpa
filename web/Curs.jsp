<%--
  Created by IntelliJ IDEA.
  User: MariusDK
  Date: 12/30/2017
  Time: 7:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<from method="post"  action=""/>
<p>ADAUGA</p>
Denumire: <input type="text" name="NAME"/>
</br>
Profesor titular: <input type="text" name="PROF_NAME"/>
</br>

<input type="button" name = "buton" value="SAVE">

<p>STERGE</p>
Alege curs:</br>
<c:forEach items="${cursList}" var="curs">
    <input type="checkbox" name="CURS" value="${curs}"/>${curs}</<br>>
</c:forEach>
<input type="button" name = "buton" value="DELETE">

<p>AFISAZA</p>
Alege curs:</br>
<c:forEach items="${cursList}" var="student">
    <input type="radio" name="STUDENT1" value="${curs}"/>${curs}</<br>>
</c:forEach>
<input type="button" name = "buton" value="SELECT">
</from>