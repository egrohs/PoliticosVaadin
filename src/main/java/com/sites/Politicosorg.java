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

import com.modelo.Partido;
import com.modelo.Politico;

public class Politicosorg extends Site {
	public Politicosorg() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		List<String> urls = new ArrayList<String>();
		while (true) {
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
				break;
			}
		}
		for (String url : urls) {
			doc = navega(url);
			String cpf = doc.select("li:matchesOwn(CPF: )").first().parent().text();
			String nome = doc.select("li:matchesOwn(Nome: )").first().parent().text();
			String codinome = doc.select("li:matchesOwn(Apelido: )").first().parent().text();
			String estado = doc.select("li:matchesOwn(Estado: )").first().parent().text();
			String cargo = doc.select("li:matchesOwn(Cargo: )").first().parent().text();
			String formacao = doc.select("li:matchesOwn(Formação Acadêmica: )").first().parent().text();
			String profissao = doc.select("li:matchesOwn(Profissão: )").first().parent().text();
			String partido = doc.select("li:matchesOwn(Partido: )").first().parent().text();
			String partidos = doc.select("li:matchesOwn(Filiações Patidárias: )").first().parent().text();
			
			Politico p = new Politico(cpf, nome, codinome, estado, cargo, formacao, profissao);
			//Partido pp = new Partido();
			// System.out.println(nome + "\t" + url);
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://www.politicos.org.br/";
	}
}
