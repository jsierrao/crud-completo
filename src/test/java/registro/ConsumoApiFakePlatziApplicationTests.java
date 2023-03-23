package registro;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import registro.model.Curso;
import registro.model.DocumentoIdentidad;
import registro.model.Estudiante;
import registro.repositorio.EstudianteRepository;

@SpringBootTest
@DataJpaTest
class ConsumoApiFakePlatziApplicationTests {

	@Autowired
	EstudianteRepository rpo;

	@Test
	void crearEstudianteTest() {
		Estudiante estudiante = Estudiante.builder()
				.primerNombre("julio")
				.segundoNombre("mario")
				.primerApellido("sierra")
				.segundoApellido("ortega")
				.estado("activo")
				.numeroDocumento(123455)
				//.tipoDocumento(DocumentoIdentidad.builder().id(1l).tipo("cedula"));
				//.curso(Curso.builder().id(1l).grado("primero").build())
				.createAt(new Date()).build();
		Estudiante stu = rpo.save(estudiante);
		
    
			
		
		
	}

}
