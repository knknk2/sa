package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.M2mDriverDTOMF;
import com.sa.sa.domain.M2mCarDTOMF;
import com.sa.sa.repository.M2mDriverDTOMFRepository;
import com.sa.sa.repository.search.M2mDriverDTOMFSearchRepository;
import com.sa.sa.service.M2mDriverDTOMFService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.M2mDriverDTOMFCriteria;
import com.sa.sa.service.M2mDriverDTOMFQueryService;

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
 * Integration tests for the {@link M2mDriverDTOMFResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class M2mDriverDTOMFResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private M2mDriverDTOMFRepository m2mDriverDTOMFRepository;

    @Autowired
    private M2mDriverDTOMFService m2mDriverDTOMFService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.M2mDriverDTOMFSearchRepositoryMockConfiguration
     */
    @Autowired
    private M2mDriverDTOMFSearchRepository mockM2mDriverDTOMFSearchRepository;

    @Autowired
    private M2mDriverDTOMFQueryService m2mDriverDTOMFQueryService;

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

    private MockMvc restM2mDriverDTOMFMockMvc;

    private M2mDriverDTOMF m2mDriverDTOMF;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final M2mDriverDTOMFResource m2mDriverDTOMFResource = new M2mDriverDTOMFResource(m2mDriverDTOMFService, m2mDriverDTOMFQueryService);
        this.restM2mDriverDTOMFMockMvc = MockMvcBuilders.standaloneSetup(m2mDriverDTOMFResource)
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
    public static M2mDriverDTOMF createEntity(EntityManager em) {
        M2mDriverDTOMF m2mDriverDTOMF = new M2mDriverDTOMF()
            .name(DEFAULT_NAME);
        return m2mDriverDTOMF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M2mDriverDTOMF createUpdatedEntity(EntityManager em) {
        M2mDriverDTOMF m2mDriverDTOMF = new M2mDriverDTOMF()
            .name(UPDATED_NAME);
        return m2mDriverDTOMF;
    }

    @BeforeEach
    public void initTest() {
        m2mDriverDTOMF = createEntity(em);
    }

    @Test
    @Transactional
    public void createM2mDriverDTOMF() throws Exception {
        int databaseSizeBeforeCreate = m2mDriverDTOMFRepository.findAll().size();

        // Create the M2mDriverDTOMF
        restM2mDriverDTOMFMockMvc.perform(post("/api/m-2-m-driver-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriverDTOMF)))
            .andExpect(status().isCreated());

        // Validate the M2mDriverDTOMF in the database
        List<M2mDriverDTOMF> m2mDriverDTOMFList = m2mDriverDTOMFRepository.findAll();
        assertThat(m2mDriverDTOMFList).hasSize(databaseSizeBeforeCreate + 1);
        M2mDriverDTOMF testM2mDriverDTOMF = m2mDriverDTOMFList.get(m2mDriverDTOMFList.size() - 1);
        assertThat(testM2mDriverDTOMF.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the M2mDriverDTOMF in Elasticsearch
        verify(mockM2mDriverDTOMFSearchRepository, times(1)).save(testM2mDriverDTOMF);
    }

    @Test
    @Transactional
    public void createM2mDriverDTOMFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = m2mDriverDTOMFRepository.findAll().size();

        // Create the M2mDriverDTOMF with an existing ID
        m2mDriverDTOMF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restM2mDriverDTOMFMockMvc.perform(post("/api/m-2-m-driver-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriverDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the M2mDriverDTOMF in the database
        List<M2mDriverDTOMF> m2mDriverDTOMFList = m2mDriverDTOMFRepository.findAll();
        assertThat(m2mDriverDTOMFList).hasSize(databaseSizeBeforeCreate);

        // Validate the M2mDriverDTOMF in Elasticsearch
        verify(mockM2mDriverDTOMFSearchRepository, times(0)).save(m2mDriverDTOMF);
    }


    @Test
    @Transactional
    public void getAllM2mDriverDTOMFS() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);

        // Get all the m2mDriverDTOMFList
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mDriverDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getM2mDriverDTOMF() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);

        // Get the m2mDriverDTOMF
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs/{id}", m2mDriverDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(m2mDriverDTOMF.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllM2mDriverDTOMFSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);

        // Get all the m2mDriverDTOMFList where name equals to DEFAULT_NAME
        defaultM2mDriverDTOMFShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the m2mDriverDTOMFList where name equals to UPDATED_NAME
        defaultM2mDriverDTOMFShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllM2mDriverDTOMFSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);

        // Get all the m2mDriverDTOMFList where name in DEFAULT_NAME or UPDATED_NAME
        defaultM2mDriverDTOMFShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the m2mDriverDTOMFList where name equals to UPDATED_NAME
        defaultM2mDriverDTOMFShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllM2mDriverDTOMFSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);

        // Get all the m2mDriverDTOMFList where name is not null
        defaultM2mDriverDTOMFShouldBeFound("name.specified=true");

        // Get all the m2mDriverDTOMFList where name is null
        defaultM2mDriverDTOMFShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllM2mDriverDTOMFSByM2mCarDTOMFIsEqualToSomething() throws Exception {
        // Initialize the database
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);
        M2mCarDTOMF m2mCarDTOMF = M2mCarDTOMFResourceIT.createEntity(em);
        em.persist(m2mCarDTOMF);
        em.flush();
        m2mDriverDTOMF.addM2mCarDTOMF(m2mCarDTOMF);
        m2mDriverDTOMFRepository.saveAndFlush(m2mDriverDTOMF);
        Long m2mCarDTOMFId = m2mCarDTOMF.getId();

        // Get all the m2mDriverDTOMFList where m2mCarDTOMF equals to m2mCarDTOMFId
        defaultM2mDriverDTOMFShouldBeFound("m2mCarDTOMFId.equals=" + m2mCarDTOMFId);

        // Get all the m2mDriverDTOMFList where m2mCarDTOMF equals to m2mCarDTOMFId + 1
        defaultM2mDriverDTOMFShouldNotBeFound("m2mCarDTOMFId.equals=" + (m2mCarDTOMFId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultM2mDriverDTOMFShouldBeFound(String filter) throws Exception {
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mDriverDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultM2mDriverDTOMFShouldNotBeFound(String filter) throws Exception {
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingM2mDriverDTOMF() throws Exception {
        // Get the m2mDriverDTOMF
        restM2mDriverDTOMFMockMvc.perform(get("/api/m-2-m-driver-dtomfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateM2mDriverDTOMF() throws Exception {
        // Initialize the database
        m2mDriverDTOMFService.save(m2mDriverDTOMF);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockM2mDriverDTOMFSearchRepository);

        int databaseSizeBeforeUpdate = m2mDriverDTOMFRepository.findAll().size();

        // Update the m2mDriverDTOMF
        M2mDriverDTOMF updatedM2mDriverDTOMF = m2mDriverDTOMFRepository.findById(m2mDriverDTOMF.getId()).get();
        // Disconnect from session so that the updates on updatedM2mDriverDTOMF are not directly saved in db
        em.detach(updatedM2mDriverDTOMF);
        updatedM2mDriverDTOMF
            .name(UPDATED_NAME);

        restM2mDriverDTOMFMockMvc.perform(put("/api/m-2-m-driver-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedM2mDriverDTOMF)))
            .andExpect(status().isOk());

        // Validate the M2mDriverDTOMF in the database
        List<M2mDriverDTOMF> m2mDriverDTOMFList = m2mDriverDTOMFRepository.findAll();
        assertThat(m2mDriverDTOMFList).hasSize(databaseSizeBeforeUpdate);
        M2mDriverDTOMF testM2mDriverDTOMF = m2mDriverDTOMFList.get(m2mDriverDTOMFList.size() - 1);
        assertThat(testM2mDriverDTOMF.getName()).isEqualTo(UPDATED_NAME);

        // Validate the M2mDriverDTOMF in Elasticsearch
        verify(mockM2mDriverDTOMFSearchRepository, times(1)).save(testM2mDriverDTOMF);
    }

    @Test
    @Transactional
    public void updateNonExistingM2mDriverDTOMF() throws Exception {
        int databaseSizeBeforeUpdate = m2mDriverDTOMFRepository.findAll().size();

        // Create the M2mDriverDTOMF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM2mDriverDTOMFMockMvc.perform(put("/api/m-2-m-driver-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriverDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the M2mDriverDTOMF in the database
        List<M2mDriverDTOMF> m2mDriverDTOMFList = m2mDriverDTOMFRepository.findAll();
        assertThat(m2mDriverDTOMFList).hasSize(databaseSizeBeforeUpdate);

        // Validate the M2mDriverDTOMF in Elasticsearch
        verify(mockM2mDriverDTOMFSearchRepository, times(0)).save(m2mDriverDTOMF);
    }

    @Test
    @Transactional
    public void deleteM2mDriverDTOMF() throws Exception {
        // Initialize the database
        m2mDriverDTOMFService.save(m2mDriverDTOMF);

        int databaseSizeBeforeDelete = m2mDriverDTOMFRepository.findAll().size();

        // Delete the m2mDriverDTOMF
        restM2mDriverDTOMFMockMvc.perform(delete("/api/m-2-m-driver-dtomfs/{id}", m2mDriverDTOMF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<M2mDriverDTOMF> m2mDriverDTOMFList = m2mDriverDTOMFRepository.findAll();
        assertThat(m2mDriverDTOMFList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the M2mDriverDTOMF in Elasticsearch
        verify(mockM2mDriverDTOMFSearchRepository, times(1)).deleteById(m2mDriverDTOMF.getId());
    }

    @Test
    @Transactional
    public void searchM2mDriverDTOMF() throws Exception {
        // Initialize the database
        m2mDriverDTOMFService.save(m2mDriverDTOMF);
        when(mockM2mDriverDTOMFSearchRepository.search(queryStringQuery("id:" + m2mDriverDTOMF.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(m2mDriverDTOMF), PageRequest.of(0, 1), 1));
        // Search the m2mDriverDTOMF
        restM2mDriverDTOMFMockMvc.perform(get("/api/_search/m-2-m-driver-dtomfs?query=id:" + m2mDriverDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mDriverDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M2mDriverDTOMF.class);
        M2mDriverDTOMF m2mDriverDTOMF1 = new M2mDriverDTOMF();
        m2mDriverDTOMF1.setId(1L);
        M2mDriverDTOMF m2mDriverDTOMF2 = new M2mDriverDTOMF();
        m2mDriverDTOMF2.setId(m2mDriverDTOMF1.getId());
        assertThat(m2mDriverDTOMF1).isEqualTo(m2mDriverDTOMF2);
        m2mDriverDTOMF2.setId(2L);
        assertThat(m2mDriverDTOMF1).isNotEqualTo(m2mDriverDTOMF2);
        m2mDriverDTOMF1.setId(null);
        assertThat(m2mDriverDTOMF1).isNotEqualTo(m2mDriverDTOMF2);
    }
}
