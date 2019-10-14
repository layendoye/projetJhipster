package com.projet.jhipster.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Client.class)
public abstract class Client_ {

	public static volatile SingularAttribute<Client, String> nci;
	public static volatile SingularAttribute<Client, String> telephone;
	public static volatile SingularAttribute<Client, Long> id;
	public static volatile SingularAttribute<Client, String> nom;

	public static final String NCI = "nci";
	public static final String TELEPHONE = "telephone";
	public static final String ID = "id";
	public static final String NOM = "nom";

}

