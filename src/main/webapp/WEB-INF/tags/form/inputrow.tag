<%@tag description="Input Row" pageEncoding="UTF-8"%>

<%@include file="../init.jspf"%>

<%@attribute name="id" type="java.lang.String" required="true" %>
<%@attribute name="path" type="java.lang.String" required="true" %>
<%@attribute name="labelCode" type="java.lang.String" required="true" %>

<f:row path="${path}" inputId="${id}">
    <f:label labelCode="${labelCode}"/>
    <f:input/>
    <f:errors/>
</f:row>
