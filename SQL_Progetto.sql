--Creazione tabelle e domini
DROP DOMAIN IF EXISTS dischi CASCADE; 
CREATE DOMAIN dischi AS VARCHAR(3)
CHECK(VALUE IN('CD', 'DVD'));

DROP DOMAIN IF EXISTS generi CASCADE; 
CREATE DOMAIN generi AS VARCHAR(20)
CHECK(VALUE IN('Country', 'Classica', 'Latin', 'Folk', 'World-music', 'Jazz', 'Pop', 'Rock', 'Hip Hop', 'Rap', 'House', 'Dance'));

DROP DOMAIN IF EXISTS pagamenti CASCADE; 
CREATE DOMAIN pagamenti AS VARCHAR(20)
CHECK(VALUE IN('Bonifico', 'Carta di credito', 'Paypal'));

DROP DOMAIN IF EXISTS consegna CASCADE; 
CREATE DOMAIN consegna AS VARCHAR(20)
CHECK(VALUE IN('Corriere', 'Posta'));

DROP TABLE IF EXISTS Disk CASCADE;
CREATE TABLE Disk(
	codice INTEGER PRIMARY KEY,
	titolo VARCHAR(30) NOT NULL,
	titoliPezzi VARCHAR(200) NOT NULL,
	copertina VARCHAR(200) DEFAULT '/Users/Ross/Desktop/CD.png',
	prezzo NUMERIC(5, 2) NOT NULL CHECK(prezzo > 0),
	dataPubblicazioneSito DATE NOT NULL,
	titolare VARCHAR(30) NOT NULL,
	descrizione TEXT NOT NULL,
	genere generi NOT NULL,
	tipo dischi NOT NULL,
	musicisti VARCHAR(200) NOT NULL,
	strumenti VARCHAR(200),
	totMagazzino INTEGER CHECK(totMagazzino >= 0)
);

