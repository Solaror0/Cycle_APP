package com.example.myapaRplication;
/**
 * Class Title: Alarm Widgets
 * Description: This class holds information about the alarm widget class
 * Last Edited: June 18th 2024
 * Author: Jun Nur Mustaqeem
 */
public class AlarmWidget extends HourWidgets {


    private Boolean alarmShow = false;
    private Boolean alarmSet = false;
    public AlarmWidget(){}

    public Boolean getAlarmShow() {
        return alarmShow;
    }

    /**
     * Sets show alarm button's clicked state
     * @param alarmShow the boolean state of the alarm button
     */
    public void setAlarmShow(Boolean alarmShow) {
        this.alarmShow = alarmShow;
    }

    /**
     * Gets alarm button's clicked state
     *
     * @return the alarm button clicked state
     */
    public Boolean getAlarmSet() {
        return alarmSet;
    }

    /**
     * Sets alarm button's clicked state
     *
     * @param alarmSet the alarm button clicked state
     */
    public void setAlarmSet(Boolean alarmSet) {
        this.alarmSet = alarmSet;
    }

}

//nothing here because i only need title