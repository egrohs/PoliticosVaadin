package com.modelo;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private String foto;// ??? ou fica em urls ???
	// private String camaraPk;
	// private String senadoId;
	private String uf;
	@ManyToOne
	private Partido partidoAtual;
	// @NotNull
	@ManyToMany
	private Set<Partido> partidos;
	@OneToMany
	@JoinColumn(name = "politico_fk") // we need to duplicate the physical
										// information
	private Set<Url> urls;
	private String cpf;
	private String formacao;

	public Politico() {
	}

	public Politico(String cpf, String nome, String codinome, String estado, String cargo, String formacao,
			String profissao) {
		// this.cpf = cpf;
		this.nome = nome;
		this.codinomes = codinome;
		this.uf = estado;
		// this.formacao = formacao;
		this.profissoes = profissao;
		this.cargos = cargo;
		// this.legislaturas = legislaturas;
		// this.foto = foto;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Field f : Politico.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				sb.append(f.get(this) + ", ");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

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
}