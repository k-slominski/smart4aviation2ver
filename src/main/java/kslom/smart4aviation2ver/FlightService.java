package kslom.smart4aviation2ver;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class FlightService
{
	private FlightRepository flightRepository;
	private CargoRepository cargoRepository;
	private BaggageRepository baggageRepository;

	FlightService (FlightRepository flightRepository, CargoRepository cargoRepository, BaggageRepository baggageRepository)
	{
		this.flightRepository = flightRepository;
		this.cargoRepository = cargoRepository;
		this.baggageRepository=baggageRepository;
	}

	double cargoWeight (Integer flightId)
	{

		return cargoRepository.findByFlightId(flightId).stream().map(c -> {
			double weight=c.getWeight();
			if (c.getWeightUnit().equals("lb"))	weight=weight/2.2046;
			return c.getPiecesOfCargo()*weight;
			}).collect(Collectors.summingDouble(Double::doubleValue));

	}

	double baggageWeight (Integer flightId)
	{

		return baggageRepository.findByFlightId(flightId).stream().map(c -> {
			double weight=c.getWeight();
			if (c.getWeightUnit().equals("lb"))	weight=weight/2.2046;
			return c.getPiecesOfCargo()*weight;
		}).collect(Collectors.summingDouble(Double::doubleValue));

	}

	long numberOfFlightsDepartingFromAirport (LocalDate date, String airportCode)
	{
		return flightRepository.findByDepartureDateAndAirportCode(date,airportCode).stream().filter(f-> f.getAirportCodeDeparture().equals(airportCode)).count();
	}

	long numberOfFlightsArrivingToTheAirport (LocalDate date, String airportCode)
	{
		return flightRepository.findByDepartureDateAndAirportCode(date,airportCode).stream().filter(f-> f.getAirportCodeArrival().equals(airportCode)).count();
	}

	int totalNumberOfBaggageOfOneFlight(Integer flightId)
	{
		return baggageRepository.findByFlightId(flightId).stream().map(b-> b.getPiecesOfCargo()).collect(Collectors.summingInt(Short::shortValue));
	}

	int totalNumberOfBaggageArrivingToTheAirport(LocalDate date, String airportCode)
	{
		return flightRepository.findByDepartureDateAndAirportCode(date, airportCode).stream()
				.filter(f->f.getAirportCodeArrival().equals(airportCode))
				.map(f->totalNumberOfBaggageOfOneFlight(f.getId()))
				.collect(Collectors.summingInt(Integer::intValue));
	}

	int totalNumberOfBaggageDepartingFromTheAirport(LocalDate date, String airportCode)
	{
		return flightRepository.findByDepartureDateAndAirportCode(date, airportCode).stream()
				.filter(f->f.getAirportCodeDeparture().equals(airportCode))
				.map(f->totalNumberOfBaggageOfOneFlight(f.getId()))
				.collect(Collectors.summingInt(Integer::intValue));
	}




}
