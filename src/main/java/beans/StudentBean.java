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
    @PersistenceContext(unitName = "ejb")
    private EntityManager manager;
    public StudentBean() {
    }

    @Override
    public void insertStudent(String name,int varsta,int CNP, List<CursEntity> cursEntityList) {
        StudentEntity studentEntity = new StudentEntity();
        List<CursEntity> cursEntities = new ArrayList<>();
        studentEntity.setName(name);
        studentEntity.setVarsta(varsta);
        studentEntity.setCNP(CNP);

        //Adauga cursurile studentului
        //studentEntity.setGetCursuri(cursEntityList);
        for (CursEntity cursEntity:cursEntityList)
        {
            CursEntity cursEntity1 = manager.find(CursEntity.class,cursEntity.getId());
            int nrStudenti = cursEntity1.getNumarStudenti();
            nrStudenti++;
            cursEntity.setNumarStudenti(nrStudenti);
            CursEntity cursEntity2 = manager.merge(cursEntity);
            cursEntities.add(cursEntity1);
        }
        studentEntity.setCursuri(cursEntities);
        manager.persist(studentEntity);
    }
    @Override
    public void deleteStudent(StudentEntity studentEntity) {
        List<CursEntity> cursEntities = studentEntity.getCursuri();
        for (CursEntity cursEntity:cursEntities)
        {
            int nrStudenti= cursEntity.getNumarStudenti();
            nrStudenti--;
            cursEntity.setNumarStudenti(nrStudenti);
            manager.merge(cursEntity);
        }
        manager.remove(manager.merge(studentEntity));
    }

    @Override
    public void updateStudent(StudentEntity studentEntity) {

    }
    @Override
    public List<StudentEntity> findAllStud() {

        Query query = manager.createQuery("select s from StudentEntity s" );
//        TypedQuery<StudentEntity> query = manager.createQuery(
//                "select s from StudentEntity s ", StudentEntity.class);
        return (List<StudentEntity>) query.getResultList();
    }
    @Override
    public StudentEntity findStud(int id) {
        Query query = manager.createQuery("select s from StudentEntity s where s.id == "+ id );
        return (StudentEntity)query.getSingleResult();
    }
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
    @Override
    public void deleteStudentR(StudentDTO studentDTO) {

    }
    @Override
    public void updateStudentR(int id, String Newname) {

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
    public StudentDTO findStudR(int id) {

        return convertEntityDTOStudent(findStud(id));
    }

    @Override
    public List<StudentDTO> findStudFromCursR(int idCurs) {
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
        studentDTO.setVarsta(studentEntity.getVarsta());
        studentDTO.setCNP(studentEntity.getCNP());
        List<CursDTO> cursDTOList = new ArrayList<>();
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
        cursDTO.setNumarStudenti(cursEntity.getNumarStudenti());
        cursDTO.setNume(cursEntity.getNume());
        cursDTO.setNumeProfesor(cursEntity.getNumeProfesor());
        cursDTO.setDepartament(cursEntity.getDepartament());
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
        studentEntity.setId(Integer.parseInt(sList[0]));
        studentEntity.setName(sList[1]);
        studentEntity.setVarsta(Integer.parseInt(sList[2]));
        studentEntity.setCNP(Integer.parseInt(sList[3]));
        return studentEntity;
    }
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
    public CursEntity convertDTOEntityCurs(CursDTO cursDTO)
    {
        CursEntity cursEntity = new CursEntity();
        cursEntity.setNume(cursDTO.getNume());
        cursEntity.setNumarStudenti(cursDTO.getNumarStudenti());
        cursEntity.setNumeProfesor(cursDTO.getNumeProfesor());
        cursEntity.setDepartament(cursDTO.getDepartament());
        List<StudentEntity> studentEntities = new ArrayList<>();
        for (StudentDTO studentDTO:cursDTO.getStudenti())
        {
            studentEntities.add(convertEntityStudentDTO(studentDTO));
        }
        cursEntity.setStudenti(studentEntities);
        return cursEntity;
    }
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
