package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.O2oCar;
import com.sa.sa.domain.O2oDriver;
import com.sa.sa.repository.O2oCarRepository;
import com.sa.sa.repository.search.O2oCarSearchRepository;
import com.sa.sa.service.O2oCarService;
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
 * Integration tests for the {@link O2oCarResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class O2oCarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private O2oCarRepository o2oCarRepository;

    @Autowired
    private O2oCarService o2oCarService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.O2oCarSearchRepositoryMockConfiguration
     */
    @Autowired
    private O2oCarSearchRepository mockO2oCarSearchRepository;

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

    private MockMvc restO2oCarMockMvc;

    private O2oCar o2oCar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final O2oCarResource o2oCarResource = new O2oCarResource(o2oCarService);
        this.restO2oCarMockMvc = MockMvcBuilders.standaloneSetup(o2oCarResource)
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
    public static O2oCar createEntity(EntityManager em) {
        O2oCar o2oCar = new O2oCar()
            .name(DEFAULT_NAME);
        // Add required entity
        O2oDriver o2oDriver;
        if (TestUtil.findAll(em, O2oDriver.class).isEmpty()) {
            o2oDriver = O2oDriverResourceIT.createEntity(em);
            em.persist(o2oDriver);
            em.flush();
        } else {
            o2oDriver = TestUtil.findAll(em, O2oDriver.class).get(0);
        }
        o2oCar.setO2oDriver(o2oDriver);
        return o2oCar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static O2oCar createUpdatedEntity(EntityManager em) {
        O2oCar o2oCar = new O2oCar()
            .name(UPDATED_NAME);
        // Add required entity
        O2oDriver o2oDriver;
        if (TestUtil.findAll(em, O2oDriver.class).isEmpty()) {
            o2oDriver = O2oDriverResourceIT.createUpdatedEntity(em);
            em.persist(o2oDriver);
            em.flush();
        } else {
            o2oDriver = TestUtil.findAll(em, O2oDriver.class).get(0);
        }
        o2oCar.setO2oDriver(o2oDriver);
        return o2oCar;
    }

    @BeforeEach
    public void initTest() {
        o2oCar = createEntity(em);
    }

    @Test
    @Transactional
    public void createO2oCar() throws Exception {
        int databaseSizeBeforeCreate = o2oCarRepository.findAll().size();

        // Create the O2oCar
        restO2oCarMockMvc.perform(post("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oCar)))
            .andExpect(status().isCreated());

        // Validate the O2oCar in the database
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeCreate + 1);
        O2oCar testO2oCar = o2oCarList.get(o2oCarList.size() - 1);
        assertThat(testO2oCar.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the id for MapsId, the ids must be same
        assertThat(testO2oCar.getId()).isEqualTo(testO2oCar.getO2oDriver().getId());

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(1)).save(testO2oCar);
    }

    @Test
    @Transactional
    public void createO2oCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = o2oCarRepository.findAll().size();

        // Create the O2oCar with an existing ID
        o2oCar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restO2oCarMockMvc.perform(post("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oCar)))
            .andExpect(status().isBadRequest());

        // Validate the O2oCar in the database
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeCreate);

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(0)).save(o2oCar);
    }

    @Test
    @Transactional
    public void updateO2oCarMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        o2oCarService.save(o2oCar);
        int databaseSizeBeforeCreate = o2oCarRepository.findAll().size();

        // Add a new parent entity
        O2oDriver o2oDriver = O2oDriverResourceIT.createUpdatedEntity(em);
        em.persist(o2oDriver);
        em.flush();

        // Load the o2oCar
        O2oCar updatedO2oCar = o2oCarRepository.findById(o2oCar.getId()).get();
        // Disconnect from session so that the updates on updatedO2oCar are not directly saved in db
        em.detach(updatedO2oCar);

        // Update the O2oDriver with new association value
        updatedO2oCar.setO2oDriver(o2oDriver);

        // Update the entity
        restO2oCarMockMvc.perform(put("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedO2oCar)))
            .andExpect(status().isOk());

        // Validate the O2oCar in the database
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeCreate);
        O2oCar testO2oCar = o2oCarList.get(o2oCarList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testO2oCar.getId()).isEqualTo(testO2oCar.getO2oDriver().getId());

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(2)).save(o2oCar);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = o2oCarRepository.findAll().size();
        // set the field null
        o2oCar.setName(null);

        // Create the O2oCar, which fails.

        restO2oCarMockMvc.perform(post("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oCar)))
            .andExpect(status().isBadRequest());

        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllO2oCars() throws Exception {
        // Initialize the database
        o2oCarRepository.saveAndFlush(o2oCar);

        // Get all the o2oCarList
        restO2oCarMockMvc.perform(get("/api/o-2-o-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(o2oCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getO2oCar() throws Exception {
        // Initialize the database
        o2oCarRepository.saveAndFlush(o2oCar);

        // Get the o2oCar
        restO2oCarMockMvc.perform(get("/api/o-2-o-cars/{id}", o2oCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(o2oCar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingO2oCar() throws Exception {
        // Get the o2oCar
        restO2oCarMockMvc.perform(get("/api/o-2-o-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateO2oCar() throws Exception {
        // Initialize the database
        o2oCarService.save(o2oCar);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockO2oCarSearchRepository);

        int databaseSizeBeforeUpdate = o2oCarRepository.findAll().size();

        // Update the o2oCar
        O2oCar updatedO2oCar = o2oCarRepository.findById(o2oCar.getId()).get();
        // Disconnect from session so that the updates on updatedO2oCar are not directly saved in db
        em.detach(updatedO2oCar);
        updatedO2oCar
            .name(UPDATED_NAME);

        restO2oCarMockMvc.perform(put("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedO2oCar)))
            .andExpect(status().isOk());

        // Validate the O2oCar in the database
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeUpdate);
        O2oCar testO2oCar = o2oCarList.get(o2oCarList.size() - 1);
        assertThat(testO2oCar.getName()).isEqualTo(UPDATED_NAME);

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(1)).save(testO2oCar);
    }

    @Test
    @Transactional
    public void updateNonExistingO2oCar() throws Exception {
        int databaseSizeBeforeUpdate = o2oCarRepository.findAll().size();

        // Create the O2oCar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restO2oCarMockMvc.perform(put("/api/o-2-o-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oCar)))
            .andExpect(status().isBadRequest());

        // Validate the O2oCar in the database
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeUpdate);

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(0)).save(o2oCar);
    }

    @Test
    @Transactional
    public void deleteO2oCar() throws Exception {
        // Initialize the database
        o2oCarService.save(o2oCar);

        int databaseSizeBeforeDelete = o2oCarRepository.findAll().size();

        // Delete the o2oCar
        restO2oCarMockMvc.perform(delete("/api/o-2-o-cars/{id}", o2oCar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<O2oCar> o2oCarList = o2oCarRepository.findAll();
        assertThat(o2oCarList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the O2oCar in Elasticsearch
        verify(mockO2oCarSearchRepository, times(1)).deleteById(o2oCar.getId());
    }

    @Test
    @Transactional
    public void searchO2oCar() throws Exception {
        // Initialize the database
        o2oCarService.save(o2oCar);
        when(mockO2oCarSearchRepository.search(queryStringQuery("id:" + o2oCar.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(o2oCar), PageRequest.of(0, 1), 1));
        // Search the o2oCar
        restO2oCarMockMvc.perform(get("/api/_search/o-2-o-cars?query=id:" + o2oCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(o2oCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(O2oCar.class);
        O2oCar o2oCar1 = new O2oCar();
        o2oCar1.setId(1L);
        O2oCar o2oCar2 = new O2oCar();
        o2oCar2.setId(o2oCar1.getId());
        assertThat(o2oCar1).isEqualTo(o2oCar2);
        o2oCar2.setId(2L);
        assertThat(o2oCar1).isNotEqualTo(o2oCar2);
        o2oCar1.setId(null);
        assertThat(o2oCar1).isNotEqualTo(o2oCar2);
    }
}
