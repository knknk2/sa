package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.M2mCarDTOMF;
import com.sa.sa.domain.M2mDriverDTOMF;
import com.sa.sa.repository.M2mCarDTOMFRepository;
import com.sa.sa.repository.search.M2mCarDTOMFSearchRepository;
import com.sa.sa.service.M2mCarDTOMFService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.M2mCarDTOMFCriteria;
import com.sa.sa.service.M2mCarDTOMFQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.ArrayList;
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
 * Integration tests for the {@link M2mCarDTOMFResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class M2mCarDTOMFResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private M2mCarDTOMFRepository m2mCarDTOMFRepository;

    @Mock
    private M2mCarDTOMFRepository m2mCarDTOMFRepositoryMock;

    @Mock
    private M2mCarDTOMFService m2mCarDTOMFServiceMock;

    @Autowired
    private M2mCarDTOMFService m2mCarDTOMFService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.M2mCarDTOMFSearchRepositoryMockConfiguration
     */
    @Autowired
    private M2mCarDTOMFSearchRepository mockM2mCarDTOMFSearchRepository;

    @Autowired
    private M2mCarDTOMFQueryService m2mCarDTOMFQueryService;

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

    private MockMvc restM2mCarDTOMFMockMvc;

    private M2mCarDTOMF m2mCarDTOMF;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final M2mCarDTOMFResource m2mCarDTOMFResource = new M2mCarDTOMFResource(m2mCarDTOMFService, m2mCarDTOMFQueryService);
        this.restM2mCarDTOMFMockMvc = MockMvcBuilders.standaloneSetup(m2mCarDTOMFResource)
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
    public static M2mCarDTOMF createEntity(EntityManager em) {
        M2mCarDTOMF m2mCarDTOMF = new M2mCarDTOMF()
            .name(DEFAULT_NAME);
        return m2mCarDTOMF;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M2mCarDTOMF createUpdatedEntity(EntityManager em) {
        M2mCarDTOMF m2mCarDTOMF = new M2mCarDTOMF()
            .name(UPDATED_NAME);
        return m2mCarDTOMF;
    }

    @BeforeEach
    public void initTest() {
        m2mCarDTOMF = createEntity(em);
    }

    @Test
    @Transactional
    public void createM2mCarDTOMF() throws Exception {
        int databaseSizeBeforeCreate = m2mCarDTOMFRepository.findAll().size();

        // Create the M2mCarDTOMF
        restM2mCarDTOMFMockMvc.perform(post("/api/m-2-m-car-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCarDTOMF)))
            .andExpect(status().isCreated());

        // Validate the M2mCarDTOMF in the database
        List<M2mCarDTOMF> m2mCarDTOMFList = m2mCarDTOMFRepository.findAll();
        assertThat(m2mCarDTOMFList).hasSize(databaseSizeBeforeCreate + 1);
        M2mCarDTOMF testM2mCarDTOMF = m2mCarDTOMFList.get(m2mCarDTOMFList.size() - 1);
        assertThat(testM2mCarDTOMF.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the M2mCarDTOMF in Elasticsearch
        verify(mockM2mCarDTOMFSearchRepository, times(1)).save(testM2mCarDTOMF);
    }

    @Test
    @Transactional
    public void createM2mCarDTOMFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = m2mCarDTOMFRepository.findAll().size();

        // Create the M2mCarDTOMF with an existing ID
        m2mCarDTOMF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restM2mCarDTOMFMockMvc.perform(post("/api/m-2-m-car-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCarDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the M2mCarDTOMF in the database
        List<M2mCarDTOMF> m2mCarDTOMFList = m2mCarDTOMFRepository.findAll();
        assertThat(m2mCarDTOMFList).hasSize(databaseSizeBeforeCreate);

        // Validate the M2mCarDTOMF in Elasticsearch
        verify(mockM2mCarDTOMFSearchRepository, times(0)).save(m2mCarDTOMF);
    }


    @Test
    @Transactional
    public void getAllM2mCarDTOMFS() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);

        // Get all the m2mCarDTOMFList
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mCarDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllM2mCarDTOMFSWithEagerRelationshipsIsEnabled() throws Exception {
        M2mCarDTOMFResource m2mCarDTOMFResource = new M2mCarDTOMFResource(m2mCarDTOMFServiceMock, m2mCarDTOMFQueryService);
        when(m2mCarDTOMFServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restM2mCarDTOMFMockMvc = MockMvcBuilders.standaloneSetup(m2mCarDTOMFResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs?eagerload=true"))
        .andExpect(status().isOk());

        verify(m2mCarDTOMFServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllM2mCarDTOMFSWithEagerRelationshipsIsNotEnabled() throws Exception {
        M2mCarDTOMFResource m2mCarDTOMFResource = new M2mCarDTOMFResource(m2mCarDTOMFServiceMock, m2mCarDTOMFQueryService);
            when(m2mCarDTOMFServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restM2mCarDTOMFMockMvc = MockMvcBuilders.standaloneSetup(m2mCarDTOMFResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs?eagerload=true"))
        .andExpect(status().isOk());

            verify(m2mCarDTOMFServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getM2mCarDTOMF() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);

        // Get the m2mCarDTOMF
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs/{id}", m2mCarDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(m2mCarDTOMF.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllM2mCarDTOMFSByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);

        // Get all the m2mCarDTOMFList where name equals to DEFAULT_NAME
        defaultM2mCarDTOMFShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the m2mCarDTOMFList where name equals to UPDATED_NAME
        defaultM2mCarDTOMFShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllM2mCarDTOMFSByNameIsInShouldWork() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);

        // Get all the m2mCarDTOMFList where name in DEFAULT_NAME or UPDATED_NAME
        defaultM2mCarDTOMFShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the m2mCarDTOMFList where name equals to UPDATED_NAME
        defaultM2mCarDTOMFShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllM2mCarDTOMFSByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);

        // Get all the m2mCarDTOMFList where name is not null
        defaultM2mCarDTOMFShouldBeFound("name.specified=true");

        // Get all the m2mCarDTOMFList where name is null
        defaultM2mCarDTOMFShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllM2mCarDTOMFSByM2mDriverDTOMFIsEqualToSomething() throws Exception {
        // Initialize the database
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);
        M2mDriverDTOMF m2mDriverDTOMF = M2mDriverDTOMFResourceIT.createEntity(em);
        em.persist(m2mDriverDTOMF);
        em.flush();
        m2mCarDTOMF.addM2mDriverDTOMF(m2mDriverDTOMF);
        m2mCarDTOMFRepository.saveAndFlush(m2mCarDTOMF);
        Long m2mDriverDTOMFId = m2mDriverDTOMF.getId();

        // Get all the m2mCarDTOMFList where m2mDriverDTOMF equals to m2mDriverDTOMFId
        defaultM2mCarDTOMFShouldBeFound("m2mDriverDTOMFId.equals=" + m2mDriverDTOMFId);

        // Get all the m2mCarDTOMFList where m2mDriverDTOMF equals to m2mDriverDTOMFId + 1
        defaultM2mCarDTOMFShouldNotBeFound("m2mDriverDTOMFId.equals=" + (m2mDriverDTOMFId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultM2mCarDTOMFShouldBeFound(String filter) throws Exception {
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mCarDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultM2mCarDTOMFShouldNotBeFound(String filter) throws Exception {
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingM2mCarDTOMF() throws Exception {
        // Get the m2mCarDTOMF
        restM2mCarDTOMFMockMvc.perform(get("/api/m-2-m-car-dtomfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateM2mCarDTOMF() throws Exception {
        // Initialize the database
        m2mCarDTOMFService.save(m2mCarDTOMF);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockM2mCarDTOMFSearchRepository);

        int databaseSizeBeforeUpdate = m2mCarDTOMFRepository.findAll().size();

        // Update the m2mCarDTOMF
        M2mCarDTOMF updatedM2mCarDTOMF = m2mCarDTOMFRepository.findById(m2mCarDTOMF.getId()).get();
        // Disconnect from session so that the updates on updatedM2mCarDTOMF are not directly saved in db
        em.detach(updatedM2mCarDTOMF);
        updatedM2mCarDTOMF
            .name(UPDATED_NAME);

        restM2mCarDTOMFMockMvc.perform(put("/api/m-2-m-car-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedM2mCarDTOMF)))
            .andExpect(status().isOk());

        // Validate the M2mCarDTOMF in the database
        List<M2mCarDTOMF> m2mCarDTOMFList = m2mCarDTOMFRepository.findAll();
        assertThat(m2mCarDTOMFList).hasSize(databaseSizeBeforeUpdate);
        M2mCarDTOMF testM2mCarDTOMF = m2mCarDTOMFList.get(m2mCarDTOMFList.size() - 1);
        assertThat(testM2mCarDTOMF.getName()).isEqualTo(UPDATED_NAME);

        // Validate the M2mCarDTOMF in Elasticsearch
        verify(mockM2mCarDTOMFSearchRepository, times(1)).save(testM2mCarDTOMF);
    }

    @Test
    @Transactional
    public void updateNonExistingM2mCarDTOMF() throws Exception {
        int databaseSizeBeforeUpdate = m2mCarDTOMFRepository.findAll().size();

        // Create the M2mCarDTOMF

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM2mCarDTOMFMockMvc.perform(put("/api/m-2-m-car-dtomfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCarDTOMF)))
            .andExpect(status().isBadRequest());

        // Validate the M2mCarDTOMF in the database
        List<M2mCarDTOMF> m2mCarDTOMFList = m2mCarDTOMFRepository.findAll();
        assertThat(m2mCarDTOMFList).hasSize(databaseSizeBeforeUpdate);

        // Validate the M2mCarDTOMF in Elasticsearch
        verify(mockM2mCarDTOMFSearchRepository, times(0)).save(m2mCarDTOMF);
    }

    @Test
    @Transactional
    public void deleteM2mCarDTOMF() throws Exception {
        // Initialize the database
        m2mCarDTOMFService.save(m2mCarDTOMF);

        int databaseSizeBeforeDelete = m2mCarDTOMFRepository.findAll().size();

        // Delete the m2mCarDTOMF
        restM2mCarDTOMFMockMvc.perform(delete("/api/m-2-m-car-dtomfs/{id}", m2mCarDTOMF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<M2mCarDTOMF> m2mCarDTOMFList = m2mCarDTOMFRepository.findAll();
        assertThat(m2mCarDTOMFList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the M2mCarDTOMF in Elasticsearch
        verify(mockM2mCarDTOMFSearchRepository, times(1)).deleteById(m2mCarDTOMF.getId());
    }

    @Test
    @Transactional
    public void searchM2mCarDTOMF() throws Exception {
        // Initialize the database
        m2mCarDTOMFService.save(m2mCarDTOMF);
        when(mockM2mCarDTOMFSearchRepository.search(queryStringQuery("id:" + m2mCarDTOMF.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(m2mCarDTOMF), PageRequest.of(0, 1), 1));
        // Search the m2mCarDTOMF
        restM2mCarDTOMFMockMvc.perform(get("/api/_search/m-2-m-car-dtomfs?query=id:" + m2mCarDTOMF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mCarDTOMF.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M2mCarDTOMF.class);
        M2mCarDTOMF m2mCarDTOMF1 = new M2mCarDTOMF();
        m2mCarDTOMF1.setId(1L);
        M2mCarDTOMF m2mCarDTOMF2 = new M2mCarDTOMF();
        m2mCarDTOMF2.setId(m2mCarDTOMF1.getId());
        assertThat(m2mCarDTOMF1).isEqualTo(m2mCarDTOMF2);
        m2mCarDTOMF2.setId(2L);
        assertThat(m2mCarDTOMF1).isNotEqualTo(m2mCarDTOMF2);
        m2mCarDTOMF1.setId(null);
        assertThat(m2mCarDTOMF1).isNotEqualTo(m2mCarDTOMF2);
    }
}
