import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String monthParam = request.getParameter("month");
            String yearParam = request.getParameter("year");

            if (monthParam == null || yearParam == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing month or year parameter");
                return;
            }

            int month = Integer.parseInt(monthParam);
            int year = Integer.parseInt(yearParam);

            if (month < 1 || month > 12 || year < 2000 || year > 2030) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid month or year");
                return;
            }

            CalendarData calendarData = generateCalendar(month, year);
            String json = new Gson().toJson(calendarData);
            out.write(json);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for month or year");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    private CalendarData generateCalendar(int month, int year) {
        List<Day> days = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1); // month - 1 vì Calendar bắt đầu từ 0
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0 = Sunday, 1 = Monday, ...

        // Thêm các ô trống trước ngày 1
        for (int i = 0; i < firstDayOfWeek; i++) {
            days.add(new Day(0, "")); // Ô trống có dayNumber = 0
        }

        // Thêm các ngày trong tháng
        for (int i = 1; i <= maxDays; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            String dayOfWeek = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
            days.add(new Day(i, dayOfWeek));
        }

        return new CalendarData(days, firstDayOfWeek);
    }

    private String getDayOfWeek(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return "Sunday";
            case Calendar.MONDAY: return "Monday";
            case Calendar.TUESDAY: return "Tuesday";
            case Calendar.WEDNESDAY: return "Wednesday";
            case Calendar.THURSDAY: "Thursday";
            case Calendar.FRIDAY: return "Friday";
            case Calendar.SATURDAY: return "Saturday";
            default: return "";
        }
    }
}

class Day {
    private int dayNumber;
    private String dayOfWeek;

    public Day(int dayNumber, String dayOfWeek) {
        this.dayNumber = dayNumber;
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayNumber() { return dayNumber; }
    public String getDayOfWeek() { return dayOfWeek; }
}

class CalendarData {
    private List<Day> days;
    private int firstDayOfWeek;

    public CalendarData(List<Day> days, int firstDayOfWeek) {
        this.days = days;
        this.firstDayOfWeek = firstDayOfWeek;
    }

    public List<Day> getDays() { return days; }
    public int getFirstDayOfWeek() { return firstDayOfWeek; }
}