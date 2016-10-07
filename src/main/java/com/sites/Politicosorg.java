package com.sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.modelo.Politico;

public class Politicosorg extends Site {
	public Politicosorg() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		while (true) {
			Elements sels = doc.select("ul.parliamentarian[data-toggle]");
			for (Element src : sels) {
				String processos = src.select("li:matchesOwn(Processos judiciais)").first().child(0).text();
				if (!"0".equals(processos)) {
					String nome = src.select("li.nome > div > span").first().text().replaceFirst(" \\(.+\\)", "");
					String url = src.select("ul").first().attr("data-url");
					System.out.println(nome + "\t"+url);
				}
			}
			WebElement next = null;
			try {
				next = driver.findElement(By.xpath("//a[text()='»']"));
			} catch (NoSuchElementException e) {
				//System.err.println("botão next paga não encontrado.");
				System.exit(0);
			}
			if (next != null) {
				clica(next);
				doc = lePaginaSemAjax();
			} else {
				break;
			}
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://www.politicos.org.br/";
	}
}
