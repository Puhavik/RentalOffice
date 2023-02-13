INSERT INTO rental_office (address, office_name) VALUES ('4397 Walker Street, Lake Kelsi, DE 41513-3284', 'Walker Street Office');
INSERT INTO rental_office (address, office_name) VALUES ('982 Ritchie Spring, West Geoffrey, AL 84572-1863', 'Ritchie Spring Office');
INSERT INTO rental_office (address, office_name) VALUES ('9729 Hintz Parks, Ryanfurt, NE 46947-5475', 'Hintz Parks Office');

INSERT INTO customer VALUES (100041, 'Marcel Sauer', '843-681-4886', 1);
INSERT INTO customer VALUES (111222, 'Vesta Prohaska', '234-294-5509', 1);
INSERT INTO customer VALUES (111333, 'Jerry Lee', '2342944566', 2);

INSERT INTO friends VALUES (100041, 111222);
INSERT INTO friends VALUES (111222, 111333);

INSERT INTO car VALUES ('s755645', 'gold', 825, 1);
INSERT INTO car VALUES ('h930594', 'cyan', 200, 1);
INSERT INTO car VALUES ('f787275', 'red', 210, 2);
INSERT INTO car VALUES ('i263001', 'black', 250, 1);
INSERT INTO car VALUES ('z157747', 'fuchsia', 426, 1);
INSERT INTO car VALUES ('b011223', 'red', 190, 2);

INSERT INTO truck VALUES ('s755645', 8052, 913, 11);
INSERT INTO truck VALUES ('h930594', 10443, 991, 12);
INSERT INTO truck VALUES ('f787275', 3500, 905, 11);

INSERT INTO passenger_car VALUES ('i263001', 131, 6, 'Electric');
INSERT INTO passenger_car VALUES ('z157747', 320, 4, 'ybrid');
INSERT INTO passenger_car VALUES ('b011223', 220, 5, 'Gasoline');


INSERT INTO reservation (reservation_begin, reservation_end, driving_license_number, plate_number) VALUES ('11-12-2022', '15-12-2022', 100041, 's755645');
INSERT INTO reservation (reservation_begin, reservation_end, driving_license_number, plate_number) VALUES ('14-12-2022', '20-12-2022', 111222, 'h930594');
INSERT INTO reservation (reservation_begin, reservation_end, driving_license_number, plate_number) VALUES ('01-01-2023', '20-01-2023', 111333, 'f787275');

INSERT INTO employee VALUES (seq_employee_id.nextval, 'Cyrus Hoeger', 304935, 1);
INSERT INTO employee VALUES (seq_employee_id.nextval, 'Arnaldo Abernathy I', 123321, 1);
INSERT INTO employee VALUES (seq_employee_id.nextval, 'Derick Veum', 123123, 2);

INSERT INTO delivery VALUES (1, 1, 's755645', 100041);
INSERT INTO delivery VALUES (2, 2, 'h930594', 111222);
INSERT INTO delivery VALUES (3, 3, 'f787275', 111333);

select price, count(plate_number) from car group by price having avg(price),

select max(fuel_consumption) from passenger_car
select avg(price) from car
select price, count(plate_number) from car group by price having price < (select avg(price) from car)

select * from customer inner join rental_office on customer.rental_id = rental_office.rental_id
select * from customer full outer join rental_office on customer.rental_id = rental_office.rental_id
select * from customer left outer join rental_office on customer.rental_id = rental_office.rental_id
select * from customer right outer join rental_office on customer.rental_id = rental_office.rental_id
select * from rental_office natural full outer join employee
