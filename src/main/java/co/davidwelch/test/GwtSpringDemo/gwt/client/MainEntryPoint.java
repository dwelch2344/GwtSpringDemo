package co.davidwelch.test.GwtSpringDemo.gwt.client;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;
import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.JsonServiceImpl;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.JsonCallback;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;
import co.davidwelch.test.GwtSpringDemo.gwt.client.view.MainView;
import co.davidwelch.test.GwtSpringDemo.gwt.client.view.PersonView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class MainEntryPoint implements EntryPoint {

	private MyFactory factory = GWT.create(MyFactory.class);
	
	@Override
	public void onModuleLoad() {
		// makeJsonCall();
		//makeJsonPost();
		
		MainView view = new MainView();
		RootPanel.get().add( view );
	}

	public void makeJsonCall(){
		JsonServiceImpl service = new JsonServiceImpl();
		service.getPerson(new JsonCallback<IPerson>(IPerson.class) {
			
			@Override
			public void onComplete(IPerson person) {
				RootPanel.get().add( new PersonView(person) );
			}
		});
	}
	
	public void makeJsonPost(){
		AutoBean<IAddress> abean = factory.address();
		IAddress address = abean.as();
		
		address.setStreet("ClientVille");
		address.setCity("SLC");
		address.setPostalCode("84109");
		
		
		JsonServiceImpl service = new JsonServiceImpl();
		service.postAddress(address, new JsonCallback<IPerson>(IPerson.class) {
			
			@Override
			public void onComplete(IPerson person) {
				RootPanel.get().add( new PersonView(person) );
			}
		});
	}

	
	/**
	 * Just an example of getting a JSON representation from our beans.
	 * @return
	 */
	public String getJsonFromBean(){
		AutoBean<IAddress> abean = factory.address();
		IAddress address = abean.as();
		
		address.setStreet("3167 S 2700 E");
		address.setCity("SLC");
		address.setPostalCode("84109");
		
		AutoBean<IPerson> pbean = factory.person();
		IPerson p = pbean.as();
		
		p.setName("Jimbo Jones");
		p.setAddress(address);
		
		String json = AutoBeanCodex.encode(pbean).getPayload();
		return json;
	}
}
