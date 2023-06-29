import view.BaseBallView;

import java.util.Map;
import java.util.Scanner;


public class BaseBallApp {

    public static void main(String[] args) {
        baseballAppStart();
    }

    private static void baseballAppStart() {
        BaseBallView view = new BaseBallView();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("\n어떤 기능을 요청하시겠습니까? (EXIT)");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                break;
            }
            String[] commandAndParams = view.separateCommandAndParams(input);
            String command = commandAndParams[0];
            if (commandAndParams.length == 1) {
                view.renderWithoutParams(command);
            } else {
                try {
                    Map<String, Object> params = view.getParams(commandAndParams);
                    view.renderWithParams(command, params);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("BaseBallApp을 종료합니다.");
    }
}
