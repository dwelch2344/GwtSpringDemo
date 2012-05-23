package co.davidwelch.test.GwtSpringDemo.model;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;
import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;


public class Person implements IPerson {

	private IAddress address;
	private String name;
	
	@Override
	public IAddress getAddress() {
		return address;
	}

	@Override
	public void setAddress(IAddress address) {
		this.address = address;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
}
