package kslom.smart4aviation2ver.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="flights")

class Flight
{
	@Id
	@GeneratedValue(generator="inc")
	@GenericGenerator(name="inc",strategy="increment")
	@Column (name="id")
	int id;
	@Column (name="flight_number")
	short flightNumber;
	@Column (name="airport_code_departure")

	String airportCodeDeparture;
	@Column (name="airport_code_arrival")

	String airportCodeArrival;
	@Column (name="departure_date")
	LocalDateTime departureDate;
	@OneToMany (cascade= CascadeType.ALL)
	@JoinColumn(name="flight_id")
	Set<CargoAbs.Cargo> cargo = new HashSet<>();

	@OneToMany (cascade= CascadeType.ALL)
	@JoinColumn(name="flight_id")
	Set<CargoAbs.Baggage> baggage = new HashSet<>();


	public Flight() {}


	private Flight (final int id, final short flightNumber, final String airportCodeDeparture,
					final String airportCodeArrival, final LocalDateTime departureDate,
					final Set<CargoAbs.Cargo> cargo, final Set<CargoAbs.Baggage>baggage)

	{
		this.id = id;
		this.flightNumber = flightNumber;
		this.airportCodeDeparture = airportCodeDeparture;
		this.airportCodeArrival = airportCodeArrival;
		this.departureDate = departureDate;
		this.cargo=cargo;
		this.baggage=baggage;

	}

	@Entity
	@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
	static class CargoAbs
		{
			@Id
			@Column(name="id")
			int id;
			@Column (name="pieces_of_cargo")
			short piecesOfCargo;
			@Column (name="weight")
			short weight;
			@Enumerated(EnumType.STRING)
			@Column (name="weight_unit")
			WeightUnit weightUnit;

			@JoinColumn (name="flight_id")
			private int flightId;

			enum WeightUnit
			{kg, lb}


			@Entity
			@Table(name="cargo")
			static class Cargo extends CargoAbs
			{
				public Cargo()	{}

				public Cargo(int id, short piecesOfCargo,short weight, WeightUnit weightUnit)
				{
					this.id=id;
					this.piecesOfCargo=piecesOfCargo;
					this.weight=weight;
					this.weightUnit=weightUnit;
				}

			}

			@Entity
			@Table(name="baggage")
			static class Baggage extends CargoAbs
			{
				public Baggage()	{}

				public Baggage(int id, short piecesOfCargo,short weight, WeightUnit weightUnit)
				{
					this.id=id;
					this.piecesOfCargo=piecesOfCargo;
					this.weight=weight;
					this.weightUnit=weightUnit;
				}

			}

		}


}