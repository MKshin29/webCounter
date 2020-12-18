package com.example.web_counter.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Column(name = "user_id")
    private long user_id;
    @Column(name = "site_id")
    private long site_id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getSite_id() {
        return site_id;
    }

    public void setSite_id(long site_id) {
        this.site_id = site_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
