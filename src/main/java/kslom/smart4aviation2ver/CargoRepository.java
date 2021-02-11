package kslom.smart4aviation2ver;

import kslom.smart4aviation2ver.dto.CargoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CargoRepository extends JpaRepository<Flight.CargoAbs.Cargo,Integer>
{
	<T> Set<T> findBy(Class<T> type);

	List<CargoDto> findByFlightId(Integer flightId);

}
