package com.blueradix.android.jetpackpersistence.monster;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MONSTER")
public class Monster {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo
    private String name;
    private String description;
    private String image;
    private int scariness;
    private int votes;
    private int stars;

    @Ignore
    public Monster() {
    }

    public Monster(String name, String description, String image, int scariness, int votes, int stars) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.scariness = scariness;
        this.votes = votes;
        this.stars = stars;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getScariness() {
        return scariness;
    }

    public void setScariness(int scariness) {
        this.scariness = scariness;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @NonNull
    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", scariness=" + scariness +
                ", votes=" + votes +
                ", stars=" + stars +
                '}';
    }
}
