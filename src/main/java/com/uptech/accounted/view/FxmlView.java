package com.uptech.accounted.view;

import java.util.ResourceBundle;

import com.uptech.accounted.controller.DepartmentController;
import com.uptech.accounted.controller.InitiatorController;
import com.uptech.accounted.controller.LedgerController;
import com.uptech.accounted.controller.LoginController;
import com.uptech.accounted.controller.MenuBarController;
import com.uptech.accounted.controller.RecipientController;
import com.uptech.accounted.controller.ReportsController;
import com.uptech.accounted.controller.TransactionController;
import com.uptech.accounted.controller.UserController;

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
  RECIPIENT {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("receiver.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Recipient.fxml";
		}

		@Override
		public Class getController() {
			return RecipientController.class;
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
	},
  REPORTS {
    @Override
    public String getTitle() {
      return "Reports";
    }

    @Override
    public String getFxmlFile() {
      return "/fxml/Reports.fxml";
    }

    @Override
    public Class getController() {
      return ReportsController.class;
    }
  };

	public abstract String getTitle();

	public abstract String getFxmlFile();

	public abstract Class getController();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
