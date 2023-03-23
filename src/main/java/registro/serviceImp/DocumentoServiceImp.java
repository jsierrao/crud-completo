package registro.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import registro.model.DocumentoIdentidad;
import registro.repositorio.DocumentoRepository;
import registro.service.DocumentoService;

@Service
public class DocumentoServiceImp implements DocumentoService {
	
	@Autowired
	DocumentoRepository repo;

	@Override
	public List<DocumentoIdentidad> findAll() {
		return repo.findAll();
	}

	@Override
	public DocumentoIdentidad crear(DocumentoIdentidad docu) {

		return repo.save(docu);
	}

	@Override
	public void eliminarDocumneto(DocumentoIdentidad docu) {
		repo.delete(docu);
		
	}

	@Override
	public DocumentoIdentidad docuPorId(Long id) {
		
		return repo.findById(id).orElse(null);
	}

}
