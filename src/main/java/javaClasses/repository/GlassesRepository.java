package javaClasses.repository;

import javaClasses.entity.Glasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий с "Очками"
 */
@Repository
public interface GlassesRepository extends JpaRepository<Glasses, Long> {
    /**
     * Поиск элементов, значение диоптрий
     * которых выше введённого порога
     * @param diopters порог диоптрий
     * @return список с найденными элементами
     */
    List<Glasses> findGlassesByDioptersGreaterThan(double diopters);

    /**
     * Вывести все элементы, отсортированные по id
     * @return отсортированный список
     */
    List<Glasses> findAllByOrderById();
}