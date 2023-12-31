package eu.senla.dutov.mapper;

import java.util.List;

public interface AbstractMapper<R, T, U> {
    String SPRING = "spring";

    T toDTO(U model);

    U toModel(R dto);

    List<T> toDTOList(List<U> models);
}
