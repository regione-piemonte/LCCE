<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it-it" dir="ltr">

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <!-- META FOR IOS & HANDHELD -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <!-- da insserire la parte webkit -->


    <meta name="HandheldFriendly" content="true"/>
    <meta name="apple-mobile-web-app-capable" content="YES"/>
    <!-- //META FOR IOS & HANDHELD -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/im/favicon.ico" type="image/x-icon"/>
    <!--link rel="manifest" href="manifest.json"-->
    <!-- Bootstrap + font-awesome-4.7.0 + custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/fontOpenSans/opensans.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/lib/bootstrap-material-design/bootstrap-material-design.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/print.css" media="print"/>
    <!--link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet"-->
    <title>Configuratore servizi sanitari digitali</title>
    <!--[if IE 9]>
    <link href="${pageContext.request.contextPath}/css/bootstrap-ie9.min.css" rel="stylesheet" media="screen"/>
    <link href="${pageContext.request.contextPath}/css/ie9.min.css" rel="stylesheet" media="screen"/>
    <![endif]-->

</head>

<body>
<div class="body-content">
    <%--<r:include url="/configuratoreServiziSanitariDigitali/sitenew/include/skip_block.html"
               resourceProvider="rp"/>--%>
    <!--skiplinks-->
    <ul class="Skiplinks sr-only">
        <li><a href="#main_content">Vai al Contenuto</a></li>
        <li><a href="#sp-navbar">Vai al menu di portale</a></li>
        <li><a href="#menu" title="accedi al menu">Vai alla navigazione del sito</a></li>
    </ul>
    <!--/skiplinks-->

<c:import url="common/portalHeader.jsp"/>