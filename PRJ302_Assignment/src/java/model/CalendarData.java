/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author admin
 */
public class CalendarData {

    private List<Day> days;
    private int firstDayOfWeek;

    public CalendarData(List<Day> days, int firstDayOfWeek) {
        this.days = days;
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public List<Day> getDays() {
        return days;
    }

    public int getFirstDayOfWeek() {
        return firstDayOfWeek;
    }
}
