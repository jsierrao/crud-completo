package registro.service;



import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import registro.model.Estudiante;
import registro.model.EstudianteDto;
import registro.response.Response;

@Primary
public interface EstudianteService {

	public Estudiante crear(Estudiante estudiante);
	
	public Estudiante actualizar( Estudiante estu, @PathVariable Long id);

	public Response findByEstado(String estado);

	public List<Estudiante> findAll();

	public Estudiante findByIdentidad(Integer numeroDocumento);

	
	public Estudiante porId(Long id);

	public void eliminar(Estudiante stu);
	
	public EstudianteDto dto();
	
	
	public Page<?> findAll(Pageable page);

}
