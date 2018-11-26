package servico;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**
 * @author Danillo
 */

@Path("service/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface InterfaceServico {
	
	/*
	 * Servico que irá ser consumido pelo coordenador central. Quando o coorCentral
	 * consumir o serviço, ele passará a lista de bairros com a presença do mosquito. 
	 * Como ação da chamada desse serviço será enviado um email passando os bairros
	 * que foram detectadas a presença do mosquito. 
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("receberAlertaSaude/{bairros}")
	public String receberAlertaSaude(@PathParam("bairros") List<String> bairros);
	  
	
	@POST
	@Produces("application/json")
	@Path("emitirAlerta/")
	public String emitirAlerta(@PathParam("id") String id);
}
