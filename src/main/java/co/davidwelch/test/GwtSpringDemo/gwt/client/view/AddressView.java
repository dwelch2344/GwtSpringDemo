package co.davidwelch.test.GwtSpringDemo.gwt.client.view;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AddressView extends Composite {

	private static AddressViewUiBinder uiBinder = GWT
			.create(AddressViewUiBinder.class);

	interface AddressViewUiBinder extends UiBinder<Widget, AddressView> {}

	@UiField Element street, street2, city, state, zip;
	
	public AddressView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setData(IAddress a){
		street.setInnerText(a.getStreet());
		street2.setInnerText(a.getStreet2());
		city.setInnerText(a.getCity());
		state.setInnerText(a.getProvidence());
		zip.setInnerText(a.getPostalCode());
	}

}
