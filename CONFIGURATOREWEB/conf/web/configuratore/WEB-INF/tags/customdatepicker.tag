<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="custom" tagdir="/WEB-INF/tags" %>
<%@attribute name="name" required="true" type="java.lang.String" %>
<%@attribute name="label" required="true" type="java.lang.String" %>
<%@attribute name="readonly" type="java.lang.Boolean" %>
<%@attribute name="helpMessage" type="java.lang.String" %>

<custom:custominput name="${name}" label="${label}" type="text" readonly="${readonly}"
                    helpMessage="${helpMessage}">
    <div class="input-group-append">
        <button type="button" class="btn-calendar"></button>
    </div>
</custom:custominput>