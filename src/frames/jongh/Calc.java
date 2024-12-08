package frames.jongh;

import theme.ThemeButton;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class Calc {
    private JPanel calPanel;
    private JLabel monthLabel;
    private int year, month;
    private final Map<String, List<Event>> events;

    public Calc(JPanel parent) {
        events = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        calPanel = new JPanel();
        calPanel.setLayout(new GridLayout(7, 7));
        calPanel.setBounds(350, 50, 500, 400); // 캘린더 위치와 크기
        parent.add(calPanel);

        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(350, 20, 500, 30);

        ThemeButton prevButton = new ThemeButton("<");
        ThemeButton nextButton = new ThemeButton(">");
        monthLabel = new JLabel(getMonthYearLabel(), JLabel.CENTER);

        prevButton.setBounds(0, 0, 50, 30);
        nextButton.setBounds(450, 0, 50, 30);
        monthLabel.setBounds(50, 0, 400, 30);

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));

        topPanel.add(prevButton);
        topPanel.add(nextButton);
        topPanel.add(monthLabel);
        parent.add(topPanel);

        updateCalendar();
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
        updateCalendar();
    }

    private void updateCalendar() {
        calPanel.removeAll();


        String[] weekdays = {"일", "월", "화", "수", "목", "금", "토"};
        for (String weekday : weekdays) {
            JLabel label = new JLabel(weekday, JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            calPanel.add(label);
        }


        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0: 일요일, 6: 토요일
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);


        for (int i = 0; i < firstDayOfWeek; i++) {
            calPanel.add(new JLabel(""));
        }


        for (int day = 1; day <= daysInMonth; day++) {
            ThemeButton dayButton = new ThemeButton(String.valueOf(day));
            String date = String.format("%d-%02d-%02d", year, month + 1, day);

            dayButton.addActionListener(e -> openEventDialog(date));
            if (events.containsKey(date)) {
                dayButton.setBackground(Color.YELLOW);
            }
            calPanel.add(dayButton);
        }


        int remainingCells = 7 - ((firstDayOfWeek + daysInMonth) % 7);
        if (remainingCells < 7) {
            for (int i = 0; i < remainingCells; i++) {
                calPanel.add(new JLabel(""));
            }
        }

        calPanel.revalidate();
        calPanel.repaint();
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

        JPanel addPanel = new JPanel(new GridLayout(6, 2));
        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea();
        JComboBox<String> priorityBox = new JComboBox<>(new String[]{"하", "중", "상"});
        JTextField startTimeField = new JTextField("HH:mm");
        JTextField endTimeField = new JTextField("HH:mm");

        addPanel.add(new JLabel("제목:"));
        addPanel.add(titleField);
        addPanel.add(new JLabel("내용:"));
        addPanel.add(new JScrollPane(descriptionArea));
        addPanel.add(new JLabel("중요도:"));
        addPanel.add(priorityBox);
        addPanel.add(new JLabel("시작 시간:"));
        addPanel.add(startTimeField);
        addPanel.add(new JLabel("종료 시간:"));
        addPanel.add(endTimeField);

        ThemeButton saveButton = new ThemeButton("저장");
        saveButton.addActionListener(e -> {
            String title = titleField.getText();
            String description = descriptionArea.getText();
            String priority = (String) priorityBox.getSelectedItem();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();

            Event newEvent = new Event(title, description, priority, date, startTime, date, endTime);
            events.computeIfAbsent(date, k -> new ArrayList<>()).add(newEvent);

            updateCalendar();
            dialog.dispose();
        });

        dialog.add(addPanel, BorderLayout.NORTH);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    static class Event {
        private final String title, description, priority, startDate, startTime, endDate, endTime;

        public Event(String title, String description, String priority, String startDate, String startTime, String endDate, String endTime) {
            this.title = title;
            this.description = description;
            this.priority = priority;
            this.startDate = startDate;
            this.startTime = startTime;
            this.endDate = endDate;
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return String.format("제목: %s\n내용: %s\n중요도: %s\n시작: %s %s\n종료: %s %s", title, description, priority, startDate, startTime, endDate, endTime);
        }
    }
}
