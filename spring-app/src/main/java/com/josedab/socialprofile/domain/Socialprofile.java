package com.josedab.socialprofile.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Socialprofile.
 */
@Entity
@Table(name = "SOCIALPROFILE")
public class Socialprofile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "twitter")
    private String twitter;
    
    @Column(name = "facebook")
    private String facebook;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "instagram")
    private String instagram;
    
    @Column(name = "pinterest")
    private String pinterest;
    
    @Column(name = "googleplus")
    private String googleplus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getPinterest() {
        return pinterest;
    }

    public void setPinterest(String pinterest) {
        this.pinterest = pinterest;
    }

    public String getGoogleplus() {
        return googleplus;
    }

    public void setGoogleplus(String googleplus) {
        this.googleplus = googleplus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Socialprofile socialprofile = (Socialprofile) o;

        if ( ! Objects.equals(id, socialprofile.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Socialprofile{" +
                "id=" + id +
                ", twitter='" + twitter + "'" +
                ", facebook='" + facebook + "'" +
                ", email='" + email + "'" +
                ", instagram='" + instagram + "'" +
                ", pinterest='" + pinterest + "'" +
                ", googleplus='" + googleplus + "'" +
                '}';
    }
}
