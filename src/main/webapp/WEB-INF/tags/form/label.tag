<%@tag description="Label" pageEncoding="UTF-8"%>

<%@include file="../init.jspf"%>

<%@attribute name="labelCode"%>

<c:if test="${not empty labelCode}">
    <spring:message code="${labelCode}" var="title"/>
    <form:label path="${path}" for="${id}" title="${title}" cssClass="large control-label">
        ${title}
    </form:label>:
</c:if>