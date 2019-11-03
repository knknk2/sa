package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.To2mPerson;
import com.sa.sa.repository.To2mPersonRepository;
import com.sa.sa.repository.search.To2mPersonSearchRepository;
import com.sa.sa.service.To2mPersonService;
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
 * Integration tests for the {@link To2mPersonResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class To2mPersonResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private To2mPersonRepository to2mPersonRepository;

    @Autowired
    private To2mPersonService to2mPersonService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.To2mPersonSearchRepositoryMockConfiguration
     */
    @Autowired
    private To2mPersonSearchRepository mockTo2mPersonSearchRepository;

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

    private MockMvc restTo2mPersonMockMvc;

    private To2mPerson to2mPerson;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final To2mPersonResource to2mPersonResource = new To2mPersonResource(to2mPersonService);
        this.restTo2mPersonMockMvc = MockMvcBuilders.standaloneSetup(to2mPersonResource)
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
    public static To2mPerson createEntity(EntityManager em) {
        To2mPerson to2mPerson = new To2mPerson()
            .name(DEFAULT_NAME);
        return to2mPerson;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static To2mPerson createUpdatedEntity(EntityManager em) {
        To2mPerson to2mPerson = new To2mPerson()
            .name(UPDATED_NAME);
        return to2mPerson;
    }

    @BeforeEach
    public void initTest() {
        to2mPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createTo2mPerson() throws Exception {
        int databaseSizeBeforeCreate = to2mPersonRepository.findAll().size();

        // Create the To2mPerson
        restTo2mPersonMockMvc.perform(post("/api/to-2-m-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPerson)))
            .andExpect(status().isCreated());

        // Validate the To2mPerson in the database
        List<To2mPerson> to2mPersonList = to2mPersonRepository.findAll();
        assertThat(to2mPersonList).hasSize(databaseSizeBeforeCreate + 1);
        To2mPerson testTo2mPerson = to2mPersonList.get(to2mPersonList.size() - 1);
        assertThat(testTo2mPerson.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the To2mPerson in Elasticsearch
        verify(mockTo2mPersonSearchRepository, times(1)).save(testTo2mPerson);
    }

    @Test
    @Transactional
    public void createTo2mPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = to2mPersonRepository.findAll().size();

        // Create the To2mPerson with an existing ID
        to2mPerson.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTo2mPersonMockMvc.perform(post("/api/to-2-m-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPerson)))
            .andExpect(status().isBadRequest());

        // Validate the To2mPerson in the database
        List<To2mPerson> to2mPersonList = to2mPersonRepository.findAll();
        assertThat(to2mPersonList).hasSize(databaseSizeBeforeCreate);

        // Validate the To2mPerson in Elasticsearch
        verify(mockTo2mPersonSearchRepository, times(0)).save(to2mPerson);
    }


    @Test
    @Transactional
    public void getAllTo2mPeople() throws Exception {
        // Initialize the database
        to2mPersonRepository.saveAndFlush(to2mPerson);

        // Get all the to2mPersonList
        restTo2mPersonMockMvc.perform(get("/api/to-2-m-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTo2mPerson() throws Exception {
        // Initialize the database
        to2mPersonRepository.saveAndFlush(to2mPerson);

        // Get the to2mPerson
        restTo2mPersonMockMvc.perform(get("/api/to-2-m-people/{id}", to2mPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(to2mPerson.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingTo2mPerson() throws Exception {
        // Get the to2mPerson
        restTo2mPersonMockMvc.perform(get("/api/to-2-m-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTo2mPerson() throws Exception {
        // Initialize the database
        to2mPersonService.save(to2mPerson);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTo2mPersonSearchRepository);

        int databaseSizeBeforeUpdate = to2mPersonRepository.findAll().size();

        // Update the to2mPerson
        To2mPerson updatedTo2mPerson = to2mPersonRepository.findById(to2mPerson.getId()).get();
        // Disconnect from session so that the updates on updatedTo2mPerson are not directly saved in db
        em.detach(updatedTo2mPerson);
        updatedTo2mPerson
            .name(UPDATED_NAME);

        restTo2mPersonMockMvc.perform(put("/api/to-2-m-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTo2mPerson)))
            .andExpect(status().isOk());

        // Validate the To2mPerson in the database
        List<To2mPerson> to2mPersonList = to2mPersonRepository.findAll();
        assertThat(to2mPersonList).hasSize(databaseSizeBeforeUpdate);
        To2mPerson testTo2mPerson = to2mPersonList.get(to2mPersonList.size() - 1);
        assertThat(testTo2mPerson.getName()).isEqualTo(UPDATED_NAME);

        // Validate the To2mPerson in Elasticsearch
        verify(mockTo2mPersonSearchRepository, times(1)).save(testTo2mPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingTo2mPerson() throws Exception {
        int databaseSizeBeforeUpdate = to2mPersonRepository.findAll().size();

        // Create the To2mPerson

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTo2mPersonMockMvc.perform(put("/api/to-2-m-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(to2mPerson)))
            .andExpect(status().isBadRequest());

        // Validate the To2mPerson in the database
        List<To2mPerson> to2mPersonList = to2mPersonRepository.findAll();
        assertThat(to2mPersonList).hasSize(databaseSizeBeforeUpdate);

        // Validate the To2mPerson in Elasticsearch
        verify(mockTo2mPersonSearchRepository, times(0)).save(to2mPerson);
    }

    @Test
    @Transactional
    public void deleteTo2mPerson() throws Exception {
        // Initialize the database
        to2mPersonService.save(to2mPerson);

        int databaseSizeBeforeDelete = to2mPersonRepository.findAll().size();

        // Delete the to2mPerson
        restTo2mPersonMockMvc.perform(delete("/api/to-2-m-people/{id}", to2mPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<To2mPerson> to2mPersonList = to2mPersonRepository.findAll();
        assertThat(to2mPersonList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the To2mPerson in Elasticsearch
        verify(mockTo2mPersonSearchRepository, times(1)).deleteById(to2mPerson.getId());
    }

    @Test
    @Transactional
    public void searchTo2mPerson() throws Exception {
        // Initialize the database
        to2mPersonService.save(to2mPerson);
        when(mockTo2mPersonSearchRepository.search(queryStringQuery("id:" + to2mPerson.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(to2mPerson), PageRequest.of(0, 1), 1));
        // Search the to2mPerson
        restTo2mPersonMockMvc.perform(get("/api/_search/to-2-m-people?query=id:" + to2mPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(to2mPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(To2mPerson.class);
        To2mPerson to2mPerson1 = new To2mPerson();
        to2mPerson1.setId(1L);
        To2mPerson to2mPerson2 = new To2mPerson();
        to2mPerson2.setId(to2mPerson1.getId());
        assertThat(to2mPerson1).isEqualTo(to2mPerson2);
        to2mPerson2.setId(2L);
        assertThat(to2mPerson1).isNotEqualTo(to2mPerson2);
        to2mPerson1.setId(null);
        assertThat(to2mPerson1).isNotEqualTo(to2mPerson2);
    }
}
