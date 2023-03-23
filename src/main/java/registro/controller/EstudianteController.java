package registro.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import registro.model.Estudiante;
import registro.model.EstudianteDto;

import registro.response.Response;
import registro.service.EstudianteService;

@CrossOrigin(originPatterns = { "http://localhost:4200" })
@RestController

public class EstudianteController {

	@Autowired
	private EstudianteService service;

	@GetMapping("/estudianteslistar")
	public ResponseEntity<List<Estudiante>> lista() {
		List<Estudiante> lista = service.findAll();
		return new ResponseEntity<>(lista, HttpStatus.OK);

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/lista/page/{page}")
	public Page<Estudiante> lista(@PathVariable Integer page) {
		Pageable pag = PageRequest.of(page, 4);
		return (Page<Estudiante>) service.findAll(pag);

	}

	@PostMapping("/estudiantesregistro")
	public ResponseEntity<?> registrar(@Valid @RequestBody Estudiante estudiante, BindingResult result) {
		Estudiante stu = null;
		Map<String, Object> students = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "elcampo '" + err.getField() + "'" + err.getDefaultMessage())
					.collect(Collectors.toList());
			students.put("errors", errors);
			return new ResponseEntity<>(students, HttpStatus.BAD_REQUEST);
		}

		try {
			stu = service.crear(estudiante);
		} catch (DataAccessException e) {
			students.put("mensaje", "error al crear el estudiante");
			//students.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(students, HttpStatus.BAD_REQUEST);
		}

		students.put("mensaje", "Estudiante creado con exito");
		students.put("estudiante", stu);
		return new ResponseEntity<>(students, HttpStatus.OK);

	}

	@PutMapping("/estudiantes/{id}")
	public ResponseEntity<?> actualizarPorId(@Valid @RequestBody Estudiante estu, BindingResult result,
			@PathVariable Long id) {

		Estudiante stuActual = service.actualizar(estu, id);

		Map<String, Object> students = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "elcampo '" + err.getField() + "'" + err.getDefaultMessage())
					.collect(Collectors.toList());
			students.put("errors", errors);
			return new ResponseEntity<>(students, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(stuActual, HttpStatus.OK);

	}

	@GetMapping("/estudiantesporidentidad")
	public ResponseEntity<?> listPorIdentidad(
			@RequestParam(value = "numeroDocumento", required = true) Integer numeroDocumento) {
		Estudiante rsp = service.findByIdentidad(numeroDocumento);

		Map<String, Object> response = new HashMap<>();
		if (rsp == null) {
			response.put("mensaje", "el documento ingresado: ".concat(numeroDocumento.toString().concat(" no existe")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(rsp, HttpStatus.OK);
	}

	@GetMapping("/estudiantesporestado")
	public ResponseEntity<Response> listPorEstado(@RequestParam(value = "estado", required = true) String estado) {
		Response rsp = service.findByEstado(estado);
		return ResponseEntity.ok(rsp);
	}

	@GetMapping("/estudiantesconsulta")
	public ResponseEntity<EstudianteDto> consulta() {
		return new ResponseEntity<>(service.dto(), HttpStatus.OK);
	}

	@GetMapping("/estudiantesporid/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Estudiante stu = null;
		Map<String, Object> response = new HashMap<>();
		try {

			stu = service.porId(id);

		} catch (DataAccessException e) {
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (stu == null) {
			response.put("mensaje",
					"el estudiante con el Id: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(stu, HttpStatus.OK);
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminacion(@PathVariable Long id) {
		Estudiante stu = service.porId(id);
		String fotoAnterior = stu.getFoto();
		if(fotoAnterior != null && fotoAnterior.length()>0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(fotoAnterior).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
			}
		
			service.eliminar(stu);
			}

	}

	@PostMapping("/cargarImagen")
	public ResponseEntity<?> cargaImagen(@RequestParam("imagen") MultipartFile imagen, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Estudiante stu = service.porId(id);

		if (!imagen.isEmpty()) {
			String nombreImagen = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename().replace("", "");
			Path rutaImagen = Paths.get("imagenes").resolve(nombreImagen).toAbsolutePath();
			try {
				Files.copy(imagen.getInputStream(), rutaImagen);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen" + nombreImagen);
				response.put("error", e.getMessage().concat(" : ").concat(e.getCause().getMessage()));

				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			String fotoAnterior = stu.getFoto();
			if(fotoAnterior != null && fotoAnterior.length()>0) {
				Path rutaFotoAnterior = Paths.get("uploads").resolve(fotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			
			
			stu.setFoto(nombreImagen);
			service.crear(stu);
			response.put("estudiante", stu);
			response.put("mensaje", "Has subido correctamente " + nombreImagen);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
