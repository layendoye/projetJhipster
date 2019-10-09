package com.projet.jhipster.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public abstract class Transaction_ {

	public static volatile SingularAttribute<Transaction, String> code;
	public static volatile SingularAttribute<Transaction, LocalDate> dateEnvois;
	public static volatile SingularAttribute<Transaction, Long> montant;
	public static volatile SingularAttribute<Transaction, User> idUserRetir;
	public static volatile SingularAttribute<Transaction, Long> taxe;
	public static volatile SingularAttribute<Transaction, Long> frais;
	public static volatile SingularAttribute<Transaction, Long> commRetireur;
	public static volatile SingularAttribute<Transaction, User> idUserExp;
	public static volatile SingularAttribute<Transaction, Client> idDest;
	public static volatile SingularAttribute<Transaction, Long> commExp;
	public static volatile SingularAttribute<Transaction, Client> idExp;
	public static volatile SingularAttribute<Transaction, Long> id;
	public static volatile SingularAttribute<Transaction, Long> commSysteme;
	public static volatile SingularAttribute<Transaction, LocalDate> dateRetrait;
	public static volatile SingularAttribute<Transaction, String> status;

	public static final String CODE = "code";
	public static final String DATE_ENVOIS = "dateEnvois";
	public static final String MONTANT = "montant";
	public static final String ID_USER_RETIR = "idUserRetir";
	public static final String TAXE = "taxe";
	public static final String FRAIS = "frais";
	public static final String COMM_RETIREUR = "commRetireur";
	public static final String ID_USER_EXP = "idUserExp";
	public static final String ID_DEST = "idDest";
	public static final String COMM_EXP = "commExp";
	public static final String ID_EXP = "idExp";
	public static final String ID = "id";
	public static final String COMM_SYSTEME = "commSysteme";
	public static final String DATE_RETRAIT = "dateRetrait";
	public static final String STATUS = "status";

}

