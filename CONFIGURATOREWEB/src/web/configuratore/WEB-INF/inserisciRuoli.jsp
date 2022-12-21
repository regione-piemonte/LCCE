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
          <div class="link mt-9">
            <a href="${root}/ruoli" class="link-prev"> Indietro</a>
          </div>
         
                  <form:form action="${root}/insertRuoli" method="post" modelAttribute="ruolo">
		  
          <div class="card-box card-box-white card-box-notifiche mt-2">
              <div class="card-box-body row">
                <div class="col-12">
                  <h1 class="titolocard">Configura nuovo ruolo</h1>
                  <c:import url="common/alert.jsp"/>  
                  </div>  
                
               

                    <div class="form-group form-group-gestutenti">
                      <label for="codice" class="bmd-label-floating">Codice</label>
                      <input type="text" class="form-control" id="codice" name= "codice" 
							maxlength="20"	value="${ruolo.codice}" path="codice"/>
                      <span class="bmd-help">Inserire un codice</span>
                    </div>

                    <div class="form-group bmd-form-group">
                    <label for="Descrizione" class="bmd-label-floating">Descrizione</label>
                    <input type="text" class="form-control" id="descrizione" name= "descrizione"
							maxlength="150"	value="${ruolo.descrizione}" path="descrizione"/>
                    <span class="bmd-help">Inserire una descrizione</span>
                    </div>
                    
                    <div class="switch">
								<c:if test="${ruolo.flagAttivo eq 'true'}">
									<script type="text/javascript">
									document.getElementById("active").checked = true;
									</script>
									</c:if>
                      <label>
                      <input type="checkbox" checked="checked" id="active" value="true" 
							 name="flagAttivo" path="flagAttivo" > Attivo  </label>
                    </div>
                    
                    


              </div>
              
            </div>
            <!-- fine card -->
            
            
                 
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
                                    <!-- form:hidden path="collTipoSel"/-->
                     
                                    <div id="ruoliSelContainer"></div>

                                    <div>
                                        <a class="btn btn-outline-aggiungi" role="button" id="addruoliSelBtn">Aggiungi
                                            ruolo</a>
                                    </div>
                                </div>
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

                            </div>
                        </div>
                        
            
            
            
                    <!-- tasti conferma -->
                    <div class="row prosegui mt-9">
                      <div class="col-md-6 col-sm-12 conferma">
                        <a class="btn btn-info btn-primary"
							href="${root}/annullaInsert" value="annulla">Annulla</a>
                      </div>
                    
                      <div class="col-md-6 col-12 text-md-right mt-2 mt-md-0">

                          <input class="btn btn-primary" type="submit" value="Salva le modifiche">
                       <!--   <div class="alert alert-success" role="alert">
          					  <p>La configurazione &egrave; stata salvata correttamente</p> 
        		 		 </div>-->
                   		 </div>
                   		</div>
                   	

                   	</form:form>            
          
           </div>
         
        </div><!--fine app-row-->


      </div><!--fine app-row-->


    </div>
</div>


<c:import url="ruoliPrototipe.jsp"/>

<c:import url="common/footer.jsp" /> 


<script src="<c:url value="/js/editRuolo.js" />" lang="javascript"></script>

<script>
    $(() => ruoliPage.initDataEditPage());
</script>


