package dtos;

import java.io.Serializable;
import java.util.List;

public class StudentDTO implements Serializable{
    private int id ;
    private String name = "";
    private int varsta;
    private int CNP;
    private List<CursDTO> getCursuri;
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

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public int getCNP() {
        return CNP;
    }

    public void setCNP(int CNP) {
        this.CNP = CNP;
    }

    public List<CursDTO> getGetCursuri() {
        return getCursuri;
    }

    public void setGetCursuri(List<CursDTO> getCursuri) {
        this.getCursuri = getCursuri;
    }
    @Override
    public String toString() {
        return id + " " + name + " " + varsta + " " + CNP;
    }

}
