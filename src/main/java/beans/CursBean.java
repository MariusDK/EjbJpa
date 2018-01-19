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


/** În bean-ul CursBean implementăm perechea de interfețe:
 * CursBean (locală) și CursBeanR (remote)
 *
 * */
@Stateless(name = "CursEJB")
@Local(interfaces.CursBean.class)
@Remote(CursBeanR.class)
public class CursBean implements CursBeanR, interfaces.CursBean{
    /** declarăm numele unității de persistență */
    @PersistenceContext(unitName = "ejb")
    /** definim un EntityManager */
    private EntityManager manager;
    public CursBean() {
    }
    /** Implementăm metodele interfeței locale CursBean
        inserăm un curs în baza de date */
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
    /** stergem un curs din baza de date */
    @Override
    public void deleteCurs(CursEntity cursEntity) {
        manager.remove(manager.merge(cursEntity));
    }
    /** căutăm un curs după id și-l returnăm*/
    @Override
    public CursEntity findCurs(int id) {
        Query query = manager.createQuery("select c from CursEntity c where c.id=="+id);

        return (entities.CursEntity) query.getSingleResult();
    }
    /** afișăm lista de cursuri din baza de date */
    @Override
    public List<CursEntity> findAllCs() {
        Query query = manager.createQuery("select c from CursEntity c");
        return (List<CursEntity>) query.getResultList();
    }
    /** afișăm lista de cursuri corespunzătoare unui Student */
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
    /** implementăm metodele interfeței remote CursBeanR
     * inserăm un  CursDTO folosind metoda
     * insertCurs()
    */
    @Override
    public void insertCursR(String nume, int numarStudenti, String numeProfesor, String departament) {
        insertCurs(nume,numarStudenti,numeProfesor,departament);
    }

    @Override
    public void updateCursR(CursDTO cursDTO) {

    }
    /**
     * convertim un CursDTO în CursEntity
     * stergem cursul folosind metoda
     * deleteCurs()
     * */
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
    /**
     * returnăm un CursEntity căutat în baza de date după id
     * il convertim în CursDTO și-l returnăm
     * */
    @Override
    public dtos.CursDTO findCursR(int id) {
        return convertEntityDTOCurs(findCurs(id));
    }
    /**
     * returnăm toate cursurile folosind metoda findAllCs()
     * într-o listă de obiecte de tipul CursEntity
     * pe care le convertim apoi
     * în o listă de obecte de tipul CursDTO
     * */
    @Override
    public List<dtos.CursDTO> findAllCsR() {
        ArrayList<dtos.CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCs())
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }

    /**
     * returnăm toate cursurile pentru un student
     * cu id-ul idStudent folosind metoda findAllCursSt(idStudent)
     * într-o listă de obiecte de tipul CursEntity
     * pe care le convertim apoi
     * în o listă de obecte de tipul CursDTO
     * */
    @Override
    public List<dtos.CursDTO> findAllCursForStR(int idStudent) {
        List<dtos.CursDTO> cursDTOList = new ArrayList<>();
        for (CursEntity cursEntity:findAllCursForSt(idStudent))
        {
            cursDTOList.add(convertEntityDTOCurs(cursEntity));
        }
        return cursDTOList;
    }
    /**
     * metodă de a converti un StudentEntity
     * într-un StudentDTO
     */
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
    /**
     * metodă de a converti un CursEntity
     * într-un CursDTO
     */
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
    /**
     * metodă de a converti obiecte de tipul String în
     * obiecte de tip-ul CursEntity
     */
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
    /**
     * metodă de a converti obiecte de tipul String în
     * obiecte de tip-ul CursDTO
     */
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
