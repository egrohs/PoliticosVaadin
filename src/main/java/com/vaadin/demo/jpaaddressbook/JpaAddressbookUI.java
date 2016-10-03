/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vaadin.demo.jpaaddressbook;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.demo.jpaaddressbook.domain.Politico;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class JpaAddressbookUI extends UI {

	public static final String PERSISTENCE_UNIT = "addressbook";

	static {
		DemoDataGenerator.create();
	}

	@Override
	protected void init(VaadinRequest request) {
		// setContent(new AddressBookMainView());

//		JPAContainer<Politico> persons = JPAContainerFactory.make(Politico.class, JpaAddressbookUI.PERSISTENCE_UNIT);
//		Table personTable = new Table(null, persons);

		VerticalLayout view = new VerticalLayout();
		view.addComponent(new Label("Hello Vaadin!"));
		setContent(view);
	}
}
