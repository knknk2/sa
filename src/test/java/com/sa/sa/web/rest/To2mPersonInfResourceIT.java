package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.To2mPersonInf;
import com.sa.sa.repository.To2mPersonInfRepository;
import com.sa.sa.repository.search.To2mPersonInfSearchRepository;
import com.sa.sa.service.To2mPersonInfService;
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
 * Integration tests for the {@link To2mPersonInfResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class To2mPersonInfResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private To2mPersonInfRepository to2mPersonInfRepository;

    @Autowired
    private To2mPersonInfService to2mPersonInfService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.To2mPersonInfSearchRepositoryMockConfiguration
     */
    @Autowired
    private To2mPersonInfSearchRepository mockTo2mPersonInfSearchRepository;

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

    private MockMvc restTo2mPersonInfMockMvc;

    private To2mPersonInf to2mPersonInf;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final To2mPersonInfResource to2mPersonInfResource = new To2mPersonInfResource(to2mPersonInfService);
        this.restTo2mPersonInfMockMvc = MockMvcBuilders.standaloneSetup(to2mPersonInfResource)
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
    public static To2mPersonInf createEntity(EntityManager em) {
        To2mPersonInf to2mPersonInf = new To2mPersonInf()
            .name(DEFAULT_NAME);
        return to2mPersonInf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static To2mPersonInf createUpdatedEntity(EntityManager em) {
        To2mPersonInf to2mPersonInf = new To2mPersonInf()
            .name(UPDATED_NAME);
        return to2mPersonInf;
    }

    @BeforeEach
    public void initTest() {
        to2mPersonInf = createEntity(em);
    }

    @Test
    @Transactional
    public void createTo2mPersonInf() throws Exception {
        int databaseSizeBeforeCreate = to2mPersonInfRepository.findAll().size();

        // Create the To2mPersonInf
        restTo2mPersonInfMockMvc.perform(post("/api/to-2-m-person-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPersonInf)))
            .andExpect(status().isCreated());

        // Validate the To2mPersonInf in the database
        List<To2mPersonInf> to2mPersonInfList = to2mPersonInfRepository.findAll();
        assertThat(to2mPersonInfList).hasSize(databaseSizeBeforeCreate + 1);
        To2mPersonInf testTo2mPersonInf = to2mPersonInfList.get(to2mPersonInfList.size() - 1);
        assertThat(testTo2mPersonInf.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the To2mPersonInf in Elasticsearch
        verify(mockTo2mPersonInfSearchRepository, times(1)).save(testTo2mPersonInf);
    }

    @Test
    @Transactional
    public void createTo2mPersonInfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = to2mPersonInfRepository.findAll().size();

        // Create the To2mPersonInf with an existing ID
        to2mPersonInf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTo2mPersonInfMockMvc.perform(post("/api/to-2-m-person-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPersonInf)))
            .andExpect(status().isBadRequest());

        // Validate the To2mPersonInf in the database
        List<To2mPersonInf> to2mPersonInfList = to2mPersonInfRepository.findAll();
        assertThat(to2mPersonInfList).hasSize(databaseSizeBeforeCreate);

        // Validate the To2mPersonInf in Elasticsearch
        verify(mockTo2mPersonInfSearchRepository, times(0)).save(to2mPersonInf);
    }


    @Test
    @Transactional
    public void getAllTo2mPersonInfs() throws Exception {
        // Initialize the database
        to2mPersonInfRepository.saveAndFlush(to2mPersonInf);

        // Get all the to2mPersonInfList
        restTo2mPersonInfMockMvc.perform(get("/api/to-2-m-person-infs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mPersonInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTo2mPersonInf() throws Exception {
        // Initialize the database
        to2mPersonInfRepository.saveAndFlush(to2mPersonInf);

        // Get the to2mPersonInf
        restTo2mPersonInfMockMvc.perform(get("/api/to-2-m-person-infs/{id}", to2mPersonInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(to2mPersonInf.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTo2mPersonInf() throws Exception {
        // Get the to2mPersonInf
        restTo2mPersonInfMockMvc.perform(get("/api/to-2-m-person-infs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTo2mPersonInf() throws Exception {
        // Initialize the database
        to2mPersonInfService.save(to2mPersonInf);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTo2mPersonInfSearchRepository);

        int databaseSizeBeforeUpdate = to2mPersonInfRepository.findAll().size();

        // Update the to2mPersonInf
        To2mPersonInf updatedTo2mPersonInf = to2mPersonInfRepository.findById(to2mPersonInf.getId()).get();
        // Disconnect from session so that the updates on updatedTo2mPersonInf are not directly saved in db
        em.detach(updatedTo2mPersonInf);
        updatedTo2mPersonInf
            .name(UPDATED_NAME);

        restTo2mPersonInfMockMvc.perform(put("/api/to-2-m-person-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTo2mPersonInf)))
            .andExpect(status().isOk());

        // Validate the To2mPersonInf in the database
        List<To2mPersonInf> to2mPersonInfList = to2mPersonInfRepository.findAll();
        assertThat(to2mPersonInfList).hasSize(databaseSizeBeforeUpdate);
        To2mPersonInf testTo2mPersonInf = to2mPersonInfList.get(to2mPersonInfList.size() - 1);
        assertThat(testTo2mPersonInf.getName()).isEqualTo(UPDATED_NAME);

        // Validate the To2mPersonInf in Elasticsearch
        verify(mockTo2mPersonInfSearchRepository, times(1)).save(testTo2mPersonInf);
    }

    @Test
    @Transactional
    public void updateNonExistingTo2mPersonInf() throws Exception {
        int databaseSizeBeforeUpdate = to2mPersonInfRepository.findAll().size();

        // Create the To2mPersonInf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTo2mPersonInfMockMvc.perform(put("/api/to-2-m-person-infs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPersonInf)))
            .andExpect(status().isBadRequest());

        // Validate the To2mPersonInf in the database
        List<To2mPersonInf> to2mPersonInfList = to2mPersonInfRepository.findAll();
        assertThat(to2mPersonInfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the To2mPersonInf in Elasticsearch
        verify(mockTo2mPersonInfSearchRepository, times(0)).save(to2mPersonInf);
    }

    @Test
    @Transactional
    public void deleteTo2mPersonInf() throws Exception {
        // Initialize the database
        to2mPersonInfService.save(to2mPersonInf);

        int databaseSizeBeforeDelete = to2mPersonInfRepository.findAll().size();

        // Delete the to2mPersonInf
        restTo2mPersonInfMockMvc.perform(delete("/api/to-2-m-person-infs/{id}", to2mPersonInf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<To2mPersonInf> to2mPersonInfList = to2mPersonInfRepository.findAll();
        assertThat(to2mPersonInfList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the To2mPersonInf in Elasticsearch
        verify(mockTo2mPersonInfSearchRepository, times(1)).deleteById(to2mPersonInf.getId());
    }

    @Test
    @Transactional
    public void searchTo2mPersonInf() throws Exception {
        // Initialize the database
        to2mPersonInfService.save(to2mPersonInf);
        when(mockTo2mPersonInfSearchRepository.search(queryStringQuery("id:" + to2mPersonInf.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(to2mPersonInf), PageRequest.of(0, 1), 1));
        // Search the to2mPersonInf
        restTo2mPersonInfMockMvc.perform(get("/api/_search/to-2-m-person-infs?query=id:" + to2mPersonInf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mPersonInf.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(To2mPersonInf.class);
        To2mPersonInf to2mPersonInf1 = new To2mPersonInf();
        to2mPersonInf1.setId(1L);
        To2mPersonInf to2mPersonInf2 = new To2mPersonInf();
        to2mPersonInf2.setId(to2mPersonInf1.getId());
        assertThat(to2mPersonInf1).isEqualTo(to2mPersonInf2);
        to2mPersonInf2.setId(2L);
        assertThat(to2mPersonInf1).isNotEqualTo(to2mPersonInf2);
        to2mPersonInf1.setId(null);
        assertThat(to2mPersonInf1).isNotEqualTo(to2mPersonInf2);
    }
}
