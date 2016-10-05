package com.ui;

import com.modelo.Partido;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;

public class ContainerJPAPartido extends JPAContainer<Partido> {

    public ContainerJPAPartido() {
        super(Partido.class);
        setEntityProvider(new CachingLocalEntityProvider<Partido>(
                Partido.class,
                JPAContainerFactory
                        .createEntityManagerForPersistenceUnit(MainUI.PERSISTENCE_UNIT)));
        setParentProperty("parent");
    }

//    @Override
//    public boolean areChildrenAllowed(Object itemId) {
//        return super.areChildrenAllowed(itemId)
//                && getItem(itemId).getEntity().isSuperPartido();
//    }
}
