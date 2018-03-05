package com.zolee.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = SecurePage.NAME)
public class SecurePage extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "Secure";
	private Label secure;
	private Label currentUser;
	private Button logoutButton;
	private Button listAllUser;
	private Button addNewUser;
	private Button findUserByName;
	
	public SecurePage() {
		
		logoutButton = new Button("Logout");
		listAllUser = new Button("List All User");
		addNewUser = new Button("Add New User");
		findUserByName = new Button("Find By Name");
		secure = new Label("secure");
		currentUser = new Label("Current User");
		addComponents(secure, currentUser, addNewUser, listAllUser, findUserByName, logoutButton);
		
		logoutButton.addClickListener( e -> logout());
		addNewUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+AddNewUserPage.NAME));
		listAllUser.addClickListener( e -> Page.getCurrent().setUriFragment("!"+ListAllUserPage.NAME));
		findUserByName.addClickListener( e -> Page.getCurrent().setUriFragment("!"+FindUserByNamePage.NAME));
	}
	
	private void logout() {
		getUI().getNavigator().removeView(SecurePage.NAME);
		getUI().getNavigator().removeView(AddNewUserPage.NAME);
		getUI().getNavigator().removeView(ListAllUserPage.NAME);
		getUI().getNavigator().removeView(FindUserByNamePage.NAME);
		VaadinSession.getCurrent().setAttribute("user", null);
		Page.getCurrent().setUriFragment("");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		currentUser.setCaption("Current user : " + VaadinSession.getCurrent().getAttribute("user").toString()); 

	}

}