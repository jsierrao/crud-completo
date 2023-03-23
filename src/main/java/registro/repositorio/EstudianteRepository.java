package registro.repositorio;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import registro.model.Estudiante;
import registro.model.EstudianteDto;
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
	
	
	@Query(value ="select * from registro_alumno where numero_documento = :numeroDocumento",nativeQuery = true)
	Estudiante buscarDocumento(@Param("numeroDocumento")Integer numeroDocumento);
	
	
	@Query(value ="select * from registro_alumno where estado = :estado",nativeQuery = true)
	List<Estudiante>buscarEstado(@Param("estado")String estado);


	

}
