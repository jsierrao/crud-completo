package registro.service;

import java.util.List;

import registro.model.DocumentoIdentidad;

public interface DocumentoService {
	
	public List<DocumentoIdentidad>findAll();
	
	public DocumentoIdentidad crear(DocumentoIdentidad docu);
	
	public void eliminarDocumneto(DocumentoIdentidad docu);
	
	public DocumentoIdentidad docuPorId(Long id);
	
	

}
