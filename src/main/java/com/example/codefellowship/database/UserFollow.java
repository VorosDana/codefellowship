package com.example.codefellowship.database;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserFollow {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationUser userFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    private ApplicationUser userTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(ApplicationUser userFrom) {
        this.userFrom = userFrom;
    }

    public ApplicationUser getUserTo() {
        return userTo;
    }

    public void setUserTo(ApplicationUser userTo) {
        this.userTo = userTo;
    }
}
