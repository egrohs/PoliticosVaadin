package com.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("mytheme")
public class MainUI extends UI {
	public static final String PERSISTENCE_UNIT = "politicos";

	static {
		//DadosInicias.create();
	}
	private static MainUI ui;
	public static MainUI getInstancia() {
		return ui;
	}

	@Override
	protected void init(VaadinRequest request) {
		//PainelPrincipal pp = ;
		//setContent(new PainelPrincipal());
		setContent(new FotosUI());
		// VerticalLayout view = new VerticalLayout();
		// view.addComponent(new Label("Hello Vaadin!"));
		// setContent(view);
		ui = this;
	}
}
