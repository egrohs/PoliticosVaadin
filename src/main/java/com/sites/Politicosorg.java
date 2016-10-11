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

public class Politicosorg extends Site {
	public Politicosorg() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		List<String> urls = new ArrayList<String>();
		//while (true) {
			Elements sels = doc.select("ul.parliamentarian[data-toggle]");
			for (Element src : sels) {
				String processos = src.select("li:matchesOwn(Processos judiciais)").first().child(0).text();
				if (!"0".equals(processos)) {
					// String nome = src.select("li.nome > div >
					// span").first().text().replaceFirst(" \\(.+\\)", "");
					String url = src.select("ul").first().attr("data-url");
					urls.add(url);
				}
			}
			WebElement next = null;
			try {
				next = driver.findElement(By.xpath("//a[text()='»']"));
			} catch (NoSuchElementException e) {
				// System.err.println("botão next paga não encontrado.");
				System.exit(0);
			}
			if (next != null && next.isEnabled()) {
				clica(next);
				doc = lePaginaSemAjax();
			} else {
				//break;
			}
		//}
			String url = urls.get(0);
		//for (String url : urls) {
			doc = navega(getUrl()+url);
			String cpf = doc.select("label:matchesOwn(CPF:)").first().parent().text();
			String nome = doc.select("label:matchesOwn(Nome:)").first().parent().text();
			String codinome = doc.select("label:matchesOwn(Apelido:)").first().parent().text();
			String estado = doc.select("label:matchesOwn(Estado:)").first().parent().text();
			String cargo = doc.select("label:matchesOwn(Cargo:)").first().parent().text();
			String formacao = doc.select("label:matchesOwn(Formação Acadêmica:)").first().parent().text();
			String profissao = doc.select("label:matchesOwn(Profissão:)").first().parent().text();
			String partido = doc.select("label:matchesOwn(Partido:)").first().parent().text();
			String partidos = doc.select("label:matchesOwn(Filiações Partidárias:)").first().parent().text()
					.replaceAll("\\s?\\,\\s?", ",").replaceAll(" e ", ",");

			Politico opolitico = Dao.getPoliticoByCPFOrNew(cpf);
			opolitico.setNome(cpf);
			opolitico.setNome(nome);
			opolitico.setCodinomes(codinome);
			opolitico.setUf(estado);
			opolitico.setCargos(cargo);
			// opolitico.setFo(formacao);
			opolitico.setProfissoes(profissao);

			// Politico opolitico = new Politico(cpf, nome, codinome, estado,
			// cargo, formacao, profissao);
			Partido opartido = Dao.getPartidoBySiglaOrNew(partido);
			opolitico.setPartidoAtual(opartido);
			for (String ps : partidos.split(",")) {
				opartido = Dao.getPartidoBySiglaOrNew(ps);
				opolitico.getPartidos().add(opartido);
			}
			Dao.salvaPolitico(opolitico);
			// System.out.println(nome + "\t" + url);
		//}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://www.politicos.org.br/";
	}
}
