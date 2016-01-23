package cz.schoolproject.starwarscompanion.models;

import java.util.List;

/**
 * Created by Jarda on 23. 1. 2016.
 */
public class PersonModel {
    private String name;
    private String sex;
    private String species;
    private String dateofbirth;
    private String dateofdeath;
    private String master;
    private String padawan;
    private String sabercolor;
    private List<Movie> movieList;
    private String image;
    private String story;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getDateofdeath() {
        return dateofdeath;
    }

    public void setDateofdeath(String dateofdeath) {
        this.dateofdeath = dateofdeath;
    }

    public String getSabercolor() {
        return sabercolor;
    }

    public void setSabercolor(String sabercolor) {
        this.sabercolor = sabercolor;
    }

    public String getPadawan() {
        return padawan;
    }

    public void setPadawan(String padawan) {
        this.padawan = padawan;
    }
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public static class Movie {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

