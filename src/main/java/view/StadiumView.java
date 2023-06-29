package view;

import model.stadium.Stadium;
import service.StadiumService;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StadiumView {
    private final StadiumService stadiumService;

    public StadiumView(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    public void create(Map<String, Object> params) {
        try {
            String name = params.get("name").toString();
            System.out.println(stadiumService.createStadium(name));
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }
    }

    public void findStadiums() {
        try {
            List<Stadium> stadiums = stadiumService.getAllStadiums();
            String[] tableHead = {"스타디움 ID", "스타디움 이름", "등록일"};
            printRow(tableHead);
            for (Stadium stadium : stadiums) {
                String[] tableData = {
                        stadium.getId().toString(),
                        stadium.getName(),
                        getFormatDate(stadium.getCreatedAt())
                };
                printRow(tableData);
            }
        } catch (Exception e) {
            throw new RuntimeException("잘못된 요청입니다.");
        }

    }
    private void printRow(String[] input) {
        StringBuilder builder = new StringBuilder();
        for (String str : input) {
            builder.append(formatData(str));
        }
        System.out.println(builder);
    }

    private String formatData(String data) {
        if (data == null) {
            return "";
        }
        return String.format("%-8s", data);
    }
    private String getFormatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY.MM.dd");
        return timestamp.toLocalDateTime().format(formatter);
    }
}
