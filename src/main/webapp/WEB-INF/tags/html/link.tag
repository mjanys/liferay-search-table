<%@tag description="Link" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="href" type="java.lang.String" required="true" %>

<a href="${href}">
    <jsp:doBody/>
</a>