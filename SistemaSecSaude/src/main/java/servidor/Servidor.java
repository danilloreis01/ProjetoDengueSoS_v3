package servidor;

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
	 * Basicamente cria um objeto JAXRSServerFactoryBean, que ser� responsavel por criar o objeto
	 * servidor, responsavel por atender �s solicita��es vindas dos clientes, ou seja, atender 
	 * �s requisi��es definidas na interface do servico.
	 */
	public static void main(String args[]) throws Exception {
		
		//criar o JAXRSServerFactoryBean e atrela a classe responsavel por gerenciar o servi�o a ele.
		JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
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
		
		//define o endere�o onde o servi�o ficar� disponivel 
		factoryBean.setAddress("http://192.168.0.101:9002/");//para acessar em rede local em casa
		//factoryBean.setAddress("http://localhost:9002/"); //para acessar na propria maquina
		//factoryBean.setAddress("http://10.62.8.239:9002/");
		//factoryBean.setAddress("http://172.26.192.215:9002/");//para acessar a rede da USP ICMC 802
		
		//cria uma instancia do servidor e inicia-lo
		Server server = factoryBean.create();
		System.out.println("\n\n#############  Servidor [SecSaude] pronto  #############\n\n");
		
		//l� �s informa��es recebidas
		System.in.read();
		
		//encerra a execu��o do servidor
		server.destroy();
		System.exit(0);
	}
}