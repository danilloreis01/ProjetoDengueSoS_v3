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
 * Esta classe cont�m a implementa��o da interface contendo os principais
 * servi�os (m�todos) que ser�o disponibilizados e acessados. Em cada m�todo
 * (servi�o) � disponibilizado o caminho de acesso por meio da
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
	 * M�todo que implementa o servi�o de buscar todas as informa��es de todos os sensores
	 * dispon�veis no Banco de Dados
	 * @return lista de informa��es dos sensores
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterInformacoesSensores/")
	public List obterInformacoesSensores();
	//http://localhost:9001/service/obterInformacoesSensores   

	/**
	 * M�todo respons�vel por conter o servi�o de obter os dados do 
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
	 * M�todo que implementa o servi�o de obter todos os bairros
	 * cadastrados de uma �nica vez
	 * 
	 * @return lista de todos os bairros cadastrados
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterTodosBairro/")
	public List obterTodosBairro();
	

	/**
	 * M�todo que implementa o servi�o de obter os dados que os sensores geram
	 * 
	 * @return lista de todos os dados dos sensores
	 */
	@GET
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obterDadosSensores/")
	public List obterDadosSensores();

}
