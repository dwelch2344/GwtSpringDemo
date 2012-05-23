package co.davidwelch.test.GwtSpringDemo.model;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;


public class Address implements IAddress {
	
	private String street, street2, city, providence, country, postalCode;

	@Override
	public String getStreet() {
		return street;
	}

	@Override
	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String getStreet2() {
		return street2;
	}

	@Override
	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getProvidence() {
		return providence;
	}

	@Override
	public void setProvidence(String providence) {
		this.providence = providence;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
}
