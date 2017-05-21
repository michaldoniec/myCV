package com.michal.myCV;

import com.michal.myCV.controller.ApplicationController;
import com.michal.myCV.dao.JDBCSQLite;

public class Application {
	private JDBCSQLite database;
	private static  Application applicationInstance;

	private  Application(){
		database = new JDBCSQLite();
	}

	public static Application getApplicationInstance(){
		return applicationInstance;
	}

	public static void run(){
		applicationInstance = new Application();
		ApplicationController applicationController = new ApplicationController(applicationInstance.getDatabase());
		applicationController.routes();
	}

	public void stop(){
		database.closeDatabase();
	}

	public JDBCSQLite getDatabase() {
		return database;
	}
}
