package service;

import model.stadium.StadiumDao;
import model.stadium.Stadium;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StadiumService {
    private StadiumDao stadiumDao;

    public StadiumService(Connection connection) {
        stadiumDao = new StadiumDao(connection);
    }

    // 야구장 등록 기능
    public String createStadium(String stadiumName) {
        try {
            int result = stadiumDao.createStadium(stadiumName);
            if (result > 0) {
                return "성공";
            } else {
                return "실패";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "실패";
        }
    }

    // 전체 야구장 목록 조회 기능
    public List<Stadium> getAllStadiums() {
        List<Stadium> stadiums = stadiumDao.getAllStadiums();
        if (stadiums != null && !stadiums.isEmpty()) {
            return stadiums;
        } else {
            return List.of();
        }
    }
}
