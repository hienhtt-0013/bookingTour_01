package app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tour_details")
public class TourDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer tourDetailsId;
	
	@Column(name="tour_date")
	private Date tourDetailsDate;
	
	 @ManyToOne
	 @JoinColumn(name = "tour_id", nullable = false)
	 private Tours tour;	
	
	@Column(name="num_person")
	private Integer tourDetailsNumPerson;


	public Integer getTourDetailsId() {
		return tourDetailsId;
	}

	public void setTourDetailsId(Integer tourDetailsId) {
		this.tourDetailsId = tourDetailsId;
	}

	public Date getTourDetailsDate() {
		return tourDetailsDate;
	}

	public void setTourDetailsDate(Date tourDetailsDate) {
		this.tourDetailsDate = tourDetailsDate;
	}

	public Tours getTour() {
		return tour;
	}

	public void setTour(Tours tour) {
		this.tour = tour;
	}

	public Integer getTourDetailsNumPerson() {
		return tourDetailsNumPerson;
	}

	public void setTourDetailsNumPerson(Integer tourDetailsNumPerson) {
		this.tourDetailsNumPerson = tourDetailsNumPerson;
	}
}