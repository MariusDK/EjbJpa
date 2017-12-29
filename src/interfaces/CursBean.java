package interfaces;

import entities.CursEntity;

import java.util.List;

public interface CursBean {
    public void insertCurs(String nume, int numarStudenti, String numeProfesor);
    public void updateCurs(Long id, String nume, int numarStudenti, String numeProfesor);
    public void deleteCurs(CursEntity cursEntity);
    public CursEntity findCurs(Long id);
    public List<CursEntity> findAllCs();
    public List<CursEntity> findAllCursForSt(Long idStudent);
}
