insert into specialties (id, name) values (1,'Heart');
insert into specialties (id, name) values (2, 'Bones');
insert into specialties (id, name) values (3, 'Organs');

insert into vets (id, first_name, last_name) values(1, 'Pepe','Márquez');
insert into vets (id, first_name, last_name) values(2, 'Álvaro','Pogoño');
insert into vets (id, first_name, last_name) values(3, 'Franch','Pedro');

insert into vet_specialties (specialty_id, vet_id) values (1,1);
insert into vet_specialties (specialty_id, vet_id ) values (2,2);

insert into vet_specialties (specialty_id, vet_id ) values (2,3);
