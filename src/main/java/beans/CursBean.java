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
    @PersistenceContext(unitName = "ejb")
    private EntityManager manager;
    public CursBean() {
    }

    @Override
    public void insertCurs(String nume, int numarStudenti, String numeProfesor, String departament) {
        CursEntity cursEntity = new CursEntity();
        cursEntity.setNume(nume);
        cursEntity.setNumarStudenti(numarStudenti);
        cursEntity.setNumeProfesor(numeProfesor);
        cursEntity.setDepartament(departament);
        manager.persist(cursEntity);
    }

    @Override
    public void updateCurs(CursEntity cursEntity) {

    }

    @Override
    public void deleteCurs(CursEntity cursEntity) {
        manager.remove(manager.merge(cursEntity));
    }

    @Override
    public CursEntity findCurs(int id) {
        Query query = manager.createQuery("select c from CursEntity c where c.id=="+id);

        return (entities.CursEntity) query.getSingleResult();
    }

    @Override
    public List<CursEntity> findAllCs() {
        Query query = manager.createQuery("select c from CursEntity c");
        return (List<CursEntity>) query.getResultList();
    }

    @Override
    public List<CursEntity> findAllCursForSt(int idStudent) {
        StudentEntity studentEntity = manager.find(StudentEntity.class,idStudent);
        List<CursEntity> cursEntities = new ArrayList<>();
        for (CursEntity cursEntity:studentEntity.getCursuri())
        {
            cursEntities.add(cursEntity);
        }

        return cursEntities;
    }
    ////////////////REMOTE
    @Override
    public void insertCursR(String nume, int numarStudenti, String numeProfesor, String departament) {
        insertCurs(nume,numarStudenti,numeProfesor,departament);
    }
    @Override
    public void updateCursR(CursDTO cursDTO) {

    }

    @Override
    public void deleteCursR(CursDTO cursDTO) {
        CursEntity cursEntity = new CursEntity();
        cursEntity.setId(cursDTO.getId());
        cursEntity.setNumarStudenti(cursDTO.getNumarStudenti());
        cursEntity.setNume(cursDTO.getNume());
        cursEntity.setNumeProfesor(cursDTO.getNumeProfesor());
        cursEntity.setDepartament(cursDTO.getDepartament());

        deleteCurs(cursEntity);
    }

    @Override
    public dtos.CursDTO findCursR(int id) {
        return convertEntityDTOCurs(findCurs(id));
    }

    @Override
    public List<dtos.CursDTO> findAllCsR() {
        ArrayList<dtos.CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCs())
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }

    @Override
    public List<dtos.CursDTO> findAllCursForStR(int idStudent) {
        List<dtos.CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCursForSt(idStudent))
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }
    public dtos.StudentDTO convertEntityDTOStudent(StudentEntity studentEntity)
    {
        dtos.StudentDTO studentDTO = new dtos.StudentDTO();
        studentDTO.setName(studentEntity.getName());
        studentDTO.setCNP(studentEntity.getCNP());
        studentDTO.setVarsta(studentEntity.getVarsta());
        List<dtos.CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:studentEntity.getCursuri())
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        studentDTO.setGetCursuri(cursDTOList);
        return studentDTO;
    }
    public CursDTO convertEntityDTOCurs(CursEntity cursEntity)
    {
        CursDTO cursDTO = new CursDTO();
        cursDTO.setId(cursEntity.getId());
        cursDTO.setNumarStudenti(cursEntity.getNumarStudenti());
        cursDTO.setNume(cursEntity.getNume());
        cursDTO.setNumeProfesor(cursEntity.getNumeProfesor());
        cursDTO.setDepartament(cursEntity.getDepartament());
        return cursDTO;
    }
    public CursEntity convertStringToObject(String s)
    {
        String[] sList = s.split(" ");
        CursEntity cursEntity = new CursEntity();
        cursEntity.setId(Integer.parseInt(sList[0]));
        cursEntity.setNume(sList[1]);
        cursEntity.setNumeProfesor(sList[3]);
        cursEntity.setNumarStudenti(Integer.parseInt(sList[2]));
        cursEntity.setDepartament(sList[4]);
        return cursEntity;
    }
    public CursDTO convertStringToObjectR(String s)
    {
        String[] sList = s.split(" ");
        CursDTO cursDTO = new CursDTO();
        cursDTO.setId(Integer.parseInt(sList[0]));
        cursDTO.setNume(sList[1]);
        cursDTO.setNumeProfesor(sList[3]);
        cursDTO.setNumarStudenti(Integer.parseInt(sList[2]));
        cursDTO.setDepartament(sList[4]);
        return cursDTO;

    }
}
