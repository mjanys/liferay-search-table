<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="init.jspf"%>

<t:html>
    <div class="alert alert-danger" role="alert" class="padding-10">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span>
            <spring:message code="msg-fatal-error"/>
        </span>

        <div>
            <fmt:formatDate value="<%=new java.util.Date()%>" pattern="<%=Constants.DATE_TIME_FORMAT%>" />
        </div>

        <c:if test="${not empty exception}">
            <div style="display: none;">
                <pre>
                    <c:out value="${exception}"/>
                </pre>
                <pre>
                    <c:forEach var="st" items="${exception.stackTrace}" varStatus="loop">
                        <c:if test="${loop.index < 30}">
                            &nbsp;&nbsp;<c:out value="${st}"/><br/>
                        </c:if>
                        <c:if test="${loop.index == 30}">
                            &nbsp;&nbsp;... and more ...<br/>
                        </c:if>
                    </c:forEach>
                </pre>
                <c:set var="exception" value="${exception.cause}" />
            </div>
        </c:if>
    </div>
    <div class="buttons">
        <h:link href="javascript:window.history.back();">
            <spring:message code='label-ok' var="label"/>
            <bs:button cssClass="button btn-default" text="${label}" />
        </h:link>
    </div>
</t:html>