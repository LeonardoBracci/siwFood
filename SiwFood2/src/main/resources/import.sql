--Ingredienti--
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Cipolla', '100');
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Pollo', '200');
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Sedano', '20');
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Guanciale', '100');
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Sugo', '200');
insert into ingrediente (id, nome, quantita) values(nextval('ingrediente_seq'), 'Pasta', '400');
--Cuochi--
insert into cuoco (id, nome, cognome, data_di_nascita) values(nextval('cuoco_seq'), 'Lory', 'Carosi', '1970-10-09');
insert into cuoco (id, nome, cognome, data_di_nascita, biografia) values(nextval('cuoco_seq'), 'Joe', 'Bastianich', '1980-08-09', 'Sono Chef Joe Bastianich, un appassionato cuoco con oltre due decenni di esperienza nella cucina italiana. La mia storia culinaria ha inizio nella vivace Napoli, dove sin da giovane ho coltivato la mia passione per il cibo, ispirato dalle tradizioni gastronomiche ricche e storiche della mia regione.');
--Admin--
insert into admin(id, nome, cognome, data_di_nascita) values(nextval('admin_seq'), 'Leo', 'Bracci', '8/05/2002')
-- Credentials --
insert into credentials(id, username, password, role, admin_id) values (nextval('credentials_seq'), 'admin', '$2a$10$c2r9F.JGOhIwyg0xSWzP0.v5vnVTv/szcKbaU8QzdsTa6Z.bASk.q', 'ADMIN',1);
insert into credentials(id, username, password, role, cuoco_id) values (nextval('credentials_seq'), 'chef', '$2a$10$NJ8S6Kw1SXd2FFqml1Lvp.qw5vPVHcv0b3GubUCANCCdMppcx04FG', 'CHEF', 1);
insert into credentials(id, username, password, role, cuoco_id) values (nextval('credentials_seq'), 'chef2', '$2a$10$NJ8S6Kw1SXd2FFqml1Lvp.qw5vPVHcv0b3GubUCANCCdMppcx04FG', 'CHEF', 51);
--Ricette--
insert into ricetta (id, cuoco_id, nome, descrizione) values(nextval('ricetta_seq'), 51,'Amatriciana', 'Condimento per la pasta');
insert into ricetta (id, cuoco_id, nome, descrizione, steps) values(nextval('ricetta_seq'), 1,'CheeseCake', 'La torta piu buona del mondo', ARRAY['Preriscalda il forno a 175 gradi','Trita i biscotti e mescolali con il burro fuso']);
insert into ricetta (id, cuoco_id, nome, descrizione) values(nextval('ricetta_seq'), 51,'Cacio e pepe', 'BONA');
-- Ingredienti e ricette --
insert into ricetta_ingredienti (ingredienti_id, ricette_id) values (151,1);
insert into ricetta_ingredienti (ingredienti_id, ricette_id) values (1,1);
insert into ricetta_ingredienti (ingredienti_id, ricette_id) values (201,1);
insert into ricetta_ingredienti (ingredienti_id, ricette_id) values (251,1);

