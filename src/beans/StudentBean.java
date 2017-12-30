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

@Stateless(name = "StudentEJB")
@Local(interfaces.StudentBean.class)
@Remote(StudentBeanR.class)
public class StudentBean implements StudentBeanR, interfaces.StudentBean{
    @PersistenceContext(unitName = "persistence")
    private EntityManager manager;
    public StudentBean() {
    }

    @Override
    public void insertStudent(String name, List<CursEntity> cursEntityList) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName(name);

        //Adauga cursurile studentului
        studentEntity.setGetCursuri(cursEntityList);
        manager.persist(studentEntity);
    }
    @Override
    public void deleteStudent(StudentEntity studentEntity) {
        manager.remove(studentEntity);
    }

    @Override
    public void updateStudent(Long id, String Newname) {

    }
    @Override
    public List<StudentEntity> findAllStud() {

        Query query = manager.createQuery("select s from StudentEntity s" );
//        TypedQuery<StudentEntity> query = manager.createQuery(
//                "select s from StudentEntity s ", StudentEntity.class);
        return (List<StudentEntity>) query.getResultList();
    }
    @Override
    public StudentEntity findStud(Long id) {
        Query query = manager.createQuery("select s from StudentEntity s where s.id == "+ id );
        return (StudentEntity)query.getSingleResult();
    }
    @Override
    public List<StudentEntity> findStudFromCurs(Long idCurs) {
        Query query = manager.createQuery("select c.studenti from CursEntity c where c.id == "+ idCurs );
        return (List<StudentEntity>) query.getResultList();
    }
    @Override
    public void insertStudentR(String name, List<CursDTO> cursDTOList) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(name);

        //Adauga cursurile studentului
        studentDTO.setGetCursuri(cursDTOList);
        manager.persist(studentDTO);
    }
    @Override
    public void deleteStudentR(StudentDTO studentDTO) {
        manager.remove(studentDTO);
    }
    @Override
    public void updateStudentR(Long id, String Newname) {

    }

    @Override
    public List<StudentDTO> findAllStudR() {
        List<StudentDTO> studentDTOS = new ArrayList<StudentDTO>();
        for (StudentEntity studentEntity:findAllStud())
        {
            studentDTOS.add(convertEntityDTOStudent(studentEntity));
        }
        return studentDTOS;
    }

    @Override
    public StudentDTO findStudR(Long id) {

        return convertEntityDTOStudent(findStud(id));
    }

    @Override
    public List<StudentDTO> findStudFromCursR(Long idCurs) {
        List<StudentDTO> studentDTOS = new ArrayList<StudentDTO>();
        for (StudentEntity studentEntity: findStudFromCurs(idCurs))
        {
            studentDTOS.add(convertEntityDTOStudent(studentEntity));
        }
        return studentDTOS;
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
    public StudentEntity convertStringToObject(String s)
    {
        String[] sList = s.split(" ");
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(Long.getLong(sList[0]));
        studentEntity.setName(sList[1]);
        return studentEntity;


    }
}
