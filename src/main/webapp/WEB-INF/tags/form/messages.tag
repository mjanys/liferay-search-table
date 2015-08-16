<%--@elvariable id="flashMessages" type="java.util.Map<java.lang.String,java.util.List<java.lang.String>>"--%>

<%@tag description="Input" pageEncoding="UTF-8"%>

<%@include file="../init.jspf"%>

<c:if test="${not empty flashMessages}">
    <c:forEach items="${flashMessages}" var="entry">
        <%-- iterate messages from flash --%>
        <c:forEach items="${entry.value}" var="msg">
            <div class="alert alert-${entry.key}" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                <span>
                    <c:out value="${msg}"/>
                </span>
            </div>
        </c:forEach>
    </c:forEach>
</c:if>