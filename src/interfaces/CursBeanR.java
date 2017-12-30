package interfaces;

import dtos.CursDTO;
import entities.CursEntity;

import java.util.List;

public interface CursBeanR {
    public void insertCursR(String nume, int numarStudenti, String numeProfesor);
    public void updateCursR(int id, String nume, int numarStudenti, String numeProfesor);
    public void deleteCursR(CursDTO cursDTO);
    public CursDTO findCursR(int id);
    public List<CursDTO> findAllCsR();
    public List<CursDTO> findAllCursForStR(int idStudent);
}
