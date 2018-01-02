package interfaces;

import dtos.CursDTO;
import dtos.StudentDTO;

import java.util.List;

public interface StudentBeanR {
    public void insertStudentR(String name ,int varsta ,int CNP ,List<CursDTO> cursDTOList);
    public void deleteStudentR(StudentDTO studentDTO);
    public void updateStudentR(int id, String Newname);
    public List<StudentDTO> findAllStudR();
    public StudentDTO findStudR(int id);
    public List<StudentDTO> findStudFromCursR(int idCurs);

}
