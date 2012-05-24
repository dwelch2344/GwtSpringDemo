package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;

/**
 * This class is just a test snapshot of some other work I was doing
 * @author dwelch
 *
 */
@Deprecated
public class JsonpService {

	public interface Callback<T>{
		void onComplete(T t);
	}
	
	private MyFactory factory = GWT.create(MyFactory.class);
	private Callback<IPerson> cb;
	private Callback<String> dataCB;
	
	public void getPerson(Callback<IPerson> callback){
		this.cb = callback;
		getRequest();
		
		dataCB = new Callback<String>() {

			@Override
			public void onComplete(String t) {
				System.out.println("Callback w/ " + t);
				AutoBean<IPerson> bean = AutoBeanCodex.decode(factory, IPerson.class, t);
				cb.onComplete(bean.as());
			}
		};
	}
	
	protected void onDataReceived(String data){
		System.out.println("Got data: " + data);
		if(dataCB == null) throw new IllegalStateException("No data callback");
		dataCB.onComplete(data);
	}
	
	public native void getRequest()/*-{
		var test, instance = this;
		$wnd.gwtJsonCallback = test = function(data){ 
			console.log("DONE: ", data, instance);
			instance.@co.davidwelch.test.GwtSpringDemo.gwt.client.svc.JsonpService::onDataReceived(Ljava/lang/String;)( JSON.stringify(data) );
		};
		$wnd.$.getJSON('http://localhost:8080/GwtSpringDemo/test2.gwt-json?callback=?', test);
	}-*/;
}
