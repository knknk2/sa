package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Um2oOwner;
import com.sa.sa.repository.Um2oOwnerRepository;
import com.sa.sa.repository.search.Um2oOwnerSearchRepository;
import com.sa.sa.service.Um2oOwnerService;
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
 * Integration tests for the {@link Um2oOwnerResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Um2oOwnerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Um2oOwnerRepository um2oOwnerRepository;

    @Autowired
    private Um2oOwnerService um2oOwnerService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Um2oOwnerSearchRepositoryMockConfiguration
     */
    @Autowired
    private Um2oOwnerSearchRepository mockUm2oOwnerSearchRepository;

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

    private MockMvc restUm2oOwnerMockMvc;

    private Um2oOwner um2oOwner;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Um2oOwnerResource um2oOwnerResource = new Um2oOwnerResource(um2oOwnerService);
        this.restUm2oOwnerMockMvc = MockMvcBuilders.standaloneSetup(um2oOwnerResource)
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
    public static Um2oOwner createEntity(EntityManager em) {
        Um2oOwner um2oOwner = new Um2oOwner()
            .name(DEFAULT_NAME);
        return um2oOwner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Um2oOwner createUpdatedEntity(EntityManager em) {
        Um2oOwner um2oOwner = new Um2oOwner()
            .name(UPDATED_NAME);
        return um2oOwner;
    }

    @BeforeEach
    public void initTest() {
        um2oOwner = createEntity(em);
    }

    @Test
    @Transactional
    public void createUm2oOwner() throws Exception {
        int databaseSizeBeforeCreate = um2oOwnerRepository.findAll().size();

        // Create the Um2oOwner
        restUm2oOwnerMockMvc.perform(post("/api/um-2-o-owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oOwner)))
            .andExpect(status().isCreated());

        // Validate the Um2oOwner in the database
        List<Um2oOwner> um2oOwnerList = um2oOwnerRepository.findAll();
        assertThat(um2oOwnerList).hasSize(databaseSizeBeforeCreate + 1);
        Um2oOwner testUm2oOwner = um2oOwnerList.get(um2oOwnerList.size() - 1);
        assertThat(testUm2oOwner.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Um2oOwner in Elasticsearch
        verify(mockUm2oOwnerSearchRepository, times(1)).save(testUm2oOwner);
    }

    @Test
    @Transactional
    public void createUm2oOwnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = um2oOwnerRepository.findAll().size();

        // Create the Um2oOwner with an existing ID
        um2oOwner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUm2oOwnerMockMvc.perform(post("/api/um-2-o-owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oOwner)))
            .andExpect(status().isBadRequest());

        // Validate the Um2oOwner in the database
        List<Um2oOwner> um2oOwnerList = um2oOwnerRepository.findAll();
        assertThat(um2oOwnerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Um2oOwner in Elasticsearch
        verify(mockUm2oOwnerSearchRepository, times(0)).save(um2oOwner);
    }


    @Test
    @Transactional
    public void getAllUm2oOwners() throws Exception {
        // Initialize the database
        um2oOwnerRepository.saveAndFlush(um2oOwner);

        // Get all the um2oOwnerList
        restUm2oOwnerMockMvc.perform(get("/api/um-2-o-owners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(um2oOwner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getUm2oOwner() throws Exception {
        // Initialize the database
        um2oOwnerRepository.saveAndFlush(um2oOwner);

        // Get the um2oOwner
        restUm2oOwnerMockMvc.perform(get("/api/um-2-o-owners/{id}", um2oOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(um2oOwner.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUm2oOwner() throws Exception {
        // Get the um2oOwner
        restUm2oOwnerMockMvc.perform(get("/api/um-2-o-owners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUm2oOwner() throws Exception {
        // Initialize the database
        um2oOwnerService.save(um2oOwner);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUm2oOwnerSearchRepository);

        int databaseSizeBeforeUpdate = um2oOwnerRepository.findAll().size();

        // Update the um2oOwner
        Um2oOwner updatedUm2oOwner = um2oOwnerRepository.findById(um2oOwner.getId()).get();
        // Disconnect from session so that the updates on updatedUm2oOwner are not directly saved in db
        em.detach(updatedUm2oOwner);
        updatedUm2oOwner
            .name(UPDATED_NAME);

        restUm2oOwnerMockMvc.perform(put("/api/um-2-o-owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUm2oOwner)))
            .andExpect(status().isOk());

        // Validate the Um2oOwner in the database
        List<Um2oOwner> um2oOwnerList = um2oOwnerRepository.findAll();
        assertThat(um2oOwnerList).hasSize(databaseSizeBeforeUpdate);
        Um2oOwner testUm2oOwner = um2oOwnerList.get(um2oOwnerList.size() - 1);
        assertThat(testUm2oOwner.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Um2oOwner in Elasticsearch
        verify(mockUm2oOwnerSearchRepository, times(1)).save(testUm2oOwner);
    }

    @Test
    @Transactional
    public void updateNonExistingUm2oOwner() throws Exception {
        int databaseSizeBeforeUpdate = um2oOwnerRepository.findAll().size();

        // Create the Um2oOwner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUm2oOwnerMockMvc.perform(put("/api/um-2-o-owners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(um2oOwner)))
            .andExpect(status().isBadRequest());

        // Validate the Um2oOwner in the database
        List<Um2oOwner> um2oOwnerList = um2oOwnerRepository.findAll();
        assertThat(um2oOwnerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Um2oOwner in Elasticsearch
        verify(mockUm2oOwnerSearchRepository, times(0)).save(um2oOwner);
    }

    @Test
    @Transactional
    public void deleteUm2oOwner() throws Exception {
        // Initialize the database
        um2oOwnerService.save(um2oOwner);

        int databaseSizeBeforeDelete = um2oOwnerRepository.findAll().size();

        // Delete the um2oOwner
        restUm2oOwnerMockMvc.perform(delete("/api/um-2-o-owners/{id}", um2oOwner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Um2oOwner> um2oOwnerList = um2oOwnerRepository.findAll();
        assertThat(um2oOwnerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Um2oOwner in Elasticsearch
        verify(mockUm2oOwnerSearchRepository, times(1)).deleteById(um2oOwner.getId());
    }

    @Test
    @Transactional
    public void searchUm2oOwner() throws Exception {
        // Initialize the database
        um2oOwnerService.save(um2oOwner);
        when(mockUm2oOwnerSearchRepository.search(queryStringQuery("id:" + um2oOwner.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(um2oOwner), PageRequest.of(0, 1), 1));
        // Search the um2oOwner
        restUm2oOwnerMockMvc.perform(get("/api/_search/um-2-o-owners?query=id:" + um2oOwner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(um2oOwner.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Um2oOwner.class);
        Um2oOwner um2oOwner1 = new Um2oOwner();
        um2oOwner1.setId(1L);
        Um2oOwner um2oOwner2 = new Um2oOwner();
        um2oOwner2.setId(um2oOwner1.getId());
        assertThat(um2oOwner1).isEqualTo(um2oOwner2);
        um2oOwner2.setId(2L);
        assertThat(um2oOwner1).isNotEqualTo(um2oOwner2);
        um2oOwner1.setId(null);
        assertThat(um2oOwner1).isNotEqualTo(um2oOwner2);
    }
}
