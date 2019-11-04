/*==============================================================*/
/* create database: db_cine_serie                              	*/
/*==============================================================*/

/*==============================================================*/
/* Sintaxis para eliminar las tablas si existe                  */
/*==============================================================*/
drop table if exists contenido_genero;
drop table if exists favorita;
drop table if exists genero;
drop table if exists contenido;
/*==============================================================*/
/* table: contenido                                        		*/
/*==============================================================*/
create table contenido
(
    con_id 					int not null,
    con_titulo 				varchar(100) not null,
    con_ano_lanzamiento 	datetime null,
    con_longitud_minutos 	varchar(5) not null,
    con_sinopsis 			varchar(10000) not null,
	con_tipo 				enum ('Pelicula','Serie') not null,
	con_cantidad 			int null
);
/*==============================================================*/
/* table: genero                                             	*/
/*==============================================================*/
create table genero
(
    gen_id 		int not null,
    gen_nombre 	varchar(100) not null
);
/*==============================================================*/
/* table: favorita                                             	*/
/*==============================================================*/
create table favorita
(
    fav_id 				int not null,
    fav_calificacion 	enum ('0','1','2','3','4','5') not null,
    fav_comentario 		varchar(10000) not null,
	fk_contenido 		int null
);
/*==============================================================*/
/* table: contenido_genero                                      */
/*==============================================================*/
create table contenido_genero
(
	cg_id int not null,
	fk_contenido int not null,
	fk_genero int not null
);
/*==================================================================================================================================*/
/* primery key																														*/
/*==================================================================================================================================*/
alter table `contenido` add primary key (`con_id`);
alter table `genero` add primary key (`gen_id`);
alter table `favorita` add primary key (`fav_id`);
alter table contenido_genero add primary key (cg_id);
/*==================================================================================================================================*/
/* Llaves autoincrementales																											*/
/*==================================================================================================================================*/
alter table contenido modify con_id int(11) not null auto_increment;
alter table genero modify gen_id int(11) not null auto_increment;
alter table favorita modify fav_id int(11) not null auto_increment;
alter table contenido_genero modify cg_id int(11) not null auto_increment;
/*==================================================================================================================================*/
/* foreign key																														*/
/*==================================================================================================================================*/
alter table favorita add constraint fk_favorita_relations_contenido foreign key (fk_contenido) references contenido (con_id);
alter table contenido_genero add constraint fk_contenido_genero_relations_contenido foreign key (fk_contenido) references contenido (con_id);
alter table contenido_genero add constraint fk_contenido_genero_relations_genero foreign key (fk_genero) references genero (gen_id);
/*==================================================================================================================================*/
/* Datos basicos para la tabla de serie en la base de datos.																		*/
/*==================================================================================================================================*/
INSERT INTO genero (gen_nombre)VALUES('Clásicos');
INSERT INTO genero (gen_nombre)VALUES('Mudas');
INSERT INTO genero (gen_nombre)VALUES('Sonoras');
INSERT INTO genero (gen_nombre)VALUES('Blanco y negro');
INSERT INTO genero (gen_nombre)VALUES('Color');
INSERT INTO genero (gen_nombre)VALUES('Acción');
INSERT INTO genero (gen_nombre)VALUES('Aventuras');
INSERT INTO genero (gen_nombre)VALUES('Comedias');
INSERT INTO genero (gen_nombre)VALUES('Dramáticas');
INSERT INTO genero (gen_nombre)VALUES('Terror');
INSERT INTO genero (gen_nombre)VALUES('Musicales');
INSERT INTO genero (gen_nombre)VALUES('Ciencia ficción');
INSERT INTO genero (gen_nombre)VALUES('Guerra o bélicas');
INSERT INTO genero (gen_nombre)VALUES('Oeste (Wester)');
INSERT INTO genero (gen_nombre)VALUES('Crimen (Suspense)');
INSERT INTO genero (gen_nombre)VALUES('Infantiles');
INSERT INTO genero (gen_nombre)VALUES('Animacion');
INSERT INTO genero (gen_nombre)VALUES('Adultos');
INSERT INTO genero (gen_nombre)VALUES('Horror');
INSERT INTO genero (gen_nombre)VALUES('Anime');