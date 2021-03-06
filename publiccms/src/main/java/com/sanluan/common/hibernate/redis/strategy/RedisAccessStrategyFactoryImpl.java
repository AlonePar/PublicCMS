package com.sanluan.common.hibernate.redis.strategy;

import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;

import com.sanluan.common.base.Base;
import com.sanluan.common.hibernate.redis.regions.RedisCollectionRegion;
import com.sanluan.common.hibernate.redis.regions.RedisEntityRegion;
import com.sanluan.common.hibernate.redis.regions.RedisNaturalIdRegion;

/**
 *
 * RedisAccessStrategyFactoryImpl
 * 
 */
public class RedisAccessStrategyFactoryImpl extends Base implements RedisAccessStrategyFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public EntityRegionAccessStrategy createEntityRegionAccessStrategy(RedisEntityRegion entityRegion, AccessType accessType) {
        switch (accessType) {
        case READ_ONLY:
            if (entityRegion.getCacheDataDescription().isMutable()) {
                log.warn("read-only cache configured for mutable entity regionName=" + entityRegion.getName());
            }
            return new ReadOnlyRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getOptions());
        case READ_WRITE:
            return new ReadWriteRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getOptions());
        case NONSTRICT_READ_WRITE:
            return new NonStrictReadWriteRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getOptions());
        case TRANSACTIONAL:
            return new TransactionalRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getOptions());
        default:
            throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(RedisCollectionRegion collectionRegion,
            AccessType accessType) {
        switch (accessType) {
        case READ_ONLY:
            if (collectionRegion.getCacheDataDescription().isMutable()) {
                log.warn("read-only cache configured for mutable entity collectionRegionName=" + collectionRegion.getName());
            }
            return new ReadOnlyRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getOptions());
        case READ_WRITE:
            return new ReadWriteRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getOptions());
        case NONSTRICT_READ_WRITE:
            return new NonStrictReadWriteRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getOptions());
        case TRANSACTIONAL:
            return new TransactionalRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getOptions());
        default:
            throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    public NaturalIdRegionAccessStrategy createNaturalIdRegionAccessStrategy(RedisNaturalIdRegion naturalIdRegion,
            AccessType accessType) {
        switch (accessType) {
        case READ_ONLY:
            if (naturalIdRegion.getCacheDataDescription().isMutable()) {
                log.warn("read-only cache configured for mutable entity naturalIdRegion=" + naturalIdRegion.getName());
            }
            return new ReadOnlyRedisNaturalIdRegionAccessStrategy(naturalIdRegion, naturalIdRegion.getOptions());
        case READ_WRITE:
            return new ReadWriteRedisNaturalIdRegionAccessStrategy(naturalIdRegion, naturalIdRegion.getOptions());
        case NONSTRICT_READ_WRITE:
            return new NonStrictReadWriteRedisNaturalIdRegionAccessStrategy(naturalIdRegion, naturalIdRegion.getOptions());
        case TRANSACTIONAL:
            return new TransactionalRedisNaturalIdRegionAccessStrategy(naturalIdRegion, naturalIdRegion.getOptions());
        default:
            throw new IllegalArgumentException("unrecognized access strategy type [" + accessType + "]");
        }
    }
}
