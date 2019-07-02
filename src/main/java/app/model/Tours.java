package app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name="tours")
public class Tours{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer tourId;
	
	@Column(name="title")
	private String tourTitle;
	
	@Column(name="description")
	private String tourDescription;
	
	@Column(name="status")
	private Integer tourStatus;
	
	@Column(name="image")
	private String tourImage;
	
	@Column(name="price")
	private Integer tourPrice;
	
	
	@ManyToOne
	  @JoinColumn(name = "id_from", nullable = false)
	private Places from;
	
	@ManyToOne
	  @JoinColumn(name = "id_destination", nullable = false)
	private Places destination;
	
	@ManyToOne
	  @JoinColumn(name = "car_id")
	private Car car;

	@Column(name="rating")
	private Integer tourRating;

	@Column(name="saleoff")
	private Integer tourSaleOff;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tour")
	 private List<TourDetails> tourdetails;
	
	public List<TourDetails> getTourdetails() {
		return tourdetails;
	}

	public void setTourdetails(List<TourDetails> tourdetails) {
		this.tourdetails = tourdetails;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Integer getTourSaleOff() {
		return tourSaleOff;
	}

	public void setTourSaleOff(Integer tourSaleOff) {
		this.tourSaleOff = tourSaleOff;
	}

	public Integer getTourPrice() {
		return tourPrice;
	}

	public void setTourPrice(Integer tourPrice) {
		this.tourPrice = tourPrice;
	}

	public Places getFrom() {
		return from;
	}

	public void setFrom(Places from) {
		this.from = from;
	}

	public Places getDestination() {
		return destination;
	}

	public void setDestination(Places destination) {
		this.destination = destination;
	}
	
	
	public Integer getTourRating() {
		return tourRating;
	}

	public void setTourRating(Integer tourRating) {
		this.tourRating = tourRating;
	}

	public Integer getTourId() {
		return tourId;
	}

	public void setTourId(Integer tourId) {
		this.tourId = tourId;
	}

	public String getTourTitle() {
		return tourTitle;
	}

	public void setTourTitle(String tourTitle) {
		this.tourTitle = tourTitle;
	}

	public String getTourDescription() {
		return tourDescription;
	}

	public void setTourDescription(String tourDescription) {
		this.tourDescription = tourDescription;
	}

	public Integer getTourStatus() {
		return tourStatus;
	}

	public void setTourStatus(Integer tourStatus) {
		this.tourStatus = tourStatus;
	}

	public String getTourImage() {
		return tourImage;
	}

	public void setTourImage(String tourImage) {
		this.tourImage = tourImage;
	}
}