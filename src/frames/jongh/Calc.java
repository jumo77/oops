package frames.jongh;

import data.DBMS;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

class Calc {
    private JPanel calPanel;
    private JLabel monthLabel;
    private JButton prevButton, nextButton;
    private int year, month;
    private final Map<String, List<Event>> events;

    public Calc() {
        events = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        calPanel = new JPanel(null);

        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(0, 0, 500, 50);

        prevButton = new JButton("<");
        nextButton = new JButton(">");
        monthLabel = new JLabel(getMonthYearLabel(), JLabel.CENTER);

        prevButton.setBounds(10, 10, 50, 30);
        nextButton.setBounds(440, 10, 50, 30);
        monthLabel.setBounds(70, 10, 360, 30);

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));

        topPanel.add(prevButton);
        topPanel.add(nextButton);
        topPanel.add(monthLabel);

        calPanel.add(topPanel);

        JPanel gridPanel = new JPanel(new GridLayout(7, 7));
        gridPanel.setBounds(0, 60, 500, 400);
        calPanel.add(gridPanel);

        loadEventsFromDB();
        updateCalendar(gridPanel);
    }

    private String getMonthYearLabel() {
        return String.format("%d년 %02d월", year, month + 1);
    }

    private void changeMonth(int increment) {
        month += increment;
        if (month < 0) {
            month = 11;
            year--;
        } else if (month > 11) {
            month = 0;
            year++;
        }
        monthLabel.setText(getMonthYearLabel());

        JPanel gridPanel = (JPanel) calPanel.getComponent(1);
        updateCalendar(gridPanel);
    }

    private void updateCalendar(JPanel gridPanel) {
        gridPanel.removeAll();

        String[] weekdays = {"일", "월", "화", "수", "목", "금", "토"};
        for (String weekday : weekdays) {
            JLabel label = new JLabel(weekday, JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            gridPanel.add(label);
        }

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < firstDayOfWeek; i++) {
            gridPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            String date = String.format("%d-%02d-%02d", year, month + 1, day);

            dayButton.addActionListener(e -> openEventDialog(date));
            if (events.containsKey(date)) {
                dayButton.setBackground(Color.YELLOW);
            }
            gridPanel.add(dayButton);
        }

        int remainingCells = 7 - ((firstDayOfWeek + daysInMonth) % 7);
        if (remainingCells < 7) {
            for (int i = 0; i < remainingCells; i++) {
                gridPanel.add(new JLabel(""));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void openEventDialog(String date) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(calPanel), "일정 보기/추가 - " + date, true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        JTextArea eventDisplay = new JTextArea();
        eventDisplay.setEditable(false);
        if (events.containsKey(date)) {
            StringBuilder eventText = new StringBuilder();
            List<Event> eventList = events.get(date);
            for (int i = 0; i < eventList.size(); i++) {
                eventText.append(String.format("일정 %d:\n%s\n\n", i + 1, eventList.get(i)));
            }
            eventDisplay.setText(eventText.toString());
        } else {
            eventDisplay.setText("일정이 없습니다.");
        }
        dialog.add(new JScrollPane(eventDisplay), BorderLayout.CENTER);

        JPanel addPanel = new JPanel(new GridLayout(5, 2));
        JTextField nameField = new JTextField();
        JTextField explanationField = new JTextField();
        JTextField startTimeField = new JTextField("HH:mm");
        JTextField endTimeField = new JTextField("HH:mm");
        JComboBox<String> importanceBox = new JComboBox<>(new String[]{"1", "2", "3"});

        addPanel.add(new JLabel("제목:"));
        addPanel.add(nameField);
        addPanel.add(new JLabel("설명:"));
        addPanel.add(explanationField);
        addPanel.add(new JLabel("시작 시간:"));
        addPanel.add(startTimeField);
        addPanel.add(new JLabel("종료 시간:"));
        addPanel.add(endTimeField);
        addPanel.add(new JLabel("중요도:"));
        addPanel.add(importanceBox);

        JButton saveButton = new JButton("저장");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String explanation = explanationField.getText();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();
            int importance = Integer.parseInt((String) importanceBox.getSelectedItem());

            saveEventToDB(new Event(name, explanation, startTime, endTime, importance, year, date));
            JPanel gridPanel = (JPanel) calPanel.getComponent(1);
            updateCalendar(gridPanel);
            dialog.dispose();
        });

        dialog.add(addPanel, BorderLayout.NORTH);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    private void saveEventToDB(Event event) {
        String startDate = event.date != null && event.date.length() >= 10
                ? event.date.substring(5)
                : "01-01";

        String query = String.format(
                "INSERT INTO team_work.calendar (calendar_date, calendar_name, calendar_explain, calendar_startdate, calendar_starttime, calendar_endtime, calendar_important, calendar_year) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, %d) " +
                        "ON DUPLICATE KEY UPDATE " +
                        "calendar_name='%s', calendar_explain='%s', calendar_startdate='%s', calendar_starttime='%s', calendar_endtime='%s', calendar_important=%d",
                event.date.replace("-", ""),
                escape(event.name),
                escape(event.explanation),
                startDate,
                event.startTime,
                event.endTime,
                event.importance,
                event.year,
                escape(event.name),
                escape(event.explanation),
                startDate,
                event.startTime,
                event.endTime,
                event.importance
        );
        try {
            DBMS.DB.executeUpdate(query);
            System.out.println("이벤트가 저장되었어요.");

            loadEventsFromDB();

            JPanel gridPanel = (JPanel) calPanel.getComponent(1);
            updateCalendar(gridPanel);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadEventsFromDB() {
        String query = "SELECT * FROM team_work.calendar WHERE calendar_year = " + year;
        try {
            ResultSet rs = DBMS.DB.executeQuery(query);
            while (rs.next()) {
                String date = String.format("%d-%02d-%02d", rs.getInt("calendar_year"),
                        Integer.parseInt(rs.getString("calendar_date").substring(4, 6)),
                        Integer.parseInt(rs.getString("calendar_date").substring(6, 8)));

                Event event = new Event(
                        rs.getString("calendar_name"),
                        rs.getString("calendar_explain"),
                        rs.getString("calendar_starttime"),
                        rs.getString("calendar_endtime"),
                        rs.getInt("calendar_important"),
                        rs.getInt("calendar_year"),
                        date
                );

                events.computeIfAbsent(date, k -> new ArrayList<>()).add(event);
            }
            System.out.println("Events loaded from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String escape(String input) {
        return input == null ? "" : input.replace("'", "''");
    }



    static class Event {
        private final String name, explanation, startTime, endTime, date;
        private final int importance, year;

        public Event(String name, String explanation, String startTime, String endTime, int importance, int year, String date) {
            this.name = name != null ? name : "";
            this.explanation = explanation != null ? explanation : "";
            this.startTime = startTime != null ? startTime : "00:00";
            this.endTime = endTime != null ? endTime : "00:00";
            this.date = date != null && date.length() >= 10 ? date : "1970-01-01";
            this.importance = importance;
            this.year = year;
        }
    @Override
        public String toString() {
            return String.format("제목: %s\n설명: %s\n시작: %s\n종료: %s\n중요도: %d\n년도: %d", name, explanation, startTime, endTime, importance, year);
        }
    }

    public JPanel getCalPanel() {
        return calPanel;
    }

    public JLabel getMonthLabel() {
        return monthLabel;
    }

    public JButton[] getNavigationButtons() {
        return new JButton[]{prevButton, nextButton};
    }
}
