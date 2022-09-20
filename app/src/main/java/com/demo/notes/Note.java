package com.demo.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey
    long id;

    public String title;
    public String content;
}
