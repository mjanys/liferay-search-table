<%@ page import="cz.janys.portlet.person.PersonConstants" %>
<%@include file="init.jspf" %>

<t:html>
    <div class="jumbotron">
        <p>
            Sample of Spring MVC application with JSP, HSQL in-memory database, JPA (hibernate) and Bootstrap compiled from LESS on Maven.
            There is also basic template pattern.
        </p>
        <p>
            For example of CRUD application continue to Person page.
        </p>
        <p>
            <portlet:renderURL var="personCreateFormUrl">
                <portlet:param name="<%=PersonConstants.PARAM_PAGE%>" value="<%=PersonConstants.PERSON_FORM_EDIT_PAGE%>"/>
            </portlet:renderURL>
            <h:link href="${personCreateFormUrl}">
                <spring:message code="label-person-page" var="btnText"/>
                <bs:button text="${btnText}" cssClass="btn-default"/>
            </h:link>
        </p>
    </div>
</t:html>
