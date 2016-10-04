package com.vaadin.demo.jpaaddressbook.domain;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Partido {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private String sigla;
	private String nome;
	private Integer numero;
	private Date dataCriacao;
	private Date dataDissolucao;
	private Long filiados;
	@NotNull
	private String espectro;
	@NotNull
	private String ideologia;
	@ManyToMany(mappedBy = "partidos")
	private Set<Politico> politicos;
	// @Transient
	// private Boolean superPartido;
	@ManyToOne
	private Partido parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataDissolucao() {
		return dataDissolucao;
	}

	public void setDataDissolucao(Date dataDissolucao) {
		this.dataDissolucao = dataDissolucao;
	}

	public Long getFiliados() {
		return filiados;
	}

	public void setFiliados(Long filiados) {
		this.filiados = filiados;
	}

	public String getEspectro() {
		return espectro;
	}

	public void setEspectro(String espectro) {
		this.espectro = espectro;
	}

	public String getIdeologia() {
		return ideologia;
	}

	public void setIdeologia(String ideologia) {
		this.ideologia = ideologia;
	}

	public Set<Politico> getPoliticos() {
		return politicos;
	}

	public void setPoliticos(Set<Politico> politicos) {
		this.politicos = politicos;
	}

	public Partido getParent() {
		return parent;
	}

	public void setParent(Partido parent) {
		this.parent = parent;
	}

	// public boolean isSuperPartido() {
	// if (superPartido == null) {
	// superPartido = getPoliticos().size() == 0;
	// }
	// return superPartido;
	// }
	// @Transient
	// public String getHierarchicalName() {
	// if (parent != null) {
	// return parent.toString() + " : " + name;
	// }
	// return name;
	// }
	//
	// @Override
	// public String toString() {
	// return getHierarchicalName();
	// }
}
