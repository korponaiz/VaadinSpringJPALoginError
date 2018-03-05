package com.zolee.ui;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;


@SuppressWarnings("serial")
@Theme("valo")
@SpringUI
public class VaadinloginUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinloginUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Autowired
	SpringViewProvider springViewProvider;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
		Navigator navigator = new Navigator(this, this);
		navigator.addProvider(springViewProvider);
		
		getNavigator().addView(LoginPage.NAME, LoginPage.class);
		getNavigator().setErrorView(LoginPage.class);
		
		Page.getCurrent().addUriFragmentChangedListener(new UriFragmentChangedListener() {
			
			@Override
			public void uriFragmentChanged(UriFragmentChangedEvent event) {
				router(event.getUriFragment());
			}
		});
		
		router("");
	}
	
	private void router(String route){
		Notification.show(route);
		if(getSession().getAttribute("user") != null){
			getNavigator().addView(SecurePage.NAME, SecurePage.class);
			getNavigator().addView(AddNewUserPage.NAME, AddNewUserPage.class);
			getNavigator().addView(ListAllUserPage.NAME, ListAllUserPage.class);
			getNavigator().addView(FindUserByNamePage.NAME, FindUserByNamePage.class);
			if(route.equals("!AddNewUser")) {
				getNavigator().navigateTo(AddNewUserPage.NAME);
			}else if(route.equals("!ListAllUser")) {
				getNavigator().navigateTo(ListAllUserPage.NAME);
			}else if(route.equals("!FindUserByName")) {
				getNavigator().navigateTo(FindUserByNamePage.NAME);
			}else{
				getNavigator().navigateTo(SecurePage.NAME);
			}
		}else{
			getNavigator().navigateTo(LoginPage.NAME);
		}
	}
}