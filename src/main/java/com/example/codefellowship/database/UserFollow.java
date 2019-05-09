package com.example.codefellowship.database;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class UserFollow {


    @ManyToOne(fetch = FetchType.EAGER)
    private List<ApplicationUser> userFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    private List<ApplicationUser> userTo;
}
