
package albin.timelogger;

import android.os.SystemClock;
import android.util.Log;
import android.util.TimingLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for logging events with timing. Works like {@link TimingLogger}
 * but will always dump to the log. Useful if you don't like setting properties
 * on your phone.
 *
 */
public class TimeLogger {

    private final String tag;

    private final String method;

    private long start;

    private List<Event> events = new ArrayList<Event>();

    public TimeLogger(String tag, String method) {
        this.tag = tag;
        this.method = method;
        start = SystemClock.uptimeMillis();
    }

    public void reset() {
        start = SystemClock.uptimeMillis();
        events.clear();
    }

    public void addSplit(String msg) {
        events.add(new Event(SystemClock.uptimeMillis(), msg));
    }

    public void dumpToLog() {
        long stop = SystemClock.uptimeMillis();
        Log.d(tag, method + ": start");
        long last = start;
        for (Event ev : events) {
            Log.d(tag, method + ":     " + (ev.time - last) + " ms, " + ev.msg);
            last = ev.time;
        }
        Log.d(tag, method + ": stop " + (stop - start) + "ms total");
    }

    private static class Event {
        long time;

        String msg;

        public Event(long time, String msg) {
            this.time = time;
            this.msg = msg;
        }

    }

}
