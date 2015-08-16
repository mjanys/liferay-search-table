<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="init.jspf"%>

<t:html>
    <div class="alert alert-danger" role="alert" class="padding-10">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span>
            <spring:message code="msg-entity-not-found"/>
        </span>
    </div>
    <div class="buttons">
        <h:link href="javascript:window.history.back();">
            <spring:message code='label-ok' var="label"/>
            <bs:button cssClass="button btn-default" text="${label}" />
        </h:link>
    </div>
</t:html>