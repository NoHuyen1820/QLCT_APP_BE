package com.qlct.core.mapper;

import java.util.List;

public interface EntityMapper<E, D> {

    /* To DTO object.
     *
     * @param entity {@link E}
     * @return {@link D}
     */
    D toDto(E entity);

    /* To entity object.
     *
     * @param dto {@link D}
     * @return {@link E}
     */
    E toEntity(D dto);

    /* To dtos.
     *
     * @param entities the entities
     * @return the list
     */
    List<D> toDtos(List<E> entities);

    /* To entities.
     *
     * @param dtos the dtos
     * @return the list
     */
    List<E> toEntities(List<D> dtos);
}
