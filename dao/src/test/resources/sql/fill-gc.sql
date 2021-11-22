INSERT INTO tag VALUES(1, 'spa');
INSERT INTO tag VALUES(2, 'thai');
INSERT INTO tag VALUES(3, 'res');
INSERT INTO tag VALUES(4, 'sky');

INSERT INTO gift_certificate VALUES(1, 'Saloon', 'Best rest', 49.99, 10, LOCALTIME(), LOCALTIME());
INSERT INTO gift_certificate VALUES(2, 'Active rest', 'Active best rest', 99.99, 5, LOCALTIME(), LOCALTIME());
INSERT INTO gift_certificate VALUES(3, 'Spa', 'Relax', 19.99, 15, LOCALTIME(), LOCALTIME());

INSERT INTO gc_tag VALUES(1, 2);
INSERT INTO gc_tag VALUES(1, 3);
INSERT INTO gc_tag VALUES(2, 3);
INSERT INTO gc_tag VALUES(2, 4);
INSERT INTO gc_tag VALUES(3, 1);
INSERT INTO gc_tag VALUES(3, 3);