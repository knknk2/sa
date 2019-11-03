package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.To2mCar;
import com.sa.sa.domain.To2mPerson;
import com.sa.sa.repository.To2mCarRepository;
import com.sa.sa.repository.search.To2mCarSearchRepository;
import com.sa.sa.service.To2mCarService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.To2mCarCriteria;
import com.sa.sa.service.To2mCarQueryService;

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
 * Integration tests for the {@link To2mCarResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class To2mCarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private To2mCarRepository to2mCarRepository;

    @Autowired
    private To2mCarService to2mCarService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.To2mCarSearchRepositoryMockConfiguration
     */
    @Autowired
    private To2mCarSearchRepository mockTo2mCarSearchRepository;

    @Autowired
    private To2mCarQueryService to2mCarQueryService;

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

    private MockMvc restTo2mCarMockMvc;

    private To2mCar to2mCar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final To2mCarResource to2mCarResource = new To2mCarResource(to2mCarService, to2mCarQueryService);
        this.restTo2mCarMockMvc = MockMvcBuilders.standaloneSetup(to2mCarResource)
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
    public static To2mCar createEntity(EntityManager em) {
        To2mCar to2mCar = new To2mCar()
            .name(DEFAULT_NAME);
        return to2mCar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static To2mCar createUpdatedEntity(EntityManager em) {
        To2mCar to2mCar = new To2mCar()
            .name(UPDATED_NAME);
        return to2mCar;
    }

    @BeforeEach
    public void initTest() {
        to2mCar = createEntity(em);
    }

    @Test
    @Transactional
    public void createTo2mCar() throws Exception {
        int databaseSizeBeforeCreate = to2mCarRepository.findAll().size();

        // Create the To2mCar
        restTo2mCarMockMvc.perform(post("/api/to-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCar)))
            .andExpect(status().isCreated());

        // Validate the To2mCar in the database
        List<To2mCar> to2mCarList = to2mCarRepository.findAll();
        assertThat(to2mCarList).hasSize(databaseSizeBeforeCreate + 1);
        To2mCar testTo2mCar = to2mCarList.get(to2mCarList.size() - 1);
        assertThat(testTo2mCar.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the To2mCar in Elasticsearch
        verify(mockTo2mCarSearchRepository, times(1)).save(testTo2mCar);
    }

    @Test
    @Transactional
    public void createTo2mCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = to2mCarRepository.findAll().size();

        // Create the To2mCar with an existing ID
        to2mCar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTo2mCarMockMvc.perform(post("/api/to-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCar)))
            .andExpect(status().isBadRequest());

        // Validate the To2mCar in the database
        List<To2mCar> to2mCarList = to2mCarRepository.findAll();
        assertThat(to2mCarList).hasSize(databaseSizeBeforeCreate);

        // Validate the To2mCar in Elasticsearch
        verify(mockTo2mCarSearchRepository, times(0)).save(to2mCar);
    }


    @Test
    @Transactional
    public void getAllTo2mCars() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTo2mCar() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get the to2mCar
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars/{id}", to2mCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(to2mCar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getAllTo2mCarsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name equals to DEFAULT_NAME
        defaultTo2mCarShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the to2mCarList where name equals to UPDATED_NAME
        defaultTo2mCarShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name not equals to DEFAULT_NAME
        defaultTo2mCarShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the to2mCarList where name not equals to UPDATED_NAME
        defaultTo2mCarShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTo2mCarShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the to2mCarList where name equals to UPDATED_NAME
        defaultTo2mCarShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name is not null
        defaultTo2mCarShouldBeFound("name.specified=true");

        // Get all the to2mCarList where name is null
        defaultTo2mCarShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTo2mCarsByNameContainsSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name contains DEFAULT_NAME
        defaultTo2mCarShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the to2mCarList where name contains UPDATED_NAME
        defaultTo2mCarShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTo2mCarsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);

        // Get all the to2mCarList where name does not contain DEFAULT_NAME
        defaultTo2mCarShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the to2mCarList where name does not contain UPDATED_NAME
        defaultTo2mCarShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTo2mCarsByTo2mOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);
        To2mPerson to2mOwner = To2mPersonResourceIT.createEntity(em);
        em.persist(to2mOwner);
        em.flush();
        to2mCar.setTo2mOwner(to2mOwner);
        to2mCarRepository.saveAndFlush(to2mCar);
        Long to2mOwnerId = to2mOwner.getId();

        // Get all the to2mCarList where to2mOwner equals to to2mOwnerId
        defaultTo2mCarShouldBeFound("to2mOwnerId.equals=" + to2mOwnerId);

        // Get all the to2mCarList where to2mOwner equals to to2mOwnerId + 1
        defaultTo2mCarShouldNotBeFound("to2mOwnerId.equals=" + (to2mOwnerId + 1));
    }


    @Test
    @Transactional
    public void getAllTo2mCarsByTo2mDriverIsEqualToSomething() throws Exception {
        // Initialize the database
        to2mCarRepository.saveAndFlush(to2mCar);
        To2mPerson to2mDriver = To2mPersonResourceIT.createEntity(em);
        em.persist(to2mDriver);
        em.flush();
        to2mCar.setTo2mDriver(to2mDriver);
        to2mCarRepository.saveAndFlush(to2mCar);
        Long to2mDriverId = to2mDriver.getId();

        // Get all the to2mCarList where to2mDriver equals to to2mDriverId
        defaultTo2mCarShouldBeFound("to2mDriverId.equals=" + to2mDriverId);

        // Get all the to2mCarList where to2mDriver equals to to2mDriverId + 1
        defaultTo2mCarShouldNotBeFound("to2mDriverId.equals=" + (to2mDriverId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTo2mCarShouldBeFound(String filter) throws Exception {
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTo2mCarShouldNotBeFound(String filter) throws Exception {
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTo2mCar() throws Exception {
        // Get the to2mCar
        restTo2mCarMockMvc.perform(get("/api/to-2-m-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTo2mCar() throws Exception {
        // Initialize the database
        to2mCarService.save(to2mCar);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTo2mCarSearchRepository);

        int databaseSizeBeforeUpdate = to2mCarRepository.findAll().size();

        // Update the to2mCar
        To2mCar updatedTo2mCar = to2mCarRepository.findById(to2mCar.getId()).get();
        // Disconnect from session so that the updates on updatedTo2mCar are not directly saved in db
        em.detach(updatedTo2mCar);
        updatedTo2mCar
            .name(UPDATED_NAME);

        restTo2mCarMockMvc.perform(put("/api/to-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTo2mCar)))
            .andExpect(status().isOk());

        // Validate the To2mCar in the database
        List<To2mCar> to2mCarList = to2mCarRepository.findAll();
        assertThat(to2mCarList).hasSize(databaseSizeBeforeUpdate);
        To2mCar testTo2mCar = to2mCarList.get(to2mCarList.size() - 1);
        assertThat(testTo2mCar.getName()).isEqualTo(UPDATED_NAME);

        // Validate the To2mCar in Elasticsearch
        verify(mockTo2mCarSearchRepository, times(1)).save(testTo2mCar);
    }

    @Test
    @Transactional
    public void updateNonExistingTo2mCar() throws Exception {
        int databaseSizeBeforeUpdate = to2mCarRepository.findAll().size();

        // Create the To2mCar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTo2mCarMockMvc.perform(put("/api/to-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mCar)))
            .andExpect(status().isBadRequest());

        // Validate the To2mCar in the database
        List<To2mCar> to2mCarList = to2mCarRepository.findAll();
        assertThat(to2mCarList).hasSize(databaseSizeBeforeUpdate);

        // Validate the To2mCar in Elasticsearch
        verify(mockTo2mCarSearchRepository, times(0)).save(to2mCar);
    }

    @Test
    @Transactional
    public void deleteTo2mCar() throws Exception {
        // Initialize the database
        to2mCarService.save(to2mCar);

        int databaseSizeBeforeDelete = to2mCarRepository.findAll().size();

        // Delete the to2mCar
        restTo2mCarMockMvc.perform(delete("/api/to-2-m-cars/{id}", to2mCar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<To2mCar> to2mCarList = to2mCarRepository.findAll();
        assertThat(to2mCarList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the To2mCar in Elasticsearch
        verify(mockTo2mCarSearchRepository, times(1)).deleteById(to2mCar.getId());
    }

    @Test
    @Transactional
    public void searchTo2mCar() throws Exception {
        // Initialize the database
        to2mCarService.save(to2mCar);
        when(mockTo2mCarSearchRepository.search(queryStringQuery("id:" + to2mCar.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(to2mCar), PageRequest.of(0, 1), 1));
        // Search the to2mCar
        restTo2mCarMockMvc.perform(get("/api/_search/to-2-m-cars?query=id:" + to2mCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(To2mCar.class);
        To2mCar to2mCar1 = new To2mCar();
        to2mCar1.setId(1L);
        To2mCar to2mCar2 = new To2mCar();
        to2mCar2.setId(to2mCar1.getId());
        assertThat(to2mCar1).isEqualTo(to2mCar2);
        to2mCar2.setId(2L);
        assertThat(to2mCar1).isNotEqualTo(to2mCar2);
        to2mCar1.setId(null);
        assertThat(to2mCar1).isNotEqualTo(to2mCar2);
    }
}
