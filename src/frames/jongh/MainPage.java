package frames.jongh;

import javax.swing.*;
import java.awt.*;
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
        calPanel.setBounds(350, 50, 500, 400); // 캘린더 위치와 크기
        parent.add(calPanel);

        JPanel topPanel = new JPanel(null);
        topPanel.setBounds(350, 20, 500, 30);

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
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
            JButton dayButton = new JButton(String.valueOf(day));
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
            return String.format("제목: %s\n내용: %s\n중요도: %s\n시작: %s %s\n종료: %s %s", title, description, priority, startDate, startTime, endDate, endTime);
        }
    }
}

public class MainPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("메인 화면");
            mainFrame.setSize(900, 800); // MainFrame 크기 설정
            mainFrame.setLayout(null);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel(null);
            mainPanel.setBounds(0, 0, 1000, 800);

            calc calendar = new calc(mainPanel);

            JLabel title_name = new JLabel("4만원 회계 관리");
            title_name.setFont(new Font("맑은 고딕", Font.BOLD, 30));
            title_name.setBounds(50, 20, 250, 30);

            JLabel user_title = new JLabel("사원 정보");
            Font font = user_title.getFont();
            Font fontsize = font.deriveFont(25f);
            user_title.setFont(fontsize);
            user_title.setBounds(100,50,200,30);

            JLabel user = new JLabel("<html><br><br>사원 코드: <br><br>사원 이름: <br><br>직급:  <br><br>");
            user.setBounds(20, 20, 300, 200);
            Font font2 = user.getFont();
            Font fontsize2 = font2.deriveFont(12f);
            user.setFont(fontsize2);
            user.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel photo = new JLabel("사진");
            photo.setBounds(350, 480, 500, 250);
            photo.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel menu = new JLabel("<<메뉴>>");
            menu.setBounds(140, 200, 300, 200);

            JButton menuButton1 = new JButton("개발");
            menuButton1.setBounds(20, 360, 300, 40);

            JButton menuButton2 = new JButton("입/출금 신고 내역");
            menuButton2.setBounds(20, 420, 300, 40);

            JButton menuButton3 = new JButton("예산 승인 내역");
            menuButton3.setBounds(20, 480, 300, 40);

            JButton menuButton4 = new JButton("인사");
            menuButton4.setBounds(20, 540, 300, 40);



            mainPanel.add(user_title);
            mainPanel.add(title_name);
            mainPanel.add(user);
            mainPanel.add(photo);
            mainPanel.add(menuButton1);
            mainPanel.add(menuButton2);
            mainPanel.add(menuButton3);
            mainPanel.add(menuButton4);
            mainPanel.add(menu);
            mainFrame.add(mainPanel);


            mainFrame.setVisible(true);
        });
    }
}
