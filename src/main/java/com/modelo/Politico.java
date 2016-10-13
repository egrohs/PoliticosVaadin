package com.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Politico extends Entidade {
	@NotNull
	@Size(min = 2, max = 100)
	private String nome;
	@Size(min = 2, max = 1000)
	private String codinomes;
	private String profissoes;
	private String cargos;
	private String legislaturas;
	private String curriculo;
	private String foto;// ??? ou fica em urls ???
	private String uf;
	@ManyToOne
	private Partido partidoAtual;
	// @NotNull
	@ManyToMany
	private Set<Partido> partidos;
	@OneToMany
	@JoinColumn(name = "politico_fk") // we need to duplicate the information
	private Set<Url> urls;
	@Id
	private String cpf;
	private String formacao;

	public Politico() {
		partidos = new HashSet<Partido>();
		urls = new HashSet<Url>();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Politico) {
			Politico pk = (Politico) obj;
			// TODO tratar null
			if (cpf == null && pk.cpf == null) {
				return super.equals(obj);
			}
			if (pk.cpf.equals(cpf)) {
				return true;
			}
		}
		return false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodinomes() {
		return codinomes;
	}

	public void setCodinomes(String codinomes) {
		this.codinomes = codinomes;
	}

	public String getProfissoes() {
		return profissoes;
	}

	public void setProfissoes(String profissoes) {
		this.profissoes = profissoes;
	}

	public String getCargos() {
		return cargos;
	}

	public void setCargos(String cargos) {
		this.cargos = cargos;
	}

	public String getLegislaturas() {
		return legislaturas;
	}

	public void setLegislaturas(String legislaturas) {
		this.legislaturas = legislaturas;
	}

	public String getCurriculo() {
		return curriculo;
	}

	public void setCurriculo(String curriculo) {
		this.curriculo = curriculo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Set<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(Set<Partido> partidos) {
		this.partidos = partidos;
	}

	public Partido getPartidoAtual() {
		return partidoAtual;
	}

	public void setPartidoAtual(Partido partidoAtual) {
		this.partidoAtual = partidoAtual;
	}

	// public String getCpf() {
	// return cpf;
	// }
	//
	// public void setCpf(String cpf) {
	// this.cpf = cpf;
	// }

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public Set<Url> getUrls() {
		return urls;
	}

	public void setUrls(Set<Url> urls) {
		this.urls = urls;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}