package com.sites;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.modelo.Politico;

public class Veja extends Site {
	public Veja() throws Exception {
		super(false);
	}

	String name = "Zilmar Fernandes";
	String sufix = "perfil/$.shtml' + '?scrollto=conteudo-rede";

	// todos desse site tem ocorrencias ruims...
	public List<Politico> getData(Document doc, List<Politico> politicos) throws IOException {
		Elements sels = doc.select("select");
		for (Element src : sels.get(1).children()) {
			if (src.tagName().equals("option")) {
				// if (name.equals(src.text())) {
				// System.out.println(src);
				// break;
				// }
				String cod = src.attr("value");
				System.out.println(cod + "\t" + getUrl() + sufix.replaceFirst("\\$", cod));
				// Document doc1 = Jsoup.connect(url+s.replaceFirst("\\$",
				// )).get();
			}
		}
		return politicos;
	}

	@Override
	public String getUrl() {
		return "http://veja.abril.com.br/infograficos/rede-escandalos/";
	}
}
