package com.hostmm.csj.utility.notification;

import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class MyNotification {

	public void getNotification(String title, String message, MyNotificationType notiType, Duration dismissTime) {
		NotificationType trayNotiType = switch (notiType) {
		case CUSTOM -> NotificationType.CUSTOM;
		case ERROR -> NotificationType.ERROR;
		case INFORMATION -> NotificationType.INFORMATION;
		case NOTICE -> NotificationType.NOTICE;
		case SUCCESS -> NotificationType.SUCCESS;
		case WARNING -> NotificationType.WARNING;

		default -> throw new IllegalArgumentException("Unexpected value: " + notiType);
		};

		TrayNotification trayNoti = new TrayNotification(title, message, trayNotiType);
		trayNoti.showAndDismiss(dismissTime);

	}


}
