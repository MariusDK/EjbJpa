package interfaces;

import entities.CursEntity;
import entities.StudentEntity;

import java.util.List;

public interface StudentBean {
    public void insertStudent(String name ,int varsta ,int CNP, List<CursEntity> cursEntityList);
    public void deleteStudent(StudentEntity studentEntity);
    public void updateStudent(StudentEntity studentEntity);
    public List<StudentEntity> findAllStud();
    public StudentEntity findStud(int id);
    public List<StudentEntity> findStudFromCurs(int idCurs);
    public StudentEntity convertStringToObject(String s);
}
