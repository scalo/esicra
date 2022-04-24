<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://hibernate.org/taglib" prefix="hib" %>

<hib:session>

<hib:load var="cat" classname="it.saga.siscotel.db.test.Cat" value="0" />

<c:set target="${cat}" property="weight" value="7.7" />

<hib:saveOrUpdate target="${cat}" />

</hib:session>
