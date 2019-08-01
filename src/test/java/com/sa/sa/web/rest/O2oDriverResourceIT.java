package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.O2oDriver;
import com.sa.sa.repository.O2oDriverRepository;
import com.sa.sa.repository.search.O2oDriverSearchRepository;
import com.sa.sa.service.O2oDriverService;
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
 * Integration tests for the {@link O2oDriverResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class O2oDriverResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private O2oDriverRepository o2oDriverRepository;

    @Autowired
    private O2oDriverService o2oDriverService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.O2oDriverSearchRepositoryMockConfiguration
     */
    @Autowired
    private O2oDriverSearchRepository mockO2oDriverSearchRepository;

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

    private MockMvc restO2oDriverMockMvc;

    private O2oDriver o2oDriver;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final O2oDriverResource o2oDriverResource = new O2oDriverResource(o2oDriverService);
        this.restO2oDriverMockMvc = MockMvcBuilders.standaloneSetup(o2oDriverResource)
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
    public static O2oDriver createEntity(EntityManager em) {
        O2oDriver o2oDriver = new O2oDriver()
            .name(DEFAULT_NAME);
        return o2oDriver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static O2oDriver createUpdatedEntity(EntityManager em) {
        O2oDriver o2oDriver = new O2oDriver()
            .name(UPDATED_NAME);
        return o2oDriver;
    }

    @BeforeEach
    public void initTest() {
        o2oDriver = createEntity(em);
    }

    @Test
    @Transactional
    public void createO2oDriver() throws Exception {
        int databaseSizeBeforeCreate = o2oDriverRepository.findAll().size();

        // Create the O2oDriver
        restO2oDriverMockMvc.perform(post("/api/o-2-o-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oDriver)))
            .andExpect(status().isCreated());

        // Validate the O2oDriver in the database
        List<O2oDriver> o2oDriverList = o2oDriverRepository.findAll();
        assertThat(o2oDriverList).hasSize(databaseSizeBeforeCreate + 1);
        O2oDriver testO2oDriver = o2oDriverList.get(o2oDriverList.size() - 1);
        assertThat(testO2oDriver.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the O2oDriver in Elasticsearch
        verify(mockO2oDriverSearchRepository, times(1)).save(testO2oDriver);
    }

    @Test
    @Transactional
    public void createO2oDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = o2oDriverRepository.findAll().size();

        // Create the O2oDriver with an existing ID
        o2oDriver.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restO2oDriverMockMvc.perform(post("/api/o-2-o-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oDriver)))
            .andExpect(status().isBadRequest());

        // Validate the O2oDriver in the database
        List<O2oDriver> o2oDriverList = o2oDriverRepository.findAll();
        assertThat(o2oDriverList).hasSize(databaseSizeBeforeCreate);

        // Validate the O2oDriver in Elasticsearch
        verify(mockO2oDriverSearchRepository, times(0)).save(o2oDriver);
    }


    @Test
    @Transactional
    public void getAllO2oDrivers() throws Exception {
        // Initialize the database
        o2oDriverRepository.saveAndFlush(o2oDriver);

        // Get all the o2oDriverList
        restO2oDriverMockMvc.perform(get("/api/o-2-o-drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(o2oDriver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getO2oDriver() throws Exception {
        // Initialize the database
        o2oDriverRepository.saveAndFlush(o2oDriver);

        // Get the o2oDriver
        restO2oDriverMockMvc.perform(get("/api/o-2-o-drivers/{id}", o2oDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(o2oDriver.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingO2oDriver() throws Exception {
        // Get the o2oDriver
        restO2oDriverMockMvc.perform(get("/api/o-2-o-drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateO2oDriver() throws Exception {
        // Initialize the database
        o2oDriverService.save(o2oDriver);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockO2oDriverSearchRepository);

        int databaseSizeBeforeUpdate = o2oDriverRepository.findAll().size();

        // Update the o2oDriver
        O2oDriver updatedO2oDriver = o2oDriverRepository.findById(o2oDriver.getId()).get();
        // Disconnect from session so that the updates on updatedO2oDriver are not directly saved in db
        em.detach(updatedO2oDriver);
        updatedO2oDriver
            .name(UPDATED_NAME);

        restO2oDriverMockMvc.perform(put("/api/o-2-o-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedO2oDriver)))
            .andExpect(status().isOk());

        // Validate the O2oDriver in the database
        List<O2oDriver> o2oDriverList = o2oDriverRepository.findAll();
        assertThat(o2oDriverList).hasSize(databaseSizeBeforeUpdate);
        O2oDriver testO2oDriver = o2oDriverList.get(o2oDriverList.size() - 1);
        assertThat(testO2oDriver.getName()).isEqualTo(UPDATED_NAME);

        // Validate the O2oDriver in Elasticsearch
        verify(mockO2oDriverSearchRepository, times(1)).save(testO2oDriver);
    }

    @Test
    @Transactional
    public void updateNonExistingO2oDriver() throws Exception {
        int databaseSizeBeforeUpdate = o2oDriverRepository.findAll().size();

        // Create the O2oDriver

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restO2oDriverMockMvc.perform(put("/api/o-2-o-drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(o2oDriver)))
            .andExpect(status().isBadRequest());

        // Validate the O2oDriver in the database
        List<O2oDriver> o2oDriverList = o2oDriverRepository.findAll();
        assertThat(o2oDriverList).hasSize(databaseSizeBeforeUpdate);

        // Validate the O2oDriver in Elasticsearch
        verify(mockO2oDriverSearchRepository, times(0)).save(o2oDriver);
    }

    @Test
    @Transactional
    public void deleteO2oDriver() throws Exception {
        // Initialize the database
        o2oDriverService.save(o2oDriver);

        int databaseSizeBeforeDelete = o2oDriverRepository.findAll().size();

        // Delete the o2oDriver
        restO2oDriverMockMvc.perform(delete("/api/o-2-o-drivers/{id}", o2oDriver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<O2oDriver> o2oDriverList = o2oDriverRepository.findAll();
        assertThat(o2oDriverList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the O2oDriver in Elasticsearch
        verify(mockO2oDriverSearchRepository, times(1)).deleteById(o2oDriver.getId());
    }

    @Test
    @Transactional
    public void searchO2oDriver() throws Exception {
        // Initialize the database
        o2oDriverService.save(o2oDriver);
        when(mockO2oDriverSearchRepository.search(queryStringQuery("id:" + o2oDriver.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(o2oDriver), PageRequest.of(0, 1), 1));
        // Search the o2oDriver
        restO2oDriverMockMvc.perform(get("/api/_search/o-2-o-drivers?query=id:" + o2oDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(o2oDriver.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(O2oDriver.class);
        O2oDriver o2oDriver1 = new O2oDriver();
        o2oDriver1.setId(1L);
        O2oDriver o2oDriver2 = new O2oDriver();
        o2oDriver2.setId(o2oDriver1.getId());
        assertThat(o2oDriver1).isEqualTo(o2oDriver2);
        o2oDriver2.setId(2L);
        assertThat(o2oDriver1).isNotEqualTo(o2oDriver2);
        o2oDriver1.setId(null);
        assertThat(o2oDriver1).isNotEqualTo(o2oDriver2);
    }
}
