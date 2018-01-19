package interfaces;

import dtos.CursDTO;
import dtos.StudentDTO;
import entities.CursEntity;
import entities.StudentEntity;

import javax.ejb.Remote;
import java.util.List;

public interface CursBeanR {
    public void insertCursR(String nume, int numarStudenti, String numeProfesor, String departament);
    public void updateCursR(CursDTO cursDTO);
    public void deleteCursR(CursDTO cursDTO);
    public CursDTO findCursR(int id);
    public List<CursDTO> findAllCsR();
    public List<CursDTO> findAllCursForStR(int idStudent);
    public CursDTO convertStringToObjectR(String s);
}
