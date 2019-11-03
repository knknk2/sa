package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Um2oCar;
import com.sa.sa.domain.Um2oOwner;
import com.sa.sa.repository.Um2oCarRepository;
import com.sa.sa.repository.search.Um2oCarSearchRepository;
import com.sa.sa.service.Um2oCarService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;

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
 * Integration tests for the {@link Um2oCarResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Um2oCarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Um2oCarRepository um2oCarRepository;

    @Autowired
    private Um2oCarService um2oCarService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Um2oCarSearchRepositoryMockConfiguration
     */
    @Autowired
    private Um2oCarSearchRepository mockUm2oCarSearchRepository;

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

    private MockMvc restUm2oCarMockMvc;

    private Um2oCar um2oCar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Um2oCarResource um2oCarResource = new Um2oCarResource(um2oCarService);
        this.restUm2oCarMockMvc = MockMvcBuilders.standaloneSetup(um2oCarResource)
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
    public static Um2oCar createEntity(EntityManager em) {
        Um2oCar um2oCar = new Um2oCar()
            .name(DEFAULT_NAME);
        // Add required entity
        Um2oOwner um2oOwner;
        if (TestUtil.findAll(em, Um2oOwner.class).isEmpty()) {
            um2oOwner = Um2oOwnerResourceIT.createEntity(em);
            em.persist(um2oOwner);
            em.flush();
        } else {
            um2oOwner = TestUtil.findAll(em, Um2oOwner.class).get(0);
        }
        um2oCar.setUm2oOwner(um2oOwner);
        return um2oCar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Um2oCar createUpdatedEntity(EntityManager em) {
        Um2oCar um2oCar = new Um2oCar()
            .name(UPDATED_NAME);
        // Add required entity
        Um2oOwner um2oOwner;
        if (TestUtil.findAll(em, Um2oOwner.class).isEmpty()) {
            um2oOwner = Um2oOwnerResourceIT.createUpdatedEntity(em);
            em.persist(um2oOwner);
            em.flush();
        } else {
            um2oOwner = TestUtil.findAll(em, Um2oOwner.class).get(0);
        }
        um2oCar.setUm2oOwner(um2oOwner);
        return um2oCar;
    }

    @BeforeEach
    public void initTest() {
        um2oCar = createEntity(em);
    }

    @Test
    @Transactional
    public void createUm2oCar() throws Exception {
        int databaseSizeBeforeCreate = um2oCarRepository.findAll().size();

        // Create the Um2oCar
        restUm2oCarMockMvc.perform(post("/api/um-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oCar)))
            .andExpect(status().isCreated());

        // Validate the Um2oCar in the database
        List<Um2oCar> um2oCarList = um2oCarRepository.findAll();
        assertThat(um2oCarList).hasSize(databaseSizeBeforeCreate + 1);
        Um2oCar testUm2oCar = um2oCarList.get(um2oCarList.size() - 1);
        assertThat(testUm2oCar.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Um2oCar in Elasticsearch
        verify(mockUm2oCarSearchRepository, times(1)).save(testUm2oCar);
    }

    @Test
    @Transactional
    public void createUm2oCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = um2oCarRepository.findAll().size();

        // Create the Um2oCar with an existing ID
        um2oCar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUm2oCarMockMvc.perform(post("/api/um-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oCar)))
            .andExpect(status().isBadRequest());

        // Validate the Um2oCar in the database
        List<Um2oCar> um2oCarList = um2oCarRepository.findAll();
        assertThat(um2oCarList).hasSize(databaseSizeBeforeCreate);

        // Validate the Um2oCar in Elasticsearch
        verify(mockUm2oCarSearchRepository, times(0)).save(um2oCar);
    }


    @Test
    @Transactional
    public void getAllUm2oCars() throws Exception {
        // Initialize the database
        um2oCarRepository.saveAndFlush(um2oCar);

        // Get all the um2oCarList
        restUm2oCarMockMvc.perform(get("/api/um-2-o-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(um2oCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUm2oCar() throws Exception {
        // Initialize the database
        um2oCarRepository.saveAndFlush(um2oCar);

        // Get the um2oCar
        restUm2oCarMockMvc.perform(get("/api/um-2-o-cars/{id}", um2oCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(um2oCar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingUm2oCar() throws Exception {
        // Get the um2oCar
        restUm2oCarMockMvc.perform(get("/api/um-2-o-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUm2oCar() throws Exception {
        // Initialize the database
        um2oCarService.save(um2oCar);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUm2oCarSearchRepository);

        int databaseSizeBeforeUpdate = um2oCarRepository.findAll().size();

        // Update the um2oCar
        Um2oCar updatedUm2oCar = um2oCarRepository.findById(um2oCar.getId()).get();
        // Disconnect from session so that the updates on updatedUm2oCar are not directly saved in db
        em.detach(updatedUm2oCar);
        updatedUm2oCar
            .name(UPDATED_NAME);

        restUm2oCarMockMvc.perform(put("/api/um-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUm2oCar)))
            .andExpect(status().isOk());

        // Validate the Um2oCar in the database
        List<Um2oCar> um2oCarList = um2oCarRepository.findAll();
        assertThat(um2oCarList).hasSize(databaseSizeBeforeUpdate);
        Um2oCar testUm2oCar = um2oCarList.get(um2oCarList.size() - 1);
        assertThat(testUm2oCar.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Um2oCar in Elasticsearch
        verify(mockUm2oCarSearchRepository, times(1)).save(testUm2oCar);
    }

    @Test
    @Transactional
    public void updateNonExistingUm2oCar() throws Exception {
        int databaseSizeBeforeUpdate = um2oCarRepository.findAll().size();

        // Create the Um2oCar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUm2oCarMockMvc.perform(put("/api/um-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oCar)))
            .andExpect(status().isBadRequest());

        // Validate the Um2oCar in the database
        List<Um2oCar> um2oCarList = um2oCarRepository.findAll();
        assertThat(um2oCarList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Um2oCar in Elasticsearch
        verify(mockUm2oCarSearchRepository, times(0)).save(um2oCar);
    }

    @Test
    @Transactional
    public void deleteUm2oCar() throws Exception {
        // Initialize the database
        um2oCarService.save(um2oCar);

        int databaseSizeBeforeDelete = um2oCarRepository.findAll().size();

        // Delete the um2oCar
        restUm2oCarMockMvc.perform(delete("/api/um-2-o-cars/{id}", um2oCar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Um2oCar> um2oCarList = um2oCarRepository.findAll();
        assertThat(um2oCarList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Um2oCar in Elasticsearch
        verify(mockUm2oCarSearchRepository, times(1)).deleteById(um2oCar.getId());
    }

    @Test
    @Transactional
    public void searchUm2oCar() throws Exception {
        // Initialize the database
        um2oCarService.save(um2oCar);
        when(mockUm2oCarSearchRepository.search(queryStringQuery("id:" + um2oCar.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(um2oCar), PageRequest.of(0, 1), 1));
        // Search the um2oCar
        restUm2oCarMockMvc.perform(get("/api/_search/um-2-o-cars?query=id:" + um2oCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(um2oCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Um2oCar.class);
        Um2oCar um2oCar1 = new Um2oCar();
        um2oCar1.setId(1L);
        Um2oCar um2oCar2 = new Um2oCar();
        um2oCar2.setId(um2oCar1.getId());
        assertThat(um2oCar1).isEqualTo(um2oCar2);
        um2oCar2.setId(2L);
        assertThat(um2oCar1).isNotEqualTo(um2oCar2);
        um2oCar1.setId(null);
        assertThat(um2oCar1).isNotEqualTo(um2oCar2);
    }
}
