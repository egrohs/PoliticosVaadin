package com.sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.modelo.Politico;

public class Excelencias extends Site {
	public Excelencias() throws Exception {
		super(true);
	}

	@Override
	public List<Politico> getData(Document doc) throws IOException {
		WebElement txt = driver.findElement(By.name("busca"));
		txt.clear();
		WebElement procurar = driver.findElement(By.name("btBusca"));
		clica(procurar);
		doc = lePaginaSemAjax();
		Elements es = doc.select("div#contem_busca > p > a");
		for (Element element : es) {
			String nome = element.text();
			nome = nome.substring(nome.indexOf(": ") + 2, nome.lastIndexOf(")"));
			// codinome??? == div[contem_busca]/href
			System.out.println(nome + "\t" + element.attr("href"));
		}
		// Abel Mesquita Jr. (DEM-RR) (nome de batismo: Abel Salvador Mesquita
		// Junior)
		// span.txt_pq > i[Não parece haver ocorrências]
		// System.out.println(lista.getText());
		return null;
	}

	@Override
	public String getUrl() {
		return "http://www.excelencias.org.br/";
	}
}