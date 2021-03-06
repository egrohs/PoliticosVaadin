package com.ui;

import com.modelo.Politico;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Viewer;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Not;
import com.vaadin.data.util.filter.Or;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class FotosUI extends TabSheet implements Viewer {
	private JPAContainer<Politico> containerPoliticos;
	private String textFilter;
	// private TextField tf;

	public FotosUI() {
		// super(10, 10);
		// containerPoliticos = new BeanItemContainer<Politico>(Politico.class);
		containerPoliticos = JPAContainerFactory.make(Politico.class, MainUI.PERSISTENCE_UNIT);
		containerPoliticos.setEntityProvider(new CachingLocalEntityProvider<Politico>(Politico.class,
				JPAContainerFactory.createEntityManagerForPersistenceUnit(MainUI.PERSISTENCE_UNIT)));
		// containerPoliticos.setParentProperty("parent");

		montaVisao();
	}

	private void montaVisao() {
		this.removeAllComponents();
		updateFilters();
		this.setContainerDataSource(containerPoliticos);

		// tf = new TextField("texto");
		// this.addComponent(tf);
		// tf.addTextChangeListener(new TextChangeListener() {
		// @Override
		// public void textChange(TextChangeEvent event) {
		// System.err.println("clicou filtrar");
		// // containerPoliticos.addEntity(beanItem.getBean());
		// textFilter = tf.getValue();
		// updateFilters();
		// }
		// });

		// TabSheet tabsheet = new TabSheet();
		// this.addTab(montaTabImagens(), "Imagens");
		// this.addTab(new PainelPrincipal(), "Tabela");
		Component panelImagens = montaTabImagensAdaptavel();
		VerticalLayout panel = new VerticalLayout(panelImagens);
		this.addTab(panel, "Imagens");
		this.addTab(new PainelPrincipal(), "Tabela");
		panel.setComponentAlignment(panelImagens, Alignment.TOP_CENTER);

		// this.addComponent(, 2, 2);
		// this.addComponent(tabsheet);
	}

	private void updateFilters() {
		containerPoliticos.setApplyFiltersImmediately(false);
		containerPoliticos.removeAllContainerFilters();
		containerPoliticos.addContainerFilter(
				new And(new Not[] { new Not(new IsNull("curriculo")), new Not(new Like("curriculo", "")) }));
		containerPoliticos
				.addContainerFilter(new And(new Not[] { new Not(new IsNull("foto")), new Not(new Like("foto", "")) }));
		if (textFilter != null && !textFilter.equals("")) {
			Or or = new Or(new Like("nome", textFilter + "%", false),
					new Like("codinomes", textFilter + "%", false));
			containerPoliticos.addContainerFilter(or);
		}
		containerPoliticos.sort(new Object[] { "rank" }, new boolean[] { false });
		containerPoliticos.applyFilters();
		// montaVisao();

		System.err.println(containerPoliticos.getItemIds().size());
	}

	@Override
	public void setContainerDataSource(Container newDataSource) {
		containerPoliticos = (JPAContainer<Politico>) newDataSource;
	}

	private Component montaTabImagens() {
		GridLayout gl = new GridLayout(16, 6);

		// FileResource resource = new FileResource(new File("walter.jpg"));
		int i = 0;
		for (Object o : getContainerDataSource().getItemIds()) {
			if (i > 40) {
				break;
			}
			// for(int i=0;i<10;i++){
			// newDataSource.getItem
			Politico p = (Politico) ((EntityItem) getContainerDataSource().getItem(o)).getEntity();

			// if (p.getFoto() != null) {
			i++;
			//TODO forçar fotos com mesmo tamanho, mesmo distorcendo
			Image image = new Image(p.getCodinomes(), new ExternalResource(p.getFoto()));
			// image.addStyleName(".v-image {border: double;}");
			image.addClickListener(new MouseEvents.ClickListener() {
				@Override
				public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
					// TODO Auto-generated method stub
					System.err.println(event.getComponent());
					// System.err.println();
					Window subWindow = new Window(((Image) event.getSource()).getCaption());
					subWindow.setWidth("50%");
					subWindow.setHeight("50%");
					subWindow.center();
					MainUI.getInstancia().addWindow(subWindow);
				}
			});
			// pictureButton.setStyleName("Button.STYLE_LINK");
			gl.addComponent(image);
			gl.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
			// }
		}
		return gl;
	}

	@Override
	public Container getContainerDataSource() {
		return containerPoliticos;
	}

	private Component montaTabImagensAdaptavel() {
		AbstractLayout layout = new CssLayout();
		layout.setSizeFull();

		int i = 0;
		for (Object o : getContainerDataSource().getItemIds()) {
			if (i > 40) {
				break;
			}
			Politico p = (Politico) ((EntityItem) getContainerDataSource().getItem(o)).getEntity();

			// if (p.getFoto() != null) {
			i++;
			Image image = new Image(null, new ExternalResource(p.getFoto()));
			Panel panel = new Panel(p.getCodinomes(), image);
			panel.setWidth(138, Sizeable.UNITS_PIXELS);
			panel.setStyleName("imagem");
			layout.addComponent(panel);
			// }
		}
		return layout;
	}
}
