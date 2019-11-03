package com.sa.sa.web.rest;

import com.sa.sa.SampleappApp;
import com.sa.sa.domain.Fields;
import com.sa.sa.repository.FieldsRepository;
import com.sa.sa.repository.search.FieldsSearchRepository;
import com.sa.sa.service.FieldsService;
import com.sa.sa.service.dto.FieldsDTO;
import com.sa.sa.service.mapper.FieldsMapper;
import com.sa.sa.web.rest.errors.ExceptionTranslator;
import com.sa.sa.service.dto.FieldsCriteria;
import com.sa.sa.service.FieldsQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.sa.sa.web.rest.TestUtil.sameInstant;
import static com.sa.sa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sa.sa.domain.enumeration.Enum1;
/**
 * Integration tests for the {@link FieldsResource} REST controller.
 */
@SpringBootTest(classes = SampleappApp.class)
public class FieldsResourceIT {

    private static final String DEFAULT_STR = "AAAAAAAAAA";
    private static final String UPDATED_STR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_1 = 0;
    private static final Integer UPDATED_NUM_1 = 1;
    private static final Integer SMALLER_NUM_1 = 0 - 1;

    private static final Long DEFAULT_NUM_2 = 0L;
    private static final Long UPDATED_NUM_2 = 1L;
    private static final Long SMALLER_NUM_2 = 0L - 1L;

    private static final Float DEFAULT_NUM_3 = 0F;
    private static final Float UPDATED_NUM_3 = 1F;
    private static final Float SMALLER_NUM_3 = 0F - 1F;

    private static final Double DEFAULT_NUM_4 = 0D;
    private static final Double UPDATED_NUM_4 = 1D;
    private static final Double SMALLER_NUM_4 = 0D - 1D;

    private static final BigDecimal DEFAULT_NUM_5 = new BigDecimal(0);
    private static final BigDecimal UPDATED_NUM_5 = new BigDecimal(1);
    private static final BigDecimal SMALLER_NUM_5 = new BigDecimal(0 - 1);

    private static final LocalDate DEFAULT_DATE_1 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_1 = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_1 = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_DATE_2 = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_2 = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_DATE_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_3 = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATE_3 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Duration DEFAULT_DATE_4 = Duration.ofHours(6);
    private static final Duration UPDATED_DATE_4 = Duration.ofHours(12);
    private static final Duration SMALLER_DATE_4 = Duration.ofHours(5);

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final Boolean DEFAULT_BOOL = false;
    private static final Boolean UPDATED_BOOL = true;

    private static final Enum1 DEFAULT_ENUMERATION = Enum1.VALID;
    private static final Enum1 UPDATED_ENUMERATION = Enum1.INVALID;

    private static final byte[] DEFAULT_BLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BLOB_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOB_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_2_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BLOB_3 = "AAAAAAAAAA";
    private static final String UPDATED_BLOB_3 = "BBBBBBBBBB";

    @Autowired
    private FieldsRepository fieldsRepository;

    @Autowired
    private FieldsMapper fieldsMapper;

    @Autowired
    private FieldsService fieldsService;

