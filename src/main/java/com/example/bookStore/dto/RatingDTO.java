package com.example.bookStore.dto;

public class RatingDTO {

	private double rating;
	private String text;
	private long bookId;
	
	
	
	public RatingDTO() {
		super();
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	
	
}
