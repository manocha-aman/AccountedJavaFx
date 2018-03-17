package com.codetreatise.view;

import java.util.ResourceBundle;

public enum FxmlView {

	DEPARTMENT {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("department.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Department.fxml";
		}
	},
	INITIATOR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("intitiator.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Initiator.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
