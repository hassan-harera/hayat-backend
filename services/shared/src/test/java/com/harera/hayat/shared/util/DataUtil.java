package com.harera.hayat.shared.util;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.harera.hayat.framework.model.BaseEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataUtil {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void delete(Object... entities) {
        for (Object entity : entities) {
            if (entity instanceof Collection) {
                delete((Collection<?>) entity);
            } else {
                delete(entity);
            }
        }
    }

    @Transactional
    void delete(Collection<?> entityCollection) {
        for (Object entity : entityCollection) {
            delete(entity);
        }
    }

    @Transactional
    void delete(Object entity) {
        try {
            if (entity instanceof BaseEntity) {
                final BaseEntity baseEntity = (BaseEntity) entityManager
                                .find(entity.getClass(), ((BaseEntity) entity).getId());
                entityManager.remove(baseEntity);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
