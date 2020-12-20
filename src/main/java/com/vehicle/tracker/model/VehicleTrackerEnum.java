package com.vehicle.tracker.model;

public enum VehicleTrackerEnum {
    IS_FINE_PENDING(true);
	private boolean isFinePending;
	VehicleTrackerEnum(boolean isFinePending){
		this.isFinePending=isFinePending;
	}
	public boolean isFinePending() {
		return isFinePending;
	}
	public void setFinePending(boolean isFinePending) {
		this.isFinePending = isFinePending;
	}
	
}
