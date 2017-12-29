package interfaces;

import dtos.CursDTO;
import dtos.StudentDTO;

import java.util.List;

public interface StudentBeanR {
    public void insertStudentR(String name,List<CursDTO> cursDTOList);
    public void deleteStudentR(StudentDTO studentDTO);
    public void updateStudentR(Long id, String Newname);
    public List<StudentDTO> findAllStudR();
    public StudentDTO findStudR(Long id);
    public List<StudentDTO> findStudFromCursR(Long idCurs);
}
