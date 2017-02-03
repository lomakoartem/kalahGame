package ua.kalahgame.infrastructure.exceptions;

import org.springframework.stereotype.Component;

/**
 * Created by a.lomako on 1/30/2017.
 */

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private long entityId;

    public EntityNotFoundException(long entityId) {
        this.entityId = entityId;
    }

    public long getEntityId() {
        return entityId;
    }
}