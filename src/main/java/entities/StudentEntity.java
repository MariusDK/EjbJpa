package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class StudentEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQMYCLASSID")

    private int id ;
    private String name = "";
    private int varsta;
    private int CNP;

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

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int sex) {
        this.varsta = sex;
    }

    public int getCNP() {
        return CNP;
    }

    public void setCNP(int CNP) {
        this.CNP = CNP;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Student_Curs",
            joinColumns = @JoinColumn( name = "Student_ID", referencedColumnName = "ID" ),
            inverseJoinColumns = @JoinColumn (name = "Curs_ID", referencedColumnName = "ID"))
    private List<CursEntity> cursuri;

    public List<CursEntity> getCursuri() {
        return cursuri;
    }

    public void setCursuri(List<CursEntity> getCursuri) {
        this.cursuri = getCursuri;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + varsta + " " + CNP;
    }
}
