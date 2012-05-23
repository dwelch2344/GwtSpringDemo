package co.davidwelch.test.GwtSpringDemo.gwt.client.util;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;
import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface MyFactory extends AutoBeanFactory{

	AutoBean<IPerson> person();
	AutoBean<IAddress> address();
}