DROP TABLE IF EXISTS Cliente CASCADE;
CREATE TABLE Cliente(
	codiceFiscale VARCHAR(20) UNIQUE NOT NULL,
	nomeUtente VARCHAR(30) PRIMARY KEY,
	password VARCHAR(30) NOT NULL,
	nome VARCHAR(20) NOT NULL,
	cognome VARCHAR(20) NOT NULL,
	città VARCHAR(20) NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	cellulare VARCHAR(20),
	personaleAutorizzato BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS Musicista CASCADE;
CREATE TABLE Musicista(
	nomeArte VARCHAR(50) PRIMARY KEY,
	genere generi NOT NULL,
	annoNascita INTEGER CHECK(annoNascita>1800 AND annoNascita<2017),
	strumenti VARCHAR(200)
);

DROP TABLE IF EXISTS inCarrello CASCADE;
CREATE TABLE inCarrello(
	cliente VARCHAR(30) REFERENCES Cliente,
	disk INTEGER REFERENCES Disk,
	quantità INTEGER CHECK(quantità > 0),
	PRIMARY KEY(cliente, disk)
);

DROP TABLE IF EXISTS Vendita CASCADE;
CREATE TABLE Vendita(
	cliente VARCHAR(30) REFERENCES Cliente,
	prodotti VARCHAR(200) NOT NULL,
	ip VARCHAR(15) NOT NULL,
	prezzoTot NUMERIC(7, 2) CHECK(prezzoTot >= 0),
	dataOraAcquisto TIMESTAMP NOT NULL,
	modalitàPagamento pagamenti NOT NULL,
	modalitàConsegna consegna NOT NULL,
	PRIMARY KEY(cliente, dataOraAcquisto)
);



-- Inserimenti
INSERT INTO Disk (codice, titolo, titoliPezzi, copertina, prezzo, dataPubblicazioneSito, titolare, descrizione, genere, tipo, musicisti, strumenti, totMagazzino)
	VALUES 	(550, 'Buoni o Cattivi', 'Buoni o Cattivi, Come Stai, Anymore, Hai mai, Non basta niente, Dimenticarsi, Da sola con te, Cosa vuoi da me, Un senso', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/BuoniOCattivi.jpg', 10.90, '2004-04-02', 'Vasco Rossi', 'Buoni o Cattivi è il quattordicesimo album di Vasco Rossi', 'Rock', 'CD', 'Vasco Rossi, Gaetano Curreri, Saverio Grandi, Guido Elmi, Tullio Ferro', 'Chitarra, Batteria, Basso, Piano', 10),
		(770, 'Made in Italy', 'La vita facile, Mi chiamano tutti Riko, Vittime e complici, Meno male, G come Giungla, Ho fatto in tempo ad avere un futuro, Made in Italy, Quasi uscito', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/MadeInItaly.png', 11.60, '2016-11-18', 'Ligabue', 'Made in Italy è il dodicesimo album di Luciano Ligabue', 'Rock', 'CD', 'Ligabue, Max Cottafavi, Davide Pezzin, Michele Urbano', 'Chitarra, Basso, Batteria', 100),
		(880, 'Midnite', 'Borderline, Russel Crowe, Rob Zombie, Space Invaders, Old Boy, Killer Game, S.A.L.M.O., Ordinaria Follia, Redneck, Sadico, Faraway', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/Midnite.jpg', 13.90, '2013-04-02', 'Salmo', 'Midnite è il terzo album del rapper Salmo', 'Rap', 'CD', 'Nitro, Noyz Narcos, Enigma', 'Basso, Batteria', 100),
		(551, 'Il mondo che vorrei', 'Il mondo che vorrei, Vieni qui, Gioca con me, E adesso che tocca a me, Dimmelo te, Cosa importa a me, Non vivo senza te, Qui si fa la storia, Colpa del whisky, Basta poco', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/IlMondoCheVorrei.jpg', 11.90, '2008-03-28', 'Vasco Rossi', 'Il mondo che vorrei è il quattordicesimo album di Vasco Rossi', 'Rock', 'CD', 'Vasco Rossi, Stef Burns, Vinnie Colaiuta, Tony Franklin, Michael Landau', 'Chitarra, Batteria, Basso, Chitarra elettrica', 100),
		(660, 'Inedito', 'Benvenuto, Non ho mai smesso, Bastava, Le cose che non mi aspetto, Troppo tempo, Mi tengo, Inedito, Nessuno sa, Celeste', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/Inedito.jpg', 10.80, '2011-11-11', 'Laura Pausini', 'Inedito è il quattordicesimo album ufficiale della cantante italiana Laura Pausini', 'Pop', 'DVD', 'Paolo Carta, Simone Bertolotti, Emiliano Bassi, Matteo Bassi', 'Chitarra elettrica, Tastiera, Batteria, Basso', 100),
		(110, 'Una parte di me', 'Contromano, Lascia che io sia, Notte bastarda, Abbracciami, Va bene così, Una parte di me, Non vale un addio, Io sono qui', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/UnaParteDiMe.jpg', 8.90, '2005-06-13', 'Nek', 'Una parte di me è il settimo album di Nek', 'Pop', 'CD', 'Nek, Cesare Chiodo, Alfredo Golino, Max Costa, Dado Parisini', 'Chitarra acustica, Basso, Batteria, Tastiera, Pianoforte', 100),
		(440, 'Verità supposte', 'Il secondo secondo me, Nessuna razza, Limiti, vengo dalla luna, Fuori dal tunnel, Giuda me, nel paese dei balordi, Dualismi', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/VeritaSupposte.jpg', 12.90, '2003-06-13', 'Caparezza', 'Verità supposte è il secondo album in studio del rapper italiano Caparezza', 'Rap', 'CD', 'Alfredo Ferrero, Carlo Rossi, Giovanni Astorino, Rino Corrieri, Pasquale Turturro', 'Chitarra, Flauto dolce, Basso, Batteria, Tromba', 100),
		(990, 'Fenomeno', 'Red Carpet, Fenomeno, Pamplona, Equilibrio, Cronico, Lascia stare, Invece no, Ogni giorno, le vacanze, Nessun aiuto', '/User/Ross/Desktop/ProgettoIngegneria/Immagini/Fenomeno.jpg', 13.90, '2017-04-07', 'Fabri Fibra', 'Fenomeno è il nono album in studio del rapper italiano Fabri Fibra', 'Rap', 'CD', 'Takagi, Big Fish, Shablo, Don Joe', 'Console', 100);

INSERT INTO Disk (codice, titolo, titoliPezzi, prezzo, dataPubblicazioneSito, titolare, descrizione, genere, tipo, musicisti, strumenti, totMagazzino)
	VALUES 	(220, 'La finestra', 'La distrazione, Parlami di amore, Un passo indietro, La finestra, Neanche il mare, Cade la pioggia, Tu ricordati di me', 7.80, '2007-06-08', 'Negramaro', 'La finestra è il quarto album in studio del gruppo musicale italiano Negramaro', 'Pop', 'CD', 'Giuliano Sangiorgi, Emanuele Spedicato, Ermanno Carlà, Andrea Mariano, Danilo Tasco, Andrea De Rocco', 'Chitarra, Basso, Pianoforte, Organo, Batteria, Organetto', 100),
		(330, 'Passione maledetta', 'Ti passerà, Francesco, California, Passione maledetta, Forse non lo sai, Stella cadente', 6.40, '2015-11-27', 'Modà', 'Passione maledetta è il sesto album in studio del gruppo musicale italiano Modà', 'Pop', 'CD', 'Kekko Silvestre, Enrico Zapparoli, Diego Arrigoni, Stefano Forcella, Claudio Dirani', 'Chitarra elettrica, Chitarra acustica, Basso, Batteria', 100);

INSERT INTO Cliente (codiceFiscale, nomeUtente, password, nome, cognome, città, telefono, personaleAutorizzato)
	VALUES 	('CLGMRC95D05C890A', 'marco', 'marco', 'Marco', 'Colognese', 'Legnago', '1234567890', FALSE),
		('RSSMTT95T22E512X', 'mattia', 'mattia', 'Mattia', 'Rossini', 'Legnago', '0123456789', FALSE),
		('RSSDVD80M15L781L', 'admin', 'aaa', 'Luca', 'Antonini', 'Verona', '0987654321', TRUE),
		('CPPMTT95M12E512L', 'user', 'bbb', 'Alessandro', 'Morello', 'Legnago', '9876543210', FALSE),
		('CMBCST94A07L781Z', 'cristian', 'ccc', 'Cristian', 'Cimbir', 'Verona', '1234567809', TRUE);

INSERT INTO inCarrello (cliente, disk, quantità)
	VALUES 	('user', 440, 3),
		('user', 880, 2),
		('user', 990, 5);

INSERT INTO Vendita (cliente, prodotti, ip, prezzoTot, 	dataOraAcquisto, modalitàPagamento, modalitàConsegna)
	VALUES 	('mattia', '110, 330, 550, 660', '192.168.10.11', 280.20, '2016-10-19 10:23:54', 'Paypal', 'Corriere'),
	 	('mattia', '220, 330, 550, 660', '192.168.10.12', 260.80, '2016-12-10 12:36:37', 'Bonifico', 'Posta'),
	 	('mattia', '220, 330, 550', '192.168.10.13', 254.80, '2016-12-23 18:30:15', 'Carta di credito', 'Posta'),
	 	('mattia', '110, 770, 880', '192.168.10.14', 251.90, '2017-05-21 22:35:09', 'Paypal', 'Corriere'),
	 	('marco', '110, 440, 880, 990', '192.170.10.15', 290.50, '2017-06-07 23:10:58', 'Carta di credito', 'Corriere'),
	 	('marco', '220, 550, 551, 770', '192.170.10.16', 242.10, '2017-07-07 16:42:43', 'Carta di credito', 'Posta');

INSERT INTO Musicista (nomeArte, genere, annoNascita, strumenti)
	VALUES 	('Vasco Rossi', 'Rock', 1952, 'Voce'),
		('Ligabue', 'Rock', 1960, 'Voce'),
		('Salmo', 'Rap', 1984, 'Voce'),
		('Laura Pausini', 'Pop', 1974, 'Voce'),
		('Negramaro', 'Pop', 2001, 'Chitarra, Basso, Pianoforte, Organo, Batteria, Organetto'),
		('Nek', 'Pop', 1972, 'Voce'),
		('Caparezza', 'Rap', 1973, 'Voce'),
		('Modà', 'Pop', 2002, 'Chitarra elettrica, Chitarra acustica, Basso, Batteria'),
		('Fabri Fibra', 'Rap', 1976, 'Voce');
