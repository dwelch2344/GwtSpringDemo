package co.davidwelch.test.GwtSpringDemo.gwt.client.svc;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;

/**
 * The basic building block for asynchronous calls to the server.  
 * 
 * @author dwelch
 *
 * @param <T> The type of the POJO your json will represent.
 */
public abstract class JsonCallback<T> {

	// the AutoBeanCodex needs the class of our domain object to convert it.
	// Sucks to provide on every callback, but I'll let it slide for now.
	// Might be able to replace this with some Deferred Binding magic.
	private Class<T> resultClass;

	public JsonCallback(Class<T> resultClass) {
		this.resultClass = resultClass;
	}

	/**
	 * The code to be executed when the call returns from the server with your data.  
	 * @param data Your data from the server call
	 */
	public abstract void onComplete(T data);
	
	// TODO add onError?
	
	final void ready(Object o) {
		try {
			@SuppressWarnings("unchecked")
			AutoBean<T> ab = (AutoBean<T>) o;
			onComplete(ab.as());
		} catch (ClassCastException e) {
			GWT.log("Failed casting payload to AutoBean", e);
			throw new IllegalStateException("Failed casting payload to AutoBean", e);
		}
	}
	
	final Class<T> getResultClass() {
		return resultClass;
	}
}
