package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT f FROM Offer as f " +
            "WHERE f.apartment.apartmentType = :at " +
            "ORDER BY f.apartment.area DESC, f.price")
    List<Offer> findBestOffers(@Param("at") ApartmentType apartmentType);
}
