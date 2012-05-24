package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * A simple implementation of {@link AbstractJsonService} that supplies it our very-basic 
 * {@link AutoBeanFactory} implementation.
 */
public class JsonServiceImpl extends AbstractJsonService{
	
	public JsonServiceImpl() {
		super( (AutoBeanFactory) GWT.create(MyFactory.class) );
	}
	
	/**
	 * Takes a parameterized callback and passes it to the underlying factory.  
	 * @param callback
	 */
	public void getPerson(JsonCallback<IPerson> callback){
		Integer callNumber = getRequest("http://localhost:8080/GwtSpringDemo/test2.gwt-json", callback);
		GWT.log("Made call for a person using call ID: " + callNumber);
	}

}
