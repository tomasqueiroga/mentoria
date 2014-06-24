package br.com.controleprocessos.business.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.StringUtils;

@Entity
public class Cliente extends SuperEntity {

	private String nome;
	private String RG;
	private String CPF;
	private EstadoCivil estadoCivil; // seria melhor um ENUM (não sei fazer)
	private String nroCTPS;
	private String honorarios;
	private String formaPagto;
	private Endereco endereco;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	@Enumerated(EnumType.STRING)
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getNroCTPS() {
		return nroCTPS;
	}

	public void setNroCTPS(String nroCTPS) {
		this.nroCTPS = nroCTPS;
	}

	public String getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(String honorarios) {
		this.honorarios = honorarios;
	}

	public String getFormaPagto() {
		return formaPagto;
	}

	public void setFormaPagto(String formaPagto) {
		this.formaPagto = formaPagto;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public void validate() throws ValidationException {
		List<String> erros = new ArrayList<String>();

		if (StringUtils.isBlank(getNome()))
			erros.add("nome-obrigatorio");

		if (!erros.isEmpty())
			throw new ValidationException(erros);
	}

}
