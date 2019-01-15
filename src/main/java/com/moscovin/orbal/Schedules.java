package com.moscovin.orbal;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;

public class Schedules {
    private List<Schedule> schedules;

    public Schedules() {
        schedules = new LinkedList<Schedule>();
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent e) {
        for (Schedule schedule: schedules) {
            if (schedule.enabled) {
                schedule.ticksLeft--;
                if (schedule.ticksLeft == 0) {
                    schedule.run();
                    schedule.enabled = false;
                }
            }
        }
    }

    public abstract static class Schedule {
        boolean enabled = true;
        int ticksLeft;
        public abstract void run();

        protected Schedule(int ticks) {
            ticksLeft = ticks * 60;
        }
    }
}
