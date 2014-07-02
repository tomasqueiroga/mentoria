package br.com.controleprocessos.business.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Endereco {
	
	@Id
	public Long id;
	private String rua;
	private String bairro;
	private String CEP;
	private String cidade;
	private String UF; //seria melhor um Enum
	
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCEP() {
		return CEP;
	}
	public void setCEP(String cep) {
		this.CEP = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uf) {
		this.UF = uf;
	}
	
	

}
