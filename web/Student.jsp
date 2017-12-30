<%--
  Created by IntelliJ IDEA.
  User: MariusDK
  Date: 12/30/2017
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<from method="post"  action="StudentServlet"/>
<p>ADAUGA</p>
Nume: <input type="text" name="NAME"/>
Alege cursuri:</br>
    <c:forEach items="${cursList}" var="curs">
        <input type="checkbox" name="CURS" value="${curs}"/>${CURS}</<br>>
    </c:forEach>
    <input type="button" name = "buton" value="SAVE">

    <p>STERGE</p>
    Alege studenti:</br>
    <c:forEach items="${studList}" var="student">
    <input type="checkbox" name="STUDENT" value="${student}"/>${student}</<br>>
    </c:forEach>
    <input type="button" name = "buton" value="DELETE">

    <p>AFISAZA</p>
    Alege student:</br>
    <c:forEach items="${studList2}" var="student">
        <input type="radio" name="STUDENT1" value="${student}"/>${student}</<br>>
    </c:forEach>
    <input type="button" name = "buton" value="SELECT">
</from>