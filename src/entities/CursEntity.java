package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class CursEntity implements Serializable{
    private Long id = 1L;
    private String nume = "";
    private int numarStudenti = 0;
    private String numeProfesor = "";
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNumarStudenti() {
        return numarStudenti;
    }

    public void setNumarStudenti(int numarStudenti) {
        this.numarStudenti = numarStudenti;
    }

    public String getNumeProfesor() {
        return numeProfesor;
    }

    public void setNumeProfesor(String numeProfesor) {
        this.numeProfesor = numeProfesor;
    }
    @ManyToMany(mappedBy = "cursuri")
    private List<StudentEntity> studenti;

    public List<StudentEntity> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<StudentEntity> studenti) {
        this.studenti = studenti;
    }
}
