package com.zolee.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Person;
import com.zolee.service.PersonService;

@SpringView(name = AddNewUserPage.NAME)
public class AddNewUserPage  extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 1L;
	public static final String NAME = "AddNewUser";

	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private Label secure;
	private Label currentUser;
	private Button logoutButton;
	private Button listAllUser;
	private Button mainsecure;
	private HorizontalLayout inputLayout;
	private TextField nameField;
	private TextField rightField;
	private PasswordField passwordField;
	private Button saveButton;
	private Button findUserByName;
	
	public AddNewUserPage() {

		inputLayout = new HorizontalLayout();
		nameField = new TextField("Name:");
		passwordField = new PasswordField("Password:");
		rightField = new TextField("Right:");
		saveButton = new Button("Save");
		logoutButton = new Button("Logout");
		listAllUser = new Button("List All User");
		mainsecure = new Button("Main Page");
		findUserByName = new Button("Find By Name");
		secure = new Label("secure");
		currentUser = new Label("Current User");
		
		inputLayout.addComponents(nameField, rightField, passwordField);
		addComponents(secure, currentUser, inputLayout, saveButton, mainsecure, listAllUser, findUserByName, logoutButton);

		listAllUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+ListAllUserPage.NAME));
		mainsecure.addClickListener( e -> Page.getCurrent().setUriFragment("!"+SecurePage.NAME));
		findUserByName.addClickListener( e -> Page.getCurrent().setUriFragment("!"+FindUserByNamePage.NAME));
		logoutButton.addClickListener( e -> logout());
		saveButton.addClickListener( e -> save());

	}

	private void logout() {
		getUI().getNavigator().removeView(SecurePage.NAME);
		getUI().getNavigator().removeView(AddNewUserPage.NAME);
		getUI().getNavigator().removeView(ListAllUserPage.NAME);
		getUI().getNavigator().removeView(FindUserByNamePage.NAME);
		VaadinSession.getCurrent().setAttribute("user", null);
		Page.getCurrent().setUriFragment("");
	}

	private void save() {
		personService.save(new Person(nameField.getValue(), passwordField.getValue(), rightField.getValue()));
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		currentUser.setCaption("Current user : " + VaadinSession.getCurrent().getAttribute("user").toString()); 

	}
}
