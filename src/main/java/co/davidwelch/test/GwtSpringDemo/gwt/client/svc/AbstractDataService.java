package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public abstract class AbstractDataService {

	public static abstract class Callback<T>{

		// the AutoBeanCodex needs the class of our domain object to convert it. 
		// Sucks to provide on every callback, but I'll let it slide for now. 
		// Might be able to replace this with some Deferred Binding magic.
		private Class<T> resultClass;

		public Callback(Class<T> resultClass){
			this.resultClass = resultClass;
		}
		
		public abstract void onComplete(T t);
		
		private final void ready(Object o){
			try{
				@SuppressWarnings("unchecked")
				AutoBean<T> ab = (AutoBean<T>) o;
				onComplete(ab.as());
			}catch(ClassCastException e){
				GWT.log("Failed casting payload to AutoBean", e);
				throw new IllegalStateException("Failed casting payload to AutoBean", e);
			}
		}
	}

	private Integer callNumber = 1;
	private final AutoBeanFactory factory;
	private Map<Integer, Callback<? extends Object>> callbacks = new HashMap<Integer, Callback<? extends Object>>();
	
	public AbstractDataService(AutoBeanFactory factory) {
		super();
		this.factory = factory;
	}

	protected final void addCallback(Callback<?> callback){
		callbacks.put(callNumber, callback);
		getRequest(callNumber);
		callNumber++;
	}
	
	private final void onDataReceived(Integer callNum, String data){
		Callback<? extends Object> cb = callbacks.get(callNum);
		if(cb == null) throw new IllegalStateException("No callback for call " + callNum);
		
		Object value = AutoBeanCodex.decode(factory, cb.resultClass, data);
		
		cb.ready(value);
	}
	
	private void getRequest(Integer callNum){
		getNativeRequest(callNum, "http://localhost:8080/GwtSpringDemo/test2.gwt-json");
	}
		
	
	private native void getNativeRequest(Integer callNum, String url)/*-{
		var instance = this;
		var test = function(data){ 
			instance.@co.davidwelch.test.GwtSpringDemo.gwt.client.svc.AbstractDataService::onDataReceived(Ljava/lang/Integer;Ljava/lang/String;)( callNum, JSON.stringify(data) );
		};
		$wnd.$.get(url, test);
	}-*/;
}
