package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Uo2oPassportDTOMFSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Uo2oPassportDTOMFSearchRepositoryMockConfiguration {

    @MockBean
    private Uo2oPassportDTOMFSearchRepository mockUo2oPassportDTOMFSearchRepository;

}
