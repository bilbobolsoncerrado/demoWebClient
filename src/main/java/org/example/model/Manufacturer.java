package org.example.model;
import java.util.Date;

public class Manufacturer {


    private Long id;


    private String name;


    private Integer numEmployees;


    private Integer year;

    private Date initialDate;
    public Manufacturer() {
    }

    public Manufacturer(Long id, String name, Integer numEmployees, Integer year, Date initialDate) {
        this.id = id;
        this.name = name;
        this.numEmployees = numEmployees;
        this.year = year;
        this.initialDate=initialDate;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumEmployees() {
        return numEmployees;
    }

    public void setNumEmployees(Integer numEmployees) {
        this.numEmployees = numEmployees;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }
    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numEmployees=" + numEmployees +
                ", year=" + year +
                ", initial_date=" + initialDate+
                '}';
    }
}
