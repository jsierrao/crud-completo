package registro.service;

import java.util.List;

import registro.model.Curso;

public interface CursoService {
	
	public List<Curso>findAll();
	
	public Curso crear (Curso curso);
	
	public void delete (Curso curso);
	
	public Curso cursoId(Long id);

}
