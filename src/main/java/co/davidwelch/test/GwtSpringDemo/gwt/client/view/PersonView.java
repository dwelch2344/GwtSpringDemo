package co.davidwelch.test.GwtSpringDemo.gwt.client.view;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class PersonView extends Composite {

	private static PersonViewUiBinder uiBinder = GWT
			.create(PersonViewUiBinder.class);

	interface PersonViewUiBinder extends UiBinder<Widget, PersonView> {}

	@UiField Element name;
	@UiField AddressView address;
	
	public PersonView(IPerson p) {
		initWidget(uiBinder.createAndBindUi(this));
		name.setInnerText( p.getName() );
		if( p.getAddress() != null){
			address.setData(p.getAddress());
		}
	}

}
