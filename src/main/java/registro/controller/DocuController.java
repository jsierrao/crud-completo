package registro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import registro.model.DocumentoIdentidad;
import registro.response.Response;
import registro.service.DocumentoService;

@CrossOrigin(originPatterns = {"http://localhost:4200"})
@RestController
public class DocuController {

	@Autowired
	DocumentoService service;

	@GetMapping("/documentos")
	public List<DocumentoIdentidad> lista() {
		return service.findAll();
	}

	@PostMapping("/crearDocuemnto")
	public DocumentoIdentidad crear(@RequestBody DocumentoIdentidad docu) {
		return service.crear(docu);
	}

	@DeleteMapping("eliminarDocumento/{id}")
	public Response eliminarDocumneto(@PathVariable Long id) {
		DocumentoIdentidad doc = service.docuPorId(id);
		service.eliminarDocumneto(doc);
		Response rsp = new Response();
		rsp.setMessage("eliminacion correcta");

		return rsp;

	}

}
