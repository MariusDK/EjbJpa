package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class StudentEntity implements Serializable{
    @Id
    @GeneratedValue
    private int id ;
    private String name = "";




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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Student_Curs",
            joinColumns = @JoinColumn( name = "Student_ID", referencedColumnName = "ID" ),
            inverseJoinColumns = @JoinColumn (name = "Curs_ID", referencedColumnName = "ID"))
    private List<CursEntity> cursuri;

    public List<CursEntity> getGetCursuri() {
        return cursuri;
    }

    public void setGetCursuri(List<CursEntity> getCursuri) {
        this.cursuri = getCursuri;
    }
}
