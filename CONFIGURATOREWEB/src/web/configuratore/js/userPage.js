let userPage = {

    /**
     * Messaggi per gli alert e le conferme
     * */
    messages: {
        confirmUserEditSaved: "<p>La richiesta configurazione di ${name} ${surname} &egrave; stata salvata correttamente.</p><p>E' stata inviata una mail a ${name} ${surname} con il riepilogo della configurazione.</p>",
        confirmDisableAll: "<p>Verranno disabilitate tutte le configurazioni per l'utente.</p><p>Continuare?</p>",
        confirmSolIntegrity: "<p>Procedendo alla modifica, verranno cancellati tutti i SoL inseriti fino ad ora.</p><p>Procedere?</p>",
        confirmSolIntegrityCollocation: "<p>Procedendo alla modifica, verranno cancellati tutti i profili SoL relativi alla Collocazione eliminata.</p><p>Procedere?</p>",
        confirmSolIntegrityRole: "<p>Procedendo alla modifica, verranno cancellati tutti i profili SoL relativi al ruolo eliminato.</p><p>Procedere?</p>",
        confirmProfileNotCompleted: "<p>Non hai completato la configurazione. Vuoi continuare con l'operazione di salvataggio e rimandare ad un momento successivo la configurazione?</p>",
        confirmRichiestaRupar: "<p>Con la richiesta credenziali RUPAR viene avviato il processo di richiesta credenziali.</p><p>Sei sicuro di voler proseguire?</p>",
        confirmInvioMail: "<p>Vuoi inviare una email all'utente informandolo della nuova profilazione?</p>",
        confirmNoRuparCredentialsRequest: "<p>I campi Inquadramento contrattuale e Telefono non sono compilati.</p> <p> Tali campi sono necessari per la richiesta delle credenziali RUPAR</p> <p>Sei sicuro di voler proseguire?</p>",
        errorValueAlreadyPresent: "<p>Valore gi&agrave; inserito.</p>",
        errorFieldsNotFilled: "<p>Compilare tutti i campi precedenti prima di inserirne di nuovi.</p>",
        errorNoRolesSelected: "<p>Per inserire un SoL, &egrave; necessario configurare prima almeno un Ruolo.</p>",
        errorNoCollocationSelected: "<p>Per inserire un SoL, &egrave; necessario configurare prima almeno una Collocazione.</p>",
        errorFunctionalitiesModalSelectSolProfileFirst: "<p>Selezionare prima un profilo SoL per visualizzarne le funzionalit&agrave;.</p>",
        errorRoleNotCompatible: "<p>Il ruolo non pu&ograve essere selezionato in quanto non compatibile con i ruoli gi&agrave assegnati</p>",
        errorSolNonTrovato:"<p>Non &egrave possibile procedere con la configurazione perch&egrave non sono stati trovati dei SOL per l'azienda/struttura e ruolo selezionati.</p>",
        errorConfigrazioneEsistente:"<p>La configurazione inserita &egrave gi&agrave presente.</p>",
        errorSolConfiguratorePresente:"<p>Questo utente ha gi&agrave un profilo titolare o delegato per il Configuratore dei serivizi digitali e per la collocazione scelta.</p>",
        errorSolConfiguratorePresenteNotVisible:"<p>Non &egrave possibile abilitare questo utente al Configuratore dei servizi digitali per la collocazione scelta.</p>",
        confirmUserEditSavedErrorMail: "<p>La richiesta configurazione di ${name} ${surname} &egrave; stata salvata correttamente.</p><p>Non &egrave stato possibile  inviare una mail a ${name} ${surname} con il riepilogo della configurazione.</p>",
        errorEmailNotInserted: "<p>Inserire indirizzo email</p>",
		errorTelefonoNotInserted: "<p>Inserire numero di cellulare</p>",
		errorTelefonoNotCompliant: "<p>Inserire un numero di cellulare valido</p>",
		errorDataFineValiditaNotInserted: "<p>La data fine validit&agrave; di ogni configurazione &egrave; obbligatoria</p>",
        confirmUserEditSavedNoMail: "<p>La richiesta configurazione di ${name} ${surname} &egrave; stata salvata correttamente.</p>",
    },

    /**
     * Contiene la mappatura per tutti i "template" di ogni sezione. Al ready del document, vengono presi tutti i blocchi
     * nel div#prototypes e inseriti qua dentro.
     */
    prototypes: {},

    /**
     * I valori di default, ovvero
     * - nell'inserimento, i valori che arrivano dopo la post della form dall'utente, ma viene rivisualizzata la pagina
     *   perche' la form contiene errori.
     * - nella modifica, i valori che sono gia' salvati a DB.
     */
    defaults: {},

    /**
     * (Solo modifica) Flag per ogni sezione, che indica la visibilita' corrente della sezione.
     */
    sectionsVisibilityState: {},

    /**
     * Lista dei SoL selezionabili dall'utente. Viene settato da userPage.getSelectableSols quando vengono inserite
     * le collocazioni, e viene utilizzato all'inserimento di ogni SoL. E' un array per ogni sol, con relativo array di
     * collocazioni per cui si applica, con relativi profili selezionabili.
     */
    selectableSols: null,

    /**
     * Con la stessa struttura di userPage.selectableSols, traccia tutti i SoL selezionati dall'utente. Utile per quando
     * vengono modificate le collocazioni, infatti viene prima manipolato questo albero, e poi ricostruita l'intera
     * sezione dei SoL.
     */
    selectedSols: [],

    /**
     * Divisore per i dati dei profili SoL.
     */
    profilesDivisor: '|',

    //ListaCollocazioni
    collocazioniSol : null,

    selectCollocationIds:[],

    //Check se il Sol scelto corrisponde al configuratore
    isSolConfig: false,

    //Check se mail gia mandata
    isMailInviata:false,

    /**
     * Inizializza entrambe le pagine. Carica i template, e setta il datepicker all'internazionalizzazione
     * corretta.
     */
    
    initCommon: function () {
        this.loadPrototypes();

        // set calendario
        $.datepicker.regional['it'] = {
            closeText: 'Chiudi',
            currentText: 'Oggi',
            monthNames: ['Gennaio', 'Febbraio', 'Marzo', 'Aprile', 'Maggio', 'Giugno', 'Luglio', 'Agosto', 'Settembre', 'Ottobre', 'Novembre', 'Dicembre'],
            monthNamesShort: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Giu', 'Lug', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
            dayNames: ['Domenica', 'Luned&#236', 'Marted&#236', 'Mercoled&#236', 'Gioved&#236', 'Venerd&#236', 'Sabato'],
            dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab'],
            dayNamesMin: ['Do', 'Lu', 'Ma', 'Me', 'Gio', 'Ve', 'Sa'],
            dateFormat: 'dd/mm/yy',
            firstDay: 1
        };

        $.datepicker.setDefaults({changeMonth: true, changeYear: true});
        $.datepicker.setDefaults($.datepicker.regional['it']);

        $('#statoUtente').change(function (e) {
            if ($(this).prop('checked')) {
                $('#dataFineValidita').val("");
            }
            $('#stato').val($(this).prop('checked'));
        });

        $('input[name="sessoUtente"]').change(function (e) {
              if ($(this).prop('checked')) {
                $('#sesso').val($(this).val());
              }
        });
    },

    /**
     * Inizializza la pagina di inserimento. Setta gli eventuali valori di default, costruisce i datepicker e binda
     * ogni pulsante visibile sulla pagina.
     *
     * @returns {Promise<void>} promise di fine caricamento pagina.
     */
    initDataInsertPage: async function () {
		showLoading();
        this.initCommon();

        await this.loadDefaults();

        this.setRolesToDefaults();
        await this.setCollocationsToDefaults();
        //await this.setSolsToDefaults();

        this.selectedSols = this.defaults.sol;
        this.defaults = null;

        this.buildDatePicker('#dataDiNascita', settings => settings.maxDate = new Date());
        this.buildDatePicker('#dataFineValidita', settings => settings.minDate = new Date());

        $('#addRoleBtn').click(() => this.addRolePrototype());
        $('#addCollocationBtn').click(() => this.addCollocationPrototype());
        $('#addSolBtn').click(() => this.addSolPrototype());

        $('#undoUserDataBtn').click(() => this.undoUserData());
        $('#rolesUndoBtn').click(() => this.undoRoles());
        $('#collocationsUndoBtn').click(() => this.undoCollocations());
        $('#solUndoBtn').click(() => this.undoSol());

        $('#formUndoBtn').click(() => this.undoForm());
        $('#formSubmitBtn').click(() => this.submitForm());

        $('#sendMailToUserBtn').click(e => {
            e.preventDefault();
            this.showConfirm(this.messages.confirmInvioMail, null, 'warning', result => {
                if (result) {
                   
                    $('#formMail').submit();
                }
            })
        });

        /*$('#backForm a.link-prev').click(e => {
            e.preventDefault();
            $('#backForm').submit();
        });*/
        
        hideLoading();
    },

    /**
     * Inizializza la pagina di inserimento, quando l'operatore ha salvato l'utente con successo.
     */
    initDataSentSuccessPage: function () {
        this.initCommon();

        $('#backForm a.link-prev').click(e => {
            e.preventDefault();
            $('#backForm').submit();
        });

        $('#sendMailToUserBtn[href="#"]').click(e => {
            e.preventDefault();
            this.showConfirm(this.messages.confirmInvioMail, null, 'warning', result => {
                if (result) {
                    
                    $('#formMail').submit();
                }
            })
        });

        $('#askForRuparBtn[href="#"]').click(e => {
            e.preventDefault();
            this.showConfirm(this.messages.confirmRichiestaRupar, null, 'warning', result => {
                if (result) $('#formRupar').submit();
            })
        });
    },

    /**
     * Inizializza la pagina di modifica. Setta gli eventuali valori di default, costruisce i datepicker e binda
     * ogni pulsante visibile sulla pagina.
     *
     * @returns {Promise<void>} promise di fine caricamento pagina.
     */
    initDataEditPage: async function (dataFromAura) {
		showLoading();
        this.initCommon();
        
		dataFromAura = true;
		
        this.sectionsVisibilityState['user-data'] = true;
        this.toggleUserData(false, dataFromAura);

        await this.loadDefaults();

        this.setRolesToDefaults();
        await this.setCollocationsToDefaults();
        await this.setSolsToDefaults();

        this.buildDatePicker('#dataDiNascita', settings => settings.maxDate = new Date());
        this.buildDatePicker('#dataFineValidita', settings => settings.minDate = new Date());

        $('#disableAllConfigsBtn').click(() => {
            this.showConfirm(this.messages.confirmDisableAll, null, 'warning', result => {
                if (result) {
					showLoading();
					$('#disableAllConfigsForm').submit();
				}
            })
        });

        $('#addRoleBtn').click(() => this.addRolePrototype());
        $('#addCollocationBtn').click(() => this.addCollocationPrototype());
        $('#addSolBtn').click(() => this.addSolPrototype());

        $('#userDataToggleBtn').click(e => {
            e.preventDefault();
            this.toggleUserData(false, dataFromAura);
        });
        $('#rolesToggleBtn').click(e => {
            e.preventDefault();
            this.toggleRoles(false);
        });
        $('#collocationsToggleBtn').click(e => {
            e.preventDefault();
            this.toggleCollocations(false);
        });

        $('#undoUserDataBtn').click(() => this.toggleUserData(true, dataFromAura));
        $('#rolesUndoBtn').click(() => this.toggleRoles(true));
        $('#collocationsUndoBtn').click(() => this.toggleCollocations(true));

        $('#saveFormBtn').click(e => {
            e.preventDefault();
            this.submitForm();
        });

        $('#backForm a.link-prev').click(e => {
            e.preventDefault();
            $('#backForm').submit();
        });

        $('#backResultBtn').click(e => {
            e.preventDefault();
            $('#backForm').submit();
        });

        $('#sendMailToUserBtn[href="#"]').click(e => {
            e.preventDefault();
            this.showConfirm(this.messages.confirmInvioMail, null, 'warning', result => {
                if (result) {
                    
                    $('#formMail').submit();
                }
            })
        });
        
        $('#askForRuparBtn[href="#"]').click(e => {
            e.preventDefault();
            this.showConfirm(this.messages.confirmRichiestaRupar, null, 'warning', result => {
                if (result) $('#formRupar').submit();
            })
        });        
        hideLoading();
    },

    /**
     * Costruisce un datepicker, bindando anche il tasto 'calendario' posto affianco all'input.
     *
     * @param element l'input su cui costruire il datepicker. Puo' essere un oggetto jQuery, o un selettore.
     * @param settingsCallback una callback, a cui viene passato l'oggetto di settings del datepicker, per poterlo
     * modificare.
     */
    buildDatePicker: function (element, settingsCallback) {
        element = $(element);
        if (!$(element).attr('readonly')) {
            let settings = {constrainInput: true};
            if (settingsCallback) settingsCallback(settings);

            $(element).datepicker(settings).blur(e => {
                let mom = moment(e.target.value, 'DD/MM/YYYY', true);
                if (!mom.isValid() || (settings.minDate && mom.isBefore(settings.minDate))
                    || (settings.maxDate && mom.isAfter(settings.maxDate))) $(e.target).val('');
            }).siblings('div').find('button.btn-calendar').click(() => {
                $(element).focus();
            });
        }
    },

    /**
     * Distrugge i datepicker passati, unbindando anche il tasto 'calendario' posto affianco all'input.
     *
     * @param elements gli input per cui distruggere il datepicker. Possono essere oggetti jQuery, o selettori.
     */
    destroyDatePicker: function (...elements) {
        elements.forEach(element => {
            $(element).datepicker('destroy')
                .off('blur')
                .siblings('div')
                .find('button.btn-calendar')
                .off('click');
        });
    },

    /**
     * Inserisce un prototipo vuoto al container indicato.
     *
     * @param containerId l'ID del container in cui inserire il nuovo prototipo.
     * @param prototypeName il nome del prototipo. E' identificato dall'attributo data-prototype.
     * @param groupName la classe identificativa del prototipo.
     * @returns {*|Window.jQuery|[]} la sezione appena inserita.
     */
    insertPrototype: function (containerId, prototypeName, groupName) {
        return $('#' + containerId)
            .append(this.prototypes[prototypeName])
            .children((groupName ? 'div.' + groupName : 'div') + ':last');
    },

    /**
     * Mostra un alert, come modale bootstrap.
     *
     * @param msg il messaggio da visualizzare.
     * @param title il titolo del modal. Se null, il titolo viene dedotto dal tipo del modale.
     * @param type il tipo del modale. Puo' essere: info - warning - error
     */
    showAlert: function (msg, title, type) {
        let cssClass;
        if (type === 'warning') {
            cssClass = 'alert-warning';
            title = !title ? 'Attenzione!' : title;
        } else if (type === 'error') {
            cssClass = 'alert-danger';
            title = !title ? 'Errore!' : title;
        } else {
            cssClass = 'alert-info';
            title = !title ? 'Informazione' : title;
        }

        bootbox.alert({
            title: '<b>' + title + '</b>',
            message: '<div role="alert" class="alert alert-heading ' + cssClass + '">' + msg + '</div>',
            size: 'large',
            centerVertical: true
        });
    },

    /**
     * Mostra una finestra di conferma, come modale bootstrap.
     *
     * @param msg il messaggio da visualizzare.
     * @param title il titolo del modal. Se null, il titolo viene dedotto dal tipo del modale.
     * @param type il tipo del modale. Puo' essere: info - warning - error
     * @param callback la callback che viene eseguita dopo che l'utente preme uno dei due pulsanti. Alla callback viene
     * passato un parametro di tipo booleano, che vale a true se l'utente ha premuto OK, false altrimenti.
     */
    showConfirm: function (msg, title, type, callback) {
        let cssClass;
        if (type === 'warning') {
            cssClass = 'alert-warning';
            title = !title ? 'Attenzione!' : title;
        } else if (type === 'error') {
            cssClass = 'alert-danger';
            title = !title ? 'Errore!' : title;
        } else {
            cssClass = 'alert-info';
            title = !title ? 'Informazione' : title;
        }

        bootbox.confirm({
            title: '<b>' + title + '</b>',
            message: '<div role="alert" class="alert alert-heading ' + cssClass + '">' + msg + '</div>',
            buttons: {
                confirm: {label: 'S&igrave;'},
                cancel: {label: 'No'}
            },
            size: 'large',
            centerVertical: true,
            callback: callback
        });
    },

    /**
     * Carica tutti i prototipi dal DOM e li inserisce in userPage.prototypes.
     */
    loadPrototypes: function () {
        let prototypesContainer = $('#prototypes');
        $(prototypesContainer)
            .find('div[data-prototype]')
            .each((i, e) => this.prototypes[$(e).attr('data-prototype')] = $(e).html());
        $(prototypesContainer).remove();
    },

    /**
     * Poiche' le select non possono essere readonly, con questo metodo viene simulato il readonly, disabilitando la
     * select in questione, e creando affianco un input hidden con il valore selezionato nella select, in modo che viene
     * inviato nell'eventuale form.
     *
     * @param select la select.
     * @param state se true, la select viene resa readonly, false altrimenti.
     */
    toggleReadonlySelect: function (select, state) {
        let isDisabled = $(select).prop('disabled');
        if (state && !isDisabled) {
            $(select).prop('disabled', true)
                .parents('div')
                .first()
                .addClass('is-filled')
                .append('<input type="hidden" name="' + $(select).attr('name') + '" value="' + $(select).val() + '"/>')
        } else if (!state && isDisabled) {
            $(select).prop('disabled', false)
                .parents('div')
                .first()
                .addClass('is-filled')
                .find('input[name="' + $(select).attr('name') + '"]')
                .remove();
        }
    },

    toggleReadonlyCheckbox: function (inputCheck, state) {
            let isDisabled = $(inputCheck).prop('disabled');
            if (state && !isDisabled) {
                if($(inputCheck).attr('checked') === "checked") {
                    $(inputCheck).prop('disabled', true)
                        .parents('div')
                        .first()
                        .addClass('is-filled');
                }
            } else if (!state && isDisabled) {
                $(inputCheck).prop('disabled', false)
                    .parents('div')
                    .first()
                    .addClass('is-filled');
            }
        },

    // pulsanti di aggiunta

    /**
     * Aggiunge una nuova sezione "Ruolo", al click del pulsante apposito. Per effettuare l'inserimento, i ruoli
     * inseriti precedentemente devono essere compilati.
     *
     * @param readonly se true, il ruolo viene aggiunto come readonly: totalmente non modificabile (ad esempio, se
     * l'operatore non ha i permessi di rimuovere/modificare quel ruolo).
     */
    addRolePrototype: function (readonly) {
        let inputs = $('#rolesContainer select[name="ruoli"]');
        if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
            this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
            return;
        }

        let inserted = this.insertPrototype('rolesContainer', !readonly ? 'role-filtered' : 'role-ro-filtered', 'role-group');
        $(inserted).find('select.role-selector')
            .focus(e => $(e.target).data('previous', $(e.target).val()))
            .change(e => this.selectRole(e.target, false));
         if (readonly) {
             $(inserted).find('select.role-selector').each((i, e) => this.toggleReadonlySelect(e, true));
         } else {
             $(inserted).find('button.btn-close').click(e => this.deleteRole(e.target));
         }
    },
    
    addDefaultRolePrototype: function (readonly) {
        let inputs = $('#rolesContainer select[name="ruoli"]');
        if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
            this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
            return;
        }

        let inserted = this.insertPrototype('rolesContainer', !readonly ? 'role' : 'role-ro', 'role-group');
        $(inserted).find('select.role-selector')
            .focus(e => $(e.target).data('previous', $(e.target).val()))
            .change(e => this.selectRole(e.target, false));
        // if (readonly) {
        //     $(inserted).find('select.role-selector').each((i, e) => this.toggleReadonlySelect(e, true));
        // } else {
        //     $(inserted).find('button.btn-close').click(e => this.deleteRole(e.target));
        // }
    },
    
       addRoleSolPrototype: function () {
     
        let inserted = this.insertPrototype('solDefaultsContainer', !readonly ? 'roleSol' : 'role-ro', 'role-groupSol');
        $(inserted).find('select.role-sol')
            .focus(e => $(e.target).data('previous', $(e.target).val()))
            .change(e => this.selectRole(e.target, false));
        // if (readonly) {
        //     $(inserted).find('select.role-sol').each((i, e) => this.toggleReadonlySelect(e, true));
        // } else {
        //     $(inserted).find('button.btn-close').click(e => this.deleteRole(e.target));
        // }
    },
 

    /**
     * Aggiunge una nuova sezione "Collocazione", al click del pulsante apposito. Per effettuare l'inserimento, le
     * collocazioni inserite precedentemente devono essere compilate.
     */
    addCollocationPrototype: function () {
        if ($('#collocationContainer input[name="collocazioni"]').filter((i, e) => $(e).val() === '').length > 0) {
            this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
            return;
        }
	
        let inserted = this.insertPrototype('collocationContainer', 'collocation', 'collocation-group');
       if($(inserted).find('select').val()!==''){
       
        $(inserted).find('div.collocation-search-box').show();
       }
       if($(inserted).find('select').val()!==''){
        if ($(inserted).find('select').val() !== '') {
            $(inserted).find('div.collocation-search-box').show();
            if (!$(inserted).find('select').find('option:selected').data('company')) {
                $(inserted).find('a.collocation-select-company-button').off('click').hide();
            } else {
                $(inserted).find('a.collocation-select-company-button').unbind('click').click(() =>
                    this.selectCollocation(inserted, {
                        id: parseInt($(inserted).find('select').val()),
                        code: '-',
                        desc: null
                    })
                ).show();
            }
        } else {
            $(inserted).find('div.collocation-search-box').hide();
        }

        $(inserted).find('input.coll-code-input').val('');
        $(inserted).find('input.coll-desc-input').val('');
        $(inserted).find('input[name="collocazioni"]').val('');
       }
        $(inserted).find('select').change(e => {
            if ($(e.target).val() !== '') {
                $(inserted).find('div.collocation-search-box').show();
                if (!$(e.target).find('option:selected').data('company')) {
                    $(inserted).find('a.collocation-select-company-button').off('click').hide();
                } else {
                    $(inserted).find('a.collocation-select-company-button').unbind('click').click(() =>
                        this.selectCollocation(inserted, {
                            id: parseInt($(inserted).find('select').val()),
                            code: '-',
                            desc: null
                        })
                    ).show();
                }
            } else {
                $(inserted).find('div.collocation-search-box').hide();
            }

            $(inserted).find('input.coll-code-input').val('');
            $(inserted).find('input.coll-desc-input').val('');
            $(inserted).find('input[name="collocazioni"]').val('');
        });

        $(inserted).find('input.coll-code-input').autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: '/configuratore/ajax/collocazione',
                    dataType: 'json',
                    data: {
                        code: $(inserted).find('select option:selected').text().trim(),
                        codeTerm: request.term
                    },
                    success: data => {
                        let resp = {};
                        data.forEach(function (item, i) {
                            resp[i] = {};
                            resp[i].label = item.codice + ' - ' + item.descrizione;
                            resp[i].value = item.codice;
                            resp[i].id = item.id;
                            resp[i].code = item.codice;
                            resp[i].desc = item.descrizione;
                        });
                        response(resp);
                    }
                });
            },
            minLength: 3,
            select: (event, ui) => this.selectCollocation(inserted, ui.item)
        });

        $(inserted).find('input.coll-desc-input').autocomplete({
            source: (request, response) => {
                $.ajax({
                    url: '/configuratore/ajax/collocazione',
                    dataType: 'json',
                    data: {
                        code: $(inserted).find('select option:selected').text().trim(),
                        descTerm: request.term
                    },
                    success: data => {
                        let resp = {};
                        data.forEach(function (item, i) {
                            resp[i] = {};
                            resp[i].label = item.codice + ' - ' + item.descrizione;
                            resp[i].value = item.descrizione;
                            resp[i].id = item.id;
                            resp[i].code = item.codice;
                            resp[i].desc = item.descrizione;
                        });
                        response(resp);
                    }
                });
            },
            minLength: 3,
            select: (event, ui) => this.selectCollocation(inserted, ui.item)
        });

        $(inserted).find('button.btn-close').click(e => this.deleteCollocation(e.target));
    },

    /**
     * Aggiunge una nuova sezione "Servizi OnLine", al click del pulsante apposito. Per effettuare l'inserimento, i SoL
     * inseriti precedentemente devono essere compilati. Inoltre, deve essere presente almeno un ruolo ed una
     * collocazione.
     * La costruzione del template e' affidata al metodo userPage.buildSolPrototype.
     */
    addSolPrototype: function () {
        if ($('#solContainer select.sol-selector').filter((i, e) => $(e).val() === '').length > 0) {
            this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
            return;
        }

        if ($('#rolesContainer').find('select[name="ruoli"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '')
            .length === 0) {
            this.showAlert(this.messages.errorNoRolesSelected, null, 'error');
            return;
        }

        if ($('#collocationContainer')
            .find('input[name="collocazioni"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '')
            .length === 0) {
            this.showAlert(this.messages.errorNoCollocationSelected, null, 'error');
            return;
        }
        
    //    let collocazioniScelte=  $('#collocationContainer')
    //         					.find('input[name="collocazioni"]')
    //         					.map((i, e) => $(e).val())
    //         					.filter((i, e) => e !== '').toArray();

    //                             $.ajax({
    //                                 url: '/configuratore/ajax/collocazioniSol',
    //                                 dataType: 'json',
    //                                 data: {
    //                                     collocazioni: collocazioniScelte
    //                                 },
    //                                 method: 'POST',
    //                                 traditional: true,
    //                                 success: data => {
    //                                     let resp = {};
    //                                     data.forEach(function (item, i) {
    //                                         resp[i] = {};
    //                                         resp[i].label = item.codice + ' - ' + item.descrizione;
    //                                         resp[i].codice = item.codice;
    //                                         resp[i].id = item.id;
    //                                         resp[i].codice = item.codice;
    //                                         resp[i].descrizione = item.descrizione;
    //                                         resp[i].aziendaSanitaria=item.aziendaSanitaria;
    //                                     });
    //                                     this.collocazioniSol=resp;
                                       
    //                                 }
    //                             } ) ;                   
            					

    //                             // if ($('#solContainer select.coll-sol').filter((i, e) => $(e).val() != '')) {
    //                             //     $('#solContainer select.role-groupSol').show();
    //                             // }	
                             
            

        // se l'alberatura non e' ancora disponibile, allora aspetto che venga scaturito l'evento 'sol-tree-downloaded'
        if (!this.selectableSols) {
            $('#solContainer').on('sol-tree-downloaded', e => {
                $(e.target).off('sol-tree-downloaded');
                this.addSolPrototype();
            });
            return;
        }

        this.buildSolPrototype(false);
       // console.log(this.collocazioniSol)
    },

 
    // pulsanti di annullamento

    /**
     * Svuota la sezione dei dati personali.
     */
    undoUserData: function () {
        $('div.user-data-group input:not([readonly])').val('');
        $('div.user-data-group select').val('');
    },

    /**
     * Svuota la sezione dei ruoli. Per fare cio' deve essere prima controllata la presenza di SoL: se presenti,
     * verranno anch'essi cancellati.
     *
     * @param force se true, il controllo sui SoL non viene effettuato.
     */
    undoRoles: function (force) {
        let callback = () => $('#rolesContainer').find('div.role-group').has('select[name="ruoli"]:not(:disabled)').remove();

        if (force) {
            callback();
        } else {
            this.doWithSolIntegrity(callback);
        }
    },

    /**
     * Svuota la sezione delle collocazioni. Per fare cio' deve essere prima controllata la presenza di SoL: se
     * presenti, verranno anch'essi cancellati.
     *
     * @param force se true, il controllo sui SoL non viene effettuato.
     */
    undoCollocations: function (force) {
        let callback = result => {
            if (result) {
				$('#collocationContainer').find('div.collocation-group').has('div.result-box button.btn-close').remove();
                //$('#collocationContainer').html('');
                this.selectableSols = null;
            }
        }

        if (force) {
            callback(true);
        } else {
            this.doWithSolIntegrity(callback);
        }
    },

    /**
     * Svuota la sezione dei SoL. Vengono annullati sia i SoL inseriti che quelli di default (in pagina di modifica).
     */
    undoSol: function () {
        $('#solContainer').html('');
        $('#solDefaultsContainer').html('');
    },

    /**
     * Svuota completamente la form.
     */
    undoForm: function () {
        this.undoSol();
        this.undoCollocations(true);
        this.undoRoles(true);
        this.undoUserData();
    },

    // ROLES FUNCTIONS

    /**
     * Eseguita alla selezione di un ruolo, o quando viene costruito un ruolo di default. Controlla se il ruolo non e'
     * stato gia' inserito, e lo fa verificando la presenza dei SoL.
     *
     * @param select la select del ruolo che e' stata appena cambiata di valore.
     * @param force se true, non effettua il controllo dei SoL.
     */
    selectRole: function (select, force) {
        let value = $(select).val();
        
        if(value !== null && value !== '') {
	        let def = false;
	        if(this.defaults != null && this.defaults.roles != null) {
		        for (id in this.defaults.roles) {
					if(value == id) {
						def = true;
						return;
					}
				}
			}
	        
			if(!def) {  
	        	$.ajax({
		            url: '/configuratore/ajax/ruoliCompatibili',
		            dataType: 'json',
		            data: {id: value},
		            method: 'POST',
		            success: data => {
		                let roles = $('#rolesContainer').find('select[name="ruoli"]').not(select)
		    			.map((i, e) => $(e).val())
		    			.filter((i, e) => e !== '')
		    			.toArray();

						roles.every(id => {
		                    if(!data.includes(id)) {
								this.showAlert(this.messages.errorRoleNotCompatible, null, 'error');
								$(select).val('');
								return false;
							}
							return true;
		               });
		            }
		        });
			}
		}
        
        let callback = result => {
            if (result) {
                if (value !== '') {
                    let present = $('#rolesContainer select[name="ruoli"]')
                        .map((i, elem) => $(elem).val())
                        .filter((i, elem) => elem === value)
                        .length > 1;
                    if (present) {
                        this.showAlert(this.messages.errorValueAlreadyPresent, null, 'error');
                        value = '';
                        $(select).val(value);
                    }
                }
            } else {
                $(select).val($(select).data('previous'));
            }
        }

        if (force) {
            callback(true);
        } else {
            let isTheOnlyValue = $('#rolesContainer select[name="ruoli"]')
                .map((i, elem) => $(elem).val())
                .filter((i, elem) => elem !== '')
                .length === 0;
            if (value === '' && isTheOnlyValue) this.doWithSolIntegrity(callback);
        }
    },

   /* deleteRole: function (button) {
        let callback = result => {
            if (result) $(button).closest('div.form-group').remove();
        };

        let value = $(button).closest('div.form-group').find('select[name="ruoli"]').val();
        let isTheOnlyValue = $('#rolesContainer select[name="ruoli"]')
            .map((i, elem) => $(elem).val())
            .filter((i, elem) => elem !== '')
            .length === 1;

        if (value !== '' && isTheOnlyValue) {
            this.doWithSolIntegrity(callback);
        } else {
            callback(true);
        }
    },*/
    
     deleteRole: function (button) {
        let value = $(button).closest('div.form-group').find('select[name="ruoli"]').val();
        let callback = async result => {
            if (result) {
                $(button).closest('div.form-group').remove();
                let valueInt = parseInt(value);

                this.selectedSols.forEach(sol => {
	 				sol.collocations.forEach(coll => {
                    	let idx = coll.roles.map(role => role.id).indexOf(value);
                    	if (idx !== -1) coll.roles.splice(idx, 1);
                    });
                });
                
                this.selectedSols.forEach(sol => {
                	sol.collocations.filter(coll => coll.roles.length === 0)
                    .forEach(coll => this.selectedSols.splice(this.selectedSols.map(sol2 => sol2.id).indexOf(sol.id), 1));
				});

                if (this.defaults) {
                    this.defaults.sol.forEach(sol => {
                        sol.collocations.forEach(coll => {
                    		let idx = coll.roles.map(role => role.id).indexOf(valueInt);
                    		if (idx !== -1) coll.roles.splice(idx, 1);
                    	});
                    });
                    this.defaults.sol.forEach(sol => {
                		sol.collocations.filter(coll => coll.roles.length === 0)
                    	.forEach(coll => this.defaults.sol.splice(this.defaults.sol.map(sol2 => sol2.id).indexOf(sol.id), 1));
					});
                }
				
				showLoading();
                await this.getSelectableSols(true);
                await this.getSelectableCollocationSols(true);
                hideLoading();
            }
        };

        let solRolesGroup = $('#solContainer div.sol-group select[name="ruoloSol"]')
            .filter((i, elem) => $(elem).val() === value)
            .map((i, elem) => $(elem).parents('div.sol-ruoli-group').get());
        
        let solDefaultsRolesGroup = $('#solDefaultsContainer div.sol-group input[name="roleName"]')
            .filter((i, elem) => $(elem).data('id') === parseInt(value))
            .map((i, elem) => $(elem).parents('div.sol-role-group').get());
            
        if (value !== '' && (solRolesGroup.length > 0 || solDefaultsRolesGroup.length > 0)) {
            this.showConfirm(this.messages.confirmSolIntegrityRole, null, 'warning', result => {
                callback(result);
            });
        } else {
            callback(true);
        }
    },

    // COLLOCATIONS FUNCTIONS

    /**
     * Eseguita alla selezione di una collocazione/azienda sanitaria. Controlla se non e' stato gia' inserita. Se
     * inserita con successo, ricostruisce l'albero userPage.selectableSols con le nuove collocazioni selezionate, e
     * inoltre ricostruisce l'intera sezione dei SoL, in modo da visualizzare nei SoL gia' inseriti le nuove
     * collocazioni.
     *
     * @param container la sezione delle collocazioni dove e' stata fatta la selezione.
     * @param item l'oggetto che descrive la collocazione.
     * @returns {Promise<boolean>}
     */
    selectCollocation: async function (container, item) {
        let present = $('#collocationContainer input[name="collocazioni"]')
            .map((i, elem) => $(elem).val())
            .filter((i, elem) => elem !== '')
            .map((i, elem) => parseInt(elem))
            .filter((i, elem) => elem === item.id)
            .length > 0;
        if (present) {
            this.showAlert(this.messages.errorValueAlreadyPresent, null, 'error');
            $(container).find('input.coll-desc-input').val('');
            $(container).find('input.coll-code-input').val('');
            return false;
        }

        let sede = $(container).find('select[name="sedeOperativa"] option:selected').text().trim();
        this.applyCollocation(container, item.id, sede, item.code, item.desc, false);
		showLoading();
        await this.getSelectableSols(false);
        await this.getSelectableCollocationSols(false);
       	hideLoading();
    },

    /**
     * Eseguita alla selezione della collocazione/azienda sanitaria, o quando ne viene costruita una di default.
     * Costruisce l'input con il suo id, la label che la descrive, e nasconde i campi di ricerca.
     *
     * @param container la sezione delle collocazioni dove e' stata fatta la selezione.
     * @param id l'id della collocazione/azienda sanitaria.
     * @param sede l'azienda sanitaria: CODICE - DESCRIZIONE
     * @param code il codice della collocazione (null se viene selezionata azienda sanitaria).
     * @param desc la descrizione della collocazione (null se viene selezionata azienda sanitaria).
     * @param readonly se true, la collocazione viene aggiunta come readonly: totalmente non modificabile (ad esempio,
     * se l'operatore non ha i permessi di rimuovere/modificare quella collocazione).
     */
    applyCollocation: function (container, id, sede, code, desc, readonly) {
		
        let label = code === '-' || sede === code + ' - ' + desc ? sede : sede + ' - ' + code + ' - ' + desc;

        $(container).find('input[name="collocazioni"]').val(id);
        $(container).find('input[name="collocazioneLabel"]').val(label);

        $(container).find('div.sede-search-box').hide();
        $(container).find('div.collocation-search-box').hide();
        $(container).find('div.result-box').removeClass('d-none').addClass('d-flex');

        if (readonly) $(container).find('button.btn-close').remove();
    },

    /**
     * Eseguita alla pressione del pulsante di cancellazione, cancella una collocazione. Inoltre legge l'albero
     * userPage.selectedSols per trovare tutti i profili SoL relativi a questa collocazione e li cancella, ricostruendo
     * cosi' interamente la sezione dei SoL.
     *
     * @param button il bottone per la cancellazione che e' stato premuto.
     */
    deleteCollocation: function (button) {
        let value = $(button).closest('div.collocation-group').find('input[name="collocazioni"]').val();
        let callback = async result => {
            if (result) {
                $(button).closest('div.collocation-group').remove();
                let valueInt = parseInt(value);

                this.selectedSols.forEach(sol => {
                    let idx = sol.collocations.map(coll => coll.id).indexOf(value);
                    if (idx !== -1) sol.collocations.splice(idx, 1);
                });
                this.selectedSols.filter(sol => sol.collocations.length === 0)
                    .forEach(sol => this.selectedSols.splice(this.selectedSols.map(sol2 => sol2.id).indexOf(sol.id), 1));

                if (this.defaults) {
                    this.defaults.sol.forEach(sol => {
                        let idx = sol.collocations.map(coll => coll.id).indexOf(valueInt);
                        if (idx !== -1) sol.collocations.splice(idx, 1);
                    });
                    this.defaults.sol.filter(sol => sol.collocations.length === 0)
                        .forEach(sol => this.defaults.sol.splice(this.defaults.sol.map(sol2 => sol2.id).indexOf(sol.id), 1));
                }
				showLoading();
                await this.getSelectableSols(true);
                await this.getSelectableCollocationSols(true);
                hideLoading();
            }
        };
        
         let solCollocationsGroup = $('#solContainer div.sol-group select[name="sedeOperativaSol"]')
            .filter((i, elem) => $(elem).val() === value)
            .map((i, elem) => $(elem).parents('div.sol-group').get());
        
        let solDefaultsCollocationsGroup = $('#solDefaultsContainer div.sol-group input[name="collocationName"]')
            .filter((i, elem) => $(elem).data('id') === parseInt(value))
            .map((i, elem) => $(elem).parents('div.sol-collocation-group').get());

        /*let solCollocationsGroup = $('#solContainer div.sol-group input[name="collocationName"], #solDefaultsContainer div.sol-group input[name="collocationName"]')
            .filter((i, elem) => $(elem).data('id') === parseInt(value))
            .map((i, elem) => $(elem).parents('div.sol-collocation-group').get());*/
        if (value !== '' && (solCollocationsGroup.length > 0 || solDefaultsCollocationsGroup.length > 0) ) {
            this.showConfirm(this.messages.confirmSolIntegrityCollocation, null, 'warning', result => {
                callback(result);
            });
        } else {
            callback(true);
        }
    },

    // SOL FUNCTIONS

    /**
     * Interroga il backend per ottenere la lista dei SoL che l'operatore puo' selezionare. La lista dei SoL e' basata
     * dalle collocazioni inserite, infatti vengono raccolti gli id di tutte le collocazioni inserite e viene effettuata
     * l'interrogazione tramite una chiamata ajax.
     * Inoltre, dopo l'interrogazione, viene scandita l'alberatura userPage.selectedSols per rimuovere i SoL che
     * eventualmente non sono piu' disponibili.
     * Dopo aver effettuato l'interrogazione, viene scatenato l'evento 'sol-tree-downloadeded' sul div#solContainer.
     *
     * @param rebuildUi se true, ricostruisce l'intera sezione dei SoL dopo l'interrogazione.
     * @returns {Promise<void>}
     */
    getSelectableSols: async function (rebuildUi) {
        let collocations = $('#collocationContainer')
            .find('input[name="collocazioni"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '')
            .map((i, e) => parseInt(e))
            .get();

        this.selectableSols = null;

        if (!collocations || collocations.length === 0) {
            if (rebuildUi) this.rebuildSolsUi();
            return;
        }

        this.selectableSols = await $.ajax({
            url: '/configuratore/ajax/sol',
            dataType: 'json',
            data: {collocazioni: collocations},
            method: 'POST',
            traditional: true
        });

        // tolgo i sol non piu' disponibili da quelli selezionati
        this.selectedSols.forEach((sol, i) => {
            if (!this.selectableSols.map(ssol => ssol.id).find(ssolId => ssolId === sol.id)) {
                this.selectedSols.splice(i, 1);
            }
        });

        if (rebuildUi) this.rebuildSolsUi();

        $('#solContainer').trigger('sol-tree-downloaded');
    },


     /**
     * Interroga il backend per ottenere la lista dei SoL che l'operatore puo' selezionare. La lista dei SoL e' basata
     * dalle collocazioni inserite, infatti vengono raccolti gli id di tutte le collocazioni inserite e viene effettuata
     * l'interrogazione tramite una chiamata ajax.
     * Inoltre, dopo l'interrogazione, viene scandita l'alberatura userPage.selectedSols per rimuovere i SoL che
     * eventualmente non sono piu' disponibili.
     * Dopo aver effettuato l'interrogazione, viene scatenato l'evento 'sol-tree-downloadeded' sul div#solContainer.
     *
     * @param rebuildUi se true, ricostruisce l'intera sezione dei SoL dopo l'interrogazione.
     * @returns {Promise<void>}
     */
      getSelectableCollocationSols: async function (rebuildUi) {
        let collocations = $('#collocationContainer')
            .find('input[name="collocazioni"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '')
            .map((i, e) => parseInt(e))
            .get();

        //this.collocazioniSol = null;

        if (!collocations || collocations.length === 0) {
            if (rebuildUi) this.rebuildSolsUi();
            return;
        }

        this.collocazioniSol = await $.ajax({
            url: '/configuratore/ajax/collocazioniSol',
            dataType: 'json',
            data: {collocazioni: collocations},
            method: 'POST',
            traditional: true
        });

        if (rebuildUi) this.rebuildSolsUi();

        $('#solContainer').trigger('sol-tree-downloaded');
    },

    /**
     * Costruisce una nuova sezione SoL.
     *
     * @param defaultLayout se true, costruisce un SoL con template di default (quello ad accordion).
     * @param solId l'ID del SoL da costruire (solo nel caso in cui sto costruendo un SoL di default, quindi
     * defaultLayout = true).
     * @returns {*|Window.jQuery|*[]} la sezione appena inserita.
     */
    buildSolPrototype: function (defaultLayout, solId) {
        let buildDefaultPrototype = defaultLayout && $('#solDefaultsContainer').length > 0;
        let prototypeName = buildDefaultPrototype ? 'sol-default' : 'sol';
        let container = buildDefaultPrototype ? 'solDefaultsContainer' : 'solContainer';
        

        let solGroup = this.insertPrototype(container, prototypeName, 'sol-group');
		
		if(!buildDefaultPrototype) {		
	        $(solGroup).find('button.delete-sol').click(e => $(e.target).closest('div.sol-group').remove());
		}
        
        if (buildDefaultPrototype) {
            let headingId = 'headingSol' + solId;
            let collapseId = 'collapseSol' + solId;
            
            $(solGroup).find('div.card-header')
            .attr('id', headingId)
            .find('button')
            .attr('data-target', '#' + collapseId)
            .attr('aria-controls', collapseId)
            .text();
            
            $(solGroup).find('div[data-parent="#accordion"]')
            .attr('id', collapseId)
            .attr('aria-labelledby', headingId);
            
            $(solGroup).find('div.titolocard a.btn-settings').click(e => {
                e.preventDefault();
                this.toggleSol(collapseId, true);
            });
            
            let solOptions = this.selectableSols.map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
            $(solGroup).find('select.sol-selector')
            .append(solOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
            	$(e.target).blur();
            	this.selectSol(solGroup);
            	this.addSolToTree(solGroup);
            });
            
        }
      
        if(this.collocazioniSol.length==1){
            let colSolOptions = this.collocazioniSol.map(e => "<option value='" + e.id +"'selected>" + e.codice + " - " + e.descrizione + "</option>")
            $(solGroup).find('select.coll-sol-selector').empty()
          .append(colSolOptions)
          .focus(e => $(e.target).data('previous-value', $(e.target).val()))
          .change(e => {
            $(e.target).blur();
            this.buildSolRolPrototype(false,e);
                    });
            $(solGroup).find('select.coll-sol-selector').change();       
        }else{
            let colSolOptions = this.collocazioniSol.map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
              $(solGroup).find('select.coll-sol-selector')
            .append(colSolOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
                $(e.target).blur();
                this.buildSolRolPrototype(false,e);
                        });
        }
        

        return solGroup;
    },

    /**
     * Costruisce una nuova sezione SoL.
     *
     * @param defaultLayout se true, costruisce un SoL con template di default (quello ad accordion).
     * @returns {*|Window.jQuery|*[]} la sezione appena inserita.
     */
     buildSolRolPrototype: function (defaultLayout,e) {
        let buildDefaultPrototype = defaultLayout && $('#solDefaultsContainer').length > 0;
        let prototypeName = buildDefaultPrototype ? 'sol-ruoli' : 'sol-ruoli';
        let container = buildDefaultPrototype ? 'rolSolContainer' : 'rolSolContainer';
        
        let roles = $('#rolesContainer').find('select[name="ruoli"]')
        //.map((i, e) => $(e).val())
            .filter((i, e) =>  $(e).val() !== '');
        
        if($(e.target).closest('.sol-group').find('#'+container).children().length>0){
            $(e.target).closest('.sol-group').find('#'+container).empty();
        }
        let solGroup = $(e.target).closest('.sol-group').find('#'+container)
            .append(this.prototypes[prototypeName])
            .children('div.sol-ruoli-group:last'); 
        //this.insertPrototype(container, prototypeName, 'sol-ruoli-group');

        

        // if (buildDefaultPrototype) {
        //     let headingId = 'headingSol' + solId;
        //     let collapseId = 'collapseSol' + solId;

        //     $(solGroup).find('div.card-header')
        //         .attr('id', headingId)
        //         .find('button')
        //         .attr('data-target', '#' + collapseId)
        //         .attr('aria-controls', collapseId)
        //         .text();

        //     $(solGroup).find('div[data-parent="#accordion"]')
        //         .attr('id', collapseId)
        //         .attr('aria-labelledby', headingId);

        //     $(solGroup).find('div.titolocard a.btn-settings').click(e => {
        //         e.preventDefault();
        //         this.toggleSol(collapseId, true);
        //     });
        // }
      
        
        if(roles.length==1){
            let rolOptions = roles.map
            ((i,e) => "<option value='" +  
            $(e).find('option:selected').val() + "' selected>"  + 
            $(e).find('option:selected').text() + 
            "</option>").toArray();

            $(solGroup).find('select.ruolo-sol-selector')
            .empty()
            .append(rolOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
                $(e.target).blur();
                this.buildSceltaSolPrototype(false, e)
                
               // this.addSolToTree(solGroup);
            });

            $(solGroup).find('select.ruolo-sol-selector').change();
        }else{
            let rolOptions = roles.map
            ((i,e) => "<option value='" +  
            $(e).find('option:selected').val() + "'>"  + 
            $(e).find('option:selected').text() + 
            "</option>").toArray()

            $(solGroup).find('select.ruolo-sol-selector')
            .append(rolOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
                $(e.target).blur();
                this.buildSceltaSolPrototype(false, e)
                
               // this.addSolToTree(solGroup);
            });

        }
            
        return solGroup;
    },

    /**
     * Nella sezione di un SoL, costruisce la sottosezione delle collocazioni (per cui si andranno ad aggiungere i
     * profili SoL relativa a quella collocazione). In base a cio' che e' stato ricevuto in userPage.selectableSols,
     * per il SoL selezionato vengono costruite tante sezioni collocazioni relative.
     *
     * @param solGroup la sezione dei SoL dove e' avvenuta la selezione.
     * @param solId l'ID del SoL selezionato.
     */
    buildSolCollocationPrototype: function (solGroup, sol) {
        //let sol = this.selectableSols.find(e => e.id === solId);

        sol.collocations
            .filter(e => $('#collocationContainer').find('input[name="collocazioni"][value="' + e.id + '"]').length > 0)
            .forEach(e => {
                let inserted = $(solGroup)
                    .find('div.sol-collocation-container')
                    .append(this.prototypes['sol-collocation'])
                    .children('div:last');

                let collocationGroup = $('#collocationContainer')
                    .find('input[name="collocazioni"][value="' + e.id + '"]')
                    .parent('div.collocation-group');

                let collocationName = $(collocationGroup).find('input[name="collocazioneLabel"]').val();
                let collocationId = parseInt($(collocationGroup).find('input[name="collocazioni"]').val());

                $(inserted).find('input[name="collocationName"]').val(collocationName).data('id', collocationId);
                
                $(inserted).find('button.delete-sol').click(e => {
						let container  = $(e.target).closest('div.sol-collocation-container');
						let solGroup= $(e.target).closest('div.sol-group');
						$(e.target).closest('div.sol-collocation-group').remove()
						if($(container).children().length == 0) {
							$(solGroup).remove()
						}
				});
                //$(inserted).find('a.add-sol-profile').click(() => this.buildProfilePrototype(inserted));
                //$(inserted).find('a.remove-sol-profiles').click(() => $(inserted).find('div.sol-profile-container').html(''));
                //userPage.buildDefaultSolRolePrototype.apply(userPage,[inserted, solId, collocationId]);
                
            });
    },
    
    buildDefaultSolCollocationPrototype: function (solGroup, collocationDefault) {

        let inserted = $(solGroup)
            .find('div.sol-collocation-container')
            .append(this.prototypes['sol-collocation'])
            .children('div:last');

        let collocationGroup = $('#collocationContainer')
            .find('input[name="collocazioni"][value="' + collocationDefault.id + '"]')
            .parent('div.collocation-group');

        let collocationName = $(collocationGroup).find('input[name="collocazioneLabel"]').val();
        let collocationId = parseInt($(collocationGroup).find('input[name="collocazioni"]').val());

       	$(inserted).find('input[name="collocationName"]').val(collocationName).data('id', collocationId);
        
        $(inserted).find('button.delete-sol').click(e => {
				let container  = $(e.target).closest('div.sol-collocation-container');
				let solGroup= $(e.target).closest('div.sol-group');
				$(e.target).closest('div.sol-collocation-group').remove()
				if($(container).children().length == 0) {
					$(solGroup).remove()
				}
		});
 
    },
    
    buildDefaultSolRolePrototype: function (solCollocationGroup, roleDefault) {

        let inserted = $(solCollocationGroup)
            .find('div.sol-role-container')
            .append(this.prototypes['sol-roles-default'])
            .children('div:last');

        let role = $('#rolesContainer').find('select[name="ruoli"]')
        	.filter((i,r) => $(r).val() !== '' && parseInt($(r).val()) === roleDefault.id);
    		
    	let roleName = $(role).find('option:selected').text();
    	let roleId = parseInt($(role).val());

        $(inserted).find('input[name="roleName"]').val(roleName).data('id', roleId);
                
              
    },

    buildSceltaSolPrototype: async function (defaultLayout, e) {
        let buildDefaultPrototype = defaultLayout && $('#solDefaultsContainer').length > 0;
        let prototypeName = buildDefaultPrototype ? 'sol-selezione' : 'sol-selezione';
        let container = buildDefaultPrototype ? 'sceltaSolContainer' : 'sceltaSolContainer';
        
        //let collocazione = $('#solContainer').find('select[name="sedeOperativaSol"]').val();
        let collocazione = $(e.target).parents(".sol-group").find('select[name="sedeOperativaSol"]').val();
        //let ruolo = $('#rolSolContainer').find('select[name="ruoloSol"]').val();
        let ruolo = $(e.target).val();
		
		showLoading();
        this.selectableSols = await $.ajax({
            url: '/configuratore/ajax/solSelezionabili',
            dataType: 'json',
            data: {collocazione: collocazione,
                   ruolo: ruolo
            },
            method: 'POST',
            traditional: true
        });
		hideLoading();
		
        if(this.selectableSols.length==0){
            this.showAlert(this.messages.errorSolNonTrovato, null, 'error');
            $('#solContainer').empty();
            return;
        }
        
        if($(e.target).closest('.sol-group').find('#'+container).children().length>0){
            $(e.target).closest('.sol-group').find('#'+container).empty();
        }
        let solGroup = $(e.target).closest('.sol-group').find('#'+container)
            .append(this.prototypes[prototypeName])
            .children('div.sol-scelta-group:last'); 
        //let solGroup = this.insertPrototype(container, prototypeName, 'sol-scelta-group');

        

        // if (buildDefaultPrototype) {
        //     let headingId = 'headingSol' + solId;
        //     let collapseId = 'collapseSol' + solId;

        //     $(solGroup).find('div.card-header')
        //         .attr('id', headingId)
        //         .find('button')
        //         .attr('data-target', '#' + collapseId)
        //         .attr('aria-controls', collapseId)
        //         .text();

        //     $(solGroup).find('div[data-parent="#accordion"]')
        //         .attr('id', collapseId)
        //         .attr('aria-labelledby', headingId);

        //     $(solGroup).find('div.titolocard a.btn-settings').click(e => {
        //         e.preventDefault();
        //         this.toggleSol(collapseId, true);
        //     });
        // }
      

        let solOptions = this.selectableSols.map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>")
        $(solGroup).next('#profileButton').find('a.add-sol-profile').click(() => this.buildProfilePrototype(solGroup, false));
        $(solGroup).next('#profileButton').find('a.remove-sol-profiles').click(() => $(solGroup).find('div.sol-profile-container').html(''));
       
        $(solGroup).find('select.sol-selector')
            .append(solOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
                $(e.target).blur();
                this.selectSol(solGroup);
                $(solGroup).next('#profileButton').show();
                this.addSolToTree(solGroup);
            
               
            });

        return solGroup;
    }, 
    
    
    
   

    /**
     * Nella sottosezione collocazione di una sezione SoL, costruisce la sottosezione dei profili.
     *
     * @param solCollocationGroup la sottosezione collocazione.
     */
    buildProfilePrototype: function (solCollocationGroup, isDefault) {
        if ($(solCollocationGroup).find('input[name="profiliSol"]').filter((i, e) => $(e).val() === '').length > 0) {
            this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
            return;
        }

        let solProfileGroup = $(solCollocationGroup)
            .find('div.sol-profile-container')
            .append(this.prototypes['sol-profile'])
            .children('div:last');

        let solId = parseInt($(solCollocationGroup).parents('div.sol-group').find('select.sol-selector').val());
        let sol = this.selectableSols.filter(e => e.id === solId)[0];
        let profilesOptions = sol.profili.map(e => "<option value='" + e.id + "'>" + e.codice + " - " + e.descrizione + "</option>");

        $(solProfileGroup).find('select.profile-selector').append(profilesOptions)
            .focus(e => $(e.target).data('previous-value', $(e.target).val()))
            .change(e => {
                $(e.target).blur();
                this.selectSolProfile(solProfileGroup,isDefault);
                this.addSolProfileToTree(solProfileGroup);
            });

        $(solProfileGroup).find('input.profile-validity').change(() => {
			this.selectSolProfile(solProfileGroup,isDefault, true);
			this.addSolProfileToTree(solProfileGroup);
		}).each((i, e) => this.buildDatePicker(e, settings => settings.minDate = new Date()));

        $(solProfileGroup).find('a.profile-viewer-button').click(e => this.showFunctionalitiesModal(e, solProfileGroup));

        $(solProfileGroup).bootstrapMaterialDesign();
    },

    /**
     * Richiamato quando viene selezionato un SoL dal selettore, verifica che il SoL non sia stato gia' selezionato, e
     * successivamente costruisce le sottosezioni collocazioni relative al SoL, tramite la funcion
     * userPage.buildSolCollocationPrototype.
     *
     * @param solGroup la sezione SoL.
     */
    selectSol: async function (solGroup) {
        $(solGroup).find('div.sol-collocation-container').html('');
        $(solGroup).find('div.sol-profile-container').html('');

        let selector = $(solGroup).find('select.sol-selector');
        let solId = $(selector).val();
       
         this.isSolConfig = await $.ajax({
            url: '/configuratore/ajax/idSol',
            dataType: 'json',
            data: {id: solId                   
            },
            method: 'POST',
            traditional: true
        });
        
    
    },

    /**
     * Quando viene cambiato il SoL in una sezione, manipola l'alberatura userPage.selectedSols: se viene aggiunto un
     * nuovo SoL, questo viene aggiunto, altrimenti viene eliminato.
     *
     * @param solGroup la sezione SoL dove e' stato cambiato il valore.
     */
    addSolToTree: function (solGroup) {
        let selector = $(solGroup).find('select.sol-selector');
        let solId = $(selector).val();
        let previous = $(selector).data('previous-value');
        previous = previous ? previous : solId;
        let previousIdx = previous && previous !== '' ? this.selectedSols.map(s => s.id).indexOf(parseInt(previous)) : -1;
        let collocazione = $('#solContainer').find('select[name="sedeOperativaSol"]').val();
        let ruolo = $('#rolSolContainer').find('select[name="ruoloSol"]').val();
        

        if (solId !== '') {
            solId = parseInt(solId);
            let sol = this.selectableSols.find(e => e.id === solId);
            // let collocations = sol.collocazioni.map(c => {
            //     return {id: c, profiles: []}
            // });

            let json = {
                id: solId,
                collocations: [],
            };
			let role = {id:ruolo, profiles: []};
			let coll = {id:collocazione, roles:[]};
			coll.roles.push(role);
            json.collocations.push(coll);
			
            if (previousIdx !== -1) {
                this.selectedSols[previousIdx] = json;
            } else {
                this.selectedSols.push(json);
            }

            $(selector).data('previous-value', null);
        } else if (previousIdx !== -1) {
            this.selectedSols.splice(previousIdx, 1);
        }
    },

    /**
     * Richiamato quando viene selezionato un profilo SoL dal selettore, verifica che il profilo non sia stato gia'
     * selezionato, e successivamente valorizza l'hidden nel formato 'ID_SOL|ID_COLLOCAZIONE|ID_PROFILO(|VALIDITA)?'
     * (il separatore sara' quello indicato da userPage.profilesDivisor).
     *
     * @param solProfileGroup la sottosezione profilo SoL.
     * @param isDefault identifica se si e' in fase di inserimento o in fase di creazione default
     */
    selectSolProfile: async function (solProfileGroup,isDefault, fromDatePicker = false) {
        let input = $(solProfileGroup).find('input[name="profiliSol"]');  
        let profiloTitolatoText = $(solProfileGroup).find('p[name="profiloTitolatoText"]');  
        let profileSelector = $(solProfileGroup).find('select.profile-selector');
        let value = $(profileSelector).val();
        let mostrare=true   

        if(!isDefault){
            let inserito = false;
            let solGroupInserimento = $(solProfileGroup).parents('div.sol-group');
            let hasSolConfig = false;
            let solConfigNotVisible = false;
    
    
    
            let collocazioniInserite = $(solProfileGroup).parents('div.sol-group')
            .find('select.coll-sol-selector')
            .map((i, elem) => parseInt($(elem).val())).get()[0];
    
            let ruoloInserito= $(solProfileGroup).parents('div.sol-group')
            .find('select.ruolo-sol-selector')
            .map((i, elem) => parseInt($(elem).val())).get()[0];
    
            let solInserito= $(solProfileGroup).parents('div.sol-group')
            .find('select.sol-selector')
            .map((i, elem) => $(elem).val()).get()[0];

            
    
            $('#solDefaultsContainer').find('div.sol-group').not(solGroupInserimento).toArray().forEach(solGroup => {
                if(!inserito) {
					$(solGroup).find('div.sol-collocation-group').toArray().forEach(abil => {
						if(!inserito) {
			                inserito = $(abil)
			                  .find('input[name="collocationName"]')
			                  .map((i, elem) => $(elem).data('id')).toArray().includes(collocazioniInserite) &&
			                  $(abil)
			                  .find('input[name="roleName"]')
			                  .map((i, elem) => $(elem).data('id')).toArray().includes(ruoloInserito) &&
			                 /* $(abil)
			                  .find('select.sol-selector')
			                  .map((i, elem) => $(elem).val()).toArray().includes(solInserito) &&*/
			                  $(abil)
			                  .find('select.profile-selector')
			                  .map((i, elem) => $(elem).val()).toArray().includes(value);
						}
					})
                }
            })

            if(!inserito) {
                $('#solContainer').find('div.sol-group').not(solGroupInserimento).toArray().forEach(solGroup => {
                    if(!inserito) {
                    inserito = $(solGroup)
                    .find('select.coll-sol-selector')
                      .map((i, elem) => parseInt($(elem).val())).toArray().includes(collocazioniInserite) &&
                      $(solGroup)
                      .find('select.ruolo-sol-selector')
                      .map((i, elem) => parseInt($(elem).val())).toArray().includes(ruoloInserito) &&
                      $(solGroup)
                      .find('select.sol-selector')
                      .map((i, elem) => $(elem).val()).toArray().includes(solInserito) &&
                      $(solGroup)
                      .find('select.profile-selector')
                      .map((i, elem) => $(elem).val()).toArray().includes(value);
                    }
                });
                profiloTitolatoText.val("profilo titolato alla funzionalit&agrave;");
            }
            
            if(this.isSolConfig && !fromDatePicker){
                let proifileSolConfig= await $.ajax({
                    url: '/configuratore/ajax/profileSolCongif',
                    dataType: 'json',
                    method: 'POST',
                    traditional: true
                });

               

                $('#solDefaultsContainer').find('div.sol-group').not(solGroupInserimento).toArray().forEach(solGroup => {
                    if(!hasSolConfig) {
						$(solGroup).find('div.sol-collocation-group').toArray().forEach(abil => {
							if(!hasSolConfig) {
		                        hasSolConfig = $(abil)
			                      .find('input[name="collocationName"]')
			                      .map((i, elem) => $(elem).data('id')).toArray().includes(collocazioniInserite) &&
			                      /* $(abil)
			                      .find('select.sol-selector')
			                      .map((i, elem) => $(elem).val()).toArray().includes(solInserito) &&*/
			                      proifileSolConfig.includes($(solGroup)
			                      	.find('select.profile-selector')
			                      	.map((i, elem) => parseInt($(elem).val())).toArray()[0]);
							}							
						})
                    }
                })

                if(!hasSolConfig) {
                    $('#solContainer').find('div.sol-group').not(solGroupInserimento).toArray().forEach(solGroup => {
                        if(!hasSolConfig) {
                        hasSolConfig = $(solGroup)
                        .find('select.coll-sol-selector')
                          .map((i, elem) => parseInt($(elem).val())).toArray().includes(collocazioniInserite) &&
                         $(solGroup)
                          .find('select.sol-selector')
                          .map((i, elem) => $(elem).val()).toArray().includes(solInserito) &&
                          proifileSolConfig.includes($(solGroup).find('select.profile-selector').map((i, elem) => parseInt($(elem).val())).toArray()[0]);
                        }
                    })
                }
                
                if(!hasSolConfig) {
					showLoading();
					let cf = $('input[name="cf"]').val();
					hasSolConfig = await $.ajax({
	                    url: '/configuratore/ajax/checkHasSolConfig',
	                    dataType: 'json',
	                    data: { cf: cf, idCollocazione: collocazioniInserite },
	                    method: 'GET',
	                    traditional: true
               		});
               		solConfigNotVisible = hasSolConfig;
               		hideLoading();
				}
             

		        if(hasSolConfig){
					if(solConfigNotVisible) {
						 this.showAlert(this.messages.errorSolConfiguratorePresenteNotVisible, null, 'error');
					} else {
				        this.showAlert(this.messages.errorSolConfiguratorePresente, null, 'error');
			        }
			        value = '';
			        $(solProfileGroup).find('select.profile-selector').val(value);
			        mostrare=false;
		        }

            }
     
        if (inserito && mostrare) {
            this.showAlert(this.messages.errorConfigrazioneEsistente, null, 'error');
            value = '';
            $(solProfileGroup).find('select.profile-selector').val(value);
        }

        }

         let present = $(solProfileGroup).parents('div.sol-profile-container')
             .find('select.profile-selector')
             .map((i, elem) => $(elem).val())
             .filter((i, elem) => elem === value)
             .length > 1;
         if (present) {
             this.showAlert(this.messages.errorValueAlreadyPresent, null, 'error');
             value = '';
             $(solProfileGroup).find('select.profile-selector').val(value);
         }

        let solId = parseInt($(solProfileGroup).parents('div.sol-group').find('select.sol-selector').val());

        let roleId;
        let collocationId;
        
        if(isDefault){
             collocationId= $(solProfileGroup).parents('div.sol-collocation-group').find('input[name="collocationName"]').data('id');
             roleId= $(solProfileGroup).parents('div.sol-role-group').find('input[name="roleName"]').data('id');

        }else{
              collocationId = $(solProfileGroup).parents('div.sol-group').find('select.coll-sol-selector').val();
              roleId = $(solProfileGroup).parents('div.sol-ruoli-group').find('select.ruolo-sol-selector').val();
        }
        
        if (value === '') {
            $(input).val('');
        } else {
            let validity = $(solProfileGroup).find('input.profile-validity').val().trim();
            let oldValue = $(solProfileGroup).find('input[name="oldValue"]').val().trim();
            let finalValue = solId + this.profilesDivisor
                + collocationId + this.profilesDivisor
                + value + this.profilesDivisor
                + roleId + this.profilesDivisor
                + validity + this.profilesDivisor
                + oldValue;
            $(input).val(finalValue);
        }
    },

    /**
     * Quando viene cambiato il profilo SoL in una sottosezione, manipola l'alberatura userPage.selectedSols: se viene
     * aggiunto un nuovo profilo SoL, questo viene aggiunto, altrimenti viene eliminato.
     *
     * @param solProfileGroup la sezione SoL dove e' stato cambiato il valore.
     */
    addSolProfileToTree: function (solProfileGroup) {
        let solId = parseInt($(solProfileGroup).parents('div.sol-group').find('select.sol-selector').val());
        let collocationId = $('#solContainer').find('select[name="sedeOperativaSol"]').val();
        let ruolo = $('#rolSolContainer').find('select[name="ruoloSol"]').val();
        let solJson = this.selectedSols.find(sol => sol.id === solId && sol.collocations[0].id === collocationId && sol.collocations[0].roles[0].id===ruolo);
        if (!solJson) return;

        let profileSelector = $(solProfileGroup).find('select.profile-selector');
        let value = $(profileSelector).val();
        let previous = $(profileSelector).data('previous-value');
        previous = previous ? previous : value;
        let previousIdx = previous && previous !== '' ? solJson.collocations[0].roles[0].profiles.map(c => c.id).indexOf(parseInt(previous)) : -1;

        if (value === '') {
            if (previousIdx !== -1) solJson.collocations[0].roles[0].profiles.splice(previousIdx, 1);
        } else {
            let validity = $(solProfileGroup).find('input.profile-validity').val().trim();
            validity = validity ? validity : null;

            let json = {
                id: parseInt(value),
                validity: validity
            };

            if (previousIdx !== -1) {
                solJson.collocations[0].roles[0].profiles[previousIdx] = json;
            } else {
                solJson.collocations[0].roles[0].profiles.push(json);
            }

            $(profileSelector).data('previous-value', null);
        }
    },

    /**
     * Ricostruisce interamente le sezioni dei SoL, sia quelli di default che quelli nuovi inseriti. Resetta le sezioni
     * e successivamente li ricostruisce in base alle alberature userPage.defaults.sol e userPage.selectedSols.
     */
    rebuildSolsUi: function () {
        $('#solContainer').html('');
        $('#solDefaultsContainer').html('');

        if (this.defaults) {
            for (let sol of this.defaults.sol) {
                let solGroup = this.buildSolPrototype(true, sol.id);
                this.setSingleSolToDefaults(sol, solGroup);
            }
        }

        $('#solDefaultsContainer div[data-parent="#accordion"]')
            .map((i, e) => $(e).attr('id'))
            .each((i, e) => {
                this.sectionsVisibilityState[e] = true;
                this.toggleSol(e, false);
            });

        for (let sol of this.selectedSols) {
            let solGroup = this.buildSolPrototype(false, sol.id);
            this.setSingleSolToInserted(sol, solGroup);
        }
    },

    /**
     * Esegue la callback solo con l'integrita' dei SoL, ovvero un'azione che richiede prima il reset dei SoL.
     * Viene mostrata una model di conferma che, se accettata, resetta la sezione SoL ed esegue la callback passata. Se
     * la sezione dei SoL e' vuota, viene eseguita semplicemente la callback.
     *
     * @param callback la callback che richiede l'integrita' dei SoL.
     */
    doWithSolIntegrity: function (callback) {
        if ($('#solContainer div.sol-group, #solDefaultsContainer div.sol-group').length > 0) {
            this.showConfirm(this.messages.confirmSolIntegrity, null, 'warning', result => {
                if (result) this.undoSol();
                callback(result);
            });
        } else {
            callback(true);
        }
    },

    /**
     * Mostra la modale delle funzionalita' di un profilo SoL.
     *
     * @param e l'evento del click sul pulsante di Mostra Funzionalita'.
     * @param solProfileGroup la sottosezione profilo SoL.
     */
    showFunctionalitiesModal: function (e, solProfileGroup) {
        e.preventDefault();

        let profileSelector = $(solProfileGroup).find('select.profile-selector');
        if ($(profileSelector).val() === '') {
            this.showAlert(this.messages.errorFunctionalitiesModalSelectSolProfileFirst, null, 'error')
            return;
        }

        let selectedProfile = parseInt($(profileSelector).val());
        let solId = parseInt($(solProfileGroup).parents('div.sol-group').find('select.sol-selector').val());
        let funcs = this.selectableSols.find(s => s.id === solId).profili.find(p => p.id === selectedProfile).funzionalita;

        let funcTree = [];
        funcs.filter(f => f.parent === null).forEach(root => {
            let rootNode = {functionality: root, children: []};
            funcTree.push(rootNode);
            (function recursive(node, children) {
                funcs.filter(f => f.parent && f.parent.id === node.functionality.id).forEach(child => {
                    let childNode = {functionality: child, children: []};
                    children.push(childNode);
                    recursive(childNode, childNode.children);
                });
            })(rootNode, rootNode.children);
        });

        let html = '<ul class="functionalities-list">';
        for (let node of funcTree) {
            let label = node.functionality.codice + ' - ' + node.functionality.descrizione;

            let nodeHtml;
            if (node.children.length > 0) {
                nodeHtml = '<li><span class="caret caret-down"> ' + label + '</span><ul class="nested active">';

                for (let childNode of node.children) {
                    (function recursive(child) {
                        let childLabel = child.functionality.codice + ' - ' + child.functionality.descrizione;
                        if (child.children.length > 0) {
                            nodeHtml += '<li><span class="caret caret-down"> ' + childLabel + '</span><ul class="nested active">';
                            for (let child2 of child.children) {
                                recursive(child2);
                            }
                            nodeHtml += '</ul></li>';
                        } else {
                            nodeHtml += '<li>' + childLabel + '</li>';
                        }
                    })(childNode);
                }
                nodeHtml += '</ul></li>';
            } else {
                nodeHtml = '<li>' + label + '</li>';
            }

            html += nodeHtml;
        }
        html += '</ul>';

        let modal = $('#funzionalitaModal');
        $(modal).find('div.modal-header h3').text($(profileSelector).find('option:selected').text());
        $(modal).find('div.modal-body div.row').html(html);
        $(modal).find(".caret").each((i, toggler) => {
            $(toggler).click(evt => {
                $(evt.target).siblings('.nested').toggleClass('active');
                $(evt.target).toggleClass('caret-down');
            });
        });

        $(modal).modal('show');
    },
    
    checkPhone: function(phoneNum) { 
		var phonePattern = /^(((\+|00)?39))?3\d{2}\d{6,7}$/; 
		
		phoneNum = phoneNum.replace(/[.,\/#!$%\^&\*;:{}=\-_`~()]/g,""); 
		phoneNum = phoneNum.replace(/\s{2,}/g," "); 
		
		if(phonePattern.test(phoneNum)) { 
			return true; 
		} else { 
			return false; 
		} 
	},

    // ~~~ SUBMIT FORM ~~~

    /**
     * Effettua la post della form, verificando prima che il profilo sia completo.
     */
    submitForm: function () {
		let email = $('input[name="email"]').val()
		if(email == null || email == '' || email.trim().length === 0) {
			this.showAlert(this.messages.errorEmailNotInserted, 'Campo obbligatorio', 'error');
			return;
		}
		let cellulare = $('input[name=telefono]').val();
        if(cellulare == null || cellulare == '' || cellulare.trim().length === 0) {
            this.showAlert(this.messages.errorTelefonoNotInserted, 'Campo obbligatorio', 'error');
            return;
        } else {
			if (!this.checkPhone(cellulare)){
            	this.showAlert(this.messages.errorTelefonoNotCompliant, 'Campo obbligatorio', 'error');
            	return;
			}
        }  
        let showMsg = false;
		$('input[name=dataFineRitiroReferti]').each(function () {
            let dataFineValidita = $(this).val();
            
	        if(dataFineValidita == null || dataFineValidita == '' || dataFineValidita.trim().length === 0) {
				showMsg = true;
			}
        });
        if(showMsg) {
            this.showAlert(this.messages.errorDataFineValiditaNotInserted, 'Campo obbligatorio', 'error');
            return;
		}
		
	/*
        let collocations = $('#collocationContainer')
            .find('input[name="collocazioni"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '').length === 0;

        let roles = $('#rolesContainer').find('select[name="ruoli"]')
            .map((i, e) => $(e).val())
            .filter((i, e) => e !== '')
            .length === 0;

        let profileBlank = $('.sol-group').filter((i,e) => {
            let profili = $(e).find('input[name="profiliSol"]');
            if (profili.length === 0)
                return true;
            if (profili.filter((i,e) => $(e).val() === '').length > 0)
                return true;
            return false;
         }).length > 0 || $('.sol-group').length === 0;
      */  
        
        let section = $('div.user-data-group');
 		let contratto = $(section).find('select[name=contratto]').val();
 		let telefono = $(section).find('input[name=telefono]').val();
 		
		if( (contratto == null || contratto == '') && (telefono == null || telefono == '')) {
			this.showConfirm(this.messages.confirmNoRuparCredentialsRequest, null, 'warning', result => { 
                if (result) {
					showLoading();
	    			$('#command').submit();
				}
            })
		}else {	
			showLoading();
            $('#command').submit();
        }
            
    },

    // ~~~ DEFAULTS FUNCTIONS ~~~

    /**
     * Carica i valori di default che sono stati passati negli input dal backend.
     *
     * @returns {Promise<void>}
     */
    loadDefaults: async function () {
        let rolesInput = $('input[name="ruoli"]');
        let collInput = $('input[name="collocazioni"]');
        let solInput = $('input[name="profiliSol"]');


        let collDefaults = $(collInput).val();

        this.defaults['user-data'] = this.parseUserData();
        this.defaults['roles'] = this.parseRoles(rolesInput);
        this.defaults['collocations'] = await this.parseCollocationsDefaults(collDefaults);
        this.defaults['sol'] = this.parseSolDefaults($(solInput).val());
        

        let collIds =  this.defaults['collocations'].map(c=>{
                return c.id;
             });

        this.defaults['sol']= this.defaults['sol'].filter(sol=>{
            return sol.collocations.every(coll=>{
            return collIds.includes(coll.id);
            })
        })

        $(rolesInput).remove();
        $(collInput).remove();
        $(solInput).remove();
        
    },

    /**
     * Effettua il parsing dei dati utenti di default.
     * @returns {{}} i dati utenti in un Json.
     */
    parseUserData: function () {
        let userData = {};
        let container = $('div.user-data-group');
        $(container).find('input:not([type="radio"]),input[type="radio"]:checked,select').each(function () {
            userData[$(this).attr('name')] = {
                value: $(this).val(),
                readonly: $(this).attr('data-default-readonly') === 'true'
            };
        });

        return userData;
    },

    parseRoles: function (rolesInput) {
        let roles = {};
        let val = $(rolesInput).val();
        if (val) {
            val.split(',').forEach(v => {
                let readonly = v.endsWith('ro');
                roles[readonly ? v.substring(0, v.length - 2) : v] = readonly;
            });
        }
        return roles;
    },

    /**
     * Effettua il parsing degli id delle collocazioni di default. Per tutti gli ID, viene effettuata una chiamata ajax
     * per recuperare i dettagli delle collocazioni passate.
     *
     * @param collDefaults gli input dove sono memorizzate le collocazioni.
     * @returns {Promise<*[]>} la lista delle collocazioni di default.
     */
    parseCollocationsDefaults: async function (collDefaults) {
        let defaults = [];

        if (!collDefaults || collDefaults === '') return [];

        let dataArray = await $.ajax({
            url: '/configuratore/ajax/collocazione/id',
            dataType: 'json',
            data: {id: collDefaults},
            method: 'POST'
        });

        if(dataArray!=null && dataArray.length!=0){
            for (let e of collDefaults.split(',')) {
                let readonly = e.endsWith('ro');
                if (readonly) e = e.substring(0, e.length - 2);
    
                let id = parseInt(e);
                if(dataArray.filter(c => c.id === id).length > 0) {
	                let data = dataArray.filter(c => c.id === id)[0];
	                defaults.push({
	                    id: id,
	                    azienda: data.azienda,
	                    codice: data.codice,
	                    descrizione: data.descrizione,
	                    readonly: readonly
	                });
                }
            }

        }

        return defaults;
    },

    /**
     * Effettua il parsing dei profili SoL, ricostruendo cosi' l'alberatura SoL -> Collocazioni -> Profili.
     *
     * @param solDefaults gli input dove sono memorizzati i profili SoL.
     * @returns {*[]} l'alberatura dei SoL di default.
     */
    parseSolDefaults: function (solDefaults) {
        let tree = [];

        if (!solDefaults || solDefaults === '') return [];

        solDefaults.split(',').forEach(e => {
            let split = e.split(this.profilesDivisor);

            let solId = parseInt(split[0]);
            let sol = tree.find(elem => elem.id === solId);
            if (!sol) {
                sol = {
                    id: solId,
                    collocations: []
                };
                tree.push(sol);
            }

            let collocationId = parseInt(split[1]);
            let collocation = sol.collocations.find(elem => elem.id === collocationId);
            if (!collocation) {
                collocation = {
                    id: collocationId,
                    roles: [],
                    //profiles: []
                };
                sol.collocations.push(collocation);
            }
            
            let roleId = parseInt(split[3])
			let role = collocation.roles.find(elem => elem.id === roleId);
			if(!role) {
				role = {
					id: roleId,
					profiles: []
				}
				collocation.roles.push(role);
			}
			
            role.profiles.push({
                id: parseInt(split[2]),
                validity: split[4] ? split[4] : null
            });
        });

        return tree;
    },

    /**
     * Setta tutti i campi dei dati utente ai valori di default.
     */
    setUserDataToDefaults: function () {
        let userData = this.defaults['user-data'];
        let container = $('div.user-data-group');
        $(container).find('input:not([type="checkbox"],[type="radio"]),select').each((i, e) => {
            $(e).val(userData[$(e).attr('name')].value);
        });

        $(container).find('input[type="checkbox"]').each((i, e) => {
            $(e).prop('checked', userData[$(e).attr('name')].value);
        });

        $(container).find('input[type="radio"]').each((i, e) => {
            let val = userData[$(e).attr('name')];
            if (val) $(e).prop('checked', $(e).val() === val.value);
        });
    },

    /**
     * Setta tutti i ruoli ai valori di default.
     */
    setRolesToDefaults: function () {
        if (!this.defaults) return;this.defaults

        this.undoRoles(true);  
        let idRolesInserted = $('#rolesContainer select.role-selector').map((i,e) => $(e).val()).toArray();
        for (id in this.defaults.roles) {
			if(idRolesInserted == null || idRolesInserted == undefined || 
				idRolesInserted.length == 0 || !idRolesInserted.includes(id.toString())) {
	            this.addDefaultRolePrototype(this.defaults.roles[id]);
	            let select = $('#rolesContainer select.role-selector:last').val(id);
	            this.selectRole(select, true);
	            if (this.defaults.roles[id]) {
	                $(select).each((i, e) => this.toggleReadonlySelect(e, true));
	            } else {
	               $(select).next('div.input-group-append').find('button.btn-close').click(e => this.deleteRole(e.target));
	            }
	        }
        }

        this.sectionsVisibilityState['roles'] = true;
        this.toggleRoles(false);
    },

    /**
     * Setta tutte le collocazioni ai valori di default.
     */
    setCollocationsToDefaults: async function () {
        if (!this.defaults) return;

        this.undoCollocations(true);
		let idCollInserted = $('#collocationContainer div.collocation-group input[name="collocazioni"]').map((i,e) => $(e).val()).toArray();
        for (let coll of this.defaults.collocations) {
			if(idCollInserted == null || idCollInserted == undefined || 
				idCollInserted.length == 0 || !idCollInserted.includes(coll.id.toString())) {				
		            this.addCollocationPrototype();
		            let inserted = $('#collocationContainer div.collocation-group:last');
		            this.applyCollocation(inserted, coll.id, coll.azienda, coll.codice, coll.descrizione, coll.readonly);
			}
        }

        await this.getSelectableSols(false);
        await this.getSelectableCollocationSols(false);

        this.sectionsVisibilityState['collocations'] = true;
        this.toggleCollocations(false);
    },

    /**
     * Setta tutti i SoL ai valori di default, richiamando per ogni sezione il metodo userPage.setSingleSolToDefaults.
     */
    setSolsToDefaults: function () {
        if (!this.defaults) return;

        this.undoSol();

        for (let sol of this.defaults.sol) {
            let solGroup = this.buildSolPrototype(true, sol.id);
            this.setSingleSolToDefaults(sol, solGroup);
        }

        $('#solDefaultsContainer div[data-parent="#accordion"]')
            .map((i, e) => $(e).attr('id'))
            .each((i, e) => {
                this.sectionsVisibilityState[e] = true;
                this.toggleSol(e, false);
            });
    },

    /**
     * Setta una singola sezione SoL ai suoi valori di default.
     */
    setSingleSolToDefaults: function (sol, solGroup) {
        $(solGroup).find('div.sol-collocation-group').html('');

        let solSelector = $(solGroup).find('select.sol-selector').val(sol.id);
        let solName = $(solSelector).find('option:selected').text();

        $(solGroup).find('div.card-header button').text(solName);

		
		let solData = [];
		sol.collocations.forEach(coll => {
			coll.roles.forEach(role => {
				role.profiles.forEach(profile => {
					solData.push({collocation:coll, role: role, profile: profile});
				});
			});
		});
		
        $(solGroup).find('div.sol-collocation-container').html('');
        
		solData.forEach(data => {
			userPage.buildDefaultSolCollocationPrototype.apply(userPage, [solGroup, data.collocation]);
			let solCollocationGroup = $(solGroup).find('div.sol-collocation-group:last').get();			
			userPage.buildDefaultSolRolePrototype.apply(userPage, [solCollocationGroup, data.role]);
			
			this.buildProfilePrototype(solCollocationGroup, true);
			let inserted = $(solCollocationGroup).find('div.profile-group:last')
    		$(inserted).find('select.profile-selector').val(data.profile.id);
    		if (data.profile.validity && data.profile.validity !== '') {
        	 	let validityInput = $(inserted).find('input.profile-validity');
        	 	$(validityInput).val(data.profile.validity);
        	 	$(validityInput).parent('div').addClass('is-filled');
    		}

    		$(inserted).find('input[name="oldValue"]').val(data.profile.id);

        	this.selectSolProfile(inserted,true)
		});
    },
    
    setSingleSolToInserted: function (sol, solGroup) {
        //$(solGroup).find('div.sol-collocation-group').html('');

        //let solSelector = $(solGroup).find('select.sol-selector').val(sol.id);
        //let solName = $(solSelector).find('option:selected').text();

        //$(solGroup).find('div.card-header button').text(solName);

		
		let solData = [];
		sol.collocations.forEach(coll => {
			coll.roles.forEach(role => {
				role.profiles.forEach(profile => {
					solData.push({collocation:coll, role: role, profile: profile});
				});
			});
		});
		
       // $(solGroup).find('div.sol-collocation-container').html('');
		$(solGroup).find('select.sedeOperativaSol').val(sol.collocations[0].id).change();
		$(solGroup).find('select.ruoloSol').val(sol.collocations[0].roles[0].id).change();
		$(solGroup).find('select.sol-selector').val(sol.id).change();
        
		solData.forEach(data => {
			//userPage.buildDefaultSolCollocationPrototype.apply(userPage, [solGroup, data.collocation]);
			//let solCollocationGroup = $(solGroup).find('div.sol-collocation-group:last').get();			
			//userPage.buildDefaultSolRolePrototype.apply(userPage, [solCollocationGroup, data.role]);
			
			this.buildProfilePrototype(solGroup, false);
			let inserted = $(solGroup).find('div.profile-group:last')
    		$(inserted).find('select.profile-selector').val(data.profile.id);
    		if (data.profile.validity && data.profile.validity !== '') {
        	 	let validityInput = $(inserted).find('input.profile-validity');
        	 	$(validityInput).val(data.profile.validity);
        	 	$(validityInput).parent('div').addClass('is-filled');
    		}

    		$(inserted).find('input[name="oldValue"]').val(data.profile.id);

        	this.selectSolProfile(inserted,false)
		});
    },

    /* ENABLE/DISABLE ACCORDIONS */

    /**
     * (solo per modifica)
     * Imposta la visualizzazione della sezione dei dati utente in modalita' lettura o modalita' modifica, in base al
     * flag memorizzato in userPage.sectionsVisibilityState.
     *
     * @param resetToDefault se true, effettua il reset dell'intera sezione ai valori di default.
     */
    toggleUserData: function (resetToDefault, dataFromAura = false) {
        let section = $('div.user-data-group');
        if (this.sectionsVisibilityState['user-data']) {
            $(section).find('#userDataToggleBtn span.fa-close').removeClass('fa-close').addClass('fa-pencil');

            if (resetToDefault) this.setUserDataToDefaults();

            $(section).find('button,a:not(.btn-settings)').hide();
            $(section).find('input').prop('readonly', true);

            $(section).find('input[type="checkbox"]').each((i, e) => $(e).prop('disabled', true));

            $('input[name="sessoUtente"]').each((i, e) => {
                $(e).prop('disabled', true);
            });

            $('#statoUtente').filter((i, e) => {
                $(e).prop('disabled', true);
            });

            $(section).find('select').each((i, e) => this.toggleReadonlySelect(e, true));
            this.destroyDatePicker('#dataDiNascita', '#dataFineValidita');

            this.sectionsVisibilityState['user-data'] = false;
        } else {
            $(section).find('#userDataToggleBtn span.fa-pencil').removeClass('fa-pencil').addClass('fa-close');

            $(section).find('button:not(.btn-calendar),a:not(.btn-settings)').show();

            $(section).find('select').each((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                if (ud) this.toggleReadonlySelect(e, false);
            });

            $('#dataFineValidita').filter((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                return ud && ud.readonly;
            }).each((i, e) => this.buildDatePicker(e, settings => settings.minDate = new Date()));

            $('#email').filter((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                if (ud) $(e).prop('readonly', false);
            });

            $('#dataFineValidita').filter((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                if (ud) $(e).prop('readonly', false);

                this.buildDatePicker(e, settings => settings);
            });

            $('#telefono').filter((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                if (ud) $(e).prop('readonly', false);
            });

            $('#statoUtente').filter((i, e) => {
                let ud = this.defaults['user-data'][$(e).attr('name')];
                if (ud) this.toggleReadonlyCheckbox(e, false);
            });

            if(!dataFromAura) {
               $('input[name="sessoUtente"]').each((i, e) => {
                   let ud = this.defaults['user-data'][$(e).attr('name')];
                   if (ud) this.toggleReadonlyCheckbox(e, false);
               });

                $(section).find('input').each((i, e) => {
                    let ud = this.defaults['user-data'][$(e).attr('name')];
                    if (ud) $(e).prop('readonly', false);
                });
                $(section).find('button.btn-calendar').each((i, e) => {
                    let name = $(e).parents('div[data-provide="datepicker"]').find('input[type=text]').attr('name');
                    let ud = this.defaults['user-data'][name];
                    $(e).show();
                });
                $('#dataDiNascita').filter((i, e) => {
                    let ud = this.defaults['user-data'][$(e).attr('name')];
                    return ud;
                }).each((i, e) => this.buildDatePicker(e, settings => settings.maxDate = new Date()));

            }

            this.sectionsVisibilityState['user-data'] = true;
        }
    },

    /**
     * (solo per modifica)
     * Imposta la visualizzazione della sezione dei ruoli in modalita' lettura o modalita' modifica, in base al
     * flag memorizzato in userPage.sectionsVisibilityState.
     *
     * @param resetToDefault se true, effettua il reset dell'intera sezione ai valori di default.
     */
    toggleRoles: function (resetToDefault) {
        let accordion = $('div.roles-accordion');
        if (this.sectionsVisibilityState['roles']) {
            $(accordion).find('#rolesToggleBtn span.fa-close').removeClass('fa-close').addClass('fa-pencil');

            if (resetToDefault) this.setRolesToDefaults();

            $(accordion).find('button,a:not(.btn-settings)').hide();
            $(accordion).find('select').filter((i, e) => !this.defaults['roles'][$(e).val()])
                .each((i, e) => this.toggleReadonlySelect(e, true));

            this.sectionsVisibilityState['roles'] = false;
        } else {
            $(accordion).find('#rolesToggleBtn span.fa-pencil').removeClass('fa-pencil').addClass('fa-close');

            $(accordion).find('button,a:not(.btn-settings)').show();
            $(accordion).find('select').filter((i, e) => !this.defaults['roles'][$(e).val()])
                .each((i, e) => this.toggleReadonlySelect(e, false));

            this.sectionsVisibilityState['roles'] = true;
        }
    },

    /**
     * (solo per modifica)
     * Imposta la visualizzazione della sezione delle collocazioni in modalita' lettura o modalita' modifica, in base al
     * flag memorizzato in userPage.sectionsVisibilityState.
     *
     * @param resetToDefault se true, effettua il reset dell'intera sezione ai valori di default.
     */
    toggleCollocations: function (resetToDefault) {
        let accordion = $('div.collocations-accordion');
        if (this.sectionsVisibilityState['collocations']) {
            $(accordion).find('#collocationsToggleBtn span.fa-close').removeClass('fa-close').addClass('fa-pencil');

            if (resetToDefault) this.setCollocationsToDefaults();
            $(accordion).find('button,a:not(.btn-settings)').hide();

            this.sectionsVisibilityState['collocations'] = false;
        } else {
            $(accordion).find('#collocationsToggleBtn span.fa-pencil').removeClass('fa-pencil').addClass('fa-close');

            $(accordion).find('button,a:not(.btn-settings)').show();

            this.sectionsVisibilityState['collocations'] = true;
        }
    },

    /**
     * (solo per modifica)
     * Imposta la visualizzazione di una sezione SoL in modalita' lettura o modalita' modifica, in base al flag
     * memorizzato in userPage.sectionsVisibilityState.
     *
     * @param groupId l'ID della sezione SoL.
     * @param resetToDefault se true, effettua il reset dell'intera sezione ai valori di default.
     */
    toggleSol: function (groupId, resetToDefault) {
        let accordion = $('#' + groupId);

        if (this.sectionsVisibilityState[groupId]) {
            let solId = parseInt(groupId.substring(11));
            let sol = this.defaults.sol.filter(s => s.id === solId)[0];
            if (resetToDefault) this.setSingleSolToDefaults(sol, accordion);

            $(accordion).find('div.titolocard a span.fa-close').removeClass('fa-close').addClass('fa-pencil');

            $(accordion).find('select').prop('disabled', true);
            let validity = $(accordion).find('input.profile-validity').removeClass('hasDatepicker').prop('disabled', true);
            this.destroyDatePicker(validity);
            $(accordion).find('button,a:not(.btn-settings)').hide();

            this.sectionsVisibilityState[groupId] = false;
        } else {
            $(accordion).find('div.titolocard a span.fa-pencil').removeClass('fa-pencil').addClass('fa-close');

            //$(accordion).find('select').prop('disabled', false);
            $(accordion).find('input.profile-validity').prop('disabled', false)
                .each((i, e) => this.buildDatePicker(e, settings => settings.minDate = new Date()));
            $(accordion).find('button,a:not(.btn-settings)').show();

            this.sectionsVisibilityState[groupId] = true;
        }
    },

     /**
     * Mostra un alert, come modale bootstrap.
     *
     * @param msg il messaggio da visualizzare.
     * @param title il titolo del modal. Se null, il titolo viene dedotto dal tipo del modale.
     * @param type il tipo del modale. Puo' essere: info - warning - error
     */
      showAlertAura: async function (mainMessage, mailAura, noMailAura) {
        if(mailAura != undefined && mailAura != null && mailAura != '') {
			let apps = mailAura.split(',');
			for(const app of apps) {
				let messaggio = await $.ajax({
            	url: '/configuratore/ajax/messaggioAura',
            	dataType: 'json',
            	data: {
					msg: app.substring(0,1),
					app: app.substring(1, app.length),
					errore: false
				},
            	method: 'POST'
				});
				mainMessage = mainMessage.concat('<p>'+messaggio+'</p>');
			}
        }
        if(noMailAura != undefined && noMailAura != null && noMailAura != '') {
			let apps = noMailAura.split(',');
			for(const app of apps) {
				let messaggio = await $.ajax({
            	url: '/configuratore/ajax/messaggioAura',
            	dataType: 'json',
            	data: {
					msg: app.substring(0,1),
					app: app.substring(1, app.length),
					errore: true
				},
            	method: 'POST'
				});
				mainMessage = mainMessage.concat('<p>'+messaggio+'</p>');
			}
        }
        
        this.showAlert(mainMessage, null, 'info');
        
       /* let cssClass;
           cssClass = 'alert-info';
           title = msg=='abilitazione' ? 'Abilitazione Scerev' : 'Disabilitazione Scerev';

           

        bootbox.alert({
            title: '<b>' + title + '</b>',
            message: '<div role="alert" class="alert alert-heading ' + cssClass + '">' + messaggio + '</div>',
            size: 'large',
            centerVertical: true
        });*/
    },

  
	

}
