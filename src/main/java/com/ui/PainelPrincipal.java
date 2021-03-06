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
package com.ui;

import com.modelo.Politico;
import com.ui.JanelaPoliticos.EditorSavedEvent;
import com.ui.JanelaPoliticos.EditorSavedListener;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PainelPrincipal extends VerticalLayout implements ComponentContainer {
	private Table politcosTable;
	private TextField searchField;
	private Button newButton;
	private Button deleteButton;
	private Button editButton;
	//TODO usar BeanItemContainer? ou SQLContainer invez do JPAContainer.
	// private JPAContainer<Partido> containerPartidos;
	private JPAContainer<Politico> containerPoliticos;
	// private Partido departmentFilter;
	private String textFilter;

	public PainelPrincipal() {
		// containerPartidos = new ContainerJPAPartido();
		containerPoliticos = JPAContainerFactory.make(Politico.class, MainUI.PERSISTENCE_UNIT);
		containerPoliticos.setEntityProvider(new CachingLocalEntityProvider<Politico>(Politico.class,
				JPAContainerFactory.createEntityManagerForPersistenceUnit(MainUI.PERSISTENCE_UNIT)));
		buildMainArea();
	}

	private void buildMainArea() {
		politcosTable = new Table(null, containerPoliticos);
		politcosTable.setSelectable(true);
		politcosTable.setImmediate(true);
		politcosTable.addListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				setModificationsEnabled(event.getProperty().getValue() != null);
			}

			private void setModificationsEnabled(boolean b) {
				deleteButton.setEnabled(b);
				editButton.setEnabled(b);
			}
		});
		// politcosTable.setHeight("100%");
		// politcosTable.setWidth("100%");
		// politcosTable.setSizeFull();
		// personTable.setSizeUndefined();
		// personTable.setSelectable(true);
		politcosTable.addListener(new ItemClickListener() {
			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					politcosTable.select(event.getItemId());
				}
			}
		});

		politcosTable.setVisibleColumns(
				new Object[] { "nome", "codinomes", "profissoes", "legislaturas", "curriculo", "uf" });

		HorizontalLayout toolbar = new HorizontalLayout();
		newButton = new Button("Add");
		newButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				final BeanItem<Politico> newPersonItem = new BeanItem<Politico>(new Politico());
				JanelaPoliticos personEditor = new JanelaPoliticos(newPersonItem);
				personEditor.addListener(new EditorSavedListener() {
					@Override
					public void editorSaved(EditorSavedEvent event) {
						containerPoliticos.addEntity(newPersonItem.getBean());
					}
				});
				UI.getCurrent().addWindow(personEditor);
			}
		});

		deleteButton = new Button("Delete");
		deleteButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				containerPoliticos.removeItem(politcosTable.getValue());
			}
		});
		deleteButton.setEnabled(false);

		editButton = new Button("Edit");
		editButton.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new JanelaPoliticos(politcosTable.getItem(politcosTable.getValue())));
			}
		});
		editButton.setEnabled(false);

		searchField = new TextField();
		searchField.setInputPrompt("Search by name");
		searchField.addTextChangeListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				textFilter = event.getText();
				updateFilters();
			}
		});

		toolbar.addComponent(newButton);
		toolbar.addComponent(deleteButton);
		toolbar.addComponent(editButton);
		toolbar.addComponent(searchField);
		toolbar.setWidth("100%");
		toolbar.setExpandRatio(searchField, 1);
		toolbar.setComponentAlignment(searchField, Alignment.TOP_RIGHT);
		politcosTable.setPageLength(30);
		this.addComponent(toolbar);
		this.addComponent(politcosTable);
		this.setExpandRatio(politcosTable, 1f);
		this.setSizeFull();
	}

	private void updateFilters() {
		containerPoliticos.setApplyFiltersImmediately(false);
		containerPoliticos.removeAllContainerFilters();
		if (textFilter != null && !textFilter.equals("")) {
			Or or = new Or(new Like("nome", textFilter + "%", false), new Like("codinomes", textFilter + "%", false));
			containerPoliticos.addContainerFilter(or);
		}
		containerPoliticos.applyFilters();
	}
}
