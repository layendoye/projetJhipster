package com.projet.jhipster.web.rest;

import com.projet.jhipster.ProjetApp;
import com.projet.jhipster.domain.Transaction;
import com.projet.jhipster.repository.TransactionRepository;
import com.projet.jhipster.service.TransactionService;
import com.projet.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.projet.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = ProjetApp.class)
public class TransactionResourceIT {

    private static final LocalDate DEFAULT_DATE_ENVOIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENVOIS = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_ENVOIS = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_MONTANT = 1L;
    private static final Long UPDATED_MONTANT = 2L;
    private static final Long SMALLER_MONTANT = 1L - 1L;

    private static final LocalDate DEFAULT_DATE_RETRAIT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETRAIT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_RETRAIT = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_FRAIS = 1L;
    private static final Long UPDATED_FRAIS = 2L;
    private static final Long SMALLER_FRAIS = 1L - 1L;

    private static final Long DEFAULT_COMM_SYSTEME = 1L;
    private static final Long UPDATED_COMM_SYSTEME = 2L;
    private static final Long SMALLER_COMM_SYSTEME = 1L - 1L;

    private static final Long DEFAULT_COMM_EXP = 1L;
    private static final Long UPDATED_COMM_EXP = 2L;
    private static final Long SMALLER_COMM_EXP = 1L - 1L;

    private static final Long DEFAULT_COMM_RETIREUR = 1L;
    private static final Long UPDATED_COMM_RETIREUR = 2L;
    private static final Long SMALLER_COMM_RETIREUR = 1L - 1L;

    private static final Long DEFAULT_TAXE = 1L;
    private static final Long UPDATED_TAXE = 2L;
    private static final Long SMALLER_TAXE = 1L - 1L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionResource transactionResource = new TransactionResource(transactionService);
        this.restTransactionMockMvc = MockMvcBuilders.standaloneSetup(transactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .dateEnvois(DEFAULT_DATE_ENVOIS)
            .montant(DEFAULT_MONTANT)
            .dateRetrait(DEFAULT_DATE_RETRAIT)
            .frais(DEFAULT_FRAIS)
            .commSysteme(DEFAULT_COMM_SYSTEME)
            .commExp(DEFAULT_COMM_EXP)
            .commRetireur(DEFAULT_COMM_RETIREUR)
            .taxe(DEFAULT_TAXE)
            .status(DEFAULT_STATUS)
            .code(DEFAULT_CODE);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity(EntityManager em) {
        Transaction transaction = new Transaction()
            .dateEnvois(UPDATED_DATE_ENVOIS)
            .montant(UPDATED_MONTANT)
            .dateRetrait(UPDATED_DATE_RETRAIT)
            .frais(UPDATED_FRAIS)
            .commSysteme(UPDATED_COMM_SYSTEME)
            .commExp(UPDATED_COMM_EXP)
            .commRetireur(UPDATED_COMM_RETIREUR)
            .taxe(UPDATED_TAXE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getDateEnvois()).isEqualTo(DEFAULT_DATE_ENVOIS);
        assertThat(testTransaction.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTransaction.getDateRetrait()).isEqualTo(DEFAULT_DATE_RETRAIT);
        assertThat(testTransaction.getFrais()).isEqualTo(DEFAULT_FRAIS);
        assertThat(testTransaction.getCommSysteme()).isEqualTo(DEFAULT_COMM_SYSTEME);
        assertThat(testTransaction.getCommExp()).isEqualTo(DEFAULT_COMM_EXP);
        assertThat(testTransaction.getCommRetireur()).isEqualTo(DEFAULT_COMM_RETIREUR);
        assertThat(testTransaction.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testTransaction.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransaction.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateEnvois").value(hasItem(DEFAULT_DATE_ENVOIS.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())))
            .andExpect(jsonPath("$.[*].dateRetrait").value(hasItem(DEFAULT_DATE_RETRAIT.toString())))
            .andExpect(jsonPath("$.[*].frais").value(hasItem(DEFAULT_FRAIS.intValue())))
            .andExpect(jsonPath("$.[*].commSysteme").value(hasItem(DEFAULT_COMM_SYSTEME.intValue())))
            .andExpect(jsonPath("$.[*].commExp").value(hasItem(DEFAULT_COMM_EXP.intValue())))
            .andExpect(jsonPath("$.[*].commRetireur").value(hasItem(DEFAULT_COMM_RETIREUR.intValue())))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.saveAndFlush(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId().intValue()))
            .andExpect(jsonPath("$.dateEnvois").value(DEFAULT_DATE_ENVOIS.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()))
            .andExpect(jsonPath("$.dateRetrait").value(DEFAULT_DATE_RETRAIT.toString()))
            .andExpect(jsonPath("$.frais").value(DEFAULT_FRAIS.intValue()))
            .andExpect(jsonPath("$.commSysteme").value(DEFAULT_COMM_SYSTEME.intValue()))
            .andExpect(jsonPath("$.commExp").value(DEFAULT_COMM_EXP.intValue()))
            .andExpect(jsonPath("$.commRetireur").value(DEFAULT_COMM_RETIREUR.intValue()))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        // Disconnect from session so that the updates on updatedTransaction are not directly saved in db
        em.detach(updatedTransaction);
        updatedTransaction
            .dateEnvois(UPDATED_DATE_ENVOIS)
            .montant(UPDATED_MONTANT)
            .dateRetrait(UPDATED_DATE_RETRAIT)
            .frais(UPDATED_FRAIS)
            .commSysteme(UPDATED_COMM_SYSTEME)
            .commExp(UPDATED_COMM_EXP)
            .commRetireur(UPDATED_COMM_RETIREUR)
            .taxe(UPDATED_TAXE)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getDateEnvois()).isEqualTo(UPDATED_DATE_ENVOIS);
        assertThat(testTransaction.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTransaction.getDateRetrait()).isEqualTo(UPDATED_DATE_RETRAIT);
        assertThat(testTransaction.getFrais()).isEqualTo(UPDATED_FRAIS);
        assertThat(testTransaction.getCommSysteme()).isEqualTo(UPDATED_COMM_SYSTEME);
        assertThat(testTransaction.getCommExp()).isEqualTo(UPDATED_COMM_EXP);
        assertThat(testTransaction.getCommRetireur()).isEqualTo(UPDATED_COMM_RETIREUR);
        assertThat(testTransaction.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testTransaction.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransaction.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Create the Transaction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transaction.class);
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        Transaction transaction2 = new Transaction();
        transaction2.setId(transaction1.getId());
        assertThat(transaction1).isEqualTo(transaction2);
        transaction2.setId(2L);
        assertThat(transaction1).isNotEqualTo(transaction2);
        transaction1.setId(null);
        assertThat(transaction1).isNotEqualTo(transaction2);
    }
}
