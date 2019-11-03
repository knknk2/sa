package com.sa.sa.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Caffeine caffeine = jHipsterProperties.getCache().getCaffeine();

        CaffeineConfiguration caffeineConfiguration = new CaffeineConfiguration();
        caffeineConfiguration.setMaximumSize(OptionalLong.of(caffeine.getMaxEntries()));
        caffeineConfiguration.setExpireAfterWrite(OptionalLong.of(TimeUnit.SECONDS.toNanos(caffeine.getTimeToLiveSeconds())));
        caffeineConfiguration.setStatisticsEnabled(true);
        jcacheConfiguration = caffeineConfiguration;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.sa.sa.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.sa.sa.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.sa.sa.domain.User.class.getName());
            createCache(cm, com.sa.sa.domain.Authority.class.getName());
            createCache(cm, com.sa.sa.domain.User.class.getName() + ".authorities");
            createCache(cm, com.sa.sa.domain.Fields.class.getName());
            createCache(cm, com.sa.sa.domain.Bo2mOwnerDTO.class.getName());
            createCache(cm, com.sa.sa.domain.Bo2mOwnerDTO.class.getName() + ".bo2mCarDTOS");
            createCache(cm, com.sa.sa.domain.Bo2mOwner.class.getName());
            createCache(cm, com.sa.sa.domain.Bo2mOwner.class.getName() + ".bo2mCars");
            createCache(cm, com.sa.sa.domain.Bo2mCar.class.getName());
            createCache(cm, com.sa.sa.domain.Bo2mCarDTO.class.getName());
            createCache(cm, com.sa.sa.domain.Um2oOwner.class.getName());
            createCache(cm, com.sa.sa.domain.Um2oCar.class.getName());
            createCache(cm, com.sa.sa.domain.To2mPerson.class.getName());
            createCache(cm, com.sa.sa.domain.To2mPerson.class.getName() + ".to2mOwnedCars");
            createCache(cm, com.sa.sa.domain.To2mPerson.class.getName() + ".to2mDrivedCars");
            createCache(cm, com.sa.sa.domain.To2mPersonInf.class.getName());
            createCache(cm, com.sa.sa.domain.To2mPersonInf.class.getName() + ".to2mOwnedCarInfs");
            createCache(cm, com.sa.sa.domain.To2mPersonInf.class.getName() + ".to2mDrivedCarInfs");
            createCache(cm, com.sa.sa.domain.To2mCar.class.getName());
            createCache(cm, com.sa.sa.domain.To2mCarInf.class.getName());
            createCache(cm, com.sa.sa.domain.M2mDriver.class.getName());
            createCache(cm, com.sa.sa.domain.M2mDriver.class.getName() + ".m2mCars");
            createCache(cm, com.sa.sa.domain.M2mDriverDTOMF.class.getName());
            createCache(cm, com.sa.sa.domain.M2mDriverDTOMF.class.getName() + ".m2mCarDTOMFS");
            createCache(cm, com.sa.sa.domain.M2mCarDTOMF.class.getName());
            createCache(cm, com.sa.sa.domain.M2mCarDTOMF.class.getName() + ".m2mDriverDTOMFS");
            createCache(cm, com.sa.sa.domain.M2mCar.class.getName());
            createCache(cm, com.sa.sa.domain.M2mCar.class.getName() + ".m2mDrivers");
            createCache(cm, com.sa.sa.domain.O2oDriver.class.getName());
            createCache(cm, com.sa.sa.domain.O2oCar.class.getName());
            createCache(cm, com.sa.sa.domain.Uo2oPassport.class.getName());
            createCache(cm, com.sa.sa.domain.Uo2oPassportDTOMF.class.getName());
            createCache(cm, com.sa.sa.domain.Uo2oCitizen.class.getName());
            createCache(cm, com.sa.sa.domain.Uo2oCitizenDTOMF.class.getName());
            // jhipster-needle-caffeine-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
