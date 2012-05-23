package co.davidwelch.test.GwtSpringDemo.gwt.client;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;
import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.AbstractDataService.Callback;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.DataServiceImpl;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

public class MainEntryPoint implements EntryPoint {

	private String test = "{\"address\":{\"postalCode\":\"84109\",\"street\":\"3167 S 2700 E\",\"city\":\"SLC\"},\"name\":\"Jimbo Jones\"}";
	
	private MyFactory factory = GWT.create(MyFactory.class);
	
	@Override
	public void onModuleLoad() {
		test3();
	}

	public void test3(){
		DataServiceImpl service = new DataServiceImpl();
		service.getPerson(new Callback<IPerson>(IPerson.class) {
			
			@Override
			public void onComplete(IPerson t) {
				Window.alert("On complete! " + t.getName());
			}
		});
	}
	
	public void test2(){
		AutoBean<IPerson> bean = AutoBeanCodex.decode(factory, IPerson.class, test);
	    IPerson p = bean.as();
	    
	    System.out.println(p.getName() + " lives in " + p.getAddress().getCity());
	}
	
	public void test1(){
		AutoBean<IAddress> abean = factory.address();
		IAddress address = abean.as();
		
		address.setStreet("3167 S 2700 E");
		address.setCity("SLC");
		address.setPostalCode("84109");
		
		AutoBean<IPerson> pbean = factory.person();
		IPerson p = pbean.as();
		
		p.setName("Jimbo Jones");
		p.setAddress(address);
		
		
		System.out.println(AutoBeanCodex.encode(pbean).getPayload());
	}
}
