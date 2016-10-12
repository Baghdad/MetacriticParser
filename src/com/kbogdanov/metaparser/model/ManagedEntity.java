package com.kbogdanov.metaparser.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Author: Kirill Bogdanov
 * Created on 12.10.2016.
 */

@MappedSuperclass
public class ManagedEntity {
    public static final long NULL_LONG = 0L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    @NotNull
    private long id = NULL_LONG;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
