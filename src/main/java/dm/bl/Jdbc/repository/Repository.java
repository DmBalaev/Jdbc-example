package dm.bl.Jdbc.repository;

import java.util.List;
import java.util.Optional;

public interface Repository <E>{
    int save(E entity);

    Optional<E> findById(Long id);

    int update(E entity);

    int deleteById(Long id);

    List<E> findAll();
}
