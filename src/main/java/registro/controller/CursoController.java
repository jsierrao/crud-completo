package registro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import registro.model.Curso;
import registro.response.Response;
import registro.service.CursoService;
@CrossOrigin(originPatterns = {"http://localhost:4200"})
@RestController
public class CursoController {
	
	@Autowired
	CursoService service;
	
	@GetMapping("/listar")
	public List<Curso> listar (){
		return service.findAll();
	}
	
	
	@PostMapping("/crearcurso")
	public ResponseEntity<Response> crear(@RequestBody Curso curso) {
		Curso cur = service.crear(curso);
		Response rsp = new Response();
		rsp.setCode(String.valueOf(HttpStatus.OK));
		rsp.setMessage("Curso creado con exito");
		rsp.setCursos(cur);
		return new ResponseEntity<>(rsp,HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminarcurso/{id}")
	public Response eliminar (@PathVariable Long id) {
		Curso cur = service.cursoId(id);
		service.delete(cur);
		Response rsp = new Response();
		rsp.setMessage("eliminacion exitosa");
		return rsp;
	}

}
