<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><!DOCTYPE html>
<html lang="it-it" dir="ltr"><head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <!-- META FOR IOS & HANDHELD -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <!-- da insserire la parte webkit -->



  <meta name="HandheldFriendly" content="true">
  <meta name="apple-mobile-web-app-capable" content="YES">
  <!-- //META FOR IOS & HANDHELD -->
  <link rel="shortcut icon" href="/ris/im/favicon.ico" type="image/x-icon">
  <!--link rel="manifest" href="manifest.json"-->
  <!-- Bootstrap + font-awesome-4.7.0 + custom CSS -->
  <link rel="stylesheet" href="/ris/lib/fontOpenSans/opensans.css">
    <link rel="stylesheet" href="/ris/lib/bootstrap-material-design/bootstrap-material-design.min.css">

  <link rel="stylesheet" type="text/css" href="/ris/css/main.css">

  <!--link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet"-->
  <title>Configuratore servizi sanitari digitali</title>
  <!--[if IE 9]>
	<link href="css/bootstrap-ie9.min.css" rel="stylesheet" media="screen" />
	<link href="css/ie9.min.css" rel="stylesheet" media="screen" />
	<![endif]-->


</head><body>
	<!--skiplinks-->
<ul class="Skiplinks sr-only">
  <li><a href="#main_content">Vai al Contenuto</a></li>
  <li><a href="#sp-navbar">Vai al menu di portale</a></li>
  <li><a href="#menu" title="accedi al menu">Vai alla navigazione del sito</a></li>
</ul>
<!--/skiplinks-->




	
<!-- portal header -->
<div class="sp_header">
	<div id="header" class="header">
		<div id="sp-navbar" class="sp_navbar sp_views1">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand" href="/configuratore/logout"> <span class="d-brand-dsk">Punto Unico di Accesso</span> <span class="d-brand-mo"><abbr title="PUA">PUA</abbr></span>
						</a>
					</div>
					<!--fine navbar-header -->
					
					<!--fine menu utilities-->
				</div>
				<!-- fine utilities-->
			</div>
			<!-- fine sp_row-->
		</div>
		<div id="sp-navbar-2" class="sp_navbar sp_views2">
			<div class="sp_container">
				<div class="sp_row">
					<div class="navbar-header">

						<a class="navbar-brand" href="/configuratore/utenti">
							<span class="d-brand-dsk">Configuratore servizi sanitari digitali</span>

						</a>
					</div>
					<!--fine navbar-header -->

				</div>
				<!-- fine utilities-->
			</div>
			<!-- fine sp_row-->
		</div>
	</div>
	<!--sp_navbar-->
	<!--- serve per l'offcanvas menu-->
	<div class="bsnav-mobile">
		<div class="bsnav-mobile-overlay"></div>

		
	</div>
	<!--- FINE serve per l'offcanvas menu-->


</div>
<!-- end portal header -->


<div id="main_content" class="app-view">
    <div class="app-container">
        <div class="app-row justify-content-md-center">
            <div class="col-12 col-lg-12">
                <div class="menu-interno d-lg-block" id="menu-interno">
                    <!-- finemenu dropdown responsive-->
                    
                </div>
			</div>
			<div class="col-12 col-lg-8">
                <h1 class="titolocard mt-7">Errore generico</h1>
                <div class="card-box card-box-white card-box-notifiche">
                    <div class="card-box-body row">
                        <div class="col-12">
                        	<p>${pageContext.errorData.throwable.message}</p>

    

<%--
                        <td><b>Stack trace:</b></td>
                        <td>
                            <c:forEach var="trace" items="${pageContext.errorData.throwable.stackTrace}">
                                <p>${trace}</p>
                            </c:forEach>
     --%>
                  
                        </div>
                    </div>

                </div>
            </div><!--fine app-row-->


        </div>
        <!--fine app-row-->

        


        

        

        
    </div>
</div>




<footer id="footer-ptu">
  <div class="footer-row-one">
    <div class="container">
      <div class="row row-logo">

        <div class="col-md-auto col-12">
          <img src="/ris/im/logo-regione-piemonte.svg" alt="Regione Piemonte" title="Regione Piemonte" class="csi-logo-regione">
        </div>

      </div>
    </div>
  </div>
  <div class="footer-row-two">
    <div class="container">
      <div class="row row-logo">
        <div class="col-md-auto col-12">
          <img src="/ris/im/logo-la-mia-salute.svg" alt="Salute Piemonte" title="Salute Piemonte" class="portale-app-logo">
          <span class="sr-only">salute piemonte</span>
        </div>
        <div class="col-md-auto col-12">
          <img src="/ris/im/logo-csi.svg" alt="CSI" class="csi-logo" title="CSI your digital partner">
        </div>
        </div>
        <div class="row pb-9 text-center text-md-left align-center">
          <div class="border-footer mx-md-4 col col-12"></div>
          <div class="justify-center col col-12">
            <ul class="list-inline-footer">
              <li class="list-inline-item"><a href="https://www.salutepiemonte.it/cookie-policy?nid=65" target="_blank">Cookie Policy</a></li>
            </ul>
          </div>
        </div>
    </div>

  </div>
</footer>



<script src="/ris/lib/vendor/jquery-3.2.1.min.js"></script>

<script src="/ris/lib/popperjs/popper.min.js"></script>

<script src="/ris/lib/vendor/bootstrap-4.0.0.min.js"></script>
<script src="/ris/lib/bootstrap-material-design/bootstrap-material-design.js"></script>
<script>$(document).ready(function() { $('body').bootstrapMaterialDesign(); });</script>
<script src="/ris/js/custom.js"></script>


<link rel="stylesheet" href="/ris/css/jquery-ui.css">
<script src="/ris/js/moment-with-locales.min.js"></script>
<script src="/ris/js/bootbox.all.min.js"></script>
<script src="/ris/js/jquery-ui.min.js"></script>






</body>
</html>

