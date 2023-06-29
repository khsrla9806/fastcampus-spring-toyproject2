package view;

import model.stadium.Stadium;
import service.StadiumService;

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
            String[] tableHead = {"Id", "스타디움명", "등록일"};
            printRow(tableHead);
            for (Stadium stadium : stadiums) {
                String[] tableData = {
                        stadium.getId().toString(),
                        stadium.getName(),
                        ViewFormatter.getFormatDateTime(stadium.getCreatedAt())
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
            builder.append(ViewFormatter.getFormatData(str));
        }
        System.out.println(builder);
    }
}
