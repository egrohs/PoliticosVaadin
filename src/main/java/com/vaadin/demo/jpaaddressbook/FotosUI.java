package com.vaadin.demo.jpaaddressbook;

import java.io.File;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Viewer;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.demo.jpaaddressbook.domain.Politico;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TextField;

public class FotosUI extends GridLayout implements Viewer {
	private JPAContainer<Politico> containerPoliticos;
	private String textFilter;
	private TextField tf;

	public FotosUI() {
		super(10, 10);
		// containerPoliticos = new BeanItemContainer<Politico>(Politico.class);
		containerPoliticos = JPAContainerFactory.make(Politico.class, MainUI.PERSISTENCE_UNIT);
		containerPoliticos.setEntityProvider(new CachingLocalEntityProvider<Politico>(Politico.class,
				JPAContainerFactory.createEntityManagerForPersistenceUnit(MainUI.PERSISTENCE_UNIT)));
		// containerPoliticos.setParentProperty("parent");

		montaVisao();
	}

	private void montaVisao() {
		this.removeAllComponents();
		this.setContainerDataSource(containerPoliticos);
		tf = new TextField("cunha");
		this.addComponent(tf);
		Button b = new Button("Filtrar");
		this.addComponent(b);
		b.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.err.println("clicou filtrar");
				// containerPoliticos.addEntity(beanItem.getBean());
				textFilter = tf.getValue();
				updateFilters();
			}
		});
		// this.addComponent(, 2, 2);
	}

	private void updateFilters() {
		containerPoliticos.setApplyFiltersImmediately(false);
		containerPoliticos.removeAllContainerFilters();
		// containerPoliticos.addContainerFilter(new Equal("department",
		// departmentFilter));
		if (textFilter != null && !textFilter.equals("")) {
			Or or = new Or(new Like("firstName", textFilter + "%", false),
					new Like("lastName", textFilter + "%", false));
			containerPoliticos.addContainerFilter(or);
		}
		containerPoliticos.applyFilters();
		montaVisao();

		System.err.println(containerPoliticos.getItemIds().size());
	}

	@Override
	public void setContainerDataSource(Container newDataSource) {
		FileResource resource = new FileResource(new File("walter.jpg"));
		for (Object o : newDataSource.getItemIds()) {
			Politico p = (Politico) ((EntityItem) newDataSource.getItem(o)).getEntity();

			// Image image = new Image(p.getLastName(), resource);
			Button pictureButton = new Button(p.getLastName());
			// pictureButton.setStyleName("Button.STYLE_LINK");
			pictureButton.setIcon(resource);// new
											// ExternalResource("http://vaadin.com/image/user_male_portrait?img_id=44268&t=1251193981449"));
			pictureButton.setSizeUndefined();
			this.addComponent(pictureButton);
			pictureButton.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					// NotificationUtil.showNotification("CLICK!", "");
					System.err.println("CLICOU");
				}
			});
		}
	}

	@Override
	public Container getContainerDataSource() {
		return containerPoliticos;
	}
}
