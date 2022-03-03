package com.codeup.springblog.models;


import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

//    had missed this!
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private List<Book> books;

    public Author() {

    }
    public Author(long id, String name, List<Book> books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }
    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
