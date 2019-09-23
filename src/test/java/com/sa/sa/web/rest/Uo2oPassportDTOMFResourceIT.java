package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Uo2oPassportDTOMF;
import com.sa.sa.repository.Uo2oPassportDTOMFRepository;
import com.sa.sa.repository.search.Uo2oPassportDTOMFSearchRepository;
import com.sa.sa.service.Uo2oPassportDTOMFService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.Uo2oPassportDTOMFCriteria;
import com.sa.sa.service.Uo2oPassportDTOMFQueryService;

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
 * Integration tests for the {@link Uo2oPassportDTOMFResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Uo2oPassportDTOMFResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository;

    @Autowired
    private Uo2oPassportDTOMFService uo2oPassportDTOMFService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Uo2oPassportDTOMFSearchRepositoryMockConfiguration
     */
    @Autowired
    private Uo2oPassportDTOMFSearchRepository mockUo2oPassportDTOMFSearchRepository;

    @Autowired
    private Uo2oPassportDTOMFQueryService uo2oPassportDTOMFQueryService;

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

    private MockMvc restUo2oPassportDTOMFMockMvc;

    private Uo2oPassportDTOMF uo2oPassportDTOMF;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Uo2oPassportDTOMFResource uo2oPassportDTOMFResource = new Uo2oPassportDTOMFResource(uo2oPassportDTOMFService, uo2oPassportDTOMFQueryService);
        this.restUo2oPassportDTOMFMockMvc = MockMvcBuilders.standaloneSetup(uo2oPassportDTOMFResource)
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
    public static Uo2oPassportDTOMF createEntity(EntityManager em) {
        Uo2oPassportDTOMF uo2oPassportDTOMF = new Uo2oPassportDTOMF()
            .name(DEFAULT_NAME);
        return uo2oPassportDTOMF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uo2oPassportDTOMF createUpdatedEntity(EntityManager em) {
        Uo2oPassportDTOMF uo2oPassportDTOMF = new Uo2oPassportDTOMF()
            .name(UPDATED_NAME);
        return uo2oPassportDTOMF;
    }

    @BeforeEach
    public void initTest() {
        uo2oPassportDTOMF = createEntity(em);
    }

    @Test
    @Transactional
    public void createUo2oPassportDTOMF() throws Exception {
        int databaseSizeBeforeCreate = uo2oPassportDTOMFRepository.findAll().size();

        // Create the Uo2oPassportDTOMF
        restUo2oPassportDTOMFMockMvc.perform(post("/api/uo-2-o-passport-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassportDTOMF)))
            .andExpect(status().isCreated());

        // Validate the Uo2oPassportDTOMF in the database
        List<Uo2oPassportDTOMF> uo2oPassportDTOMFList = uo2oPassportDTOMFRepository.findAll();
        assertThat(uo2oPassportDTOMFList).hasSize(databaseSizeBeforeCreate + 1);
        Uo2oPassportDTOMF testUo2oPassportDTOMF = uo2oPassportDTOMFList.get(uo2oPassportDTOMFList.size() - 1);
        assertThat(testUo2oPassportDTOMF.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Uo2oPassportDTOMF in Elasticsearch
        verify(mockUo2oPassportDTOMFSearchRepository, times(1)).save(testUo2oPassportDTOMF);
    }

    @Test
    @Transactional
    public void createUo2oPassportDTOMFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uo2oPassportDTOMFRepository.findAll().size();

        // Create the Uo2oPassportDTOMF with an existing ID
        uo2oPassportDTOMF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUo2oPassportDTOMFMockMvc.perform(post("/api/uo-2-o-passport-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassportDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oPassportDTOMF in the database
        List<Uo2oPassportDTOMF> uo2oPassportDTOMFList = uo2oPassportDTOMFRepository.findAll();
        assertThat(uo2oPassportDTOMFList).hasSize(databaseSizeBeforeCreate);

        // Validate the Uo2oPassportDTOMF in Elasticsearch
        verify(mockUo2oPassportDTOMFSearchRepository, times(0)).save(uo2oPassportDTOMF);
    }


    @Test
    @Transactional
    public void getAllUo2oPassportDTOMFS() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFRepository.saveAndFlush(uo2oPassportDTOMF);

        // Get all the uo2oPassportDTOMFList
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oPassportDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getUo2oPassportDTOMF() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFRepository.saveAndFlush(uo2oPassportDTOMF);

        // Get the uo2oPassportDTOMF
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs/{id}", uo2oPassportDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uo2oPassportDTOMF.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllUo2oPassportDTOMFSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFRepository.saveAndFlush(uo2oPassportDTOMF);

        // Get all the uo2oPassportDTOMFList where name equals to DEFAULT_NAME
        defaultUo2oPassportDTOMFShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uo2oPassportDTOMFList where name equals to UPDATED_NAME
        defaultUo2oPassportDTOMFShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oPassportDTOMFSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFRepository.saveAndFlush(uo2oPassportDTOMF);

        // Get all the uo2oPassportDTOMFList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUo2oPassportDTOMFShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uo2oPassportDTOMFList where name equals to UPDATED_NAME
        defaultUo2oPassportDTOMFShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUo2oPassportDTOMFSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFRepository.saveAndFlush(uo2oPassportDTOMF);

        // Get all the uo2oPassportDTOMFList where name is not null
        defaultUo2oPassportDTOMFShouldBeFound("name.specified=true");

        // Get all the uo2oPassportDTOMFList where name is null
        defaultUo2oPassportDTOMFShouldNotBeFound("name.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUo2oPassportDTOMFShouldBeFound(String filter) throws Exception {
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oPassportDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUo2oPassportDTOMFShouldNotBeFound(String filter) throws Exception {
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUo2oPassportDTOMF() throws Exception {
        // Get the uo2oPassportDTOMF
        restUo2oPassportDTOMFMockMvc.perform(get("/api/uo-2-o-passport-dtomfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUo2oPassportDTOMF() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFService.save(uo2oPassportDTOMF);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUo2oPassportDTOMFSearchRepository);

        int databaseSizeBeforeUpdate = uo2oPassportDTOMFRepository.findAll().size();

        // Update the uo2oPassportDTOMF
        Uo2oPassportDTOMF updatedUo2oPassportDTOMF = uo2oPassportDTOMFRepository.findById(uo2oPassportDTOMF.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oPassportDTOMF are not directly saved in db
        em.detach(updatedUo2oPassportDTOMF);
        updatedUo2oPassportDTOMF
            .name(UPDATED_NAME);

        restUo2oPassportDTOMFMockMvc.perform(put("/api/uo-2-o-passport-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oPassportDTOMF)))
            .andExpect(status().isOk());

        // Validate the Uo2oPassportDTOMF in the database
        List<Uo2oPassportDTOMF> uo2oPassportDTOMFList = uo2oPassportDTOMFRepository.findAll();
        assertThat(uo2oPassportDTOMFList).hasSize(databaseSizeBeforeUpdate);
        Uo2oPassportDTOMF testUo2oPassportDTOMF = uo2oPassportDTOMFList.get(uo2oPassportDTOMFList.size() - 1);
        assertThat(testUo2oPassportDTOMF.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Uo2oPassportDTOMF in Elasticsearch
        verify(mockUo2oPassportDTOMFSearchRepository, times(1)).save(testUo2oPassportDTOMF);
    }

    @Test
    @Transactional
    public void updateNonExistingUo2oPassportDTOMF() throws Exception {
        int databaseSizeBeforeUpdate = uo2oPassportDTOMFRepository.findAll().size();

        // Create the Uo2oPassportDTOMF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUo2oPassportDTOMFMockMvc.perform(put("/api/uo-2-o-passport-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassportDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oPassportDTOMF in the database
        List<Uo2oPassportDTOMF> uo2oPassportDTOMFList = uo2oPassportDTOMFRepository.findAll();
        assertThat(uo2oPassportDTOMFList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Uo2oPassportDTOMF in Elasticsearch
        verify(mockUo2oPassportDTOMFSearchRepository, times(0)).save(uo2oPassportDTOMF);
    }

    @Test
    @Transactional
    public void deleteUo2oPassportDTOMF() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFService.save(uo2oPassportDTOMF);

        int databaseSizeBeforeDelete = uo2oPassportDTOMFRepository.findAll().size();

        // Delete the uo2oPassportDTOMF
        restUo2oPassportDTOMFMockMvc.perform(delete("/api/uo-2-o-passport-dtomfs/{id}", uo2oPassportDTOMF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uo2oPassportDTOMF> uo2oPassportDTOMFList = uo2oPassportDTOMFRepository.findAll();
        assertThat(uo2oPassportDTOMFList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Uo2oPassportDTOMF in Elasticsearch
        verify(mockUo2oPassportDTOMFSearchRepository, times(1)).deleteById(uo2oPassportDTOMF.getId());
    }

    @Test
    @Transactional
    public void searchUo2oPassportDTOMF() throws Exception {
        // Initialize the database
        uo2oPassportDTOMFService.save(uo2oPassportDTOMF);
        when(mockUo2oPassportDTOMFSearchRepository.search(queryStringQuery("id:" + uo2oPassportDTOMF.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uo2oPassportDTOMF), PageRequest.of(0, 1), 1));
        // Search the uo2oPassportDTOMF
        restUo2oPassportDTOMFMockMvc.perform(get("/api/_search/uo-2-o-passport-dtomfs?query=id:" + uo2oPassportDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oPassportDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uo2oPassportDTOMF.class);
        Uo2oPassportDTOMF uo2oPassportDTOMF1 = new Uo2oPassportDTOMF();
        uo2oPassportDTOMF1.setId(1L);
        Uo2oPassportDTOMF uo2oPassportDTOMF2 = new Uo2oPassportDTOMF();
        uo2oPassportDTOMF2.setId(uo2oPassportDTOMF1.getId());
        assertThat(uo2oPassportDTOMF1).isEqualTo(uo2oPassportDTOMF2);
        uo2oPassportDTOMF2.setId(2L);
        assertThat(uo2oPassportDTOMF1).isNotEqualTo(uo2oPassportDTOMF2);
        uo2oPassportDTOMF1.setId(null);
        assertThat(uo2oPassportDTOMF1).isNotEqualTo(uo2oPassportDTOMF2);
    }
}
