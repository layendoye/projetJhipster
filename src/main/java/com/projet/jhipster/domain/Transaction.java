package com.projet.jhipster.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_envois")
    private LocalDate dateEnvois;

    @Column(name = "montant")
    private Long montant;

    @Column(name = "date_retrait")
    private LocalDate dateRetrait;

    @Column(name = "frais")
    private Long frais;

    @Column(name = "comm_systeme")
    private Long commSysteme;

    @Column(name = "comm_exp")
    private Long commExp;

    @Column(name = "comm_retireur")
    private Long commRetireur;

    @Column(name = "taxe")
    private Long taxe;

    @Column(name = "status")
    private String status;

    @Column(name = "code")
    private String code;

    @ManyToOne
    private Client idExp;

    @ManyToOne
    private Client idDest;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private User idUserExp;

    @ManyToOne
    @JsonIgnoreProperties("transactions")
    private User idUserRetir;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEnvois() {
        return dateEnvois;
    }

    public Transaction dateEnvois(LocalDate dateEnvois) {
        this.dateEnvois = dateEnvois;
        return this;
    }

    public void setDateEnvois(LocalDate dateEnvois) {
        this.dateEnvois = dateEnvois;
    }

    public Long getMontant() {
        return montant;
    }

    public Transaction montant(Long montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public Transaction dateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
        return this;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public Long getFrais() {
        return frais;
    }

    public Transaction frais(Long frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Long frais) {
        this.frais = frais;
    }

    public Long getCommSysteme() {
        return commSysteme;
    }

    public Transaction commSysteme(Long commSysteme) {
        this.commSysteme = commSysteme;
        return this;
    }

    public void setCommSysteme(Long commSysteme) {
        this.commSysteme = commSysteme;
    }

    public Long getCommExp() {
        return commExp;
    }

    public Transaction commExp(Long commExp) {
        this.commExp = commExp;
        return this;
    }

    public void setCommExp(Long commExp) {
        this.commExp = commExp;
    }

    public Long getCommRetireur() {
        return commRetireur;
    }

    public Transaction commRetireur(Long commRetireur) {
        this.commRetireur = commRetireur;
        return this;
    }

    public void setCommRetireur(Long commRetireur) {
        this.commRetireur = commRetireur;
    }

    public Long getTaxe() {
        return taxe;
    }

    public Transaction taxe(Long taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(Long taxe) {
        this.taxe = taxe;
    }

    public String getStatus() {
        return status;
    }

    public Transaction status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public Transaction code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getIdExp() {
        return idExp;
    }

    public Transaction idExp(Client client) {
        this.idExp = client;
        return this;
    }

    public void setIdExp(Client client) {
        this.idExp = client;
    }

    public Client getIdDest() {
        return idDest;
    }

    public Transaction idDest(Client client) {
        this.idDest = client;
        return this;
    }

    public void setIdDest(Client client) {
        this.idDest = client;
    }

    public User getIdUserExp() {
        return idUserExp;
    }

    public Transaction idUserExp(User user) {
        this.idUserExp = user;
        return this;
    }

    public void setIdUserExp(User user) {
        this.idUserExp = user;
    }

    public User getIdUserRetir() {
        return idUserRetir;
    }

    public Transaction idUserRetir(User user) {
        this.idUserRetir = user;
        return this;
    }

    public void setIdUserRetir(User user) {
        this.idUserRetir = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", dateEnvois='" + getDateEnvois() + "'" +
            ", montant=" + getMontant() +
            ", dateRetrait='" + getDateRetrait() + "'" +
            ", frais=" + getFrais() +
            ", commSysteme=" + getCommSysteme() +
            ", commExp=" + getCommExp() +
            ", commRetireur=" + getCommRetireur() +
            ", taxe=" + getTaxe() +
            ", status='" + getStatus() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }

}
