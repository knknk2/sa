package com.sa.sa.service.impl;

import com.sa.sa.service.SimpleServiceImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SimpleServiceImplServiceImpl implements SimpleServiceImplService {

    private final Logger log = LoggerFactory.getLogger(SimpleServiceImplServiceImpl.class);

}
