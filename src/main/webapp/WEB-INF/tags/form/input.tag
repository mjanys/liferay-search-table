<%@tag description="Input" pageEncoding="UTF-8"%>

<%@include file="../init.jspf"%>

<form:input path="${path}" id="${id}" cssClass="form-control"/>
<c:if test="${hasErrors}">
    <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
    <span id="inputError2Status" class="sr-only">(error)</span>
</c:if>