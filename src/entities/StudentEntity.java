package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class StudentEntity implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id = 1L;
    private String name = "";




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
    @ManyToMany
    @JoinTable(
            name = "Student_Curs",
            joinColumns = @JoinColumn( name = "Student_ID", referencedColumnName = "ID" ),
            inverseJoinColumns = @JoinColumn (name = "Curs_ID", referencedColumnName = "ID"))
    private List<CursEntity> getCursuri;

    public List<CursEntity> getGetCursuri() {
        return getCursuri;
    }

    public void setGetCursuri(List<CursEntity> getCursuri) {
        this.getCursuri = getCursuri;
    }
}
