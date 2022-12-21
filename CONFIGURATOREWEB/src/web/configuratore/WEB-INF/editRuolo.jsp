<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="root">${pageContext.request.contextPath}</c:set>

<c:import url="common/top.jsp" />
<%@include file="common/controllofunzionalita.jsp" %>

<div id="main_content" class="app-view">
    <div class="app-container">
      <div class="app-row justify-content-md-center">
        <div class="col-12 col-lg-8">
          
          <form:form action="${root}/modificaRuolo" method="post" modelAttribute="ruolo">
        
          <div class="link mt-9">
            <a href="${root}/ruoli" class="link-prev"> Indietro</a>
          </div>
          
          <div class="card-box card-box-white card-box-notifiche mt-2">
              <div class="card-box-body row">
                <div class="col-12">
                  <h1 class="titolocard">Modifica Configurazione Ruolo</h1>
                  <jsp:include page="common/alert.jsp"/>
                  </div>  
                  
                  
                  <!--  form class="form-newruoli"-->
                  		 <input type="hidden" name="id" value="${ruolo.id}">

                    <div class="form-group form-group-gestutenti">
                      <label for="codice" class="bmd-label-floating">Codice</label>
                      	<input type="text" class="form-control" id="codice" name= "codice" 
								value="${ruolo.codice}" path="codice" readonly/>
                    </div>
                    
                       <div class="form-group form-group-gestutenti">
                      <label for="codice" class="bmd-label-floating">Descrizione</label>
                      <input type="text" class="form-control" id="descrizione" name= "descrizione" 
							maxlength="150"	value="${ruolo.descrizione}" path="descrizione"/>
                    </div>
                    
                    <div class="switch">			
                      	<label>
                   		    <input type="checkbox" checked="checked" id="active" 
								 name="flagAttivo"> Attivo </label>
								 <c:if test="${ruolo.flagAttivo eq 'false'}">
										<script type="text/javascript">
										document.getElementById("active").checked = false;
										</script>
								</c:if> 		
                    	</div>
                  
                  


                          <!-- /form  -->
                   	
                   </div>
                 </div>
                 
                        <!-- RUOLI -->
                        <div class="card-box card-box-white card-box-notifiche mt-2">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Ruoli selezionabili dall'operatore </h2>
                                    <spring:bind path="ruoliSel">
                                        <c:if test="${status.error}">
                                            <div class="alert alert-danger">
                                                <c:forEach items="${status.errorMessages}" var="message">
                                                    <p>${message}</p>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </spring:bind>

                                    <form:hidden path="ruoliSel"/>
                                    <!-- form:hidden path="collTipoSel"/ -->
                     
                                    <div id="ruoliSelContainer"></div>

                                    <div>
                                        <a class="btn btn-outline-aggiungi" role="button" id="addruoliSelBtn">Aggiungi
                                            ruolo</a>
                                    </div>
                                </div>
                                <!-- div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class=" col-sm-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" type="button"
                                                    id="rolesUndoBtn">Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div -->
                            </div>
                        </div>
                 

                        <!-- RUOLI COMPATIBILI -->
                        <div class="card-box card-box-white card-box-notifiche mt-2">
                            <div class="card-box-body row">
                                <div class="col-12">
                                    <h2 class="titolocard">Ruoli Compatibili </h2>
                                    <spring:bind path="ruoliSel">
                                        <c:if test="${status.error}">
                                            <div class="alert alert-danger">
                                                <c:forEach items="${status.errorMessages}" var="message">
                                                    <p>${message}</p>
                                                </c:forEach>
                                            </div>
                                        </c:if>
                                    </spring:bind>

                                    <form:hidden path="ruoliCompatibili"/>
                     
                                    <div id="ruoliCompatibiliContainer"></div>

                                    <div>
                                        <a class="btn btn-outline-aggiungi" role="button" id="addruoliCompatibiliBtn">Aggiungi
                                            ruolo</a>
                                    </div>
                                </div>
                                <!--  div class="col-12">
                                    <div class="row prosegui mt-9">
                                        <div class=" col-sm-12 indietro">
                                            <button class="btn btn-primary btn-outline-primary" type="button"
                                                    id="ruoliCompatibilisUndoBtn">Annulla
                                            </button>
                                        </div>
                                    </div>
                                </div  -->
                            </div>
                        </div>
                        
                        
                        
                        
                      <div class="row prosegui mt-9">
                      	<div class="col-md-6 col-sm-12 conferma">
                            <c:url var="annullaModifica" value="/updateRuolo">
                                <c:param name="id" value="${ruolo.id}" />
                            </c:url>
                      	  <a class="btn btn-info btn-primary"
								href="${annullaModifica}" value="annulla">Annulla</a>
                     	 </div>
                    
                      <div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">
                          <input class="btn btn-primary" type="submit" value="Salva le modifiche">
                              </div>
                   		</div>
                   		                 
                 </form:form>
                 
              </div>
            </div>
    </div>
</div>

<c:import url="ruoliPrototipe.jsp"/>

<c:import url="common/footer.jsp" /> 

<script src="<c:url value="/js/editRuolo.js" />" lang="javascript"></script>

<script>
    $(() => ruoliPage.initDataEditPage());
</script>

