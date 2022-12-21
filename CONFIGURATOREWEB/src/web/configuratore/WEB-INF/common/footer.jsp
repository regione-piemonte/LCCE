<%@ taglib uri="http://www.csi.it/taglibs/remincl-1.0" prefix="r" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</div>
<r:include url="/include/footer.html"
           resourceProvider="rp" />
<%--<footer id="footer-ptu">
    <div class="footer-row-one">
        <div class="container">
            <div class="row row-logo">

                <div class="col-md-auto col-12">
                    <img src="<c:url value="/im/logo-regione-piemonte.svg" />" alt="Regione Piemonte"
                         title="Regione Piemonte"
                         class="csi-logo-regione"/>
                </div>

            </div>
        </div>
    </div>
    <div class="footer-row-two">
        <div class="container">
            <div class="row row-logo">
                <div class="col-md-auto col-12">
                    <img src="<c:url value="/im/logo-la-mia-salute.svg" />" alt="Salute Piemonte"
                         title="Salute Piemonte"
                         class="portale-app-logo">
                    <span class="sr-only">salute piemonte</span>
                </div>
                <div class="col-md-auto col-12">
                    <img src="<c:url value="/im/logo-csi.svg" />" alt="CSI" class="csi-logo"
                         title="CSI your digital partner"/>
                </div>
            </div>
            <div class="row pb-9 text-center text-md-left align-center">
                <div class="border-footer mx-md-4 col col-12"></div>
                <div class="justify-center col col-12">
                    <ul class="list-inline-footer">
                        <li class="list-inline-item"><a href="https://www.salutepiemonte.it/cookie-policy?nid=65"
                                                        target="_blank">Cookie Policy</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</footer>--%>

<r:include url="/include/footer-js.html"
           resourceProvider="rp" />

<link rel="stylesheet" href="/ris/css/jquery-ui.css">
<script src="/ris/js/moment-with-locales.min.js"></script>
<script src="/ris/js/bootbox.all.min.js"></script>
<script src="/ris/js/jquery-ui.min.js"></script>
<%--<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet"/>--%>
<%--<script src="<c:url value="/lib/vendor/jquery-3.2.1.min.js" />"></script>

<script src="<c:url value="/lib/popperjs/popper.min.js" />"></script>

<script src="<c:url value="/lib/vendor/bootstrap-4.0.0.min.js" />"></script>
<script src="<c:url value="/lib/bootstrap-material-design/bootstrap-material-design.js" />"></script>
<script>$(document).ready(function () {
    $('body').bootstrapMaterialDesign();
});</script>
<script src="<c:url value="/js/custom.js"/>"></script>
<script src="<c:url value="/lib/vendor/jquery-ui.min.js" />"></script>
<script>
    $(function() {
        $("#datepickerrupar").datepicker();
    });
</script>--%>

<style>
	.loading {
	  position: fixed;
	  top: 0;
	  left: 0;
	  right: 0;
	  bottom: 0;
	  background-color: #ffffff;
	  opacity: 80%;
	}
	
	.spinner {
	   position: absolute;
	   left: 50%;
	   top: 50%;
	   height:60px;
	   width:60px;
	   margin:0px auto;
	   -webkit-animation: rotation .6s infinite linear;
	   -moz-animation: rotation .6s infinite linear;
	   -o-animation: rotation .6s infinite linear;
	   animation: rotation .6s infinite linear;
	   border-left:6px solid rgba(0,174,239,.15);
	   border-right:6px solid rgba(0,174,239,.15);
	   border-bottom:6px solid rgba(0,174,239,.15);
	   border-top:6px solid rgba(0,174,239,.8);
	   border-radius:100%;
	}
	
	@-webkit-keyframes rotation {
	   from {-webkit-transform: rotate(0deg);}
	   to {-webkit-transform: rotate(359deg);}
	}
	@-moz-keyframes rotation {
	   from {-moz-transform: rotate(0deg);}
	   to {-moz-transform: rotate(359deg);}
	}
	@-o-keyframes rotation {
	   from {-o-transform: rotate(0deg);}
	   to {-o-transform: rotate(359deg);}
	}
	@keyframes rotation {
	   from {transform: rotate(-360deg);}
</style>

<div class="loading" style="display: none;">
  <div class="spinner"></div>
</div>
<script type="text/javascript">
	function showLoading() {
		$('div.loading').fadeIn();
	}
	
	function hideLoading() {
		$('div.loading').fadeOut();
	}
</script>
</body>

</html>
