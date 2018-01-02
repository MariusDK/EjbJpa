package dtos;

import java.io.Serializable;
import java.util.List;

public class CursDTO implements Serializable{

        private int id ;
        private String nume = "";
        private int numarStudenti = 0;
        private String numeProfesor = "";
        private String departament = "";
        private List<StudentDTO> studenti;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNume() {
            return nume;
        }

        public void setNume(String nume) {
            this.nume = nume;
        }

        public int getNumarStudenti() {
            return numarStudenti;
        }

        public void setNumarStudenti(int numarStudenti) {
            this.numarStudenti = numarStudenti;
        }

        public String getNumeProfesor() {
            return numeProfesor;
        }

        public void setNumeProfesor(String numeProfesor) {
            this.numeProfesor = numeProfesor;
        }

        public String getDepartament() {
        return departament;
        }
        public void setDepartament(String departament) {
        this.departament = departament;
        }
        public List<StudentDTO> getStudenti() {
            return studenti;
        }
        public void setStudenti(List<StudentDTO> studenti) {
            this.studenti = studenti;
        }
    @Override
    public String toString() {
        return  id +
                " " + nume +
                " " + numarStudenti +
                " " + numeProfesor +
                " " + departament;
    }
}
