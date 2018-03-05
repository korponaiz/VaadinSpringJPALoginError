package com.zolee.ui;


import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.zolee.authentication.Authentication;
import com.zolee.service.PersonService;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = LoginPage.NAME)
public class LoginPage extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "";
	private Authentication Auth;
	
	private TextField username;
	private PasswordField password;
	private Panel panel;
	private FormLayout content;
	private Button okButton;
	
	@Autowired
	public void setAuth(Authentication tempAuth) {
		Auth = tempAuth;
	}

	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public LoginPage(){
		
		panel = new Panel("Login");
		content = new FormLayout();
		username = new TextField("Username");
		password = new PasswordField("Password");
		okButton = new Button("Enter");

		panel.setSizeUndefined();
		addComponent(panel);
		content.addComponents(username, password, okButton);
		content.setSizeUndefined();
		content.setMargin(true);
		panel.setContent(content);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		okButton.addClickListener( e -> authenticate());
		
	}
	
	private void authenticate() {
		if(Auth.authenticate(username.getValue(), password.getValue())){
			VaadinSession.getCurrent().setAttribute("user", username.getValue());
			VaadinSession.getCurrent().setAttribute("right", personService.findByName(username.getValue()).getRight());
			getUI().getNavigator().addView(SecurePage.NAME, SecurePage.class);
			getUI().getNavigator().addView(AddNewUserPage.NAME, AddNewUserPage.class);
			getUI().getNavigator().addView(ListAllUserPage.NAME, ListAllUserPage.class);
			getUI().getNavigator().addView(FindUserByNamePage.NAME, FindUserByNamePage.class);
			Page.getCurrent().setUriFragment("!"+SecurePage.NAME);
		}else{
			Notification.show("Invalid credentials", Notification.Type.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
