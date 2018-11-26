package servico;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**

 *
 * @author Danillo
 */

@Path("service/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface InterfaceServico {

	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("receberAlertaSecServPublicos/{bairros}")
	public String receberAlertaSecServicosPublicos(@PathParam("bairros") List<String> bairros);
	//http://localhost:9003/servicospublicos/receberAlertaSecServPublicos  
}
