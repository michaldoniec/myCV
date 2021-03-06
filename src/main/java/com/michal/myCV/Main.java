package com.michal.myCV;

import static spark.Spark.port;
import static spark.Spark.*;

public class Main {

	public static void main(String[] args) {
		port(getHerokuAssignedPort());
       Application.run();
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567;
	}

}
