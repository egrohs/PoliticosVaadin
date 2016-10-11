package com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Url {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private boolean sujo;
	@NotNull
	private String url;
	private String urlId;
	@ManyToOne
	@JoinColumn(name = "politico_fk", insertable = false, updatable = false)
	private Politico politico;
	@ManyToOne
	@JoinColumn(name = "partido_fk", insertable = false, updatable = false)
	private Partido partido;
	public Url(){}
	public Url(String urlId, boolean sujo, String url, Politico politico) {
		this.urlId = urlId;
		this.sujo = sujo;
		this.url = url;
		this.politico = politico;
	}

	public boolean isSujo() {
		return sujo;
	}

	public void setSujo(boolean sujo) {
		this.sujo = sujo;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String tipo) {
		this.urlId = tipo;
	}

	public Politico getPolitico() {
		return politico;
	}

	public void setPolitico(Politico politico) {
		this.politico = politico;
	}
}