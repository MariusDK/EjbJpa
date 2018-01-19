package beans;

import dtos.CursDTO;
import dtos.StudentDTO;
import entities.CursEntity;
import entities.StudentEntity;
import interfaces.StudentBeanR;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/** În bean-ul StudentBean implementăm perechea de interfețe:
 * StudentBean (locală) și StudentBeanR (remote)
 *
 * */
@Stateless(name = "StudentEJB")
@Local(interfaces.StudentBean.class)
@Remote(StudentBeanR.class)
public class StudentBean implements StudentBeanR, interfaces.StudentBean{
    /** declarăm numele unității de persistență */
    @PersistenceContext(unitName = "ejb")
    /** definim un EntityManager */
    private EntityManager manager;
    public StudentBean() {
    }
    /** Implementăm metodele interfeței locale StudentBean
     inserăm un student în baza de date */
    @Override
    public void insertStudent(String name,int varsta,int CNP, List<CursEntity> cursEntityList) {
        StudentEntity studentEntity = new StudentEntity();
        List<CursEntity> cursEntities = new ArrayList<>();
        studentEntity.setName(name);
        studentEntity.setVarsta(varsta);
        studentEntity.setCNP(CNP);

        //Adauga cursurile studentului
        //studentEntity.setGetCursuri(cursEntityList);
        if (cursEntityList.size()!=0) {
            for (CursEntity cursEntity : cursEntityList) {
                CursEntity cursEntity1 = manager.find(CursEntity.class, cursEntity.getId());
                int nrStudenti = cursEntity1.getNumarStudenti();
                nrStudenti++;
                cursEntity.setNumarStudenti(nrStudenti);
                CursEntity cursEntity2 = manager.merge(cursEntity);
                cursEntities.add(cursEntity1);
            }
            studentEntity.setCursuri(cursEntities);

            manager.persist(studentEntity);
        }
        else {
            manager.persist(studentEntity);
        }
    }
    /** stergem un student din baza de date */
    @Override
    public void deleteStudent(StudentEntity studentEntity) {
        studentEntity = manager.merge(studentEntity);
        List<CursEntity> cursEntities = studentEntity.getCursuri();
        for (CursEntity cursEntity:cursEntities)
        {
            int nrStudenti= cursEntity.getNumarStudenti();
            nrStudenti--;
            cursEntity.setNumarStudenti(nrStudenti);
            manager.merge(cursEntity);
        }
        manager.remove(studentEntity);
    }