    /**
     * This repository is mocked in the com.sa.sa.repository.search test package.
     *
     * @see com.sa.sa.repository.search.FieldsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FieldsSearchRepository mockFieldsSearchRepository;

    @Autowired
    private FieldsQueryService fieldsQueryService;

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

    private MockMvc restFieldsMockMvc;

    private Fields fields;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FieldsResource fieldsResource = new FieldsResource(fieldsService, fieldsQueryService);
        this.restFieldsMockMvc = MockMvcBuilders.standaloneSetup(fieldsResource)
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
    public static Fields createEntity(EntityManager em) {
        Fields fields = new Fields()
            .str(DEFAULT_STR)
            .num1(DEFAULT_NUM_1)
            .num2(DEFAULT_NUM_2)
            .num3(DEFAULT_NUM_3)
            .num4(DEFAULT_NUM_4)
            .num5(DEFAULT_NUM_5)
            .date1(DEFAULT_DATE_1)
            .date2(DEFAULT_DATE_2)
            .date3(DEFAULT_DATE_3)
            .date4(DEFAULT_DATE_4)
            .uuid(DEFAULT_UUID)
            .bool(DEFAULT_BOOL)
            .enumeration(DEFAULT_ENUMERATION)
            .blob(DEFAULT_BLOB)
            .blobContentType(DEFAULT_BLOB_CONTENT_TYPE)
            .blob2(DEFAULT_BLOB_2)
            .blob2ContentType(DEFAULT_BLOB_2_CONTENT_TYPE)
            .blob3(DEFAULT_BLOB_3);
        return fields;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fields createUpdatedEntity(EntityManager em) {
        Fields fields = new Fields()
            .str(UPDATED_STR)
            .num1(UPDATED_NUM_1)
            .num2(UPDATED_NUM_2)
            .num3(UPDATED_NUM_3)
            .num4(UPDATED_NUM_4)
            .num5(UPDATED_NUM_5)
            .date1(UPDATED_DATE_1)
            .date2(UPDATED_DATE_2)
            .date3(UPDATED_DATE_3)
            .date4(UPDATED_DATE_4)
            .uuid(UPDATED_UUID)
            .bool(UPDATED_BOOL)
            .enumeration(UPDATED_ENUMERATION)
            .blob(UPDATED_BLOB)
            .blobContentType(UPDATED_BLOB_CONTENT_TYPE)
            .blob2(UPDATED_BLOB_2)
            .blob2ContentType(UPDATED_BLOB_2_CONTENT_TYPE)
            .blob3(UPDATED_BLOB_3);
        return fields;
    }

    @BeforeEach
    public void initTest() {
        fields = createEntity(em);
    }

    @Test
    @Transactional
    public void createFields() throws Exception {
        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);
        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate + 1);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getStr()).isEqualTo(DEFAULT_STR);
        assertThat(testFields.getNum1()).isEqualTo(DEFAULT_NUM_1);
        assertThat(testFields.getNum2()).isEqualTo(DEFAULT_NUM_2);
        assertThat(testFields.getNum3()).isEqualTo(DEFAULT_NUM_3);
        assertThat(testFields.getNum4()).isEqualTo(DEFAULT_NUM_4);
        assertThat(testFields.getNum5()).isEqualTo(DEFAULT_NUM_5);
        assertThat(testFields.getDate1()).isEqualTo(DEFAULT_DATE_1);
        assertThat(testFields.getDate2()).isEqualTo(DEFAULT_DATE_2);
        assertThat(testFields.getDate3()).isEqualTo(DEFAULT_DATE_3);
        assertThat(testFields.getDate4()).isEqualTo(DEFAULT_DATE_4);
        assertThat(testFields.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFields.isBool()).isEqualTo(DEFAULT_BOOL);
        assertThat(testFields.getEnumeration()).isEqualTo(DEFAULT_ENUMERATION);
        assertThat(testFields.getBlob()).isEqualTo(DEFAULT_BLOB);
        assertThat(testFields.getBlobContentType()).isEqualTo(DEFAULT_BLOB_CONTENT_TYPE);
        assertThat(testFields.getBlob2()).isEqualTo(DEFAULT_BLOB_2);
        assertThat(testFields.getBlob2ContentType()).isEqualTo(DEFAULT_BLOB_2_CONTENT_TYPE);
        assertThat(testFields.getBlob3()).isEqualTo(DEFAULT_BLOB_3);

        // Validate the Fields in Elasticsearch
        verify(mockFieldsSearchRepository, times(1)).save(testFields);
    }

    @Test
    @Transactional
    public void createFieldsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();

        // Create the Fields with an existing ID
        fields.setId(1L);
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Fields in Elasticsearch
        verify(mockFieldsSearchRepository, times(0)).save(fields);
    }


    @Test
    @Transactional
    public void checkStrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setStr(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum1IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setNum1(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum2IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setNum2(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum3IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setNum3(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum4IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setNum4(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNum5IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setNum5(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate1IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setDate1(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate2IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setDate2(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate3IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setDate3(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate4IsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setDate4(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setUuid(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBoolIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setBool(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnumerationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setEnumeration(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc.perform(post("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFields() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList
        restFieldsMockMvc.perform(get("/api/fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fields.getId().intValue())))
            .andExpect(jsonPath("$.[*].str").value(hasItem(DEFAULT_STR)))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1)))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2.intValue())))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3.doubleValue())))
            .andExpect(jsonPath("$.[*].num4").value(hasItem(DEFAULT_NUM_4.doubleValue())))
            .andExpect(jsonPath("$.[*].num5").value(hasItem(DEFAULT_NUM_5.intValue())))
            .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1.toString())))
            .andExpect(jsonPath("$.[*].date2").value(hasItem(DEFAULT_DATE_2.toString())))
            .andExpect(jsonPath("$.[*].date3").value(hasItem(sameInstant(DEFAULT_DATE_3))))
            .andExpect(jsonPath("$.[*].date4").value(hasItem(DEFAULT_DATE_4.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].bool").value(hasItem(DEFAULT_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].enumeration").value(hasItem(DEFAULT_ENUMERATION.toString())))
            .andExpect(jsonPath("$.[*].blobContentType").value(hasItem(DEFAULT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB))))
            .andExpect(jsonPath("$.[*].blob2ContentType").value(hasItem(DEFAULT_BLOB_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_2))))
            .andExpect(jsonPath("$.[*].blob3").value(hasItem(DEFAULT_BLOB_3.toString())));
    }
    
    @Test
    @Transactional
    public void getFields() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get the fields
        restFieldsMockMvc.perform(get("/api/fields/{id}", fields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fields.getId().intValue()))
            .andExpect(jsonPath("$.str").value(DEFAULT_STR))
            .andExpect(jsonPath("$.num1").value(DEFAULT_NUM_1))
            .andExpect(jsonPath("$.num2").value(DEFAULT_NUM_2.intValue()))
            .andExpect(jsonPath("$.num3").value(DEFAULT_NUM_3.doubleValue()))
            .andExpect(jsonPath("$.num4").value(DEFAULT_NUM_4.doubleValue()))
            .andExpect(jsonPath("$.num5").value(DEFAULT_NUM_5.intValue()))
            .andExpect(jsonPath("$.date1").value(DEFAULT_DATE_1.toString()))
            .andExpect(jsonPath("$.date2").value(DEFAULT_DATE_2.toString()))
            .andExpect(jsonPath("$.date3").value(sameInstant(DEFAULT_DATE_3)))
            .andExpect(jsonPath("$.date4").value(DEFAULT_DATE_4.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.bool").value(DEFAULT_BOOL.booleanValue()))
            .andExpect(jsonPath("$.enumeration").value(DEFAULT_ENUMERATION.toString()))
            .andExpect(jsonPath("$.blobContentType").value(DEFAULT_BLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.blob").value(Base64Utils.encodeToString(DEFAULT_BLOB)))
            .andExpect(jsonPath("$.blob2ContentType").value(DEFAULT_BLOB_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.blob2").value(Base64Utils.encodeToString(DEFAULT_BLOB_2)))
            .andExpect(jsonPath("$.blob3").value(DEFAULT_BLOB_3.toString()));
    }

    @Test
    @Transactional
    public void getAllFieldsByStrIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str equals to DEFAULT_STR
        defaultFieldsShouldBeFound("str.equals=" + DEFAULT_STR);

        // Get all the fieldsList where str equals to UPDATED_STR
        defaultFieldsShouldNotBeFound("str.equals=" + UPDATED_STR);
    }

    @Test
    @Transactional
    public void getAllFieldsByStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str not equals to DEFAULT_STR
        defaultFieldsShouldNotBeFound("str.notEquals=" + DEFAULT_STR);

        // Get all the fieldsList where str not equals to UPDATED_STR
        defaultFieldsShouldBeFound("str.notEquals=" + UPDATED_STR);
    }

    @Test
    @Transactional
    public void getAllFieldsByStrIsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str in DEFAULT_STR or UPDATED_STR
        defaultFieldsShouldBeFound("str.in=" + DEFAULT_STR + "," + UPDATED_STR);

        // Get all the fieldsList where str equals to UPDATED_STR
        defaultFieldsShouldNotBeFound("str.in=" + UPDATED_STR);
    }

    @Test
    @Transactional
    public void getAllFieldsByStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str is not null
        defaultFieldsShouldBeFound("str.specified=true");

        // Get all the fieldsList where str is null
        defaultFieldsShouldNotBeFound("str.specified=false");
    }
                @Test
    @Transactional
    public void getAllFieldsByStrContainsSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str contains DEFAULT_STR
        defaultFieldsShouldBeFound("str.contains=" + DEFAULT_STR);

        // Get all the fieldsList where str contains UPDATED_STR
        defaultFieldsShouldNotBeFound("str.contains=" + UPDATED_STR);
    }

    @Test
    @Transactional
    public void getAllFieldsByStrNotContainsSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where str does not contain DEFAULT_STR
        defaultFieldsShouldNotBeFound("str.doesNotContain=" + DEFAULT_STR);

        // Get all the fieldsList where str does not contain UPDATED_STR
        defaultFieldsShouldBeFound("str.doesNotContain=" + UPDATED_STR);
    }


    @Test
    @Transactional
    public void getAllFieldsByNum1IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 equals to DEFAULT_NUM_1
        defaultFieldsShouldBeFound("num1.equals=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 equals to UPDATED_NUM_1
        defaultFieldsShouldNotBeFound("num1.equals=" + UPDATED_NUM_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 not equals to DEFAULT_NUM_1
        defaultFieldsShouldNotBeFound("num1.notEquals=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 not equals to UPDATED_NUM_1
        defaultFieldsShouldBeFound("num1.notEquals=" + UPDATED_NUM_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 in DEFAULT_NUM_1 or UPDATED_NUM_1
        defaultFieldsShouldBeFound("num1.in=" + DEFAULT_NUM_1 + "," + UPDATED_NUM_1);

        // Get all the fieldsList where num1 equals to UPDATED_NUM_1
        defaultFieldsShouldNotBeFound("num1.in=" + UPDATED_NUM_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 is not null
        defaultFieldsShouldBeFound("num1.specified=true");

        // Get all the fieldsList where num1 is null
        defaultFieldsShouldNotBeFound("num1.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 is greater than or equal to DEFAULT_NUM_1
        defaultFieldsShouldBeFound("num1.greaterThanOrEqual=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 is greater than or equal to (DEFAULT_NUM_1 + 1)
        defaultFieldsShouldNotBeFound("num1.greaterThanOrEqual=" + (DEFAULT_NUM_1 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 is less than or equal to DEFAULT_NUM_1
        defaultFieldsShouldBeFound("num1.lessThanOrEqual=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 is less than or equal to SMALLER_NUM_1
        defaultFieldsShouldNotBeFound("num1.lessThanOrEqual=" + SMALLER_NUM_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 is less than DEFAULT_NUM_1
        defaultFieldsShouldNotBeFound("num1.lessThan=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 is less than (DEFAULT_NUM_1 + 1)
        defaultFieldsShouldBeFound("num1.lessThan=" + (DEFAULT_NUM_1 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num1 is greater than DEFAULT_NUM_1
        defaultFieldsShouldNotBeFound("num1.greaterThan=" + DEFAULT_NUM_1);

        // Get all the fieldsList where num1 is greater than SMALLER_NUM_1
        defaultFieldsShouldBeFound("num1.greaterThan=" + SMALLER_NUM_1);
    }


    @Test
    @Transactional
    public void getAllFieldsByNum2IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 equals to DEFAULT_NUM_2
        defaultFieldsShouldBeFound("num2.equals=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 equals to UPDATED_NUM_2
        defaultFieldsShouldNotBeFound("num2.equals=" + UPDATED_NUM_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 not equals to DEFAULT_NUM_2
        defaultFieldsShouldNotBeFound("num2.notEquals=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 not equals to UPDATED_NUM_2
        defaultFieldsShouldBeFound("num2.notEquals=" + UPDATED_NUM_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 in DEFAULT_NUM_2 or UPDATED_NUM_2
        defaultFieldsShouldBeFound("num2.in=" + DEFAULT_NUM_2 + "," + UPDATED_NUM_2);

        // Get all the fieldsList where num2 equals to UPDATED_NUM_2
        defaultFieldsShouldNotBeFound("num2.in=" + UPDATED_NUM_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 is not null
        defaultFieldsShouldBeFound("num2.specified=true");

        // Get all the fieldsList where num2 is null
        defaultFieldsShouldNotBeFound("num2.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 is greater than or equal to DEFAULT_NUM_2
        defaultFieldsShouldBeFound("num2.greaterThanOrEqual=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 is greater than or equal to (DEFAULT_NUM_2 + 1)
        defaultFieldsShouldNotBeFound("num2.greaterThanOrEqual=" + (DEFAULT_NUM_2 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 is less than or equal to DEFAULT_NUM_2
        defaultFieldsShouldBeFound("num2.lessThanOrEqual=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 is less than or equal to SMALLER_NUM_2
        defaultFieldsShouldNotBeFound("num2.lessThanOrEqual=" + SMALLER_NUM_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 is less than DEFAULT_NUM_2
        defaultFieldsShouldNotBeFound("num2.lessThan=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 is less than (DEFAULT_NUM_2 + 1)
        defaultFieldsShouldBeFound("num2.lessThan=" + (DEFAULT_NUM_2 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num2 is greater than DEFAULT_NUM_2
        defaultFieldsShouldNotBeFound("num2.greaterThan=" + DEFAULT_NUM_2);

        // Get all the fieldsList where num2 is greater than SMALLER_NUM_2
        defaultFieldsShouldBeFound("num2.greaterThan=" + SMALLER_NUM_2);
    }


    @Test
    @Transactional
    public void getAllFieldsByNum3IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 equals to DEFAULT_NUM_3
        defaultFieldsShouldBeFound("num3.equals=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 equals to UPDATED_NUM_3
        defaultFieldsShouldNotBeFound("num3.equals=" + UPDATED_NUM_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 not equals to DEFAULT_NUM_3
        defaultFieldsShouldNotBeFound("num3.notEquals=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 not equals to UPDATED_NUM_3
        defaultFieldsShouldBeFound("num3.notEquals=" + UPDATED_NUM_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 in DEFAULT_NUM_3 or UPDATED_NUM_3
        defaultFieldsShouldBeFound("num3.in=" + DEFAULT_NUM_3 + "," + UPDATED_NUM_3);

        // Get all the fieldsList where num3 equals to UPDATED_NUM_3
        defaultFieldsShouldNotBeFound("num3.in=" + UPDATED_NUM_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 is not null
        defaultFieldsShouldBeFound("num3.specified=true");

        // Get all the fieldsList where num3 is null
        defaultFieldsShouldNotBeFound("num3.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 is greater than or equal to DEFAULT_NUM_3
        defaultFieldsShouldBeFound("num3.greaterThanOrEqual=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 is greater than or equal to (DEFAULT_NUM_3 + 1)
        defaultFieldsShouldNotBeFound("num3.greaterThanOrEqual=" + (DEFAULT_NUM_3 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 is less than or equal to DEFAULT_NUM_3
        defaultFieldsShouldBeFound("num3.lessThanOrEqual=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 is less than or equal to SMALLER_NUM_3
        defaultFieldsShouldNotBeFound("num3.lessThanOrEqual=" + SMALLER_NUM_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 is less than DEFAULT_NUM_3
        defaultFieldsShouldNotBeFound("num3.lessThan=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 is less than (DEFAULT_NUM_3 + 1)
        defaultFieldsShouldBeFound("num3.lessThan=" + (DEFAULT_NUM_3 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num3 is greater than DEFAULT_NUM_3
        defaultFieldsShouldNotBeFound("num3.greaterThan=" + DEFAULT_NUM_3);

        // Get all the fieldsList where num3 is greater than SMALLER_NUM_3
        defaultFieldsShouldBeFound("num3.greaterThan=" + SMALLER_NUM_3);
    }


    @Test
    @Transactional
    public void getAllFieldsByNum4IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 equals to DEFAULT_NUM_4
        defaultFieldsShouldBeFound("num4.equals=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 equals to UPDATED_NUM_4
        defaultFieldsShouldNotBeFound("num4.equals=" + UPDATED_NUM_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 not equals to DEFAULT_NUM_4
        defaultFieldsShouldNotBeFound("num4.notEquals=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 not equals to UPDATED_NUM_4
        defaultFieldsShouldBeFound("num4.notEquals=" + UPDATED_NUM_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 in DEFAULT_NUM_4 or UPDATED_NUM_4
        defaultFieldsShouldBeFound("num4.in=" + DEFAULT_NUM_4 + "," + UPDATED_NUM_4);

        // Get all the fieldsList where num4 equals to UPDATED_NUM_4
        defaultFieldsShouldNotBeFound("num4.in=" + UPDATED_NUM_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 is not null
        defaultFieldsShouldBeFound("num4.specified=true");

        // Get all the fieldsList where num4 is null
        defaultFieldsShouldNotBeFound("num4.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 is greater than or equal to DEFAULT_NUM_4
        defaultFieldsShouldBeFound("num4.greaterThanOrEqual=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 is greater than or equal to (DEFAULT_NUM_4 + 1)
        defaultFieldsShouldNotBeFound("num4.greaterThanOrEqual=" + (DEFAULT_NUM_4 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 is less than or equal to DEFAULT_NUM_4
        defaultFieldsShouldBeFound("num4.lessThanOrEqual=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 is less than or equal to SMALLER_NUM_4
        defaultFieldsShouldNotBeFound("num4.lessThanOrEqual=" + SMALLER_NUM_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 is less than DEFAULT_NUM_4
        defaultFieldsShouldNotBeFound("num4.lessThan=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 is less than (DEFAULT_NUM_4 + 1)
        defaultFieldsShouldBeFound("num4.lessThan=" + (DEFAULT_NUM_4 + 1));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num4 is greater than DEFAULT_NUM_4
        defaultFieldsShouldNotBeFound("num4.greaterThan=" + DEFAULT_NUM_4);

        // Get all the fieldsList where num4 is greater than SMALLER_NUM_4
        defaultFieldsShouldBeFound("num4.greaterThan=" + SMALLER_NUM_4);
    }


    @Test
    @Transactional
    public void getAllFieldsByNum5IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 equals to DEFAULT_NUM_5
        defaultFieldsShouldBeFound("num5.equals=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 equals to UPDATED_NUM_5
        defaultFieldsShouldNotBeFound("num5.equals=" + UPDATED_NUM_5);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 not equals to DEFAULT_NUM_5
        defaultFieldsShouldNotBeFound("num5.notEquals=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 not equals to UPDATED_NUM_5
        defaultFieldsShouldBeFound("num5.notEquals=" + UPDATED_NUM_5);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 in DEFAULT_NUM_5 or UPDATED_NUM_5
        defaultFieldsShouldBeFound("num5.in=" + DEFAULT_NUM_5 + "," + UPDATED_NUM_5);

        // Get all the fieldsList where num5 equals to UPDATED_NUM_5
        defaultFieldsShouldNotBeFound("num5.in=" + UPDATED_NUM_5);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 is not null
        defaultFieldsShouldBeFound("num5.specified=true");

        // Get all the fieldsList where num5 is null
        defaultFieldsShouldNotBeFound("num5.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 is greater than or equal to DEFAULT_NUM_5
        defaultFieldsShouldBeFound("num5.greaterThanOrEqual=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 is greater than or equal to (DEFAULT_NUM_5.add(BigDecimal.ONE))
        defaultFieldsShouldNotBeFound("num5.greaterThanOrEqual=" + (DEFAULT_NUM_5.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 is less than or equal to DEFAULT_NUM_5
        defaultFieldsShouldBeFound("num5.lessThanOrEqual=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 is less than or equal to SMALLER_NUM_5
        defaultFieldsShouldNotBeFound("num5.lessThanOrEqual=" + SMALLER_NUM_5);
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 is less than DEFAULT_NUM_5
        defaultFieldsShouldNotBeFound("num5.lessThan=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 is less than (DEFAULT_NUM_5.add(BigDecimal.ONE))
        defaultFieldsShouldBeFound("num5.lessThan=" + (DEFAULT_NUM_5.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllFieldsByNum5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where num5 is greater than DEFAULT_NUM_5
        defaultFieldsShouldNotBeFound("num5.greaterThan=" + DEFAULT_NUM_5);

        // Get all the fieldsList where num5 is greater than SMALLER_NUM_5
        defaultFieldsShouldBeFound("num5.greaterThan=" + SMALLER_NUM_5);
    }


    @Test
    @Transactional
    public void getAllFieldsByDate1IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 equals to DEFAULT_DATE_1
        defaultFieldsShouldBeFound("date1.equals=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 equals to UPDATED_DATE_1
        defaultFieldsShouldNotBeFound("date1.equals=" + UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 not equals to DEFAULT_DATE_1
        defaultFieldsShouldNotBeFound("date1.notEquals=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 not equals to UPDATED_DATE_1
        defaultFieldsShouldBeFound("date1.notEquals=" + UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 in DEFAULT_DATE_1 or UPDATED_DATE_1
        defaultFieldsShouldBeFound("date1.in=" + DEFAULT_DATE_1 + "," + UPDATED_DATE_1);

        // Get all the fieldsList where date1 equals to UPDATED_DATE_1
        defaultFieldsShouldNotBeFound("date1.in=" + UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 is not null
        defaultFieldsShouldBeFound("date1.specified=true");

        // Get all the fieldsList where date1 is null
        defaultFieldsShouldNotBeFound("date1.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 is greater than or equal to DEFAULT_DATE_1
        defaultFieldsShouldBeFound("date1.greaterThanOrEqual=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 is greater than or equal to UPDATED_DATE_1
        defaultFieldsShouldNotBeFound("date1.greaterThanOrEqual=" + UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 is less than or equal to DEFAULT_DATE_1
        defaultFieldsShouldBeFound("date1.lessThanOrEqual=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 is less than or equal to SMALLER_DATE_1
        defaultFieldsShouldNotBeFound("date1.lessThanOrEqual=" + SMALLER_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 is less than DEFAULT_DATE_1
        defaultFieldsShouldNotBeFound("date1.lessThan=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 is less than UPDATED_DATE_1
        defaultFieldsShouldBeFound("date1.lessThan=" + UPDATED_DATE_1);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date1 is greater than DEFAULT_DATE_1
        defaultFieldsShouldNotBeFound("date1.greaterThan=" + DEFAULT_DATE_1);

        // Get all the fieldsList where date1 is greater than SMALLER_DATE_1
        defaultFieldsShouldBeFound("date1.greaterThan=" + SMALLER_DATE_1);
    }


    @Test
    @Transactional
    public void getAllFieldsByDate2IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date2 equals to DEFAULT_DATE_2
        defaultFieldsShouldBeFound("date2.equals=" + DEFAULT_DATE_2);

        // Get all the fieldsList where date2 equals to UPDATED_DATE_2
        defaultFieldsShouldNotBeFound("date2.equals=" + UPDATED_DATE_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date2 not equals to DEFAULT_DATE_2
        defaultFieldsShouldNotBeFound("date2.notEquals=" + DEFAULT_DATE_2);

        // Get all the fieldsList where date2 not equals to UPDATED_DATE_2
        defaultFieldsShouldBeFound("date2.notEquals=" + UPDATED_DATE_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate2IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date2 in DEFAULT_DATE_2 or UPDATED_DATE_2
        defaultFieldsShouldBeFound("date2.in=" + DEFAULT_DATE_2 + "," + UPDATED_DATE_2);

        // Get all the fieldsList where date2 equals to UPDATED_DATE_2
        defaultFieldsShouldNotBeFound("date2.in=" + UPDATED_DATE_2);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate2IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date2 is not null
        defaultFieldsShouldBeFound("date2.specified=true");

        // Get all the fieldsList where date2 is null
        defaultFieldsShouldNotBeFound("date2.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 equals to DEFAULT_DATE_3
        defaultFieldsShouldBeFound("date3.equals=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 equals to UPDATED_DATE_3
        defaultFieldsShouldNotBeFound("date3.equals=" + UPDATED_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 not equals to DEFAULT_DATE_3
        defaultFieldsShouldNotBeFound("date3.notEquals=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 not equals to UPDATED_DATE_3
        defaultFieldsShouldBeFound("date3.notEquals=" + UPDATED_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 in DEFAULT_DATE_3 or UPDATED_DATE_3
        defaultFieldsShouldBeFound("date3.in=" + DEFAULT_DATE_3 + "," + UPDATED_DATE_3);

        // Get all the fieldsList where date3 equals to UPDATED_DATE_3
        defaultFieldsShouldNotBeFound("date3.in=" + UPDATED_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 is not null
        defaultFieldsShouldBeFound("date3.specified=true");

        // Get all the fieldsList where date3 is null
        defaultFieldsShouldNotBeFound("date3.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 is greater than or equal to DEFAULT_DATE_3
        defaultFieldsShouldBeFound("date3.greaterThanOrEqual=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 is greater than or equal to UPDATED_DATE_3
        defaultFieldsShouldNotBeFound("date3.greaterThanOrEqual=" + UPDATED_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 is less than or equal to DEFAULT_DATE_3
        defaultFieldsShouldBeFound("date3.lessThanOrEqual=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 is less than or equal to SMALLER_DATE_3
        defaultFieldsShouldNotBeFound("date3.lessThanOrEqual=" + SMALLER_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 is less than DEFAULT_DATE_3
        defaultFieldsShouldNotBeFound("date3.lessThan=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 is less than UPDATED_DATE_3
        defaultFieldsShouldBeFound("date3.lessThan=" + UPDATED_DATE_3);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date3 is greater than DEFAULT_DATE_3
        defaultFieldsShouldNotBeFound("date3.greaterThan=" + DEFAULT_DATE_3);

        // Get all the fieldsList where date3 is greater than SMALLER_DATE_3
        defaultFieldsShouldBeFound("date3.greaterThan=" + SMALLER_DATE_3);
    }


    @Test
    @Transactional
    public void getAllFieldsByDate4IsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 equals to DEFAULT_DATE_4
        defaultFieldsShouldBeFound("date4.equals=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 equals to UPDATED_DATE_4
        defaultFieldsShouldNotBeFound("date4.equals=" + UPDATED_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 not equals to DEFAULT_DATE_4
        defaultFieldsShouldNotBeFound("date4.notEquals=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 not equals to UPDATED_DATE_4
        defaultFieldsShouldBeFound("date4.notEquals=" + UPDATED_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 in DEFAULT_DATE_4 or UPDATED_DATE_4
        defaultFieldsShouldBeFound("date4.in=" + DEFAULT_DATE_4 + "," + UPDATED_DATE_4);

        // Get all the fieldsList where date4 equals to UPDATED_DATE_4
        defaultFieldsShouldNotBeFound("date4.in=" + UPDATED_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 is not null
        defaultFieldsShouldBeFound("date4.specified=true");

        // Get all the fieldsList where date4 is null
        defaultFieldsShouldNotBeFound("date4.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 is greater than or equal to DEFAULT_DATE_4
        defaultFieldsShouldBeFound("date4.greaterThanOrEqual=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 is greater than or equal to UPDATED_DATE_4
        defaultFieldsShouldNotBeFound("date4.greaterThanOrEqual=" + UPDATED_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 is less than or equal to DEFAULT_DATE_4
        defaultFieldsShouldBeFound("date4.lessThanOrEqual=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 is less than or equal to SMALLER_DATE_4
        defaultFieldsShouldNotBeFound("date4.lessThanOrEqual=" + SMALLER_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsLessThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 is less than DEFAULT_DATE_4
        defaultFieldsShouldNotBeFound("date4.lessThan=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 is less than UPDATED_DATE_4
        defaultFieldsShouldBeFound("date4.lessThan=" + UPDATED_DATE_4);
    }

    @Test
    @Transactional
    public void getAllFieldsByDate4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where date4 is greater than DEFAULT_DATE_4
        defaultFieldsShouldNotBeFound("date4.greaterThan=" + DEFAULT_DATE_4);

        // Get all the fieldsList where date4 is greater than SMALLER_DATE_4
        defaultFieldsShouldBeFound("date4.greaterThan=" + SMALLER_DATE_4);
    }


    @Test
    @Transactional
    public void getAllFieldsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where uuid equals to DEFAULT_UUID
        defaultFieldsShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the fieldsList where uuid equals to UPDATED_UUID
        defaultFieldsShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllFieldsByUuidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where uuid not equals to DEFAULT_UUID
        defaultFieldsShouldNotBeFound("uuid.notEquals=" + DEFAULT_UUID);

        // Get all the fieldsList where uuid not equals to UPDATED_UUID
        defaultFieldsShouldBeFound("uuid.notEquals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllFieldsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultFieldsShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the fieldsList where uuid equals to UPDATED_UUID
        defaultFieldsShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllFieldsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where uuid is not null
        defaultFieldsShouldBeFound("uuid.specified=true");

        // Get all the fieldsList where uuid is null
        defaultFieldsShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByBoolIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where bool equals to DEFAULT_BOOL
        defaultFieldsShouldBeFound("bool.equals=" + DEFAULT_BOOL);

        // Get all the fieldsList where bool equals to UPDATED_BOOL
        defaultFieldsShouldNotBeFound("bool.equals=" + UPDATED_BOOL);
    }

    @Test
    @Transactional
    public void getAllFieldsByBoolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where bool not equals to DEFAULT_BOOL
        defaultFieldsShouldNotBeFound("bool.notEquals=" + DEFAULT_BOOL);

        // Get all the fieldsList where bool not equals to UPDATED_BOOL
        defaultFieldsShouldBeFound("bool.notEquals=" + UPDATED_BOOL);
    }

    @Test
    @Transactional
    public void getAllFieldsByBoolIsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where bool in DEFAULT_BOOL or UPDATED_BOOL
        defaultFieldsShouldBeFound("bool.in=" + DEFAULT_BOOL + "," + UPDATED_BOOL);

        // Get all the fieldsList where bool equals to UPDATED_BOOL
        defaultFieldsShouldNotBeFound("bool.in=" + UPDATED_BOOL);
    }

    @Test
    @Transactional
    public void getAllFieldsByBoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where bool is not null
        defaultFieldsShouldBeFound("bool.specified=true");

        // Get all the fieldsList where bool is null
        defaultFieldsShouldNotBeFound("bool.specified=false");
    }

    @Test
    @Transactional
    public void getAllFieldsByEnumerationIsEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where enumeration equals to DEFAULT_ENUMERATION
        defaultFieldsShouldBeFound("enumeration.equals=" + DEFAULT_ENUMERATION);

        // Get all the fieldsList where enumeration equals to UPDATED_ENUMERATION
        defaultFieldsShouldNotBeFound("enumeration.equals=" + UPDATED_ENUMERATION);
    }

    @Test
    @Transactional
    public void getAllFieldsByEnumerationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where enumeration not equals to DEFAULT_ENUMERATION
        defaultFieldsShouldNotBeFound("enumeration.notEquals=" + DEFAULT_ENUMERATION);

        // Get all the fieldsList where enumeration not equals to UPDATED_ENUMERATION
        defaultFieldsShouldBeFound("enumeration.notEquals=" + UPDATED_ENUMERATION);
    }

    @Test
    @Transactional
    public void getAllFieldsByEnumerationIsInShouldWork() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where enumeration in DEFAULT_ENUMERATION or UPDATED_ENUMERATION
        defaultFieldsShouldBeFound("enumeration.in=" + DEFAULT_ENUMERATION + "," + UPDATED_ENUMERATION);

        // Get all the fieldsList where enumeration equals to UPDATED_ENUMERATION
        defaultFieldsShouldNotBeFound("enumeration.in=" + UPDATED_ENUMERATION);
    }

    @Test
    @Transactional
    public void getAllFieldsByEnumerationIsNullOrNotNull() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        // Get all the fieldsList where enumeration is not null
        defaultFieldsShouldBeFound("enumeration.specified=true");

        // Get all the fieldsList where enumeration is null
        defaultFieldsShouldNotBeFound("enumeration.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFieldsShouldBeFound(String filter) throws Exception {
        restFieldsMockMvc.perform(get("/api/fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fields.getId().intValue())))
            .andExpect(jsonPath("$.[*].str").value(hasItem(DEFAULT_STR)))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1)))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2.intValue())))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3.doubleValue())))
            .andExpect(jsonPath("$.[*].num4").value(hasItem(DEFAULT_NUM_4.doubleValue())))
            .andExpect(jsonPath("$.[*].num5").value(hasItem(DEFAULT_NUM_5.intValue())))
            .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1.toString())))
            .andExpect(jsonPath("$.[*].date2").value(hasItem(DEFAULT_DATE_2.toString())))
            .andExpect(jsonPath("$.[*].date3").value(hasItem(sameInstant(DEFAULT_DATE_3))))
            .andExpect(jsonPath("$.[*].date4").value(hasItem(DEFAULT_DATE_4.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].bool").value(hasItem(DEFAULT_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].enumeration").value(hasItem(DEFAULT_ENUMERATION.toString())))
            .andExpect(jsonPath("$.[*].blobContentType").value(hasItem(DEFAULT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB))))
            .andExpect(jsonPath("$.[*].blob2ContentType").value(hasItem(DEFAULT_BLOB_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_2))))
            .andExpect(jsonPath("$.[*].blob3").value(hasItem(DEFAULT_BLOB_3.toString())));

        // Check, that the count call also returns 1
        restFieldsMockMvc.perform(get("/api/fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFieldsShouldNotBeFound(String filter) throws Exception {
        restFieldsMockMvc.perform(get("/api/fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFieldsMockMvc.perform(get("/api/fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFields() throws Exception {
        // Get the fields
        restFieldsMockMvc.perform(get("/api/fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFields() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields
        Fields updatedFields = fieldsRepository.findById(fields.getId()).get();
        // Disconnect from session so that the updates on updatedFields are not directly saved in db
        em.detach(updatedFields);
        updatedFields
            .str(UPDATED_STR)
            .num1(UPDATED_NUM_1)
            .num2(UPDATED_NUM_2)
            .num3(UPDATED_NUM_3)
            .num4(UPDATED_NUM_4)
            .num5(UPDATED_NUM_5)
            .date1(UPDATED_DATE_1)
            .date2(UPDATED_DATE_2)
            .date3(UPDATED_DATE_3)
            .date4(UPDATED_DATE_4)
            .uuid(UPDATED_UUID)
            .bool(UPDATED_BOOL)
            .enumeration(UPDATED_ENUMERATION)
            .blob(UPDATED_BLOB)
            .blobContentType(UPDATED_BLOB_CONTENT_TYPE)
            .blob2(UPDATED_BLOB_2)
            .blob2ContentType(UPDATED_BLOB_2_CONTENT_TYPE)
            .blob3(UPDATED_BLOB_3);
        FieldsDTO fieldsDTO = fieldsMapper.toDto(updatedFields);

        restFieldsMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getStr()).isEqualTo(UPDATED_STR);
        assertThat(testFields.getNum1()).isEqualTo(UPDATED_NUM_1);
        assertThat(testFields.getNum2()).isEqualTo(UPDATED_NUM_2);
        assertThat(testFields.getNum3()).isEqualTo(UPDATED_NUM_3);
        assertThat(testFields.getNum4()).isEqualTo(UPDATED_NUM_4);
        assertThat(testFields.getNum5()).isEqualTo(UPDATED_NUM_5);
        assertThat(testFields.getDate1()).isEqualTo(UPDATED_DATE_1);
        assertThat(testFields.getDate2()).isEqualTo(UPDATED_DATE_2);
        assertThat(testFields.getDate3()).isEqualTo(UPDATED_DATE_3);
        assertThat(testFields.getDate4()).isEqualTo(UPDATED_DATE_4);
        assertThat(testFields.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFields.isBool()).isEqualTo(UPDATED_BOOL);
        assertThat(testFields.getEnumeration()).isEqualTo(UPDATED_ENUMERATION);
        assertThat(testFields.getBlob()).isEqualTo(UPDATED_BLOB);
        assertThat(testFields.getBlobContentType()).isEqualTo(UPDATED_BLOB_CONTENT_TYPE);
        assertThat(testFields.getBlob2()).isEqualTo(UPDATED_BLOB_2);
        assertThat(testFields.getBlob2ContentType()).isEqualTo(UPDATED_BLOB_2_CONTENT_TYPE);
        assertThat(testFields.getBlob3()).isEqualTo(UPDATED_BLOB_3);

        // Validate the Fields in Elasticsearch
        verify(mockFieldsSearchRepository, times(1)).save(testFields);
    }

    @Test
    @Transactional
    public void updateNonExistingFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldsMockMvc.perform(put("/api/fields")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Fields in Elasticsearch
        verify(mockFieldsSearchRepository, times(0)).save(fields);
    }

    @Test
    @Transactional
    public void deleteFields() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);

        int databaseSizeBeforeDelete = fieldsRepository.findAll().size();

        // Delete the fields
        restFieldsMockMvc.perform(delete("/api/fields/{id}", fields.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Fields in Elasticsearch
        verify(mockFieldsSearchRepository, times(1)).deleteById(fields.getId());
    }

    @Test
    @Transactional
    public void searchFields() throws Exception {
        // Initialize the database
        fieldsRepository.saveAndFlush(fields);
        when(mockFieldsSearchRepository.search(queryStringQuery("id:" + fields.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fields), PageRequest.of(0, 1), 1));
        // Search the fields
        restFieldsMockMvc.perform(get("/api/_search/fields?query=id:" + fields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fields.getId().intValue())))
            .andExpect(jsonPath("$.[*].str").value(hasItem(DEFAULT_STR)))
            .andExpect(jsonPath("$.[*].num1").value(hasItem(DEFAULT_NUM_1)))
            .andExpect(jsonPath("$.[*].num2").value(hasItem(DEFAULT_NUM_2.intValue())))
            .andExpect(jsonPath("$.[*].num3").value(hasItem(DEFAULT_NUM_3.doubleValue())))
            .andExpect(jsonPath("$.[*].num4").value(hasItem(DEFAULT_NUM_4.doubleValue())))
            .andExpect(jsonPath("$.[*].num5").value(hasItem(DEFAULT_NUM_5.intValue())))
            .andExpect(jsonPath("$.[*].date1").value(hasItem(DEFAULT_DATE_1.toString())))
            .andExpect(jsonPath("$.[*].date2").value(hasItem(DEFAULT_DATE_2.toString())))
            .andExpect(jsonPath("$.[*].date3").value(hasItem(sameInstant(DEFAULT_DATE_3))))
            .andExpect(jsonPath("$.[*].date4").value(hasItem(DEFAULT_DATE_4.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].bool").value(hasItem(DEFAULT_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].enumeration").value(hasItem(DEFAULT_ENUMERATION.toString())))
            .andExpect(jsonPath("$.[*].blobContentType").value(hasItem(DEFAULT_BLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB))))
            .andExpect(jsonPath("$.[*].blob2ContentType").value(hasItem(DEFAULT_BLOB_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blob2").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_2))))
            .andExpect(jsonPath("$.[*].blob3").value(hasItem(DEFAULT_BLOB_3.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fields.class);
        Fields fields1 = new Fields();
        fields1.setId(1L);
        Fields fields2 = new Fields();
        fields2.setId(fields1.getId());
        assertThat(fields1).isEqualTo(fields2);
        fields2.setId(2L);
        assertThat(fields1).isNotEqualTo(fields2);
        fields1.setId(null);
        assertThat(fields1).isNotEqualTo(fields2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldsDTO.class);
        FieldsDTO fieldsDTO1 = new FieldsDTO();
        fieldsDTO1.setId(1L);
        FieldsDTO fieldsDTO2 = new FieldsDTO();
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
        fieldsDTO2.setId(fieldsDTO1.getId());
        assertThat(fieldsDTO1).isEqualTo(fieldsDTO2);
        fieldsDTO2.setId(2L);
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
        fieldsDTO1.setId(null);
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fieldsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fieldsMapper.fromId(null)).isNull();
    }
}
