package frames.jongh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class calc {
    private JPanel calPanel;
    private JLabel monthLabel;
    private int year, month;
    private final Map<String, List<Event>> events;

    public calc(JPanel parent) {
        events = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        calPanel = new JPanel();
        calPanel.setLayout(new GridLayout(7, 7));
        calPanel.setBounds(200 , 200, 300, 300);
        parent.add(calPanel);

        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(20, 330, 300, 40);

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        monthLabel = new JLabel(getMonthYearLabel(), JLabel.CENTER);

        prevButton.setBounds(0, 0, 40, 40);
        nextButton.setBounds(260, 0, 40, 40);
        monthLabel.setBounds(40, 0, 220, 40);

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
            calPanel.add(new JLabel(weekday, JLabel.CENTER));
        }

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < firstDayOfWeek; i++) {
            calPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            String date = String.format("%d-%02d-%02d", year, month + 1, day);

            dayButton.addActionListener(e -> openEventDialog(date));
            if (events.containsKey(date)) {
                dayButton.setBackground(Color.YELLOW);
            }
            calPanel.add(dayButton);
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

        JButton saveButton = new JButton("저장");
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
            return String.format("제목: %s\n내용: %s\n중요도: %s\n시작: %s %s\n종료: %s %s",
                    title, description, priority, startDate, startTime, endDate, endTime);
        }
    }
}

public class MainPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("메인 화면");
            mainFrame.setSize(1000, 800);
            mainFrame.setLayout(null);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(null);
            mainPanel.setBounds(0, 0, 800, 600);

            calc calendar = new calc(mainPanel);

            JLabel userInfo = new JLabel("회원 정보");
            userInfo.setBounds(20, 20, 200, 100);
            userInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel photoPanel = new JLabel("사진");
            photoPanel.setBounds(20, 140, 200, 200);
            photoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JButton menuButton = new JButton("Product");
            menuButton.setBounds(20, 360, 200, 40);

            mainPanel.add(userInfo);
            mainPanel.add(photoPanel);
            mainPanel.add(menuButton);
            mainFrame.add(mainPanel);

            mainFrame.setVisible(true);
        });
    }
}
