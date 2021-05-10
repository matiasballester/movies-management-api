insert into genre (id, description) values (1, 'Action');
insert into genre (id, description) values (2, 'Adventure');
insert into genre (id, description) values (3, 'Comedy');
insert into genre (id, description) values (4, 'Crime');
insert into genre (id, description) values (5, 'Mystery');
insert into genre (id, description) values (6, 'Fantasy');
insert into genre (id, description) values (7, 'Historical');
insert into genre (id, description) values (8, 'Horror');
insert into genre (id, description) values (9, 'Drama');
insert into genre (id, description) values (10, 'Science Fiction');
insert into genre (id, description) values (11, 'Terror');
insert into genre (id, description) values (12, 'Thriller');
insert into genre (id, description) values (13, 'Western');
insert into genre (id, description) values (14, 'Other');

insert into movie (movie_id, title, genre_id, casting, crew, rating, release_date, created_at) values (1, 'Rocky', 1, 'Sylvester Stallone as Robert Balboa', 'Directed By: John Avildsen, Produced By: Irwin Winkler', 9.3, '1970-01-13', now());
insert into movie (movie_id, title, genre_id, casting, crew, rating, release_date, created_at) values (2, 'The Shawshank Redemption', 9, 'Tim Robbins as Andy Dufresne, Morgan Freeman as Ellis Boyd Redding', 'Directed By: Frank Darabont, Produced By: Niki Marvin, Story By: Rita Hayworth and Shawshank Redemption by Stephen King', 9.8, '1995-03-25', now());
insert into movie (movie_id, title, genre_id, casting, crew, rating, release_date, created_at) values (3, 'Indiana Jones and the Last Crusade', 2, 'Harrison Ford as Indiana Jones', 'Directed By: Steven Spielberg, Produced By: Robert Watts, Story By: George Lucas', 8.5, '1989-05-24', now());