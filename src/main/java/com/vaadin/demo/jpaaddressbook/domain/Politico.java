package com.vaadin.demo.jpaaddressbook.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Politico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 2, max = 24)
    private String nome;
    @Size(min = 2, max = 24)
    private String codinomes;
    private String profissoes;
    private String cargos;
    private String legislaturas;
    private String curriculo;
    private String foto;
    
    private String camaraPk;
    private String senadoId;
    private String uf;
    //@NotNull
    @ManyToMany
    private Set<Partido> partidos;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCamaraPk() {
		return camaraPk;
	}
	public void setCamaraPk(String camaraPk) {
		this.camaraPk = camaraPk;
	}
	public String getSenadoId() {
		return senadoId;
	}
	public void setSenadoId(String senadoId) {
		this.senadoId = senadoId;
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
}