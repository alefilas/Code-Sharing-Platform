package platform.repository;

import org.springframework.data.repository.CrudRepository;
import platform.entity.Code;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code, Long> {

    List<Code> findTop10ByTimeIsNullAndViewsIsNullOrderByDateDesc();

    Optional<Code> findByUuid(String UUID);

}
