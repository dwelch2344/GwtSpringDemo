package co.davidwelch.test.GwtSpringDemo.gwt.client.model;


public interface IPerson {

	public abstract IAddress getAddress();

	public abstract void setAddress(IAddress address);

	public abstract String getName();

	public abstract void setName(String name);

}