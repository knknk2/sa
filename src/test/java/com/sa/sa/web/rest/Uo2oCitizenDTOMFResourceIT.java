package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Uo2oCitizenDTOMF;
import com.sa.sa.domain.Uo2oPassportDTOMF;
import com.sa.sa.repository.Uo2oCitizenDTOMFRepository;
import com.sa.sa.repository.search.Uo2oCitizenDTOMFSearchRepository;
import com.sa.sa.service.Uo2oCitizenDTOMFService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.Uo2oCitizenDTOMFCriteria;
import com.sa.sa.service.Uo2oCitizenDTOMFQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.sa.sa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Uo2oCitizenDTOMFResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Uo2oCitizenDTOMFResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Uo2oCitizenDTOMFRepository uo2oCitizenDTOMFRepository;

    @Autowired
    private Uo2oCitizenDTOMFService uo2oCitizenDTOMFService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Uo2oCitizenDTOMFSearchRepositoryMockConfiguration
     */
    @Autowired
    private Uo2oCitizenDTOMFSearchRepository mockUo2oCitizenDTOMFSearchRepository;

    @Autowired
    private Uo2oCitizenDTOMFQueryService uo2oCitizenDTOMFQueryService;

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

    private MockMvc restUo2oCitizenDTOMFMockMvc;

    private Uo2oCitizenDTOMF uo2oCitizenDTOMF;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Uo2oCitizenDTOMFResource uo2oCitizenDTOMFResource = new Uo2oCitizenDTOMFResource(uo2oCitizenDTOMFService, uo2oCitizenDTOMFQueryService);
        this.restUo2oCitizenDTOMFMockMvc = MockMvcBuilders.standaloneSetup(uo2oCitizenDTOMFResource)
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
    public static Uo2oCitizenDTOMF createEntity(EntityManager em) {
        Uo2oCitizenDTOMF uo2oCitizenDTOMF = new Uo2oCitizenDTOMF()
            .name(DEFAULT_NAME);
        // Add required entity
        Uo2oPassportDTOMF uo2oPassportDTOMF;
        if (TestUtil.findAll(em, Uo2oPassportDTOMF.class).isEmpty()) {
            uo2oPassportDTOMF = Uo2oPassportDTOMFResourceIT.createEntity(em);
            em.persist(uo2oPassportDTOMF);
            em.flush();
        } else {
            uo2oPassportDTOMF = TestUtil.findAll(em, Uo2oPassportDTOMF.class).get(0);
        }
        uo2oCitizenDTOMF.setUo2oPassportDTOMF(uo2oPassportDTOMF);
        return uo2oCitizenDTOMF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uo2oCitizenDTOMF createUpdatedEntity(EntityManager em) {
        Uo2oCitizenDTOMF uo2oCitizenDTOMF = new Uo2oCitizenDTOMF()
            .name(UPDATED_NAME);
        // Add required entity
        Uo2oPassportDTOMF uo2oPassportDTOMF;
        if (TestUtil.findAll(em, Uo2oPassportDTOMF.class).isEmpty()) {
            uo2oPassportDTOMF = Uo2oPassportDTOMFResourceIT.createUpdatedEntity(em);
            em.persist(uo2oPassportDTOMF);
            em.flush();
        } else {
            uo2oPassportDTOMF = TestUtil.findAll(em, Uo2oPassportDTOMF.class).get(0);
        }
        uo2oCitizenDTOMF.setUo2oPassportDTOMF(uo2oPassportDTOMF);
        return uo2oCitizenDTOMF;
    }

    @BeforeEach
    public void initTest() {
        uo2oCitizenDTOMF = createEntity(em);
    }

    @Test
    @Transactional
    public void createUo2oCitizenDTOMF() throws Exception {
        int databaseSizeBeforeCreate = uo2oCitizenDTOMFRepository.findAll().size();

        // Create the Uo2oCitizenDTOMF
        restUo2oCitizenDTOMFMockMvc.perform(post("/api/uo-2-o-citizen-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizenDTOMF)))
            .andExpect(status().isCreated());

        // Validate the Uo2oCitizenDTOMF in the database
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeCreate + 1);
        Uo2oCitizenDTOMF testUo2oCitizenDTOMF = uo2oCitizenDTOMFList.get(uo2oCitizenDTOMFList.size() - 1);
        assertThat(testUo2oCitizenDTOMF.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUo2oCitizenDTOMF.getId()).isEqualTo(testUo2oCitizenDTOMF.getUo2oPassportDTOMF().getId());

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(1)).save(testUo2oCitizenDTOMF);
    }

    @Test
    @Transactional
    public void createUo2oCitizenDTOMFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uo2oCitizenDTOMFRepository.findAll().size();

        // Create the Uo2oCitizenDTOMF with an existing ID
        uo2oCitizenDTOMF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUo2oCitizenDTOMFMockMvc.perform(post("/api/uo-2-o-citizen-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizenDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oCitizenDTOMF in the database
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeCreate);

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(0)).save(uo2oCitizenDTOMF);
    }

    @Test
    @Transactional
    public void updateUo2oCitizenDTOMFMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);
        int databaseSizeBeforeCreate = uo2oCitizenDTOMFRepository.findAll().size();

        // Add a new parent entity
        Uo2oPassportDTOMF uo2oPassportDTOMF = Uo2oPassportDTOMFResourceIT.createUpdatedEntity(em);
        em.persist(uo2oPassportDTOMF);
        em.flush();

        // Load the uo2oCitizenDTOMF
        Uo2oCitizenDTOMF updatedUo2oCitizenDTOMF = uo2oCitizenDTOMFRepository.findById(uo2oCitizenDTOMF.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oCitizenDTOMF are not directly saved in db
        em.detach(updatedUo2oCitizenDTOMF);

        // Update the Uo2oPassportDTOMF with new association value
        updatedUo2oCitizenDTOMF.setUo2oPassportDTOMF(uo2oPassportDTOMF);

        // Update the entity
        restUo2oCitizenDTOMFMockMvc.perform(put("/api/uo-2-o-citizen-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oCitizenDTOMF)))
            .andExpect(status().isOk());

        // Validate the Uo2oCitizenDTOMF in the database
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeCreate);
        Uo2oCitizenDTOMF testUo2oCitizenDTOMF = uo2oCitizenDTOMFList.get(uo2oCitizenDTOMFList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUo2oCitizenDTOMF.getId()).isEqualTo(testUo2oCitizenDTOMF.getUo2oPassportDTOMF().getId());

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(2)).save(uo2oCitizenDTOMF);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFS() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oCitizenDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUo2oCitizenDTOMF() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get the uo2oCitizenDTOMF
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs/{id}", uo2oCitizenDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uo2oCitizenDTOMF.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name equals to DEFAULT_NAME
        defaultUo2oCitizenDTOMFShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uo2oCitizenDTOMFList where name equals to UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name not equals to DEFAULT_NAME
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uo2oCitizenDTOMFList where name not equals to UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uo2oCitizenDTOMFList where name equals to UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name is not null
        defaultUo2oCitizenDTOMFShouldBeFound("name.specified=true");

        // Get all the uo2oCitizenDTOMFList where name is null
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameContainsSomething() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name contains DEFAULT_NAME
        defaultUo2oCitizenDTOMFShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uo2oCitizenDTOMFList where name contains UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);

        // Get all the uo2oCitizenDTOMFList where name does not contain DEFAULT_NAME
        defaultUo2oCitizenDTOMFShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uo2oCitizenDTOMFList where name does not contain UPDATED_NAME
        defaultUo2oCitizenDTOMFShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUo2oCitizenDTOMFSByUo2oPassportDTOMFIsEqualToSomething() throws Exception {
        // Get already existing entity
        Uo2oPassportDTOMF uo2oPassportDTOMF = uo2oCitizenDTOMF.getUo2oPassportDTOMF();
        uo2oCitizenDTOMFRepository.saveAndFlush(uo2oCitizenDTOMF);
        Long uo2oPassportDTOMFId = uo2oPassportDTOMF.getId();

        // Get all the uo2oCitizenDTOMFList where uo2oPassportDTOMF equals to uo2oPassportDTOMFId
        defaultUo2oCitizenDTOMFShouldBeFound("uo2oPassportDTOMFId.equals=" + uo2oPassportDTOMFId);

        // Get all the uo2oCitizenDTOMFList where uo2oPassportDTOMF equals to uo2oPassportDTOMFId + 1
        defaultUo2oCitizenDTOMFShouldNotBeFound("uo2oPassportDTOMFId.equals=" + (uo2oPassportDTOMFId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUo2oCitizenDTOMFShouldBeFound(String filter) throws Exception {
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oCitizenDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUo2oCitizenDTOMFShouldNotBeFound(String filter) throws Exception {
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUo2oCitizenDTOMF() throws Exception {
        // Get the uo2oCitizenDTOMF
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/uo-2-o-citizen-dtomfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUo2oCitizenDTOMF() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUo2oCitizenDTOMFSearchRepository);

        int databaseSizeBeforeUpdate = uo2oCitizenDTOMFRepository.findAll().size();

        // Update the uo2oCitizenDTOMF
        Uo2oCitizenDTOMF updatedUo2oCitizenDTOMF = uo2oCitizenDTOMFRepository.findById(uo2oCitizenDTOMF.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oCitizenDTOMF are not directly saved in db
        em.detach(updatedUo2oCitizenDTOMF);
        updatedUo2oCitizenDTOMF
            .name(UPDATED_NAME);

        restUo2oCitizenDTOMFMockMvc.perform(put("/api/uo-2-o-citizen-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oCitizenDTOMF)))
            .andExpect(status().isOk());

        // Validate the Uo2oCitizenDTOMF in the database
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeUpdate);
        Uo2oCitizenDTOMF testUo2oCitizenDTOMF = uo2oCitizenDTOMFList.get(uo2oCitizenDTOMFList.size() - 1);
        assertThat(testUo2oCitizenDTOMF.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(1)).save(testUo2oCitizenDTOMF);
    }

    @Test
    @Transactional
    public void updateNonExistingUo2oCitizenDTOMF() throws Exception {
        int databaseSizeBeforeUpdate = uo2oCitizenDTOMFRepository.findAll().size();

        // Create the Uo2oCitizenDTOMF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUo2oCitizenDTOMFMockMvc.perform(put("/api/uo-2-o-citizen-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizenDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oCitizenDTOMF in the database
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(0)).save(uo2oCitizenDTOMF);
    }

    @Test
    @Transactional
    public void deleteUo2oCitizenDTOMF() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);

        int databaseSizeBeforeDelete = uo2oCitizenDTOMFRepository.findAll().size();

        // Delete the uo2oCitizenDTOMF
        restUo2oCitizenDTOMFMockMvc.perform(delete("/api/uo-2-o-citizen-dtomfs/{id}", uo2oCitizenDTOMF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uo2oCitizenDTOMF> uo2oCitizenDTOMFList = uo2oCitizenDTOMFRepository.findAll();
        assertThat(uo2oCitizenDTOMFList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Uo2oCitizenDTOMF in Elasticsearch
        verify(mockUo2oCitizenDTOMFSearchRepository, times(1)).deleteById(uo2oCitizenDTOMF.getId());
    }

    @Test
    @Transactional
    public void searchUo2oCitizenDTOMF() throws Exception {
        // Initialize the database
        uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);
        when(mockUo2oCitizenDTOMFSearchRepository.search(queryStringQuery("id:" + uo2oCitizenDTOMF.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uo2oCitizenDTOMF), PageRequest.of(0, 1), 1));
        // Search the uo2oCitizenDTOMF
        restUo2oCitizenDTOMFMockMvc.perform(get("/api/_search/uo-2-o-citizen-dtomfs?query=id:" + uo2oCitizenDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oCitizenDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uo2oCitizenDTOMF.class);
        Uo2oCitizenDTOMF uo2oCitizenDTOMF1 = new Uo2oCitizenDTOMF();
        uo2oCitizenDTOMF1.setId(1L);
        Uo2oCitizenDTOMF uo2oCitizenDTOMF2 = new Uo2oCitizenDTOMF();
        uo2oCitizenDTOMF2.setId(uo2oCitizenDTOMF1.getId());
        assertThat(uo2oCitizenDTOMF1).isEqualTo(uo2oCitizenDTOMF2);
        uo2oCitizenDTOMF2.setId(2L);
        assertThat(uo2oCitizenDTOMF1).isNotEqualTo(uo2oCitizenDTOMF2);
        uo2oCitizenDTOMF1.setId(null);
        assertThat(uo2oCitizenDTOMF1).isNotEqualTo(uo2oCitizenDTOMF2);
    }
}
