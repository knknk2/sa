package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.To2mCarInf;
import com.sa.sa.domain.To2mPersonInf;
import com.sa.sa.repository.To2mCarInfRepository;
import com.sa.sa.repository.search.To2mCarInfSearchRepository;
import com.sa.sa.service.To2mCarInfService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.To2mCarInfCriteria;
import com.sa.sa.service.To2mCarInfQueryService;

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
 * Integration tests for the {@link To2mCarInfResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class To2mCarInfResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private To2mCarInfRepository to2mCarInfRepository;

    @Autowired
    private To2mCarInfService to2mCarInfService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.To2mCarInfSearchRepositoryMockConfiguration
     */
    @Autowired
    private To2mCarInfSearchRepository mockTo2mCarInfSearchRepository;

    @Autowired
    private To2mCarInfQueryService to2mCarInfQueryService;

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

    private MockMvc restTo2mCarInfMockMvc;

    private To2mCarInf to2mCarInf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final To2mCarInfResource to2mCarInfResource = new To2mCarInfResource(to2mCarInfService, to2mCarInfQueryService);
        this.restTo2mCarInfMockMvc = MockMvcBuilders.standaloneSetup(to2mCarInfResource)
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
    public static To2mCarInf createEntity(EntityManager em) {
        To2mCarInf to2mCarInf = new To2mCarInf()
            .name(DEFAULT_NAME);
        return to2mCarInf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static To2mCarInf createUpdatedEntity(EntityManager em) {
        To2mCarInf to2mCarInf = new To2mCarInf()
            .name(UPDATED_NAME);
        return to2mCarInf;
    }

    @BeforeEach
    public void initTest() {
        to2mCarInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createTo2mCarInf() throws Exception {
        int databaseSizeBeforeCreate = to2mCarInfRepository.findAll().size();

        // Create the To2mCarInf
        restTo2mCarInfMockMvc.perform(post("/api/to-2-m-car-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCarInf)))
            .andExpect(status().isCreated());

        // Validate the To2mCarInf in the database
        List<To2mCarInf> to2mCarInfList = to2mCarInfRepository.findAll();
        assertThat(to2mCarInfList).hasSize(databaseSizeBeforeCreate + 1);
        To2mCarInf testTo2mCarInf = to2mCarInfList.get(to2mCarInfList.size() - 1);
        assertThat(testTo2mCarInf.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the To2mCarInf in Elasticsearch
        verify(mockTo2mCarInfSearchRepository, times(1)).save(testTo2mCarInf);
    }

    @Test
    @Transactional
    public void createTo2mCarInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = to2mCarInfRepository.findAll().size();

        // Create the To2mCarInf with an existing ID
        to2mCarInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTo2mCarInfMockMvc.perform(post("/api/to-2-m-car-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCarInf)))
            .andExpect(status().isBadRequest());

        // Validate the To2mCarInf in the database
        List<To2mCarInf> to2mCarInfList = to2mCarInfRepository.findAll();
        assertThat(to2mCarInfList).hasSize(databaseSizeBeforeCreate);

        // Validate the To2mCarInf in Elasticsearch
        verify(mockTo2mCarInfSearchRepository, times(0)).save(to2mCarInf);
    }


    @Test
    @Transactional
    public void getAllTo2mCarInfs() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCarInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTo2mCarInf() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get the to2mCarInf
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs/{id}", to2mCarInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(to2mCarInf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getAllTo2mCarInfsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name equals to DEFAULT_NAME
        defaultTo2mCarInfShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the to2mCarInfList where name equals to UPDATED_NAME
        defaultTo2mCarInfShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarInfsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name not equals to DEFAULT_NAME
        defaultTo2mCarInfShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the to2mCarInfList where name not equals to UPDATED_NAME
        defaultTo2mCarInfShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarInfsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTo2mCarInfShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the to2mCarInfList where name equals to UPDATED_NAME
        defaultTo2mCarInfShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarInfsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name is not null
        defaultTo2mCarInfShouldBeFound("name.specified=true");

        // Get all the to2mCarInfList where name is null
        defaultTo2mCarInfShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTo2mCarInfsByNameContainsSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name contains DEFAULT_NAME
        defaultTo2mCarInfShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the to2mCarInfList where name contains UPDATED_NAME
        defaultTo2mCarInfShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarInfsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);

        // Get all the to2mCarInfList where name does not contain DEFAULT_NAME
        defaultTo2mCarInfShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the to2mCarInfList where name does not contain UPDATED_NAME
        defaultTo2mCarInfShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTo2mCarInfsByTo2mOwnerInfIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);
        To2mPersonInf to2mOwnerInf = To2mPersonInfResourceIT.createEntity(em);
        em.persist(to2mOwnerInf);
        em.flush();
        to2mCarInf.setTo2mOwnerInf(to2mOwnerInf);
        to2mCarInfRepository.saveAndFlush(to2mCarInf);
        Long to2mOwnerInfId = to2mOwnerInf.getId();

        // Get all the to2mCarInfList where to2mOwnerInf equals to to2mOwnerInfId
        defaultTo2mCarInfShouldBeFound("to2mOwnerInfId.equals=" + to2mOwnerInfId);

        // Get all the to2mCarInfList where to2mOwnerInf equals to to2mOwnerInfId + 1
        defaultTo2mCarInfShouldNotBeFound("to2mOwnerInfId.equals=" + (to2mOwnerInfId + 1));
    }


    @Test
    @Transactional
    public void getAllTo2mCarInfsByTo2mDriverInfIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarInfRepository.saveAndFlush(to2mCarInf);
        To2mPersonInf to2mDriverInf = To2mPersonInfResourceIT.createEntity(em);
        em.persist(to2mDriverInf);
        em.flush();
        to2mCarInf.setTo2mDriverInf(to2mDriverInf);
        to2mCarInfRepository.saveAndFlush(to2mCarInf);
        Long to2mDriverInfId = to2mDriverInf.getId();

        // Get all the to2mCarInfList where to2mDriverInf equals to to2mDriverInfId
        defaultTo2mCarInfShouldBeFound("to2mDriverInfId.equals=" + to2mDriverInfId);

        // Get all the to2mCarInfList where to2mDriverInf equals to to2mDriverInfId + 1
        defaultTo2mCarInfShouldNotBeFound("to2mDriverInfId.equals=" + (to2mDriverInfId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTo2mCarInfShouldBeFound(String filter) throws Exception {
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCarInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTo2mCarInfShouldNotBeFound(String filter) throws Exception {
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTo2mCarInf() throws Exception {
        // Get the to2mCarInf
        restTo2mCarInfMockMvc.perform(get("/api/to-2-m-car-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTo2mCarInf() throws Exception {
        // Initialize the database
        to2mCarInfService.save(to2mCarInf);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTo2mCarInfSearchRepository);

        int databaseSizeBeforeUpdate = to2mCarInfRepository.findAll().size();

        // Update the to2mCarInf
        To2mCarInf updatedTo2mCarInf = to2mCarInfRepository.findById(to2mCarInf.getId()).get();
        // Disconnect from session so that the updates on updatedTo2mCarInf are not directly saved in db
        em.detach(updatedTo2mCarInf);
        updatedTo2mCarInf
            .name(UPDATED_NAME);

        restTo2mCarInfMockMvc.perform(put("/api/to-2-m-car-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTo2mCarInf)))
            .andExpect(status().isOk());

        // Validate the To2mCarInf in the database
        List<To2mCarInf> to2mCarInfList = to2mCarInfRepository.findAll();
        assertThat(to2mCarInfList).hasSize(databaseSizeBeforeUpdate);
        To2mCarInf testTo2mCarInf = to2mCarInfList.get(to2mCarInfList.size() - 1);
        assertThat(testTo2mCarInf.getName()).isEqualTo(UPDATED_NAME);

        // Validate the To2mCarInf in Elasticsearch
        verify(mockTo2mCarInfSearchRepository, times(1)).save(testTo2mCarInf);
    }

    @Test
    @Transactional
    public void updateNonExistingTo2mCarInf() throws Exception {
        int databaseSizeBeforeUpdate = to2mCarInfRepository.findAll().size();

        // Create the To2mCarInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTo2mCarInfMockMvc.perform(put("/api/to-2-m-car-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCarInf)))
            .andExpect(status().isBadRequest());

        // Validate the To2mCarInf in the database
        List<To2mCarInf> to2mCarInfList = to2mCarInfRepository.findAll();
        assertThat(to2mCarInfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the To2mCarInf in Elasticsearch
        verify(mockTo2mCarInfSearchRepository, times(0)).save(to2mCarInf);
    }

    @Test
    @Transactional
    public void deleteTo2mCarInf() throws Exception {
        // Initialize the database
        to2mCarInfService.save(to2mCarInf);

        int databaseSizeBeforeDelete = to2mCarInfRepository.findAll().size();

        // Delete the to2mCarInf
        restTo2mCarInfMockMvc.perform(delete("/api/to-2-m-car-infs/{id}", to2mCarInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<To2mCarInf> to2mCarInfList = to2mCarInfRepository.findAll();
        assertThat(to2mCarInfList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the To2mCarInf in Elasticsearch
        verify(mockTo2mCarInfSearchRepository, times(1)).deleteById(to2mCarInf.getId());
    }

    @Test
    @Transactional
    public void searchTo2mCarInf() throws Exception {
        // Initialize the database
        to2mCarInfService.save(to2mCarInf);
        when(mockTo2mCarInfSearchRepository.search(queryStringQuery("id:" + to2mCarInf.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(to2mCarInf), PageRequest.of(0, 1), 1));
        // Search the to2mCarInf
        restTo2mCarInfMockMvc.perform(get("/api/_search/to-2-m-car-infs?query=id:" + to2mCarInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCarInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(To2mCarInf.class);
        To2mCarInf to2mCarInf1 = new To2mCarInf();
        to2mCarInf1.setId(1L);
        To2mCarInf to2mCarInf2 = new To2mCarInf();
        to2mCarInf2.setId(to2mCarInf1.getId());
        assertThat(to2mCarInf1).isEqualTo(to2mCarInf2);
        to2mCarInf2.setId(2L);
        assertThat(to2mCarInf1).isNotEqualTo(to2mCarInf2);
        to2mCarInf1.setId(null);
        assertThat(to2mCarInf1).isNotEqualTo(to2mCarInf2);
    }
}
