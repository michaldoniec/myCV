package com.michal.myCV;

import com.michal.myCV.controller.ApplicationController;
import com.michal.myCV.dao.JDBCPostgres;

public class Application {
	private JDBCPostgres database;
	private static  Application applicationInstance;

	private  Application(){
		database = new JDBCPostgres();
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

	public JDBCPostgres getDatabase() {
		return database;
	}
}
