package com.example.petcare;



import android.content.res.Resources;

import com.example.petcare.db.Notification;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class petDecorator implements DayViewDecorator {
    private final List<Notification> notifications;
    private final Resources resources;

    public petDecorator(List<Notification> notifications, Resources resources) {
        this.notifications = notifications;
        this.resources = resources;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        try{
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            for (Notification notification : notifications ){
                Date date = dateFormat.parse(notification.getDate());
                calendar.setTime(date);
                if(calendar.get(Calendar.YEAR) == day.getYear() && calendar.get(Calendar.MONTH)+1 == day.getMonth() && calendar.get(Calendar.DAY_OF_MONTH) == day.getDay()){
                    return true;
                }
            }
            return false;
        }
        catch(Throwable t){
            return false;
        }

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_event, null));
    }
}