    @Override
    public void updateStudent(StudentEntity studentEntity) {
    }
    /** afișăm lista de studenți din baza de date */
    @Override
    public List<StudentEntity> findAllStud() {

        Query query = manager.createQuery("select s from StudentEntity s" );
//        TypedQuery<StudentEntity> query = manager.createQuery(
//                "select s from StudentEntity s ", StudentEntity.class);
        return (List<StudentEntity>) query.getResultList();
    }
    /** căutăm un student după id și-l returnăm*/
    @Override
    public StudentEntity findStud(int id) {
        Query query = manager.createQuery("select s from StudentEntity s where s.id == "+ id );
        return (StudentEntity)query.getSingleResult();
    }
    /** afișăm lista de studenți corespunzătoare unui Curs */
    @Override
    public List<StudentEntity> findStudFromCurs(int idCurs) {
         CursEntity cursEntity = manager.find(CursEntity.class,idCurs);
         ArrayList<StudentEntity> studentEntities = new ArrayList<>();
         for (StudentEntity studentEntity:cursEntity.getStudenti())
         {
             System.out.println(studentEntity);
             studentEntities.add(studentEntity);
         }
         return studentEntities;
    }
    /** implementăm metodele interfeței remote StudentBeanR
     * inserăm un  StudentDTO folosind metoda insertCurs()
     */
    @Override
    public void insertStudentR(String name,int varsta,int departament, List<CursDTO> cursDTOList) {
        List<CursEntity> cursEntities = new ArrayList<>();
        for (CursDTO cursDTO:cursDTOList)
        {
            System.out.println(cursDTO.toString());
            cursEntities.add(convertDTOEntityCurs(cursDTO));
        }
        insertStudent(name,varsta,departament, cursEntities);
    }
    /** stergem un student din baza de date
     * folosind metoda deleteStudent()
     * pentru asta il convertim din DTO
     * in Entity
     * */
    @Override
    public void deleteStudentR(StudentDTO studentDTO) {
        StudentEntity studentEntity = convertEntityStudentDTO(studentDTO);
        deleteStudent(studentEntity);

    }
    @Override
    public void updateStudentR(int id, String Newname) {

    }
    /**
     * Afisăm toți studenții
     * */
    @Override
    public List<StudentDTO> findAllStudR() {
        List<StudentDTO> studentDTOS = new ArrayList<StudentDTO>();
        for (StudentEntity studentEntity:findAllStud())
        {
            studentDTOS.add(convertEntityDTOStudent(studentEntity));
        }
        return studentDTOS;
    }
    /**
     * Afisăm un student cu un id dat
     * */
    @Override
    public StudentDTO findStudR(int id) {

        return convertEntityDTOStudent(findStud(id));
    }
    /**
     * Afisăm toți studentii ai unui curs
     * cu un id dat idCurs
     * */
    @Override
    public List<StudentDTO> findStudFromCursR(int idCurs) {
        List<StudentDTO> studentDTOS = new ArrayList<StudentDTO>();
        for (StudentEntity studentEntity: findStudFromCurs(idCurs))
        {
            studentDTOS.add(convertEntityDTOStudent(studentEntity));
        }
        return studentDTOS;
    }
    /**
     * Convertim din StudentEntity în StudentDTO
     * */
    public StudentDTO convertEntityDTOStudent(StudentEntity studentEntity)
    {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(studentEntity.getId());
        studentDTO.setName(studentEntity.getName());
        studentDTO.setVarsta(studentEntity.getVarsta());
        studentDTO.setCNP(studentEntity.getCNP());
//        List<CursDTO> cursDTOList = new ArrayList<>();
//        for (CursEntity cursEntity:studentEntity.getCursuri())
//        {
//            cursDTOList.add(convertEntityDTOCurs(cursEntity));
//        }
//        studentDTO.setGetCursuri(cursDTOList);
        return studentDTO;
    }
    /**
     * Convertim din CursEntity în CursDTO
     * */
    public CursDTO convertEntityDTOCurs(CursEntity cursEntity)
    {
        CursDTO cursDTO = new CursDTO();
        cursDTO.setNumarStudenti(cursEntity.getNumarStudenti());
        cursDTO.setNume(cursEntity.getNume());
        cursDTO.setNumeProfesor(cursEntity.getNumeProfesor());
        cursDTO.setDepartament(cursEntity.getDepartament());
        List<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
//        for (StudentEntity studentEntity: cursEntity.getStudenti())
//        {
//            studentDTOList.add(convertEntityDTOStudent(studentEntity));
//        }
//        cursDTO.setStudenti(studentDTOList);
        return cursDTO;
    }
    /**
     * Convertim din String în StudentEntity
     * */
    public StudentEntity convertStringToObject(String s)
    {
        String[] sList = s.split(" ");
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(Integer.parseInt(sList[0]));
        studentEntity.setName(sList[1]);
        studentEntity.setVarsta(Integer.parseInt(sList[2]));
        studentEntity.setCNP(Integer.parseInt(sList[3]));
        return studentEntity;
    }
    /**
     * Convertim din StudentDTO în StudentEntity
     * */

    public StudentEntity convertEntityStudentDTO(StudentDTO studentDTO)
    {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(studentDTO.getName());
        studentEntity.setVarsta(studentDTO.getVarsta());
        studentEntity.setCNP(studentDTO.getCNP());
        List<CursEntity> cursEntitiesList = new ArrayList<>();
        for (CursDTO cursDTO:studentDTO.getGetCursuri())
        {
            cursEntitiesList.add(convertDTOEntityCurs(cursDTO));
        }
        studentEntity.setCursuri(cursEntitiesList);
        return studentEntity;
    }
    /**
     * Convertim din CursDTO în CursEntity
     * */
    public CursEntity convertDTOEntityCurs(CursDTO cursDTO)
    {
        CursEntity cursEntity = new CursEntity();
        cursEntity.setId(cursDTO.getId());
        cursEntity.setNume(cursDTO.getNume());
        cursEntity.setNumarStudenti(cursDTO.getNumarStudenti());
        cursEntity.setNumeProfesor(cursDTO.getNumeProfesor());
        cursEntity.setDepartament(cursDTO.getDepartament());
        return cursEntity;
    }
    /**
     * Convertim din String în DTO
     * */
    public StudentDTO convertStringToObjectR(String s)
    {
        String[] sList = s.split(" ");
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(Integer.parseInt(sList[0]));
        studentDTO.setName(sList[1]);
        studentDTO.setVarsta(Integer.parseInt(sList[2]));
        studentDTO.setCNP(Integer.parseInt(sList[3]));
        return studentDTO;
    }
}
