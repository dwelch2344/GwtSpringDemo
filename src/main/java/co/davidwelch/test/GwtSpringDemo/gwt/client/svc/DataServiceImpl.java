package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public class DataServiceImpl extends AbstractDataService{
	
	public DataServiceImpl() {
		super( (AutoBeanFactory) GWT.create(MyFactory.class) );
	}
	
	public void getPerson(Callback<IPerson> callback){
		addCallback(callback);
	}

}
