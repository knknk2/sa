package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Uo2oCitizen;
import com.sa.sa.domain.Uo2oPassport;
import com.sa.sa.repository.Uo2oCitizenRepository;
import com.sa.sa.repository.search.Uo2oCitizenSearchRepository;
import com.sa.sa.service.Uo2oCitizenService;
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
 * Integration tests for the {@link Uo2oCitizenResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class Uo2oCitizenResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private Uo2oCitizenRepository uo2oCitizenRepository;

    @Autowired
    private Uo2oCitizenService uo2oCitizenService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.Uo2oCitizenSearchRepositoryMockConfiguration
     */
    @Autowired
    private Uo2oCitizenSearchRepository mockUo2oCitizenSearchRepository;

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

    private MockMvc restUo2oCitizenMockMvc;

    private Uo2oCitizen uo2oCitizen;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Uo2oCitizenResource uo2oCitizenResource = new Uo2oCitizenResource(uo2oCitizenService);
        this.restUo2oCitizenMockMvc = MockMvcBuilders.standaloneSetup(uo2oCitizenResource)
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
    public static Uo2oCitizen createEntity(EntityManager em) {
        Uo2oCitizen uo2oCitizen = new Uo2oCitizen()
            .name(DEFAULT_NAME);
        // Add required entity
        Uo2oPassport uo2oPassport;
        if (TestUtil.findAll(em, Uo2oPassport.class).isEmpty()) {
            uo2oPassport = Uo2oPassportResourceIT.createEntity(em);
            em.persist(uo2oPassport);
            em.flush();
        } else {
            uo2oPassport = TestUtil.findAll(em, Uo2oPassport.class).get(0);
        }
        uo2oCitizen.setUo2oPassport(uo2oPassport);
        return uo2oCitizen;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uo2oCitizen createUpdatedEntity(EntityManager em) {
        Uo2oCitizen uo2oCitizen = new Uo2oCitizen()
            .name(UPDATED_NAME);
        // Add required entity
        Uo2oPassport uo2oPassport;
        if (TestUtil.findAll(em, Uo2oPassport.class).isEmpty()) {
            uo2oPassport = Uo2oPassportResourceIT.createUpdatedEntity(em);
            em.persist(uo2oPassport);
            em.flush();
        } else {
            uo2oPassport = TestUtil.findAll(em, Uo2oPassport.class).get(0);
        }
        uo2oCitizen.setUo2oPassport(uo2oPassport);
        return uo2oCitizen;
    }

    @BeforeEach
    public void initTest() {
        uo2oCitizen = createEntity(em);
    }

    @Test
    @Transactional
    public void createUo2oCitizen() throws Exception {
        int databaseSizeBeforeCreate = uo2oCitizenRepository.findAll().size();

        // Create the Uo2oCitizen
        restUo2oCitizenMockMvc.perform(post("/api/uo-2-o-citizens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizen)))
            .andExpect(status().isCreated());

        // Validate the Uo2oCitizen in the database
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeCreate + 1);
        Uo2oCitizen testUo2oCitizen = uo2oCitizenList.get(uo2oCitizenList.size() - 1);
        assertThat(testUo2oCitizen.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the id for MapsId, the ids must be same
        assertThat(testUo2oCitizen.getId()).isEqualTo(testUo2oCitizen.getUo2oPassport().getId());

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(1)).save(testUo2oCitizen);
    }

    @Test
    @Transactional
    public void createUo2oCitizenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uo2oCitizenRepository.findAll().size();

        // Create the Uo2oCitizen with an existing ID
        uo2oCitizen.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUo2oCitizenMockMvc.perform(post("/api/uo-2-o-citizens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizen)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oCitizen in the database
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeCreate);

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(0)).save(uo2oCitizen);
    }

    @Test
    @Transactional
    public void updateUo2oCitizenMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        uo2oCitizenService.save(uo2oCitizen);
        int databaseSizeBeforeCreate = uo2oCitizenRepository.findAll().size();

        // Add a new parent entity
        Uo2oPassport uo2oPassport = Uo2oPassportResourceIT.createUpdatedEntity(em);
        em.persist(uo2oPassport);
        em.flush();

        // Load the uo2oCitizen
        Uo2oCitizen updatedUo2oCitizen = uo2oCitizenRepository.findById(uo2oCitizen.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oCitizen are not directly saved in db
        em.detach(updatedUo2oCitizen);

        // Update the Uo2oPassport with new association value
        updatedUo2oCitizen.setUo2oPassport(uo2oPassport);

        // Update the entity
        restUo2oCitizenMockMvc.perform(put("/api/uo-2-o-citizens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oCitizen)))
            .andExpect(status().isOk());

        // Validate the Uo2oCitizen in the database
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeCreate);
        Uo2oCitizen testUo2oCitizen = uo2oCitizenList.get(uo2oCitizenList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testUo2oCitizen.getId()).isEqualTo(testUo2oCitizen.getUo2oPassport().getId());

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(2)).save(uo2oCitizen);
    }

    @Test
    @Transactional
    public void getAllUo2oCitizens() throws Exception {
        // Initialize the database
        uo2oCitizenRepository.saveAndFlush(uo2oCitizen);

        // Get all the uo2oCitizenList
        restUo2oCitizenMockMvc.perform(get("/api/uo-2-o-citizens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oCitizen.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getUo2oCitizen() throws Exception {
        // Initialize the database
        uo2oCitizenRepository.saveAndFlush(uo2oCitizen);

        // Get the uo2oCitizen
        restUo2oCitizenMockMvc.perform(get("/api/uo-2-o-citizens/{id}", uo2oCitizen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uo2oCitizen.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUo2oCitizen() throws Exception {
        // Get the uo2oCitizen
        restUo2oCitizenMockMvc.perform(get("/api/uo-2-o-citizens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUo2oCitizen() throws Exception {
        // Initialize the database
        uo2oCitizenService.save(uo2oCitizen);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUo2oCitizenSearchRepository);

        int databaseSizeBeforeUpdate = uo2oCitizenRepository.findAll().size();

        // Update the uo2oCitizen
        Uo2oCitizen updatedUo2oCitizen = uo2oCitizenRepository.findById(uo2oCitizen.getId()).get();
        // Disconnect from session so that the updates on updatedUo2oCitizen are not directly saved in db
        em.detach(updatedUo2oCitizen);
        updatedUo2oCitizen
            .name(UPDATED_NAME);

        restUo2oCitizenMockMvc.perform(put("/api/uo-2-o-citizens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUo2oCitizen)))
            .andExpect(status().isOk());

        // Validate the Uo2oCitizen in the database
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeUpdate);
        Uo2oCitizen testUo2oCitizen = uo2oCitizenList.get(uo2oCitizenList.size() - 1);
        assertThat(testUo2oCitizen.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(1)).save(testUo2oCitizen);
    }

    @Test
    @Transactional
    public void updateNonExistingUo2oCitizen() throws Exception {
        int databaseSizeBeforeUpdate = uo2oCitizenRepository.findAll().size();

        // Create the Uo2oCitizen

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUo2oCitizenMockMvc.perform(put("/api/uo-2-o-citizens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uo2oCitizen)))
            .andExpect(status().isBadRequest());

        // Validate the Uo2oCitizen in the database
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(0)).save(uo2oCitizen);
    }

    @Test
    @Transactional
    public void deleteUo2oCitizen() throws Exception {
        // Initialize the database
        uo2oCitizenService.save(uo2oCitizen);

        int databaseSizeBeforeDelete = uo2oCitizenRepository.findAll().size();

        // Delete the uo2oCitizen
        restUo2oCitizenMockMvc.perform(delete("/api/uo-2-o-citizens/{id}", uo2oCitizen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uo2oCitizen> uo2oCitizenList = uo2oCitizenRepository.findAll();
        assertThat(uo2oCitizenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Uo2oCitizen in Elasticsearch
        verify(mockUo2oCitizenSearchRepository, times(1)).deleteById(uo2oCitizen.getId());
    }

    @Test
    @Transactional
    public void searchUo2oCitizen() throws Exception {
        // Initialize the database
        uo2oCitizenService.save(uo2oCitizen);
        when(mockUo2oCitizenSearchRepository.search(queryStringQuery("id:" + uo2oCitizen.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uo2oCitizen), PageRequest.of(0, 1), 1));
        // Search the uo2oCitizen
        restUo2oCitizenMockMvc.perform(get("/api/_search/uo-2-o-citizens?query=id:" + uo2oCitizen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uo2oCitizen.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uo2oCitizen.class);
        Uo2oCitizen uo2oCitizen1 = new Uo2oCitizen();
        uo2oCitizen1.setId(1L);
        Uo2oCitizen uo2oCitizen2 = new Uo2oCitizen();
        uo2oCitizen2.setId(uo2oCitizen1.getId());
        assertThat(uo2oCitizen1).isEqualTo(uo2oCitizen2);
        uo2oCitizen2.setId(2L);
        assertThat(uo2oCitizen1).isNotEqualTo(uo2oCitizen2);
        uo2oCitizen1.setId(null);
        assertThat(uo2oCitizen1).isNotEqualTo(uo2oCitizen2);
    }
}
