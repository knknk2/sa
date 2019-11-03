package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Uo2oPassport;
import com.sa.sa.repository.Uo2oPassportRepository;
import com.sa.sa.repository.search.Uo2oPassportSearchRepository;
import com.sa.sa.service.Uo2oPassportService;
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
 * Integration tests for the {@link Uo2oPassportResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Uo2oPassportResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Uo2oPassportRepository uo2oPassportRepository;

    @Autowired
    private Uo2oPassportService uo2oPassportService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Uo2oPassportSearchRepositoryMockConfiguration
     */
    @Autowired
    private Uo2oPassportSearchRepository mockUo2oPassportSearchRepository;

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

    private MockMvc restUo2oPassportMockMvc;

    private Uo2oPassport uo2oPassport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Uo2oPassportResource uo2oPassportResource = new Uo2oPassportResource(uo2oPassportService);
        this.restUo2oPassportMockMvc = MockMvcBuilders.standaloneSetup(uo2oPassportResource)
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
    public static Uo2oPassport createEntity(EntityManager em) {
        Uo2oPassport uo2oPassport = new Uo2oPassport()
            .name(DEFAULT_NAME);
        return uo2oPassport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uo2oPassport createUpdatedEntity(EntityManager em) {
        Uo2oPassport uo2oPassport = new Uo2oPassport()
            .name(UPDATED_NAME);
        return uo2oPassport;
    }

    @BeforeEach
    public void initTest() {
        uo2oPassport = createEntity(em);
    }

    @Test
    @Transactional
    public void createUo2oPassport() throws Exception {
        int databaseSizeBeforeCreate = uo2oPassportRepository.findAll().size();

        // Create the Uo2oPassport
        restUo2oPassportMockMvc.perform(post("/api/uo-2-o-passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassport)))
            .andExpect(status().isCreated());

        // Validate the Uo2oPassport in the database
        List<Uo2oPassport> uo2oPassportList = uo2oPassportRepository.findAll();
        assertThat(uo2oPassportList).hasSize(databaseSizeBeforeCreate + 1);
        Uo2oPassport testUo2oPassport = uo2oPassportList.get(uo2oPassportList.size() - 1);
        assertThat(testUo2oPassport.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Uo2oPassport in Elasticsearch
        verify(mockUo2oPassportSearchRepository, times(1)).save(testUo2oPassport);
    }

    @Test
    @Transactional
    public void createUo2oPassportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uo2oPassportRepository.findAll().size();

        // Create the Uo2oPassport with an existing ID
        uo2oPassport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUo2oPassportMockMvc.perform(post("/api/uo-2-o-passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassport)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oPassport in the database
        List<Uo2oPassport> uo2oPassportList = uo2oPassportRepository.findAll();
        assertThat(uo2oPassportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Uo2oPassport in Elasticsearch
        verify(mockUo2oPassportSearchRepository, times(0)).save(uo2oPassport);
    }


    @Test
    @Transactional
    public void getAllUo2oPassports() throws Exception {
        // Initialize the database
        uo2oPassportRepository.saveAndFlush(uo2oPassport);

        // Get all the uo2oPassportList
        restUo2oPassportMockMvc.perform(get("/api/uo-2-o-passports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oPassport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getUo2oPassport() throws Exception {
        // Initialize the database
        uo2oPassportRepository.saveAndFlush(uo2oPassport);

        // Get the uo2oPassport
        restUo2oPassportMockMvc.perform(get("/api/uo-2-o-passports/{id}", uo2oPassport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uo2oPassport.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingUo2oPassport() throws Exception {
        // Get the uo2oPassport
        restUo2oPassportMockMvc.perform(get("/api/uo-2-o-passports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUo2oPassport() throws Exception {
        // Initialize the database
        uo2oPassportService.save(uo2oPassport);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUo2oPassportSearchRepository);

        int databaseSizeBeforeUpdate = uo2oPassportRepository.findAll().size();

        // Update the uo2oPassport
        Uo2oPassport updatedUo2oPassport = uo2oPassportRepository.findById(uo2oPassport.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oPassport are not directly saved in db
        em.detach(updatedUo2oPassport);
        updatedUo2oPassport
            .name(UPDATED_NAME);

        restUo2oPassportMockMvc.perform(put("/api/uo-2-o-passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oPassport)))
            .andExpect(status().isOk());

        // Validate the Uo2oPassport in the database
        List<Uo2oPassport> uo2oPassportList = uo2oPassportRepository.findAll();
        assertThat(uo2oPassportList).hasSize(databaseSizeBeforeUpdate);
        Uo2oPassport testUo2oPassport = uo2oPassportList.get(uo2oPassportList.size() - 1);
        assertThat(testUo2oPassport.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Uo2oPassport in Elasticsearch
        verify(mockUo2oPassportSearchRepository, times(1)).save(testUo2oPassport);
    }

    @Test
    @Transactional
    public void updateNonExistingUo2oPassport() throws Exception {
        int databaseSizeBeforeUpdate = uo2oPassportRepository.findAll().size();

        // Create the Uo2oPassport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUo2oPassportMockMvc.perform(put("/api/uo-2-o-passports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oPassport)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oPassport in the database
        List<Uo2oPassport> uo2oPassportList = uo2oPassportRepository.findAll();
        assertThat(uo2oPassportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Uo2oPassport in Elasticsearch
        verify(mockUo2oPassportSearchRepository, times(0)).save(uo2oPassport);
    }

    @Test
    @Transactional
    public void deleteUo2oPassport() throws Exception {
        // Initialize the database
        uo2oPassportService.save(uo2oPassport);

        int databaseSizeBeforeDelete = uo2oPassportRepository.findAll().size();

        // Delete the uo2oPassport
        restUo2oPassportMockMvc.perform(delete("/api/uo-2-o-passports/{id}", uo2oPassport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uo2oPassport> uo2oPassportList = uo2oPassportRepository.findAll();
        assertThat(uo2oPassportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Uo2oPassport in Elasticsearch
        verify(mockUo2oPassportSearchRepository, times(1)).deleteById(uo2oPassport.getId());
    }

    @Test
    @Transactional
    public void searchUo2oPassport() throws Exception {
        // Initialize the database
        uo2oPassportService.save(uo2oPassport);
        when(mockUo2oPassportSearchRepository.search(queryStringQuery("id:" + uo2oPassport.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uo2oPassport), PageRequest.of(0, 1), 1));
        // Search the uo2oPassport
        restUo2oPassportMockMvc.perform(get("/api/_search/uo-2-o-passports?query=id:" + uo2oPassport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oPassport.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uo2oPassport.class);
        Uo2oPassport uo2oPassport1 = new Uo2oPassport();
        uo2oPassport1.setId(1L);
        Uo2oPassport uo2oPassport2 = new Uo2oPassport();
        uo2oPassport2.setId(uo2oPassport1.getId());
        assertThat(uo2oPassport1).isEqualTo(uo2oPassport2);
        uo2oPassport2.setId(2L);
        assertThat(uo2oPassport1).isNotEqualTo(uo2oPassport2);
        uo2oPassport1.setId(null);
        assertThat(uo2oPassport1).isNotEqualTo(uo2oPassport2);
    }
}
