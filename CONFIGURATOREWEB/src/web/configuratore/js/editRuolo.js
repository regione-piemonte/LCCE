let ruoliPage = {
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
		this.setRuoliSelezionabiliToDefaults();
		this.setRuoliCompatibiliToDefaults();

		$('#addruoliSelBtn').click(() => this.addRuoliSelezionabiliPrototype());
		$('#addruoliCompatibiliBtn').click(() => this.addRuoliCompatibiliPrototype());


		hideLoading();
	},

	/**
	 * Carica i valori di default che sono stati passati negli input dal backend.
	 *
	 * @returns {Promise<void>}
	 */
	loadDefaults: async function() {
		let rolesInput = $('input[name="ruoliSel"]');
		this.defaults['roles'] = this.parseValuesExt(rolesInput);
		$(rolesInput).remove();


		let ruoliCompatibiliInput = $('input[name="ruoliCompatibili"]');
		this.defaults['ruoliCompatibili'] = this.parseValues(ruoliCompatibiliInput);
		$(ruoliCompatibiliInput).remove();
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

	addDefaultRuoliSelezionabiliPrototype: function(readonly) {
		let inputs = $('#ruoliSelContainer select[name="ruoli"]');
		if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
			this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
			return;
		}

		let inserted = this.insertPrototype('ruoliSelContainer', !readonly ? 'role' : 'role-ro', 'role-group');
		$(inserted).find('select.role-selector')
			.focus(e => $(e.target).data('previous', $(e.target).val()))
			.change(e => this.selectRole(e.target, false));
	},
	addDefaultRuoliCompatibiliPrototype: function(readonly) {
		let inputs = $('#ruoliCompatibiliContainer select[name="ruoliCompatibili"]');
		if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
			this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
			return;
		}

		let inserted = this.insertPrototype('ruoliCompatibiliContainer', !readonly ? 'ruoliCompatibili' : 'role-ro', 'role-group');
		$(inserted).find('select.role-selector')
			.focus(e => $(e.target).data('previous', $(e.target).val()))
			.change(e => this.selectRuoliCompatibili(e.target, false));
	},


	/**
	 * Setta tutti i ruoli ai valori di default.
	 */
	setRuoliSelezionabiliToDefaults: function() {
		if (!this.defaults) return; this.defaults

		this.undoRoles(true);

		for (id in this.defaults.roles) {
			
			console.log(
				 "index::" + id + "; " +  
				 "value::" + this.defaults.roles[id]
			);
			
			this.addDefaultRuoliSelezionabiliPrototype(false);
			let select = $('#ruoliSelContainer select.role-selector:last').val(id);
			let selectColl = $('#ruoliSelContainer select.collTipo-selector:last').val(this.defaults.roles[id]);


			this.selectRole(select, true);

			$(select).next('div.input-group-append').find('button.btn-close').click(e => this.deleteRole(e.target));
			$(selectColl).next('div.input-group-append').find('button.btn-close').click(e => this.deleteRole(e.target));
			
			let hiddevVal = $('#ruoliSelContainer input.ruoliSelH:last').val(id + "-" + this.defaults.roles[id]);
			
			$(select).change(function() {
				console.log(">> " + $(this).val() + "-" + $(selectColl).val());
				$(hiddevVal).val( $(this).val() + "-" + $(selectColl).val() );
			}
			);

			$(selectColl).change(function() {
				console.log(">> " + $(select).val() + "-" + $(this).val());
				$(hiddevVal).val( $(select).val() + "-" + $(this).val() );
			}
			);
			
		}

		this.sectionsVisibilityState['roles'] = true;
		//this.toggleRoles();
	},
	/**
	 * Setta tutti i ruoli ai valori di default.
	 */
	setRuoliCompatibiliToDefaults: function() {
		if (!this.defaults) return; this.defaults

		this.undoRoles(true);

		for (id in this.defaults.ruoliCompatibili) {
			console.log(
				 "ruoliCompatibili::" + id + "; "
			);
			
			this.addDefaultRuoliCompatibiliPrototype(this.defaults.ruoliCompatibili[id]);
			let select = $('#ruoliCompatibiliContainer select.ruoliComp-selector:last').val(id);
			this.selectRuoliCompatibili(select, true);
			if (this.defaults.ruoliCompatibili[id]) {
				$(select).each((i, e) => this.toggleReadonlySelect(e, true));
			} else {
				$(select).next('div.input-group-append').find('button.btn-close').click(e => this.deleteRole(e.target));
			}
		}

		this.sectionsVisibilityState['ruoliCompatibili'] = true;
		//this.toggleRoles();
	},
	/**
 * Svuota la sezione dei ruoli. Per fare cio' deve essere prima controllata la presenza di SoL: se presenti,
 * verranno anch'essi cancellati.
 *
 * @param force se true, il controllo sui SoL non viene effettuato.
 */
	undoRoles: function() {
		let callback = () => $('#rolesContainer').find('div.role-group').has('select[name="ruoli"]:not(:disabled)').remove();

		callback();
	},

	/**
	 * Aggiunge una nuova sezione "Ruolo", al click del pulsante apposito. Per effettuare l'inserimento, i ruoli
	 * inseriti precedentemente devono essere compilati.
	 *
	 * @param readonly se true, il ruolo viene aggiunto come readonly: totalmente non modificabile (ad esempio, se
	 * l'operatore non ha i permessi di rimuovere/modificare quel ruolo).
	 */
	addRuoliSelezionabiliPrototype: function(readonly) {
		let inputs = $('#ruoliSelContainer select[name="ruoliSelect"]');
		if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
			this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
			return;
		}

		let inserted = this.insertPrototype('ruoliSelContainer', !readonly ? 'role' : 'role-ro', 'role-group');
		
					
		$(inserted).find('select.role-selector')
			.focus(e => $(e.target).data('previous', $(e.target).val()))
			.change(e => this.selectRole(e.target, false));
		if (readonly) {
			$(inserted).find('select.role-selector').each((i, e) => this.toggleReadonlySelect(e, true));
		} else {
			// console.log('>>' + $(inserted).html());
			// console.log('>>' + $(inserted).find('button.btn-close').html());
			let d= $(inserted).find('div.collTipo-group').find('button.btn-close');
			//selezione gista??
			$(inserted).find('button.btn-close').click(e => this.deleteRole(e.target));
			//$(inserted).find('button.btn-close').click(e => this.test(e.target));
			
			
			
			
			let select = $(inserted).find('select.role-selector:last');
			let selectColl = $(inserted).find('select.collTipo-selector:last');
			let hiddevVal =  $(inserted).find('input.ruoliSelH:last');
			$(select).change(function() {
				console.log(">> " + $(this).val() + "-" + $(selectColl).val());
				$(hiddevVal).val( $(this).val() + "-" + $(selectColl).val() );
			}
			);

			$(selectColl).change(function() {
				console.log(">> " + $(select).val() + "-" + $(this).val());
				$(hiddevVal).val( $(select).val() + "-" + $(this).val() );
			}
			);


		}
	},

	/**
	 * Aggiunge una nuova sezione "Ruolo", al click del pulsante apposito. Per effettuare l'inserimento, i ruoli
	 * inseriti precedentemente devono essere compilati.
	 *
	 * @param readonly se true, il ruolo viene aggiunto come readonly: totalmente non modificabile (ad esempio, se
	 * l'operatore non ha i permessi di rimuovere/modificare quel ruolo).
	 */
	addRuoliCompatibiliPrototype: function(readonly) {
		let inputs = $('#ruoliCompatibiliContainer select[name="ruoliCompatibili"]');
		if ($(inputs).filter((i, e) => $(e).val() === '').length > 0) {
			this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
			return;
		}

		let inserted = this.insertPrototype('ruoliCompatibiliContainer', !readonly ? 'ruoliCompatibili' : 'role-ro', 'role-group');
		$(inserted).find('select.ruoliComp-selector')
			.focus(e => $(e.target).data('previous', $(e.target).val()))
			.change(e => this.selectRuoliCompatibili(e.target, false));
		if (readonly) {
			$(inserted).find('select.ruoliComp-selector').each((i, e) => this.toggleReadonlySelect(e, true));
		} else {
			$(inserted).find('button.btn-close').click(e => this.deleteRole(e.target));
		}
	},
	
	
	/**
	 * Eseguita alla selezione di un ruolo, o quando viene costruito un ruolo di default. Controlla se il ruolo non e'
	 * stato gia' inserito, e lo fa verificando la presenza dei SoL.
	 *
	 * @param select la select del ruolo che e' stata appena cambiata di valore.
	 * @param force se true, non effettua il controllo dei SoL.
	 */
	selectRole: function(select, force) {
		let value = $(select).val();

		if (value !== null && value !== '') {
			let def = false;
			if (this.defaults != null && this.defaults.roles != null) {
				for (id in this.defaults.roles) {
					if (value == id) {
						def = true;
						return;
					}
				}
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
	/**
	 * Eseguita alla selezione di un ruolo, o quando viene costruito un ruolo di default. Controlla se il ruolo non e'
	 * stato gia' inserito, e lo fa verificando la presenza dei SoL.
	 *
	 * @param select la select del ruolo che e' stata appena cambiata di valore.
	 * @param force se true, non effettua il controllo dei SoL.
	 */
	selectRuoliCompatibili: function(select, force) {
		let value = $(select).val();

		if (value !== null && value !== '') {
			let def = false;
			if (this.defaults != null && this.defaults.ruoliCompatibili != null) {
				for (id in this.defaults.ruoliCompatibili) {
					if (value == id) {
						def = true;
						return;
					}
				}
			}

		}

		let callback = result => {
			if (result) {
				if (value !== '') {
					let present = $('#ruoliCompatibiliContainer select[name="ruoliCompatibili"]')
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
			let isTheOnlyValue = $('#ruoliCompatibiliContainer select[name="ruoliCompatibili"]')
				.map((i, elem) => $(elem).val())
				.filter((i, elem) => elem !== '')
				.length === 0;
			if (value === '' && isTheOnlyValue) this.doWithSolIntegrity(callback);
		}
	},
	deleteRole: function(button) {
		//let value = $(button).closest('div.form-group').find('select[name="ruoli"]').val();
		let callback = async result => {
			if (result) {
				$(button).closest('div.form-group').remove();

			}
		};
		callback(true);

	},
	test: function(button) {
		//let value = $(button).closest('div.form-group').find('select[name="ruoli"]').val();
		let callback = async result => {
			this.showAlert(this.messages.errorFieldsNotFilled, null, 'error');
		};
		callback(true);

	},
	// COLLOCATIONS FUNCTIONS
}