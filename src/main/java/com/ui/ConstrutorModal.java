package com.ui;

import com.modelo.Partido;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.IsNull;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;

public class ConstrutorModal extends CustomField<Partido> {
    private ComboBox geographicalDepartment = new ComboBox();
    private ComboBox department = new ComboBox();

    private JPAContainer<Partido> container;
    private JPAContainer<Partido> geoContainer;

    public ConstrutorModal() {
        container = JPAContainerFactory.make(Partido.class,
                MainUI.PERSISTENCE_UNIT);
        geoContainer = JPAContainerFactory.make(Partido.class,
                MainUI.PERSISTENCE_UNIT);
        setCaption("Department");
        // Only list "roots" which are in our example geographical super
        // departments
        geoContainer.addContainerFilter(new IsNull("parent"));
        geographicalDepartment.setContainerDataSource(geoContainer);
        geographicalDepartment.setItemCaptionPropertyId("name");
        geographicalDepartment.setImmediate(true);

        container.setApplyFiltersImmediately(false);
        filterDepartments(null);
        department.setContainerDataSource(container);
        department.setItemCaptionPropertyId("name");

        geographicalDepartment.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(
                    com.vaadin.data.Property.ValueChangeEvent event) {
                /*
                 * Modify filtering of the department combobox
                 */
                EntityItem<Partido> item = geoContainer
                        .getItem(geographicalDepartment.getValue());
                Partido entity = item.getEntity();
                filterDepartments(entity);
            }
        });
        department.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(
                    com.vaadin.data.Property.ValueChangeEvent event) {
                /*
                 * Modify the actual value of the custom field.
                 */
                if (department.getValue() == null) {
                    setValue(null, false);
                } else {
                    Partido entity = container
                            .getItem(department.getValue()).getEntity();
                    setValue(entity, false);
                }
            }
        });
    }

    @Override
    protected Component initContent() {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addComponent(geographicalDepartment);
        cssLayout.addComponent(department);
        return cssLayout;
    }

    /**
     * Modify available options based on the "geo department" select.
     * 
     * @param currentGeoDepartment
     */
    private void filterDepartments(Partido currentGeoDepartment) {
        if (currentGeoDepartment == null) {
            department.setValue(null);
            department.setEnabled(false);
        } else {
            container.removeAllContainerFilters();
            container.addContainerFilter(new Equal("parent",
                    currentGeoDepartment));
            container.applyFilters();
            department.setValue(null);
            department.setEnabled(true);
        }
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);
        setDepartment((Partido) newDataSource.getValue());
    }

    @Override
    public void setValue(Partido newValue) throws ReadOnlyException,
            Converter.ConversionException {
        super.setValue(newValue);
        setDepartment(newValue);
    }

    private void setDepartment(Partido department) {
        geographicalDepartment.setValue(department != null ? department
                .getParent().getId() : null);
        this.department
                .setValue(department != null ? department.getId() : null);
    }

    @Override
    public Class<? extends Partido> getType() {
        return Partido.class;
    }

}
