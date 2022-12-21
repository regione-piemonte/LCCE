<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

            <%@ page import="it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp" %>
                <c:set var="UTENTE_NON_PRESENTE" value="<%=ConstantsWebApp.UTENTE_NON_PRESENTE%>" />
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
                    <script src="${pageContext.request.contextPath}/js/abilitazioneMassivaModal.js"
                        lang="javascript"></script>
                    <div id="main_content" class="app-view">
                        <div class="app-container">
                            <div class="app-row">
                                <div class="col-12 col-lg-8">
                                    <div class="card-box card-box-white card-box-notifiche">
                                        <div class="card-box-body row">
                                            <div class="col-12">
                                                <form:form id="abilitazioneForm"
                                                    action="/configuratore/cercaUtentiAbilitazioneMassiva" method="post"
                                                    modelAttribute="abilitazione" class="form-inline">

                                                    <%--<spring:bind path="selected">
                                                        <c:if test="${status.error}">
                                                            <div class="alert alert-danger">
                                                                <c:forEach items="${status.errorMessages}"
                                                                    var="message">
                                                                    <p>${message}</p>
                                                                </c:forEach>
                                                            </div>
                                                        </c:if>
                                                        </spring:bind>

                                                        <form:hidden path="selected" /> --%>
                                                        <input type="hidden" name="selected" />

                                                

                                                        <div class="form-group mx-2 bmd-form-group col-7"
                                                            style="overflow: hidden">
                                                            <div class="w-100">
                                                                <div class="form-group mx-2 bmd-form-group w-100"
                                                                    style="padding-bottom: 30px">
                                                                    <label for="sol" class="bmd-label-floating">Servizio
                                                                        Online</label>
                                                                    <select class="w-100 form-control" name="solSelezionabili"
                                                                        id="solSelezionabili" path="sol">
                                                                        <option selected value="">
                                                                            Seleziona un SOL</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="form-group mx-2 bmd-form-group col-7"
                                                            style="overflow: hidden">
                                                            <div class="w-100">
                                                                <div class="form-group mx-2 bmd-form-group w-100"
                                                                    style="padding-bottom: 30px">
                                                                    <label for="sol" class="bmd-label-floating">Profilo</label>
                                                                    <select class="w-100 form-control" name="profili"
                                                                        id="profili" path="profili" disabled="disabled">
                                                                        <option selected value="">
                                                                            Seleziona un Profilo</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </div>

                                                      <span class="col-md-12 form-group bmd-form-group">
                                                            <!-- needed to match padding for floating labels -->
                                                            <button type="submit" class="btn btn-primary"
                                                                id="btnRicercaMassiva">Abilita</button>
                                                        </span>
                                                </form:form>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>