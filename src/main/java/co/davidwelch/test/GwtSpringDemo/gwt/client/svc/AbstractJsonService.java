package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * The backbone for the Gwt + SpringMvc Json communication. Implementations should extend this class and provide methods to target specific 
 * controller endpoints by delegating to the {@link AbstractJsonService#getRequest(String, JsonCallback)} method as appropriate.
 * 
 * See {@link JsonServiceImpl} for a simple example.
 * 
 * @author dwelch
 *
 */
public abstract class AbstractJsonService {

	

	private Integer callNumber = 1;
	private final AutoBeanFactory factory;
	private Map<Integer, JsonCallback<? extends Object>> callbacks = new HashMap<Integer, JsonCallback<? extends Object>>();
	
	public AbstractJsonService(AutoBeanFactory factory) {
		super();
		this.factory = factory;
	}
	
	

	protected Integer getRequest(String url, JsonCallback<?> callback){
		callbacks.put(callNumber, callback);
		getNativeRequest(callNumber, url);
		Integer result = callNumber;
		callNumber++;
		return result;
	}

	private void onDataReceived(Integer callNum, String data){
		GWT.log("Received response for call number " + callNum + ": " + data);
		JsonCallback<? extends Object> cb = callbacks.get(callNum);
		if(cb == null) throw new IllegalStateException("No callback for call " + callNum);
		
		Object value = AutoBeanCodex.decode(factory, cb.getResultClass(), data);
		cb.ready(value);
	}
	
	private native void getNativeRequest(Integer callNum, String url)/*-{
		var instance = this;
		var test = function(data){ 
			instance.@co.davidwelch.test.GwtSpringDemo.gwt.client.svc.AbstractJsonService::onDataReceived(Ljava/lang/Integer;Ljava/lang/String;)( callNum, JSON.stringify(data) );
		};
		$wnd.$.get(url, test);
	}-*/;
}
