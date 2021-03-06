package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Uo2oCitizenDTOMFSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Uo2oCitizenDTOMFSearchRepositoryMockConfiguration {

    @MockBean
    private Uo2oCitizenDTOMFSearchRepository mockUo2oCitizenDTOMFSearchRepository;

}
