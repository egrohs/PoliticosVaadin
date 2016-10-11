package com.sites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.modelo.Politico;
import com.modelo.Url;

public class Senado extends Site {
	public Senado() throws Exception {
		super(true);
	}

	@Override
	public String getUrl() {
		return "http://www25.senado.leg.br/web/senadores/senador/-/perfil/4990";
		// return
		// "http://www25.senado.leg.br/web/senadores/legislaturas-anteriores";
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		// WebElement nome = driver.findElement(By.id("nome"));
		// nome.sendKeys(n);
		WebElement leg = driver.findElement(By.id("select-legislatura"));
		// leg.sendKeys("Qualquer Legislatura...");
		leg.sendKeys("54� Legislatura (2011 - 2015)");
		// WebElement pesq = driver.findElement(By.id("Pesquisa2"));
		// clica(pesq);

		Map<String, Politico> pols = new HashMap<String, Politico>();
		for (Politico politico : politicos) {
			pols.put(politico.getSenadoId(), politico);
		}
		parseData(pols);
		// // for (Element e : es) {
		// // System.out.println(e.text());
		// // }
		// //span
		return politicos;
	}

	private void parseData(Map<String, Politico> politicos) throws IOException {
		Document doc;
		doc = lePaginaSemAjax();
		Elements lines = doc.select("tbody > tr[data-suplente]");
		List<String> urls = new ArrayList<String>();
		for (Element line : lines) {
			// System.out.println(p.attr("href"));
			String url = line.select("a").first().attr("href");
			// if(nao existe){
			urls.add(url);
			// String partido = line.child(1).text();
			// String uf = line.child(2).text();
			// urls.add();

		}
		for (String url : urls) {
			doc = navega(url);
			String sp[] = url.split("/");
			String idsen = sp[sp.length - 1];
			String nome = doc.select("dt:matchesOwn(Nome civil:)").first().nextElementSibling().text();
			String codinome = doc.select("h1 > span").first().text();
			String foto = doc.select("div > img").first().attr("src");
			Politico politico = new Politico(nome, codinome, "uf", "profissoes", "Senado", "legislaturas", foto);
			Url u = new Url(idsen, false, url, politico);
			politicos.put(idsen, politico);
			System.out.println(politico);
		}
	}
}