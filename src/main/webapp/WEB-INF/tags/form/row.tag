<%@tag description="Row" pageEncoding="UTF-8"%>

<%@include file="../init.jspf"%>

<%@attribute name="path" required="true" %>
<%@attribute name="inputId" required="true" %>

<c:set var="id" value="${inputId}" scope="request"/>
<c:set var="path" value="${path}" scope="request"/>
<c:set var="id" value="${inputId}" scope="request"/>

<c:set var="errors">
    <form:errors path="${path}"/>
</c:set>
<c:set var="hasErrors" value="${not empty errors}" scope="request"/>

<c:if test="${hasErrors}">
    <c:set var="errorClass" value="has-error has-feedback"/>
</c:if>

<div class="form-group ${errorClass}">
    <jsp:doBody/>
</div>
