package com.vaadin.demo.jpaaddressbook.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "partidos")
    private Set<Politico> politicos;

    @Transient
    private Boolean superPartido;

    @ManyToOne
    private Partido parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Politico> getPoliticos() {
        return politicos;
    }

    public void setPersons(Set<Politico> persons) {
        this.politicos = persons;
    }

    public Partido getParent() {
        return parent;
    }

    public void setParent(Partido parent) {
        this.parent = parent;
    }

    public boolean isSuperPartido() {
        if (superPartido == null) {
            superPartido = getPoliticos().size() == 0;
        }
        return superPartido;
    }

    @Transient
    public String getHierarchicalName() {
        if (parent != null) {
            return parent.toString() + " : " + name;
        }
        return name;
    }

    @Override
    public String toString() {
        return getHierarchicalName();
    }
}
