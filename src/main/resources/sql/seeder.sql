USE springblog_db;

drop table books;
drop table  authors;

insert into authors(name) VALUES ('Victor Hugo'), ('George Orwell'), ('John Steinbeck');

insert into books(author_id, title) VALUES
()