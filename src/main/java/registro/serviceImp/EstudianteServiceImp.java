package registro.serviceImp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;
import registro.model.Curso;
import registro.model.DocumentoIdentidad;
import registro.model.Estudiante;
import registro.model.EstudianteDto;

import registro.repositorio.CursoRepository;
import registro.repositorio.DocumentoRepository;
import registro.repositorio.EstudianteRepository;

import registro.response.Response;
import registro.service.EstudianteService;

@Service
@Slf4j
public class EstudianteServiceImp implements EstudianteService {

	@Autowired
	EstudianteRepository repo;

	@Autowired
	CursoRepository curso;

	@Autowired
	DocumentoRepository docu;

	@Override
	public Estudiante crear(Estudiante estudiante) {
         estudiante.setCreateAt(new Date());
		return estudiante = repo.save(estudiante);

	}

	@Override
	public Estudiante actualizar(Estudiante estu, @PathVariable Long id) {
		log.info("validaicon por id : {}", repo.findById(id));
		Estudiante stu = porId(id);
		stu.setPrimerNombre(estu.getPrimerNombre());
		stu.setSegundoNombre(estu.getSegundoNombre());
		stu.setPrimerApellido(estu.getPrimerApellido());
		stu.setSegundoApellido(estu.getSegundoApellido());
		stu.setTipoDocumento(estu.getTipoDocumento());
		stu.setNumeroDocumento(estu.getNumeroDocumento());
		stu.setCurso(estu.getCurso());
		stu.setEstado(estu.getEstado());
		stu.setCreateAt(estu.getCreateAt());
		crear(stu);
		return stu;
	}

	@Override
	public Response findByEstado(String estado) {
		List<Estudiante> query = null;
		Response rsp = new Response();
		if ("activo".equals(estado) || "inactivo".equals(estado)) {
			query = repo.buscarEstado(estado);
			rsp.setCode(String.valueOf(HttpStatus.OK));
			rsp.setMessage("lista generada");
			rsp.setEstudiante(query);
		} else {
			rsp.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
			rsp.setMessage("estado no valido");
		}
		return rsp;
	}

	@Override
	public Estudiante findByIdentidad(Integer numeroDocumento) {
	  return  repo.buscarDocumento(numeroDocumento);
		
		
	}

	@Override
	public List<Estudiante> findAll() {
		Response rsp = new Response();
		List<Estudiante> queryEstudiante = null;
		queryEstudiante = repo.findAll();
		if (queryEstudiante.isEmpty()) {
			ResponseEntity.noContent().build();

		} else {

			rsp.setCode("200");
			rsp.setMessage("lista generada");
			rsp.setEstudiante(queryEstudiante);

		}
		return queryEstudiante;

	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estudiante> findAll(Pageable page) {
		return repo.findAll(page);

	}

	@Override
	public EstudianteDto dto() {
		List<Curso> cur = curso.findAll();
		List<DocumentoIdentidad> doc = docu.findAll();
		EstudianteDto stu = new EstudianteDto();
		stu.setCurso(cur);
		stu.setTipoDocumento(doc);

		return stu;
	}

	@Override
	public Estudiante porId(Long id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Estudiante stu) {
		repo.delete(stu);
	}

}
