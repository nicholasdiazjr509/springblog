package com.codeup.springblog.models;


import javax.persistence.*;



@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //could also use the 'name - "nameGoesHere"' argument to establish
    //a specific name that differs from the name of the field.
    @Column(nullable = false, length = 100)//for a NOT null FIELD
    private String title;

    @Column(nullable = false)//unique is false by default-, unique = true arg
    private String author;

    //    forgot the constructor !!!!!
    public Book(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}