package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.M2mDriver;
import com.sa.sa.repository.M2mDriverRepository;
import com.sa.sa.repository.search.M2mDriverSearchRepository;
import com.sa.sa.service.M2mDriverService;
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
 * Integration tests for the {@link M2mDriverResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class M2mDriverResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private M2mDriverRepository m2mDriverRepository;

    @Autowired
    private M2mDriverService m2mDriverService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.M2mDriverSearchRepositoryMockConfiguration
     */
    @Autowired
    private M2mDriverSearchRepository mockM2mDriverSearchRepository;

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

    private MockMvc restM2mDriverMockMvc;

    private M2mDriver m2mDriver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final M2mDriverResource m2mDriverResource = new M2mDriverResource(m2mDriverService);
        this.restM2mDriverMockMvc = MockMvcBuilders.standaloneSetup(m2mDriverResource)
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
    public static M2mDriver createEntity(EntityManager em) {
        M2mDriver m2mDriver = new M2mDriver()
            .name(DEFAULT_NAME);
        return m2mDriver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static M2mDriver createUpdatedEntity(EntityManager em) {
        M2mDriver m2mDriver = new M2mDriver()
            .name(UPDATED_NAME);
        return m2mDriver;
    }

    @BeforeEach
    public void initTest() {
        m2mDriver = createEntity(em);
    }

    @Test
    @Transactional
    public void createM2mDriver() throws Exception {
        int databaseSizeBeforeCreate = m2mDriverRepository.findAll().size();

        // Create the M2mDriver
        restM2mDriverMockMvc.perform(post("/api/m-2-m-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriver)))
            .andExpect(status().isCreated());

        // Validate the M2mDriver in the database
        List<M2mDriver> m2mDriverList = m2mDriverRepository.findAll();
        assertThat(m2mDriverList).hasSize(databaseSizeBeforeCreate + 1);
        M2mDriver testM2mDriver = m2mDriverList.get(m2mDriverList.size() - 1);
        assertThat(testM2mDriver.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the M2mDriver in Elasticsearch
        verify(mockM2mDriverSearchRepository, times(1)).save(testM2mDriver);
    }

    @Test
    @Transactional
    public void createM2mDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = m2mDriverRepository.findAll().size();

        // Create the M2mDriver with an existing ID
        m2mDriver.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restM2mDriverMockMvc.perform(post("/api/m-2-m-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriver)))
            .andExpect(status().isBadRequest());

        // Validate the M2mDriver in the database
        List<M2mDriver> m2mDriverList = m2mDriverRepository.findAll();
        assertThat(m2mDriverList).hasSize(databaseSizeBeforeCreate);

        // Validate the M2mDriver in Elasticsearch
        verify(mockM2mDriverSearchRepository, times(0)).save(m2mDriver);
    }


    @Test
    @Transactional
    public void getAllM2mDrivers() throws Exception {
        // Initialize the database
        m2mDriverRepository.saveAndFlush(m2mDriver);

        // Get all the m2mDriverList
        restM2mDriverMockMvc.perform(get("/api/m-2-m-drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mDriver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getM2mDriver() throws Exception {
        // Initialize the database
        m2mDriverRepository.saveAndFlush(m2mDriver);

        // Get the m2mDriver
        restM2mDriverMockMvc.perform(get("/api/m-2-m-drivers/{id}", m2mDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(m2mDriver.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingM2mDriver() throws Exception {
        // Get the m2mDriver
        restM2mDriverMockMvc.perform(get("/api/m-2-m-drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateM2mDriver() throws Exception {
        // Initialize the database
        m2mDriverService.save(m2mDriver);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockM2mDriverSearchRepository);

        int databaseSizeBeforeUpdate = m2mDriverRepository.findAll().size();

        // Update the m2mDriver
        M2mDriver updatedM2mDriver = m2mDriverRepository.findById(m2mDriver.getId()).get();
        // Disconnect from session so that the updates on updatedM2mDriver are not directly saved in db
        em.detach(updatedM2mDriver);
        updatedM2mDriver
            .name(UPDATED_NAME);

        restM2mDriverMockMvc.perform(put("/api/m-2-m-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedM2mDriver)))
            .andExpect(status().isOk());

        // Validate the M2mDriver in the database
        List<M2mDriver> m2mDriverList = m2mDriverRepository.findAll();
        assertThat(m2mDriverList).hasSize(databaseSizeBeforeUpdate);
        M2mDriver testM2mDriver = m2mDriverList.get(m2mDriverList.size() - 1);
        assertThat(testM2mDriver.getName()).isEqualTo(UPDATED_NAME);

        // Validate the M2mDriver in Elasticsearch
        verify(mockM2mDriverSearchRepository, times(1)).save(testM2mDriver);
    }

    @Test
    @Transactional
    public void updateNonExistingM2mDriver() throws Exception {
        int databaseSizeBeforeUpdate = m2mDriverRepository.findAll().size();

        // Create the M2mDriver

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restM2mDriverMockMvc.perform(put("/api/m-2-m-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(m2mDriver)))
            .andExpect(status().isBadRequest());

        // Validate the M2mDriver in the database
        List<M2mDriver> m2mDriverList = m2mDriverRepository.findAll();
        assertThat(m2mDriverList).hasSize(databaseSizeBeforeUpdate);

        // Validate the M2mDriver in Elasticsearch
        verify(mockM2mDriverSearchRepository, times(0)).save(m2mDriver);
    }

    @Test
    @Transactional
    public void deleteM2mDriver() throws Exception {
        // Initialize the database
        m2mDriverService.save(m2mDriver);

        int databaseSizeBeforeDelete = m2mDriverRepository.findAll().size();

        // Delete the m2mDriver
        restM2mDriverMockMvc.perform(delete("/api/m-2-m-drivers/{id}", m2mDriver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<M2mDriver> m2mDriverList = m2mDriverRepository.findAll();
        assertThat(m2mDriverList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the M2mDriver in Elasticsearch
        verify(mockM2mDriverSearchRepository, times(1)).deleteById(m2mDriver.getId());
    }

    @Test
    @Transactional
    public void searchM2mDriver() throws Exception {
        // Initialize the database
        m2mDriverService.save(m2mDriver);
        when(mockM2mDriverSearchRepository.search(queryStringQuery("id:" + m2mDriver.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(m2mDriver), PageRequest.of(0, 1), 1));
        // Search the m2mDriver
        restM2mDriverMockMvc.perform(get("/api/_search/m-2-m-drivers?query=id:" + m2mDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(m2mDriver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(M2mDriver.class);
        M2mDriver m2mDriver1 = new M2mDriver();
        m2mDriver1.setId(1L);
        M2mDriver m2mDriver2 = new M2mDriver();
        m2mDriver2.setId(m2mDriver1.getId());
        assertThat(m2mDriver1).isEqualTo(m2mDriver2);
        m2mDriver2.setId(2L);
        assertThat(m2mDriver1).isNotEqualTo(m2mDriver2);
        m2mDriver1.setId(null);
        assertThat(m2mDriver1).isNotEqualTo(m2mDriver2);
    }
}
