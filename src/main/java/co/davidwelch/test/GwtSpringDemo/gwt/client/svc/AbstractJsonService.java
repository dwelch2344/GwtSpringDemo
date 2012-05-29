package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

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

	
	private final String baseUrl;
	private Integer callNumber = 1;
	private final AutoBeanFactory factory;
	private Map<Integer, JsonCallback<? extends Object>> callbacks = new HashMap<Integer, JsonCallback<? extends Object>>();
	
	public AbstractJsonService(AutoBeanFactory factory) {
		this(factory, GWT.getHostPageBaseURL());
	}
	
	public AbstractJsonService(AutoBeanFactory factory, String baseUrl) {
		super();
		this.factory = factory;
		this.baseUrl = baseUrl == null ? "" : baseUrl;
	}
	
	protected Integer getRequest(String url, JsonCallback<?> callback){
		callbacks.put(callNumber, callback);
		getNativeRequest(callNumber, baseUrl + url);
		Integer result = callNumber;
		callNumber++;
		return result;
	}
	
	protected Integer postRequest(String url, JsonCallback<?> callback, Object param){
		String paramJson = null;
		if(param != null){
			if(param instanceof Collection){
				
				
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				
				Collection<?> col = ((Collection<?>) param);
				Iterator<?> itr = col.iterator();
				while(itr.hasNext()){
					AutoBean<?> bean = AutoBeanUtils.getAutoBean(itr.next());
					if(bean != null){
						sb.append( AutoBeanCodex.encode(bean).getPayload() );
					}
					if(itr.hasNext()){
						sb.append(", ");
					}
				}
				sb.append("]");
				paramJson = sb.toString();
			}else{
				AutoBean<?> bean = AutoBeanUtils.getAutoBean(param);
				if(bean != null){
					paramJson = AutoBeanCodex.encode(bean).getPayload();
				}
			}
		}
		
		callbacks.put(callNumber, callback); 
		postNativeRequest(callNumber, baseUrl + url, paramJson);
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
		var callback = function(data){ 
			instance.@co.davidwelch.test.GwtSpringDemo.gwt.client.svc.AbstractJsonService::onDataReceived(Ljava/lang/Integer;Ljava/lang/String;)( callNum, JSON.stringify(data) );
		};
		$wnd.$.get(url, callback);
	}-*/;
	
	private native void postNativeRequest(Integer callNum, String url, String paramJson)/*-{
		var instance = this;
		var callback = function(data){ 
			instance.@co.davidwelch.test.GwtSpringDemo.gwt.client.svc.AbstractJsonService::onDataReceived(Ljava/lang/Integer;Ljava/lang/String;)( callNum, JSON.stringify(data) );
		};
		if( paramJson ){
			var config = {
				type: 'POST',
				url: url, 
				data : paramJson,
				dataType: 'json',
				contentType: "application/json", 
				success: callback
			};
			$wnd.$.ajax(config);
		}else{
			$wnd.$.post(url, callback);
		}
		
		
	}-*/;
}
