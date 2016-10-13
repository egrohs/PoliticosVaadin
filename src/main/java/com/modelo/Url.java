package com.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Url extends Entidade {
	@Id
	private String url;
	private String urlId;
	private Integer peso;
	@ManyToOne
	@JoinColumn(name = "politico_fk", insertable = false, updatable = false)
	private Politico politico;
	@ManyToOne
	@JoinColumn(name = "partido_fk", insertable = false, updatable = false)
	private Partido partido;

	public Url() {
	}

//	public Url(String urlId, boolean sujo, String url, Politico politico) {
//		this.urlId = urlId;
//		this.sujo = sujo;
//		this.url = url;
//		this.politico = politico;
//	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Url) {
			Url pk = (Url) obj;
			// TODO levar em conta o id aqui?
			if (url == null && pk.url == null) {
				return super.equals(obj);
			}
			if (pk.url.equals(url)) {
				return true;
			}
		}
		return false;
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

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}
}