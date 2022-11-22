module CoffeeSoJava {
	requires javafx.controls;
	requires com.jfoenix;
	requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	requires TrayNotification;
	
	opens com.hostmm.csj.main to javafx.graphics, javafx.fxml;
	opens com.hostmm.csj.cashier.controller to javafx.fxml;
	opens com.hostmm.csj.admin.controller to javafx.fxml;
	opens com.hostmm.csj.staff.controller to javafx.fxml;
	opens com.hostmm.csj.staff.model to javafx.base;
	opens com.hostmm.csj.login.controller to javafx.fxml;
	opens com.hostmm.csj.item.controller to javafx.fxml;
	opens com.hostmm.csj.item.model to javafx.base;
	opens com.hostmm.csj.item.card.controller to javafx.fxml;
	opens com.hostmm.csj.bill.controller to javafx.fxml;
	opens com.hostmm.csj.bill.model to javafx.base;
}
