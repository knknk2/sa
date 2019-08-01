package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link To2mPersonInfSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class To2mPersonInfSearchRepositoryMockConfiguration {

    @MockBean
    private To2mPersonInfSearchRepository mockTo2mPersonInfSearchRepository;

}
