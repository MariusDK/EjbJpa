package interfaces;

import entities.CursEntity;
import entities.StudentEntity;

import java.util.List;

public interface StudentBean {
    public void insertStudent(String name,List<CursEntity> cursEntityList);
    public void deleteStudent(StudentEntity studentEntity);
    public void updateStudent(Long id, String Newname);
    public List<StudentEntity> findAllStud();
    public StudentEntity findStud(Long id);
    public List<StudentEntity> findStudFromCurs(Long idCurs);
    public StudentEntity convertStringToObject(String s);
}
