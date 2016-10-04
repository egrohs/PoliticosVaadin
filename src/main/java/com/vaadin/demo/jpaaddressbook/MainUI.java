package com.vaadin.demo.jpaaddressbook;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MainUI extends UI {

	public static final String PERSISTENCE_UNIT = "addressbook";

	static {
		//DadosInicias.create();
	}

	@Override
	protected void init(VaadinRequest request) {
		//PainelPrincipal pp = ;
		//setContent(new PainelPrincipal());
		setContent(new FotosUI());
		// VerticalLayout view = new VerticalLayout();
		// view.addComponent(new Label("Hello Vaadin!"));
		// setContent(view);
	}
}
