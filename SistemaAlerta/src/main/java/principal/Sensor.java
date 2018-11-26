package principal;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sensor {
	
	private int id;
	private String nome;
	private String latitude;
	private String longitude;
	private int idBairro;
	private List<Leitura> leituras = new ArrayList<Leitura>();
	

	public void salvarLeitura(String ocorrencia) {
		
		String[] atributo = ocorrencia.split(",");
		
		Leitura leitura = new Leitura();
		
		String dataHora = atributo[1].substring(atributo[1].indexOf(":")+1);		
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		Date t;
		
		try {
			t = ft.parse(dataHora);
			leitura.setData(t);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		String presenca = atributo[2].substring(atributo[2].indexOf(":")+1);
		boolean presencaConvertida = Boolean.valueOf(presenca);
		
		leitura.setPresenca(presencaConvertida);
		leitura.setBairro(atributo[6].substring(atributo[6].indexOf(":")+1, atributo[6].length()-1));
				
		leituras.add(leitura);
		
	}
	
	

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(int idBairro) {
		this.idBairro = idBairro;
	}
	
	public List<Leitura> getLeituras() {
		return leituras;
	}



	public void setLeituras(List<Leitura> leituras) {
		this.leituras = leituras;
	}

}
