let collocazioni = {
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
		confirmNoRuparCredentialsRequest: "<p>I campi Inquadramento contrattuale e Telefono non sono compilati.</p> <p> Non sar&agrave possibile effettuare successivamente la richiesta delle credenziali RUPAR</p> <p>Sei sicuro di voler proseguire?</p>",
		errorValueAlreadyPresent: "<p>Valore gi&agrave; inserito.</p>",
		errorFieldsNotFilled: "<p>Compilare tutti i campi precedenti prima di inserirne di nuovi.</p>",
		errorNoRolesSelected: "<p>Per inserire un SoL, &egrave; necessario configurare prima almeno un Ruolo.</p>",
		errorNoCollocationSelected: "<p>Per inserire un SoL, &egrave; necessario configurare prima almeno una Collocazione.</p>",
		errorFunctionalitiesModalSelectSolProfileFirst: "<p>Selezionare prima un profilo SoL per visualizzarne le funzionalit&agrave;.</p>",
		errorRoleNotCompatible: "<p>Il ruolo non pu&ograve essere selezionato in quanto non compatibile con i ruoli gi&agrave assegnati</p>",
		errorSolNonTrovato: "<p>Non &egrave possibile procedere con la configurazione perch&egrave non sono stati trovati dei SOL per l'azienda/struttura e ruolo selezionati.</p>",
		errorConfigrazioneEsistente: "<p>La configurazione inserita &egrave gi&agrave presente.</p>",
		errorSolConfiguratorePresente: "<p>Questo utente ha gi&agrave un profilo titolare o delegato per il Configuratore dei serivizi digitali e per la collocazione scelta.</p>",
		errorSolConfiguratorePresenteNotVisible: "<p>Non &egrave possibile abilitare questo utente al Configuratore dei servizi digitali per la collocazione scelta.</p>",
		confirmUserEditSavedErrorMail: "<p>La richiesta configurazione di ${name} ${surname} &egrave; stata salvata correttamente.</p><p>Non &egrave stato possibile  inviare una mail a ${name} ${surname} con il riepilogo della configurazione.</p>",
		errorEmailNotInserted: "<p>Inserire indirizzo email</p>",
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
	 * Divisore per i dati dei profili SoL.
	 */
	profilesDivisor: '|',
	//-------------

	initDataEditPage: async function() {
		showLoading();
		//this.initCommon();
		this.loadPrototypes();
		await this.loadDefaults();
		
		await this.setCollocationsToDefaults();
		$('#addCollocationBtn').click(() => this.addCollocationPrototype());

		hideLoading();
	},

	/**
	 * Carica i valori di default che sono stati passati negli input dal backend.
	 *
	 * @returns {Promise<void>}
	 */
	loadDefaults: async function() {

        let collInput = $('input[name="collocazioni"]');
        let collDefaults = $(collInput).val();
        
        this.defaults['collocations'] = await this.parseCollocationsDefaults(collDefaults);
        
        $(collInput).remove();
        
	},
	parseValues: function(rolesInput) {
		let values = {};
		let val = $(rolesInput).val();
		if (val) {
			val.split(',').forEach(v => {
				let readonly = v.endsWith('ro');
				values[readonly ? v.substring(0, v.length - 2) : v] = readonly;
			});
		}
		return values;
	},
	parseValuesExt: function(rolesInput) {
		let values = {};
		let val = $(rolesInput).val();
		if (val) {
			val.split(',').forEach(v => {
				let dataExt = v.split('-')
				values[dataExt[0]] = dataExt[1];
			});
		}
		return values;
	},
	/**
	 * Carica tutti i prototipi dal DOM e li inserisce in userPage.prototypes.
	 */
	loadPrototypes: function() {
		let prototypesContainer = $('#prototypes');
		$(prototypesContainer)
			.find('div[data-prototype]')
			.each((i, e) => this.prototypes[$(e).attr('data-prototype')] = $(e).html());
		$(prototypesContainer).remove();
	},

	/**
	 * Mostra un alert, come modale bootstrap.
	 *
	 * @param msg il messaggio da visualizzare.
	 * @param title il titolo del modal. Se null, il titolo viene dedotto dal tipo del modale.
	 * @param type il tipo del modale. Puo' essere: info - warning - error
	 */
	showAlert: function(msg, title, type) {
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
	 * Inserisce un prototipo vuoto al container indicato.
	 *
	 * @param containerId l'ID del container in cui inserire il nuovo prototipo.
	 * @param prototypeName il nome del prototipo. E' identificato dall'attributo data-prototype.
	 * @param groupName la classe identificativa del prototipo.
	 * @returns {*|Window.jQuery|[]} la sezione appena inserita.
	 */
	insertPrototype: function(containerId, prototypeName, groupName) {
		return $('#' + containerId)
			.append(this.prototypes[prototypeName])
			.children((groupName ? 'div.' + groupName : 'div') + ':last');
	},
	/**
	 * Poiche' le select non possono essere readonly, con questo metodo viene simulato il readonly, disabilitando la
	 * select in questione, e creando affianco un input hidden con il valore selezionato nella select, in modo che viene
	 * inviato nell'eventuale form.
	 *
	 * @param select la select.
	 * @param state se true, la select viene resa readonly, false altrimenti.
	 */
	toggleReadonlySelect: function(select, state) {
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

	//---

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
                // $(inserted).find('div.collocation-search-box').show();
                if (!$(e.target).find('option:selected').data('company')) {
                    $(inserted).find('a.collocation-select-company-button').off('click').hide();
                } else {
                    
                        this.selectCollocation(inserted, {
                            id: parseInt($(inserted).find('select').val()),
                            code: '-',
                            desc: null
                        });                    
/*                    
                    $(inserted).find('a.collocation-select-company-button').unbind('click').click(() =>
                        this.selectCollocation(inserted, {
                            id: parseInt($(inserted).find('select').val()),
                            code: '-',
                            desc: null
                        })
                    ).show();
*/                    
                    
                    
                }
            } else {
                $(inserted).find('div.collocation-search-box').hide();
            }


            $(inserted).find('input.coll-code-input').val('');
            $(inserted).find('input.coll-desc-input').val('');
            
            /*  
            $(inserted).find('input[name="collocazioni"]').val('');
            */
            
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

/*
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
*/

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
     * Setta tutte le collocazioni ai valori di default.
     */
    setCollocationsToDefaults: async function () {
        if (!this.defaults) return;

        // this.undoCollocations(true);

        for (let coll of this.defaults.collocations) {
            this.addCollocationPrototype();
            let inserted = $('#collocationContainer div.collocation-group:last');
            this.applyCollocation(inserted, coll.id, coll.azienda, coll.codice, coll.descrizione, coll.readonly);
        }


        this.sectionsVisibilityState['collocations'] = true;
        this.toggleCollocations(false);
    },
    
}