package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import servico.Servico;

public class Servidor {
	
	/**
	 * Basicamente cria um objeto JAXRSServerFactoryBean, que será responsavel por criar o objeto
	 * servidor, responsavel por atender às solicitações vindas dos clientes, ou seja, atender 
	 * às requisições definidas na interface do servico.
	 * 
	 * JAX-RS é uma API para Web Services RESTFUL
	 */
	public static void main(String args[]) throws Exception {
		
		//criar o JAXRSServerFactoryBean e atrela a classe responsavel por gerenciar o serviço a ele.
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
		
		//mapeando a classe Servico para ser um servico do tipo RESTFUL
		factoryBean.setResourceClasses(Servico.class);
		factoryBean.setResourceProvider(new SingletonResourceProvider(new Servico()));
		
		//especifica os tipos de dados que podem ser gerenciados pelo factoryBean. Nesse caso, XLM e JSON
		Map<Object, Object> extensionMappings = new HashMap<Object, Object>();
		extensionMappings.put("xml", MediaType.APPLICATION_XML);
		extensionMappings.put("json", MediaType.APPLICATION_JSON);
		factoryBean.setExtensionMappings(extensionMappings);
		
		List<Object> providers = new ArrayList<Object>();
		providers.add(new JAXBElementProvider());
		providers.add(new JacksonJsonProvider());
		factoryBean.setProviders(providers);
		
		//define o endereço onde o serviço ficará disponivel
		factoryBean.setAddress("http://192.168.0.101:9001/");//para acessar em rede local em casa
		//factoryBean.setAddress("http://localhost:9001/"); //para acessar na própria máquina
		//factoryBean.setAddress("http://10.62.8.239:9001/");//para acessar a rede do labes
		//factoryBean.setAddress("http://172.26.192.215:9001/");//para acessar a rede da USP ICMC 802
		
		//cria uma instancia do servidor e inicia-lo
		Server server = factoryBean.create();
		System.out.println("\n\n#############  Servidor pronto  #############\n\n");
		
		//lê às informações recebidas
		System.in.read();
		
		//encerra a execução do servidor
		server.destroy();
		System.exit(0);
	}
}