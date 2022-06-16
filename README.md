# Music Media Store
## Specifica
Si vuole progettare un sistema informativo per gestire le informazioni relative alla gestione di un negozio virtuale di CD e DVD musicali (vende solo via web). \
Il negozio mette in vendita CD di diversi generi: jazz, rock, classica, latin, folk, world-music, e così via. Per ogni CD o DVD il sistema memorizza: un codice univoco, il titolo, i titoli di tutti i pezzi contenuti, eventuali fotografie della copertina, il prezzo, la data dalla quale è presente sul sito web del negozio, il musicista/band titolare, una descrizione, il genere del CD o DVD, i musicisti che vi suonano, con il dettaglio degli strumenti musicali usati. Per ogni musicista il sistema registra il nome d’arte, il genere principale, l’anno di nascita, se noto, gli strumenti che suona. Sul sito web del negozio è illustrato il catalogo dei prodotti in vendita. Cliccando sul nome del prodotto, appare una finestra con i dettagli del prodotto stesso. \
\
I clienti possono acquistare on-line selezionando gli oggetti da mettere in un “carrello della spesa” virtuale. Deve essere possibile visualizzare il contenuto del carrello, modificare il contenuto del carrello, togliendo alcuni articoli. Al termine dell’acquisto va gestito il pagamento, che può avvenire con diverse modalità. \
\
Il sistema supporta differenti ricerche: per genere, per titolare del CD o DVD, per musicista partecipante, per prezzo. Coerentemente, differenti modalità di visualizzazione, sono altresì supportate. \
Ogni vendita viene registrata indicando il cliente che ha acquistato, i prodotti acquistati, il prezzo complessivo, la data di acquisto, l’ora, l’indirizzo IP del PC da cui è stato effettuato l’acquisto, la modalità di pagamento (bonifico, carta di credito, Paypal) e la modalità di consegna (corriere, posta). \
\
Per ogni cliente il sistema registra: il suo codice fiscale, il nome utente (univoco) con cui si è registrato, la sua password, il nome, il cognome, la città di residenza, il numero di telefono ed eventualmente il numero di cellulare. \
Per i clienti autenticati, il sistema propone pagine specializzate che mostrano suggerimenti basati sul genere dei precedenti prodotti acquistati. Se il cliente ha fatto già 3 acquisti superiori ai 250 euro l’uno entro l’anno, il sistema gli propone sconti e consegna senza spese di spedizione. \
\
Il personale autorizzato del negozio può inserire tutti i dati dei CD e DVD in vendita. Il personale inserisce anche il numero di pezzi a magazzino. Il sistema tiene aggiornato il numero dei pezzi a magazzino durante la vendita e avvisa il personale del negozio quando un articolo (CD o DVD) scende sotto i 2 pezzi in magazzino.
\
## Analisi dei requisiti
Di seguito sono riportati i requisiti funzionali del software realizzato.
1.  Gli utenti che si interfacciano al sistema devono aver la possibilità di visualizzare il catalogo che mostra i prodotti (CD/DVD) salvati nel database. Cliccando su un prodotto, il sistema deve mostrare la scheda con i dettagli del CD/DVD. \
Il sistema deve inoltre permettere di effettuare ricerche che permettano di filtrare i prodotti del catalogo secondo alcuni criteri.
2.  Il sistema deve permettere a tutti gli utenti di registrarsi o autenticarsi tramite credenziali. Una volta autenticato l’utente acquisirà i permessi di cliente oppure di personale autorizzato.
    
3.  Il cliente può visualizzare un catalogo personale generato in base ai suoi precedenti acquisti. Il sistema riporterà anche degli sconti se il cliente ha già effettuato almeno 3 acquisti che superano i e250 entro l’anno. \
I clienti possono acquistare on-line selezionando gli oggetti da mettere in un “carrello della spesa” virtuale. \
Deve essere possibile visualizzare il contenuto del carrello, modificare il contenuto del carrello, togliendo alcuni articoli. Al termine dell’acquisto va gestito il pagamento, che può avvenire con diverse modalità. \
Ogni vendita viene registrata indicando il cliente che ha acquistato, i prodotti acquistati, il prezzo complessivo, la data di acquisto, l’ora, l’indirizzo IP del PC da cui è stato effettuato l’acquisto, la modalità di pagamento (bonifico, carta di credito, Paypal) e la modalità di consegna (corriere, posta).
    
4.  Il personale autorizzato ad ogni accesso riceverà una notifica nel caso in cui le scorte di 1 o più prodotti scendano sotto i 3 pezzi e potrà decidere di modificarle. \
Ha anche la possibilità di inserire un musicista nel database indicando il nome d’arte, il genere principale, l’anno di nascita e, se noto, gli strumenti che suona. Infine può inserire un prodotto nel database del sistema indicando un codice univoco, il titolo, i titoli di tutti i pezzi contenuti, eventuale fotografia della copertina, il prezzo, la data dalla quale è presente sul sito web del negozio, il musicista/band titolare, una descrizione, il genere del CD o DVD, i musicisti che vi suonano, con il dettaglio degli strumenti musicali usati.


## Interpretazione dei requisiti e scelte progettuali
Di seguito sono riportate le scelte progettuali effettuate dopo l’interpretazione dei requisiti del sistema:
- ogni utente che si interfaccia con il sito web può visualizzare il catalogo, effettuare ricerche e richiedere le schede con i dettagli dei prodotti anche se non si è autenticato;
- ogni utente che effettua la registrazione al sito viene anche automaticamente loggato attraverso le sue credenziali;
- la ricerca è implementata attraverso l’applicazione di filtri al catalogo, e non come riordinamento dello stesso, variandone dunque la modalità di visualizzazione;
- il catalogo personale di un cliente viene generato facendo riferimento alla storia dei suoi acquisti e mettendo in primo piano i CD/DVD del genere più acquistato;
- gli sconti per i clienti che hanno effettuato almeno 3 acquisti da e250 entro l’anno vengono riportati direttamente sul catalogo;
- il personale autorizzato può inserire anche dei musicisti nel database del sistema oltre ai prodotti e variare le scorte.