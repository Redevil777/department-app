package com.andrey.model;

/**
 * Created by kobri on 30.06.2016.
 */
public class Department {

    private Long id;

    private String dep_name;

    private long avgSalary;

    public Department(){
    }

    public Department(Long id, String dep_name) {
        this.id = id;
        this.dep_name = dep_name;
    }


    public Department(Long id, String dep_name, long avgSalary) {
        this.id = id;
        this.dep_name = dep_name;
        this.avgSalary = avgSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDep_name() {
        return dep_name;
    }

    public void setDep_name(String dep_name) {
        this.dep_name = dep_name;
    }

    public long getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(long avgSalary) {
        this.avgSalary = avgSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (avgSalary != that.avgSalary) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return dep_name != null ? dep_name.equals(that.dep_name) : that.dep_name == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dep_name != null ? dep_name.hashCode() : 0);
        result = 31 * result + (int) (avgSalary ^ (avgSalary >>> 32));
        return result;
    }
}