package registro.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import registro.model.Curso;
import registro.model.Estudiante;

@Setter
@Getter
public class Response {
	
	@JsonInclude(value = Include.NON_NULL)
	private String code;
	
	@JsonInclude(value = Include.NON_NULL)
	private String message;
	
	@JsonInclude(value = Include.NON_NULL)
	private List<Estudiante> estudiante;
	
	@JsonInclude(value = Include.NON_NULL)
	private Estudiante estudiantes;
	
	@JsonInclude(value = Include.NON_NULL)
    private Curso cursos;
    
	@JsonInclude(value = Include.NON_NULL)
    private List<Curso>curso;
	
	

}