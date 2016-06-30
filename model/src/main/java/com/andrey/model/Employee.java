package com.andrey.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalDate;

/**
 * Created by kobri on 30.06.2016.
 */
public class Employee {
    private Long id;

    private String fname;

    private String lname;

    private String mname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private long salary;

    private long dep_id;

    public Employee(){}

    public Employee(Long id, String fname, String lname, String mname, LocalDate birthday, long salary, long dep_id) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.birthday = birthday;
        this.salary = salary;
        this.dep_id = dep_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public long getDep_id() {
        return dep_id;
    }

    public void setDep_id(long dep_id) {
        this.dep_id = dep_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (salary != employee.salary) return false;
        if (dep_id != employee.dep_id) return false;
        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (fname != null ? !fname.equals(employee.fname) : employee.fname != null) return false;
        if (lname != null ? !lname.equals(employee.lname) : employee.lname != null) return false;
        if (mname != null ? !mname.equals(employee.mname) : employee.mname != null) return false;
        return birthday != null ? birthday.equals(employee.birthday) : employee.birthday == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fname != null ? fname.hashCode() : 0);
        result = 31 * result + (lname != null ? lname.hashCode() : 0);
        result = 31 * result + (mname != null ? mname.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        result = 31 * result + (int) (dep_id ^ (dep_id >>> 32));
        return result;
    }

}