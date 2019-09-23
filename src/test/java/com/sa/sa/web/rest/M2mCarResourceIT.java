package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.M2mCar;
import com.sa.sa.repository.M2mCarRepository;
import com.sa.sa.repository.search.M2mCarSearchRepository;
import com.sa.sa.service.M2mCarService;
import com.sa.sa.web.rest.errors.ExceptionTranslator;

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
 * Integration tests for the {@link M2mCarResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class M2mCarResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private M2mCarRepository m2mCarRepository;

    @Mock
    private M2mCarRepository m2mCarRepositoryMock;

    @Mock
    private M2mCarService m2mCarServiceMock;

    @Autowired
    private M2mCarService m2mCarService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.M2mCarSearchRepositoryMockConfiguration
     */
    @Autowired
    private M2mCarSearchRepository mockM2mCarSearchRepository;

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

    private MockMvc restM2mCarMockMvc;

    private M2mCar m2mCar;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final M2mCarResource m2mCarResource = new M2mCarResource(m2mCarService);
        this.restM2mCarMockMvc = MockMvcBuilders.standaloneSetup(m2mCarResource)
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
    public static M2mCar createEntity(EntityManager em) {
        M2mCar m2mCar = new M2mCar()
            .name(DEFAULT_NAME);
        return m2mCar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M2mCar createUpdatedEntity(EntityManager em) {
        M2mCar m2mCar = new M2mCar()
            .name(UPDATED_NAME);
        return m2mCar;
    }

    @BeforeEach
    public void initTest() {
        m2mCar = createEntity(em);
    }

    @Test
    @Transactional
    public void createM2mCar() throws Exception {
        int databaseSizeBeforeCreate = m2mCarRepository.findAll().size();

        // Create the M2mCar
        restM2mCarMockMvc.perform(post("/api/m-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCar)))
            .andExpect(status().isCreated());

        // Validate the M2mCar in the database
        List<M2mCar> m2mCarList = m2mCarRepository.findAll();
        assertThat(m2mCarList).hasSize(databaseSizeBeforeCreate + 1);
        M2mCar testM2mCar = m2mCarList.get(m2mCarList.size() - 1);
        assertThat(testM2mCar.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the M2mCar in Elasticsearch
        verify(mockM2mCarSearchRepository, times(1)).save(testM2mCar);
    }

    @Test
    @Transactional
    public void createM2mCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = m2mCarRepository.findAll().size();

        // Create the M2mCar with an existing ID
        m2mCar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restM2mCarMockMvc.perform(post("/api/m-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCar)))
            .andExpect(status().isBadRequest());

        // Validate the M2mCar in the database
        List<M2mCar> m2mCarList = m2mCarRepository.findAll();
        assertThat(m2mCarList).hasSize(databaseSizeBeforeCreate);

        // Validate the M2mCar in Elasticsearch
        verify(mockM2mCarSearchRepository, times(0)).save(m2mCar);
    }


    @Test
    @Transactional
    public void getAllM2mCars() throws Exception {
        // Initialize the database
        m2mCarRepository.saveAndFlush(m2mCar);

        // Get all the m2mCarList
        restM2mCarMockMvc.perform(get("/api/m-2-m-cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllM2mCarsWithEagerRelationshipsIsEnabled() throws Exception {
        M2mCarResource m2mCarResource = new M2mCarResource(m2mCarServiceMock);
        when(m2mCarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restM2mCarMockMvc = MockMvcBuilders.standaloneSetup(m2mCarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restM2mCarMockMvc.perform(get("/api/m-2-m-cars?eagerload=true"))
        .andExpect(status().isOk());

        verify(m2mCarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllM2mCarsWithEagerRelationshipsIsNotEnabled() throws Exception {
        M2mCarResource m2mCarResource = new M2mCarResource(m2mCarServiceMock);
            when(m2mCarServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restM2mCarMockMvc = MockMvcBuilders.standaloneSetup(m2mCarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restM2mCarMockMvc.perform(get("/api/m-2-m-cars?eagerload=true"))
        .andExpect(status().isOk());

            verify(m2mCarServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getM2mCar() throws Exception {
        // Initialize the database
        m2mCarRepository.saveAndFlush(m2mCar);

        // Get the m2mCar
        restM2mCarMockMvc.perform(get("/api/m-2-m-cars/{id}", m2mCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(m2mCar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingM2mCar() throws Exception {
        // Get the m2mCar
        restM2mCarMockMvc.perform(get("/api/m-2-m-cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateM2mCar() throws Exception {
        // Initialize the database
        m2mCarService.save(m2mCar);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockM2mCarSearchRepository);

        int databaseSizeBeforeUpdate = m2mCarRepository.findAll().size();

        // Update the m2mCar
        M2mCar updatedM2mCar = m2mCarRepository.findById(m2mCar.getId()).get();
        // Disconnect from session so that the updates on updatedM2mCar are not directly saved in db
        em.detach(updatedM2mCar);
        updatedM2mCar
            .name(UPDATED_NAME);

        restM2mCarMockMvc.perform(put("/api/m-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedM2mCar)))
            .andExpect(status().isOk());

        // Validate the M2mCar in the database
        List<M2mCar> m2mCarList = m2mCarRepository.findAll();
        assertThat(m2mCarList).hasSize(databaseSizeBeforeUpdate);
        M2mCar testM2mCar = m2mCarList.get(m2mCarList.size() - 1);
        assertThat(testM2mCar.getName()).isEqualTo(UPDATED_NAME);

        // Validate the M2mCar in Elasticsearch
        verify(mockM2mCarSearchRepository, times(1)).save(testM2mCar);
    }

    @Test
    @Transactional
    public void updateNonExistingM2mCar() throws Exception {
        int databaseSizeBeforeUpdate = m2mCarRepository.findAll().size();

        // Create the M2mCar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM2mCarMockMvc.perform(put("/api/m-2-m-cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mCar)))
            .andExpect(status().isBadRequest());

        // Validate the M2mCar in the database
        List<M2mCar> m2mCarList = m2mCarRepository.findAll();
        assertThat(m2mCarList).hasSize(databaseSizeBeforeUpdate);

        // Validate the M2mCar in Elasticsearch
        verify(mockM2mCarSearchRepository, times(0)).save(m2mCar);
    }

    @Test
    @Transactional
    public void deleteM2mCar() throws Exception {
        // Initialize the database
        m2mCarService.save(m2mCar);

        int databaseSizeBeforeDelete = m2mCarRepository.findAll().size();

        // Delete the m2mCar
        restM2mCarMockMvc.perform(delete("/api/m-2-m-cars/{id}", m2mCar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<M2mCar> m2mCarList = m2mCarRepository.findAll();
        assertThat(m2mCarList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the M2mCar in Elasticsearch
        verify(mockM2mCarSearchRepository, times(1)).deleteById(m2mCar.getId());
    }

    @Test
    @Transactional
    public void searchM2mCar() throws Exception {
        // Initialize the database
        m2mCarService.save(m2mCar);
        when(mockM2mCarSearchRepository.search(queryStringQuery("id:" + m2mCar.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(m2mCar), PageRequest.of(0, 1), 1));
        // Search the m2mCar
        restM2mCarMockMvc.perform(get("/api/_search/m-2-m-cars?query=id:" + m2mCar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mCar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M2mCar.class);
        M2mCar m2mCar1 = new M2mCar();
        m2mCar1.setId(1L);
        M2mCar m2mCar2 = new M2mCar();
        m2mCar2.setId(m2mCar1.getId());
        assertThat(m2mCar1).isEqualTo(m2mCar2);
        m2mCar2.setId(2L);
        assertThat(m2mCar1).isNotEqualTo(m2mCar2);
        m2mCar1.setId(null);
        assertThat(m2mCar1).isNotEqualTo(m2mCar2);
    }
}
