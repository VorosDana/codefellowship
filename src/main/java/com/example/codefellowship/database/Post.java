package com.example.codefellowship.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;
    private String body;
    private Date createdAt;

    @ManyToOne
    private ApplicationUser poster;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getPoster() {
        return poster;
    }

    public void setPoster(ApplicationUser poster) {
        this.poster = poster;
    }
}
