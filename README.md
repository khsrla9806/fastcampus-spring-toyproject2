# ⚾️ 야구관리 프로그램 (FastCampus Toy Project)

<br>

## 🧑🏻‍💻 팀 구성
<table>
   <tr>
      <td align="center">
        <a href="https://github.com/khsrla9806">
          <img src="https://avatars.githubusercontent.com/u/70641477?v=4" width="100px;" alt=""/><br>
          <sub><b>김훈섭</b></sub><br>
          <sub><b>khsrla9806</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/jeanparkk">
          <img src="https://avatars.githubusercontent.com/u/119830820?v=4" width="100px;" alt=""/><br>
          <sub><b>박장희</b></sub><br>
          <sub><b>jeanparkk</b></sub>
        </a>
      </td>
   </tr>
</table>

<br><br>

## 🛠️ 프로젝트에 사용한 기술
<img src="https://img.shields.io/badge/Java 11-FF160B?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/JDBC-A9225C?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=flat-square&logo=IntelliJ IDEA&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=flat-square&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/notion-000000?style=flat-square&logo=notion&logoColor=white">

<br><br>

## 🙋🏻‍♂️ 팀원 역할
`김훈섭`
- 테이블 SQL 설계
- Player, OutPlayer 모델, DAO 설계
- PlayerService, OutPlayerService 구현
- Player, OutPlayer View 구현
- 전체 기능 테스트 및 리펙토링

<br>

`박장희`
- 테이블 SQL 설계
- Stadium, Team 모델, DAO 설계
- StadiumService, TeamService 구현
- Stadium, Team View 구현
- 전체 기능 테스트 및 리펙토링

<br><br>

## 🖥️ 프로젝트 초기세팅
`데이터베이스 스키마/테이블 생성`
``` sql
create database baseball;
use baseball;

create table stadium(
  id int primary key auto_increment,
  name varchar(20),
  created_at timestamp
);

create table team (
  id int primary key auto_increment,
  stadium_id int,
  name varchar(20),
  created_at timestamp,
  foreign key(stadium_id) references stadium(id)
);

create table player(
  id int primary key auto_increment,
  team_id int,
  name varchar(20),
  position varchar(10),
  created_at timestamp,
  unique(team_id, position),
  foreign key (team_id) references team(id)
);

create table out_player(
  id int primary key auto_increment,
  player_id int,
  reason varchar(255),
  created_at timestamp,
  foreign key(player_id) references player(id)
);
```

<br>

`의존성 추가`
``` groovy
dependencies {
    implementation 'org.assertj:assertj-core:3.24.2'
    compileOnly group: 'org.projectlombok', name: 'lombok', version: '1.18.28'
    annotationProcessor group: 'org.projectlombok', name: 'lombok', version: '1.18.28'
    implementation 'com.mysql:mysql-connector-j:8.0.32'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
}
```

<br>

`DBConnection 생성`
``` java
public class DBConnection {
    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/baseball";
        String username = "root";
        String password = "root1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

<br>

`DBCOnnection 연동 테스트`
``` java
class DBConnectionTest {
    @Test
    @DisplayName("DB Connection 테스트")
    void DBConnectionTest() {
        // Given & When
        Connection connection = DBConnection.getConnection();

        // Then
        assertThat(connection).isNotNull();
    }
}
```

<br><br>

## ✋🏻 깃허브 커밋 메시지 규칙
```
feat: 새로운 기능 추가했을 때
fix: 버그나 오류 수정했을 때
refactor: 코드 리팩토링했을 때
chore: 약간 애매한 기타 변경사항
docs: 리드미 파일이나 md 파일 수정할 때 (문서작업)
```

<br><br>

## 🙌🏻 협업 규칙
- 각자 작업 브랜치를 생성하여 맡은 작업을 진행
- 작업이 끝나면 `develop` 브랜치로 Pull Request를 요청
- 모두 PR이 끝나면 모여서 코드 논의 후에 merge 진행
- 모든 작업이 끝난 후 `develop` 브랜치에서 각자 전체 기능 테스트
- 모든 테스트 완료 시 `main` 브랜치로 merge
