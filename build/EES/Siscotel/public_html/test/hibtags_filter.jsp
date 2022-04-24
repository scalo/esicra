<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<hib:session>
 <p>
  <hib:load var="cat" classname="it.saga.siscotel.db.test.Cat" value="0"/><b>madre</b><br></br><c:out value="${cat.pkid} ${cat.name} ${cat.sex} "/><br></br>
 </p>
 <p>
  <hib:filter var="femaleKittens" items="${cat.kittens}">
   where this.sex = 'F'
  </hib:filter><br></br><b>femmine</b><br></br><c:forEach items="${femaleKittens}"
                                                          var="kitten">
   <c:out value="${kitten.name}"/>
  </c:forEach><br></br><hib:filter var="femaleKittens" items="${cat.kittens}">
   where this.sex = 'M'
  </hib:filter><br></br><b>maschi</b><br></br><c:forEach items="${femaleKittens}"
                                                         var="kitten">
   <c:out value="${kitten.name}"/>
  </c:forEach>
 </p>
</hib:session>
