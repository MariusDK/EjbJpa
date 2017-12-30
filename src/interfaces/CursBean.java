package interfaces;

import dtos.CursDTO;
import dtos.StudentDTO;
import entities.CursEntity;
import entities.StudentEntity;

import java.util.List;

public interface CursBean {
    public void insertCurs(String nume, int numarStudenti, String numeProfesor);
    public void updateCurs(int id, String nume, int numarStudenti, String numeProfesor);
    public void deleteCurs(CursEntity cursEntity);
    public CursEntity findCurs(int id);
    public List<CursEntity> findAllCs();
    public List<CursEntity> findAllCursForSt(int idStudent);
    public StudentDTO convertEntityDTOStudent(StudentEntity studentEntity);
    public CursDTO convertEntityDTOCurs(CursEntity cursEntity);
    public CursEntity convertStringToObject(String s);
}
