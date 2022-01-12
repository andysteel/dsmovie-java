package com.gmail.andersoninfonet.dsmovie.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_score")
public class Score implements Serializable {
    
    @EmbeddedId
    private ScorePK id = new ScorePK();

    private Double value;
    /**
     * 
     */
    public Score() {
    }

    /**
     * @param movie the movie to set
     */
    public void setMovie(final Movie movie) {
        id.setMovie(movie);
    }

    /**
     * @param user the user to set
     */
    public void setUser(final User user) {
        id.setUser(user);
    }

    /**
     * @return the id
     */
    public ScorePK getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final ScorePK id) {
        this.id = id;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(final Double value) {
        this.value = value;
    }

}
