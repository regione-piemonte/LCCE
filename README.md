
# Prodotto
Il prodotto ***Configuratore Operatori***   realizza un sistema software per l'accesso ai servizi online della Sanità della Regione Piemonte che comprende:
- un punto unico di accesso (PUA) per consentire agli utenti di enti pubblici e privati di accedere ad uno dei servizi online a cui sono abilitati dopo essersi autenticati e aver selezionato il *ruolo* con cui vogliono operare e la *collocazione* presso cui operano 
- un database che memorizza tutte le informazioni relative a utenti, ruoli, collocazioni e abilitazioni attive
- un meccanismo di accesso per consentire l'integrazione sicura fra il PUA e i servizi online a cui l'utente è abilitato.


# Descrizione del prodotto 
Il prodotto è composto attualmente dalle seguenti componenti 
| Componente |Descrizione  |Versione |
|--|--|--|
| PUAWA | Web Application che realizza il PUA |-XXX-|
| PUARW | Risorse statiche utili alla componente software PUAWA |-XXXXX-|
| DMALOGINCCEDB | Script per creazione database che memorizza tutte le informazioni relative a utenti, ruoli, collocazioni e abilitazioni attive |-XXXXX-|
| DMALOGINCCEBL | Web Service utili a realizzare un meccanismo di accesso per consentire l'integrazione sicura fra il PUA e i vari servizi online a cui l'utente è abilitato |-XXXXX-|
| SOLCONFIG |  web application che consente di configurare gli utenti del sistema |*DI PROSSIMA PUBBLICAZIONE*|


# Prerequisiti di sistema 

## Software
- [Apache 2.4](https://www.apache.org/)
- [RedHat JBoss EAP 6.4](https://developers.redhat.com/products/eap/download)
- [JDK Oracle 1.8](https://www.oracle.com/java/technologies/downloads/archive/) 
- [PostgreSQL 9.6](https://www.postgresql.org/download/)
- [CentOS 7.6](https://www.centos.org/)

## Dipendenze da sistemi esterni
### Sistema di autenticazione
Il sistema di autenticazione su cui si basa il PUA è esterno al presente prodotto ed è basato sul framework SHIBBOLETH composto da Service Provider e Identity Provider. Gli operatori che accedono ai servizi online della Sanità regionale piemontese si basano su 
- credenziali della PA Piemontese
- certificati digitali

# Installing
Vedere il file install.pdf nella cartella DOCS 


# Versioning
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).

# Authors
La lista delle persone che hanno partecipato alla realizzazione del software sono:
- Davide Grosso
- Giuliano Iunco
- Giuseppe Vezzetti
- Manuela Bontempi
- Michele Mastrorilli
- Nicola Gaudenzi
- Viviana Addari
- Yvonne Carpegna


# Copyrights
© Copyright Regione Piemonte – 2022


# License
EUPL-1.2 Compatibile
Vedere il file LICENSE.txt per i dettagli.