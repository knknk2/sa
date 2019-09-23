package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Uo2oCitizenSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Uo2oCitizenSearchRepositoryMockConfiguration {

    @MockBean
    private Uo2oCitizenSearchRepository mockUo2oCitizenSearchRepository;

}
