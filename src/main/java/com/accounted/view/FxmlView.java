package com.accounted.view;

import java.util.ResourceBundle;

import com.accounted.controller.DepartmentController;
import com.accounted.controller.InitiatorController;
import com.accounted.controller.LedgerController;
import com.accounted.controller.LoginController;
import com.accounted.controller.MenuBarController;
import com.accounted.controller.ReceiverController;
import com.accounted.controller.TransactionController;
import com.accounted.controller.UserController;

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

		@Override
		public Class getController() {
			return DepartmentController.class;
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

		@Override
		public Class getController() {
			return InitiatorController.class;
		}
	},
	LEDGER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ledger.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Ledger.fxml";
		}

		@Override
		public Class getController() {
			return LedgerController.class;
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

		@Override
		public Class getController() {
			return LoginController.class;
		}
	},
	MENUBAR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("menubar.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MenuBar.fxml";
		}

		@Override
		public Class getController() {
			return MenuBarController.class;
		}
	},
	RECEIVER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("receiver.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Receiver.fxml";
		}

		@Override
		public Class getController() {
			return ReceiverController.class;
		}
	},
	TRANSACTION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("transaction.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Transaction.fxml";
		}

		@Override
		public Class getController() {
			return TransactionController.class;
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

		@Override
		public Class getController() {
			return UserController.class;
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	public abstract Class getController();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
