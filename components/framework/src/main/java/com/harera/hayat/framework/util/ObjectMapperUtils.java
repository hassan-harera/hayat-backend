package com.harera.hayat.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectMapperUtils {

    private static final ModelMapper mapper = new ModelMapper();

    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see
     * {@link MatchingStrategies} Custom mappings are added using
     * {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    }

    /**
     * Hide from public usage.
     */
    private ObjectMapperUtils() {
    }

    /**
     * <p>
     * Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D> type of result object.
     * @param <T> type of source object to map from.
     * @param entity entity that needs to be mapped.
     * @param outClass class of result object.
     * @return new object of <code>outClass</code> type.
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        if (entity == null) {
            return null;
        }
        return mapper.map(entity, outClass);
    }

    /**
     * <p>
     * Note: outClass object must have default constructor with no arguments</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outCLass class of result list element
     * @param <D> type of objects in result list
     * @param <T> type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type.
     */
    public static <D, T> List<D> mapAll(final Collection<T> entityList,
                    Class<D> outCLass) {
        if (CollectionUtils.isEmpty(entityList)) {
            return new ArrayList<>();
        }
        return entityList.stream().map(entity -> map(entity, outCLass)).toList();
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param <S>
     * @param <D>
     * @param source object to map from
     * @param destination object to map to
     * @return
     */
    public static <S, D> D map(final S source, D destination) {
        mapper.map(source, destination);
        return destination;
    }
}
