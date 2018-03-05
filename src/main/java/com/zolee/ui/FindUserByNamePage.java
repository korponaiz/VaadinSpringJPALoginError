package com.zolee.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Person;
import com.zolee.service.PersonService;

@SpringView(name = FindUserByNamePage.NAME)
public class FindUserByNamePage extends VerticalLayout implements View{
	
	private static final long serialVersionUID = 1L;
	public static final String NAME = "FindUserByName";
	
	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private Label secure;
	private Label currentUser;
	private Button logoutButton;
	private Button listAllUser;
	private Button addNewUser;
	private Button mainsecure;
	private TextField nameField;
	private Button searchButton;
	private Label resultLabel;
	
	public FindUserByNamePage() {
		
		nameField = new TextField("Name:");
		searchButton = new Button("Search");
		addNewUser = new Button("Add New User");
		logoutButton = new Button("Logout");
		listAllUser = new Button("List All User");
		mainsecure = new Button("Main Page");
		secure = new Label("secure");
		currentUser = new Label("Current User");
		resultLabel = new Label("No result found!");
		
		addComponents(secure, currentUser, nameField, searchButton, mainsecure, addNewUser, listAllUser, logoutButton, resultLabel);

		listAllUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+ListAllUserPage.NAME));
		addNewUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+AddNewUserPage.NAME));
		mainsecure.addClickListener( e -> Page.getCurrent().setUriFragment("!"+SecurePage.NAME));
		logoutButton.addClickListener( e -> logout());
		searchButton.addClickListener( e -> search());

	}

	private void search() {
		Person person =	personService.findByName(nameField.getValue());
		resultLabel.setValue("Found result: " + person.getName() + " / " + person.getRight());
	}

	private void logout() {
		getUI().getNavigator().removeView(SecurePage.NAME);
		getUI().getNavigator().removeView(AddNewUserPage.NAME);
		getUI().getNavigator().removeView(ListAllUserPage.NAME);
		VaadinSession.getCurrent().setAttribute("user", null);
		Page.getCurrent().setUriFragment("");
	}

}
