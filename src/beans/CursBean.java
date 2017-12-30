package beans;

import dtos.CursDTO;
import dtos.StudentDTO;
import entities.CursEntity;
import entities.StudentEntity;
import interfaces.CursBeanR;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "CursEJB")
@Local(interfaces.CursBean.class)
@Remote(CursBeanR.class)
public class CursBean implements CursBeanR, interfaces.CursBean{
    private EntityManager manager;
    public CursBean() {
    }

    @Override
    public void insertCurs(String nume, int numarStudenti, String numeProfesor) {
        CursEntity cursEntity = new CursEntity();
        cursEntity.setNume(nume);
        cursEntity.setNumarStudenti(numarStudenti);
        cursEntity.setNumeProfesor(numeProfesor);
        manager.persist(cursEntity);
    }

    @Override
    public void updateCurs(Long id, String nume, int numarStudenti, String numeProfesor) {

    }

    @Override
    public void deleteCurs(CursEntity cursEntity) {
        manager.remove(cursEntity);
    }

    @Override
    public CursEntity findCurs(Long id) {
        Query query = manager.createQuery("select c from CursEntity c where c.id=="+id);
        return (CursEntity) query.getSingleResult();
    }

    @Override
    public List<CursEntity> findAllCs() {
        Query query = manager.createQuery("select c from CursEntity c");
        return (List<CursEntity>) query.getResultList();
    }

    @Override
    public List<CursEntity> findAllCursForSt(Long idStudent) {
        Query query = manager.createQuery("select s.getCursuri from StudentEntity s where s.id =="+ idStudent);
        return (List<CursEntity>) query.getResultList();
    }

    @Override
    public void insertCursR(String nume, int numarStudenti, String numeProfesor) {
        CursDTO cursDTO = new CursDTO();
        cursDTO.setNume(nume);
        cursDTO.setNumeProfesor(numeProfesor);
        cursDTO.setNumarStudenti(numarStudenti);
        manager.persist(cursDTO);
    }

    @Override
    public void updateCursR(Long id, String nume, int numarStudenti, String numeProfesor) {

    }

    @Override
    public void deleteCursR(CursDTO cursDTO) {
        manager.remove(cursDTO);
    }

    @Override
    public CursDTO findCursR(Long id) {
        return convertEntityDTOCurs(findCurs(id));
    }

    @Override
    public List<CursDTO> findAllCsR() {
        List<CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCs())
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }

    @Override
    public List<CursDTO> findAllCursForStR(Long idStudent) {
        List<CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCursForSt(idStudent))
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }
    public StudentDTO convertEntityDTOStudent(StudentEntity studentEntity)
    {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(studentEntity.getName());
        List<CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:studentEntity.getGetCursuri())
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        studentDTO.setGetCursuri(cursDTOList);
        return studentDTO;
    }
    public CursDTO convertEntityDTOCurs(CursEntity cursEntity)
    {
        CursDTO cursDTO = new CursDTO();
        cursDTO.setNumarStudenti(cursEntity.getNumarStudenti());
        cursDTO.setNume(cursEntity.getNume());
        cursDTO.setNumeProfesor(cursEntity.getNumeProfesor());
        List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
        for (StudentEntity studentEntity: cursEntity.getStudenti())
        {
            studentDTOList.add(convertEntityDTOStudent(studentEntity));
        }
        cursDTO.setStudenti(studentDTOList);
        return cursDTO;
    }
    public CursEntity convertStringToObject(String s)
    {
        String[] sList = s.split(" ");
        CursEntity cursEntity = new CursEntity();
        cursEntity.setId(Long.getLong(sList[0]));
        cursEntity.setNume(sList[1]);
        cursEntity.setNumeProfesor(sList[2]);
        cursEntity.setNumarStudenti(Integer.parseInt(sList[3]));
        return cursEntity;
    }
}
