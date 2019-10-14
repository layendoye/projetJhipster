package com.projet.jhipster.web.rest;

import com.projet.jhipster.domain.RetraitForm;
import com.projet.jhipster.domain.Transaction;
import com.projet.jhipster.domain.User;
import com.projet.jhipster.service.TransactionService;
import com.projet.jhipster.service.UserService;
import com.projet.jhipster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.projet.jhipster.domain.Transaction}.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {
    @Autowired
    UserService userService;

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private static final String ENTITY_NAME = "transaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * {@code POST  /transactions} : Create a new transaction.
     *
     * @param transaction the transaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transaction, or with status {@code 400 (Bad Request)} if the transaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to save Transaction : {}", transaction);
        if (transaction.getId() != null) {
            throw new BadRequestAlertException("A new transaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        User userExp=userService.getUserWithAuthorities().get();//user connecte
        transaction.setIdUserExp(userExp);
        long frais=(long) (transaction.getMontant()*0.1);
        long unSurCinq=(long) (frais*0.2);
        long commSys=(long) (frais*0.4);
        transaction.setCommExp(unSurCinq);
        transaction.setCommSysteme(commSys);
        transaction.setTaxe(unSurCinq);
        transaction.setDateEnvois(LocalDate.now());
        transaction.setFrais(frais);
        transaction.setStatus("Envoyer");
        SimpleDateFormat format = new SimpleDateFormat("ssmm ddhh yyMM");
        String code=format.format(new Date());
        transaction.setCode(code);
        Transaction result = transactionService.save(transaction);
        return ResponseEntity.created(new URI("/api/transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transactions} : Updates an existing transaction.
     *
     * @param transaction the transaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transaction,
     * or with status {@code 400 (Bad Request)} if the transaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transactions")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) throws URISyntaxException {
        log.debug("REST request to update Transaction : {}", transaction);
        if (transaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transaction result = transactionService.save(transaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transaction.getId().toString()))
            .body(result);
    }

    @PostMapping("/retrait")
    public ResponseEntity<Transaction> retraitTrans(@RequestBody RetraitForm retrait) throws Exception {
        Transaction transaction=transactionService.findTransactionByCode(retrait.getCode()).orElseThrow(
            ()->new Exception("Ce code n'existe pas !!")
        );
        if(!transaction.getIdDest().getNci().equals(retrait.getNciRecp())){
            throw new Exception("Impossible car ce numéro de carte d'identité ne correspond pas à celui du récepteur "+retrait.getNciRecp()+" di "+transaction.getIdDest().getNci());
        }
        if(transaction.getStatus().equals("Retirer")){
            throw new Exception("Impossible code déja retiré !!");
        }
        transaction.setStatus("Retirer");
        transaction.setCommRetireur(transaction.getCommExp());//les 2 prennent 1/5 toute façon
        transaction.dateRetrait(LocalDate.now());
        User userRecp=userService.getUserWithAuthorities().get();//user connecte
        transaction.setIdUserRetir(userRecp);
        Transaction result = transactionService.save(transaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(Pageable pageable) {
        log.debug("REST request to get a page of Transactions");
        //Page<Transaction> page = transactionService.findAll(pageable);
        Page<Transaction> page = transactionService.findMesTransaction(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);//findMesTransaction
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transaction.
     *
     * @param id the id of the transaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
        Optional<Transaction> transaction = transactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transaction);
    }

    /**
     * {@code DELETE  /transactions/:id} : delete the "id" transaction.
     *
     * @param id the id of the transaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        log.debug("REST request to delete Transaction : {}", id);
        transactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
