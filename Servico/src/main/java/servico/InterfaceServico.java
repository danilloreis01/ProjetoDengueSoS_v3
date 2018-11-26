package servico;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entidades.Bairro;
import entidades.Sensor;

/**
 * Esta classe contém a implementação da interface contendo os principais
 * serviços (métodos) que serão disponibilizados e acessados. Em cada método
 * (serviço) é disponibilizado o caminho de acesso por meio da
 * tag @RequestMapping
 *
 * @author Danillo
 */

@Path("service/")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface InterfaceServico {

	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("enviarDados/")
	public void enviarDados(Sensor s);

	/**
	 * Método que implementa o serviço de buscar todas as informações de todos os sensores
	 * disponíveis no Banco de Dados
	 * @return lista de informações dos sensores
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterInformacoesSensores/")
	public List obterInformacoesSensores();
	//http://localhost:9001/service/obterInformacoesSensores   

	/**
	 * Método responsável por conter o serviço de obter os dados do 
	 * bairro cadastrado passando o ID do bairro
	 * 
	 * @return bairro
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterDadosBairro/{varX}")
	public Bairro obterDadosBairro(@PathParam("varX") int bairro);
	
	
	/**
	 * Método que implementa o serviço de obter todos os bairros
	 * cadastrados de uma única vez
	 * 
	 * @return lista de todos os bairros cadastrados
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterTodosBairro/")
	public List obterTodosBairro();
	

	/**
	 * Método que implementa o serviço de obter os dados que os sensores geram
	 * 
	 * @return lista de todos os dados dos sensores
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterDadosSensores/")
	public List obterDadosSensores();

}
