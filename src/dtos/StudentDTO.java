package dtos;

import java.io.Serializable;
import java.util.List;

public class StudentDTO implements Serializable{
    private Long id = 1L;
    private String name = "";
    private List<CursDTO> getCursuri;
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
    public List<CursDTO> getGetCursuri() {
        return getCursuri;
    }

    public void setGetCursuri(List<CursDTO> getCursuri) {
        this.getCursuri = getCursuri;
    }
}
