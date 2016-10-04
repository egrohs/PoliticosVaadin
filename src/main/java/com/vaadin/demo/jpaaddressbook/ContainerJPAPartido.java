package com.vaadin.demo.jpaaddressbook;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.demo.jpaaddressbook.domain.Partido;

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
