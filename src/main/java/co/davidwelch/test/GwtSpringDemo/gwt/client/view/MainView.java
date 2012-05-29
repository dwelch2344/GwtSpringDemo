package co.davidwelch.test.GwtSpringDemo.gwt.client.view;

import java.util.ArrayList;
import java.util.List;

import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IAddress;
import co.davidwelch.test.GwtSpringDemo.gwt.client.model.IPerson;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.JsonCallback;
import co.davidwelch.test.GwtSpringDemo.gwt.client.svc.JsonServiceImpl;
import co.davidwelch.test.GwtSpringDemo.gwt.client.util.MyFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);
	interface MainViewUiBinder extends UiBinder<Widget, MainView> {}

	private MyFactory factory = GWT.create(MyFactory.class);
	
	@UiField TextBox name, street, street2, city, providence;
	@UiField Button addButton, sendButton, loadButton;
	@UiField ComplexPanel panel;
	
	private List<IPerson> people = new ArrayList<IPerson>();
	// this would be in a Presenter / Controller or something
	private JsonServiceImpl service = new JsonServiceImpl();
	
	
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addButton")
	void onAddClicked(ClickEvent e){
		IAddress a = factory.address().as();
		a.setStreet(street.getText());
		a.setStreet2(street2.getText());
		a.setCity(city.getText());
		a.setProvidence(providence.getText());
		
		IPerson p = factory.person().as();
		p.setName(name.getText());
		p.setAddress(a);
		
		addPerson(p);
	}
	
	@UiHandler("sendButton")
	void onSendtoServerClicked(ClickEvent e){
		service.postListOfPeople(people, callback);
	}
	
	@UiHandler("loadButton")
	void onLoadClicked(ClickEvent e){
		service.getListOfPeople(callback);
	}
	
	private void addPerson(IPerson p) {
		PersonView newView = new PersonView(p);
		panel.add(newView);
		people.add(p);
		clear(name, street, street2, city, providence);
	}
	
	private void clear(TextBox... fields){
		for(TextBox box : fields){
			box.setText("");
		}
	}
	
	
	JsonCallback<List<IPerson>> callback = new JsonCallback<List<IPerson>>((Class<List<IPerson>>) people.getClass()) {
		
		@Override
		public void onComplete(List<IPerson> data) {
			GWT.log("Message sent to server.");
			panel.clear();
			for(IPerson p : data){
				addPerson(p);
			}
			people = data;
		}
	};
}
