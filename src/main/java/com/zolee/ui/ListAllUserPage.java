package com.zolee.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.zolee.domain.Person;
import com.zolee.service.PersonService;

@SpringView(name = ListAllUserPage.NAME)
public class ListAllUserPage  extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "ListAllUser";
	
	private PersonService personService;
	
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	private Label secure;
	private Label currentUser;
	private Button logoutButton;
	private Button addNewUser;
	private Button mainsecure;
	private Button findUserByName;
	private Grid<Person> grid;

	public ListAllUserPage() {
		
		logoutButton = new Button("Logout");
		addNewUser = new Button("Add New User");
		mainsecure = new Button("Main Page");
		findUserByName = new Button("Find By Name");
		secure = new Label("secure");
		currentUser = new Label("Current User");
		grid = new Grid<>(Person.class);
		addComponents(secure, currentUser, mainsecure, addNewUser, findUserByName, logoutButton, grid);
		
		addNewUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+AddNewUserPage.NAME));
		mainsecure.addClickListener( e -> Page.getCurrent().setUriFragment("!"+SecurePage.NAME));
		findUserByName.addClickListener( e -> Page.getCurrent().setUriFragment("!"+FindUserByNamePage.NAME));
		logoutButton.addClickListener( e -> logout());

		upDateGrid();
	}
	
	private void logout() {
		getUI().getNavigator().removeView(SecurePage.NAME);
		getUI().getNavigator().removeView(AddNewUserPage.NAME);
		getUI().getNavigator().removeView(ListAllUserPage.NAME);
		getUI().getNavigator().removeView(FindUserByNamePage.NAME);
		VaadinSession.getCurrent().setAttribute("user", null);
		Page.getCurrent().setUriFragment("");
	}

	private void upDateGrid() {
		grid.setItems(personService.findAll());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		currentUser.setCaption("Current user : " + VaadinSession.getCurrent().getAttribute("user").toString()); 
	}
}
