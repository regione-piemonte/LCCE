<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="label" required="true" type="java.lang.String" %>
<%@attribute name="type" required="true" type="java.lang.String" %>
<%@attribute name="maxlength" required="false" type="java.lang.String" %>
<%@attribute name="size" required="false" type="java.lang.String" %>
<%@attribute name="readonly" type="java.lang.Boolean" %>
<%@attribute name="helpMessage" type="java.lang.String" %>

<spring:bind path="${name}">
    <form:label path="${name}" class="bmd-label-floating">${label}</form:label>

    <c:choose>
        <c:when test="${readonly ne null}">
            <form:input path="${name}" type="${type}" maxlength="${maxlength}" size="${size}"
                        class="form-control ${status.error ? 'is-invalid' : ''}"
                        readonly="${readonly}" data-default-readonly="${readonly}"/>
        </c:when>
        <c:otherwise>
            <form:input path="${name}" type="${type}" maxlength="${maxlength}" size="${size}"
                        class="form-control ${status.error ? 'is-invalid' : ''}"/>
        </c:otherwise>
    </c:choose>

    <jsp:doBody/>

    <c:if test="${helpMessage ne null}">
        <c:if test="${not status.error}">
            <span class="bmd-help">${helpMessage}</span>
        </c:if>
    </c:if>

    <form:errors path="${name}" cssClass="invalid-feedback"/>
</spring:bind>
