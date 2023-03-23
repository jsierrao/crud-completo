package registro.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import registro.model.DocumentoIdentidad;

public interface DocumentoRepository extends JpaRepository<DocumentoIdentidad, Long> {

}