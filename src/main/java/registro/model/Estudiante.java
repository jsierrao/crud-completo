package registro.model;

import java.util.Date;
import java.util.List;





import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.micrometer.common.lang.NonNull;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import registro.model.DocumentoIdentidad.DocumentoIdentidadBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "registro_alumno")
public class Estudiante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@NotEmpty(message="primer nombre no puede estar vacio")
	@Size(min=3,max=12, message = "debe contener min 4 caracteres max 12")
	private String primerNombre;
    
	private String segundoNombre;

	@Size(min=3,max=12, message = "debe contener min 4 caracteres max 12")
	@NotEmpty(message="primer apellido no puede estar vacio")
	private String primerApellido;

	@Size(min=3,max=12, message = "debe contener min 4 caracteres max 12")
	@NotEmpty(message="segundo apellido no puede estar vacio")
	private String segundoApellido;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_identidad")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private DocumentoIdentidad tipoDocumento;

	@Min(value = 1)
	@Max(value = 1999999999)
	@Column(nullable = true, unique = true)
	private Integer numeroDocumento;
    
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_curso")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Curso curso;
 
	@Size(min=3,max=12, message = "debe contener min 4 caracteres max 12")
	@NotEmpty(message="estado no puede estar vacio")
	private String estado;

	
	@Column(name = "fecha_de_registro")
	@Temporal(TemporalType.DATE)
	@JsonProperty(value = "fecha")
	private Date createAt;
	
	private String foto;

}
