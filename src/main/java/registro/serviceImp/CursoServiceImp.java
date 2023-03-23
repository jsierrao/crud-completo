package registro.serviceImp;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import registro.model.Curso;
import registro.repositorio.CursoRepository;
import registro.service.CursoService;
@Service
public class CursoServiceImp implements CursoService {
	
	@Autowired
	private CursoRepository repo;

	@Override
	public List<Curso> findAll() {

		return repo.findAll();
	}

	@Override
	public Curso crear(Curso curso) {
         return repo.save(curso);
	}

	@Override
	public void delete(Curso curso) {
		 repo.delete(curso);
	}

	@Override
	public Curso cursoId(Long id) {
		
	return repo.findById(id).orElse(null);
	}

}
