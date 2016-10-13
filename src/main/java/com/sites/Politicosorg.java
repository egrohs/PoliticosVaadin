package com.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.dao.Dao;
import com.modelo.Partido;
import com.modelo.Politico;
import com.modelo.Url;

public class Politicosorg extends Site {
	private boolean begin;

	public Politicosorg() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc) throws IOException {
		List<String> urls = new ArrayList<String>();
		while (true) {
			Elements sels = doc.select("ul.parliamentarian[data-toggle]");
			for (Element src : sels) {
				String processos = src.select("li:matchesOwn(Processos judiciais)").first().child(0).text();
				// TODO BAIXAR OS LIMPOS TB!!!
				if (!"0".equals(processos)) {
					// String nome = src.select("li.nome > div >
					// span").first().text().replaceFirst(" \\(.+\\)", "");
					String url = src.select("ul").first().attr("data-url");
				}
			}
			WebElement next = null;
			try {
				next = driver.findElement(By.xpath("//a[text()='»']"));
			} catch (NoSuchElementException e) {
				// System.err.println("botão next paga não encontrado.");
				break;
			}
			if (next != null && next.isEnabled()) {
				clica(next);
				doc = lePaginaSemAjax();
			} else {
				break;
			}
		}
		List<Politico> politicos = new ArrayList<Politico>();
		for (String url : urls) {
			politicos.add(atualizaPolitico(url));
			// System.out.println(nome + "\t" + url);
		}
		return politicos;
	}

	private Politico atualizaPolitico(String url) {
		Document doc;
		doc = navega(getUrl() + url);
		int peso = Integer.parseInt(doc.select("div:matchesOwn(Processos judiciais)").first().nextElementSibling()
				.nextElementSibling().text());
		String cpf = doc.select("label:matchesOwn(CPF:)").first().parent().text().replaceFirst(".+\\: ", "");
		String nome = doc.select("label:matchesOwn(Nome:)").first().parent().text().replaceFirst(".+\\: ", "");
		String codinome = doc.select("label:matchesOwn(Apelido:)").first().parent().text().replaceFirst(".+\\: ", "");
		String estado = doc.select("label:matchesOwn(Estado:)").first().parent().text().replaceFirst(".+\\: ", "");
		String cargo = doc.select("label:matchesOwn(Cargo:)").first().parent().text().replaceFirst(".+\\: ", "");
		String formacao = doc.select("label:matchesOwn(Formação Acadêmica:)").first().parent().text()
				.replaceFirst(".+\\: ", "");
		String profissao = doc.select("label:matchesOwn(Profissão:)").first().parent().text().replaceFirst(".+\\: ",
				"");
		String partido = doc.select("label:matchesOwn(Partido:)").first().parent().text().replaceFirst(".+\\: ", "");
		String partidos = doc.select("label:matchesOwn(Filiações Partidárias:)").first().parent().text()
				.replaceAll("\\s?\\,\\s?", ",").replaceAll(" e ", ",").replaceFirst(".+\\: ", "");

		Politico opolitico = Dao.getPoliticoByCPFOrNew(cpf);
		opolitico.setCpf(cpf);
		opolitico.setNome(nome);
		opolitico.setCodinomes(codinome);
		opolitico.setUf(estado);
		opolitico.setCargos(cargo);
		// opolitico.setFo(formacao);
		opolitico.setProfissoes(profissao);

		// Politico opolitico = new Politico(cpf, nome, codinome, estado,
		// cargo, formacao, profissao);
		Partido opartido = Dao.getPartidoBySigla(partido);
		opolitico.setPartidoAtual(opartido);
		for (String ps : partidos.split(",")) {
			opartido = Dao.getPartidoBySigla(ps);
			opolitico.getPartidos().add(opartido);
		}
		Url u = Dao.getUrlByUrlOrNew(url);
		opolitico.getUrls().add(u);
		u.setUrl(url);
		u.setPeso(peso);
		u.setPolitico(opolitico);
		u.setPartido(opartido);
		// Dao.atualizaPolitico(opolitico);
		return opolitico;
	}

	@Override
	public String getUrl() {
		return "http://www.politicos.org.br";
	}
}
