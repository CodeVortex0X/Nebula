package com.nebula.NebulaApp;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class EventDecorator implements DayViewDecorator {
    private int color = Color.parseColor("#D0BCFF");
    private CalendarDay currentDate;

    public EventDecorator(CalendarDay currentDate) {
        this.currentDate = currentDate;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(currentDate);
    }

    @Override
    public void decorate(DayViewFacade view) {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(Color.parseColor("#D0BCFF"));
        view.setBackgroundDrawable(drawable);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#381E72"));
        view.addSpan(span);
    }
}

