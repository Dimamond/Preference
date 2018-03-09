import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//Класс делает парсинг данных раздач и выводит результат в соответствии с требованиями API
public class InformHandler {
    private Player[] players;
    private List<Hand> hands;
    private BufferedWriter writer;
    private boolean isOutputToFile;


    public InformHandler(Player[] players, List<Hand> hands){
        this.players = players;
        this.hands = hands;
    }

    private void print(String s)throws IOException{
        if(isOutputToFile){
            writer.write(s.trim());
            writer.newLine();
        }
        System.out.println(s.trim());
    }

    public void getDataAPI1(String handNumber)throws Exception{
        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String[]strings1 = strings[1].trim().split("\n");
        for (int i = 0; i < strings1.length; i++){
            print(strings1[i]);
        }
        print(players[0] + " - начинает торги");
    }

    public void getDataAPI2(String handNumber)throws Exception{

        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String[] patterns = {"<<ТОРГИ>>", "<<ПРИКУП И СБРОС>>"};
        for (int i = 0; i < strings.length; i++){
            for (int j = 0; j < patterns.length; j++){
                if(strings[i].contains(patterns[j])){
                    String[]strings1 = strings[i].trim().split("\n");
                    for(int n = 0; n < strings1.length; n++){
                        print(strings1[n]);
                    }
                }
            }
        }

    }

    public void getDataAPI3(String handNumber)throws Exception{
        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String[] patterns = {"<<ФИНАЛЬНАЯ ЗАЯВКА>>", "<<ЗАЯВКИ ДРУГИХ ИГРОКОВ>>"};
        for (int i = 0; i < strings.length; i++){
            for (int j = 0; j < patterns.length; j++){
                if(strings[i].contains(patterns[j])){
                    String[]strings1 = strings[i].trim().split("\n");
                    for(int n = 0; n < strings1.length; n++){
                        print(strings1[n]);

                    }
                }
            }
        }
    }

    public void getDataAPI4(String handNumber)throws Exception{
        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String pattern = "<<РОЗЫГРЫШ>>";
        for (int i = 0; i < strings.length; i++){
            if(strings[i].contains(pattern)){
                String[]strings1 = strings[i].trim().split("\n");
                for(int n = 0; n < strings1.length; n++){
                    print(strings1[n]);

                }
            }
        }

    }

    public void getDataAPI5(String handNumber)throws Exception{
        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        List<Integer> previousResult = new ArrayList<>();
        if(number - 1 > 0){
            String previous = hands.get(number - 2).getDatebase().toString();
            String[] stringsPrevious = previous.split("[*]{10}");
            String[] stringsPrevious1 = stringsPrevious[stringsPrevious.length - 1].split("[*]{5}");
            for(int i = 1; i < stringsPrevious1.length ; i++){
                String[] stringsPrevious2 = stringsPrevious1[i].trim().split("\n");
                for (int j = 2; j < stringsPrevious2.length; j++){
                    String[] stringsPrevious3 = stringsPrevious2[j].split(" - ");
                    int result = Integer.parseInt(stringsPrevious3[1].trim());
                    previousResult.add(result);
                }
            }
        }

        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String[] strings1 = strings[strings.length - 1].split("[*]{5}");
        for (int i = 1; i < strings1.length; i++){

            String[] strings2 = strings1[i].trim().split("\n");
            print(strings2[0]);
            print(strings2[1]);

            for (int j = 2; j < strings2.length; j++){
                if(number - 1 == 0){
                    print(strings2[j]);

                }else{
                    String[] strings3 = strings2[j].split(" - ");
                    int result = Integer.parseInt(strings3[1].trim());

                    result = result - previousResult.remove(0);
                    String newResult = strings3[0] + " - " + result;
                    print(newResult);

                }
            }
        }
    }


    public void getDataAPI6(String handNumber)throws Exception{

        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.trim().split("\n");
        for (int i = 0; i < strings.length; i++){
            print(strings[i]);
        }

    }

    public void getDataAPI7(String playerName, String handNumber)throws Exception{

        boolean isFound = false;
        for(int i = 0 ; i < players.length; i++){
            if(players[i].toString().trim().equals(playerName.trim())){
                isFound = true;
            }
        }
        if(!isFound)throw new Exception("Ошибка: игрока с таким именем не существует");

        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        print(strings[0]);
        String[] strings1 = strings[strings.length - 1].split("[*]{5}");
        for (int i = 1; i < strings1.length; i++){
            if(strings1[i].contains(playerName + ":")){
                String[] strings2 = strings1[i].trim().split("\n");
                for(int j = 0; j < strings2.length; j++){
                    if(j == 1)continue;
                    print(strings2[j]);
                }

            }
        }
    }

    public void getDataAPI8(String playerName, String handNumber)throws Exception{

        boolean isFound = false;
        for(int i = 0 ; i < players.length; i++){
            if(players[i].toString().trim().equals(playerName.trim())){
                isFound = true;
            }
        }
        if(!isFound)throw new Exception("Ошибка: игрока с таким именем не существует");

        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        String[] strings1 = strings[strings.length - 1].split("[*]{5}");

        int[][] bullet = new int[3][4];

        for (int i = 1; i < strings1.length ; i++){
            String[] strings2 = strings1[i].trim().split("\n");
            for (int j = 2; j < strings2.length; j++){
                String[] strings3 = strings2[j].trim().split(" - ");
                int result = Integer.parseInt(strings3[1]);
                bullet[i - 1][j - 2] = result;
            }

        }

        int minMountain = bullet[0][0];

        for (int i = 1 ; i < bullet.length; i++){
            if(minMountain > bullet[i][0]) minMountain = bullet[i][0];
        }

        for (int i = 0 ; i < bullet.length; i++){
            bullet[i][0] = minMountain;
        }

        int playerMauntain;

        playerMauntain = bullet[0][0] / 3 * 10;
        bullet[0][0] = 0;
        bullet[1][2] += playerMauntain;
        bullet[2][2] += playerMauntain;

        playerMauntain = bullet[1][0] / 3 * 10;
        bullet[1][0] = 0;
        bullet[0][2] += playerMauntain;
        bullet[2][3] += playerMauntain;

        playerMauntain = bullet[2][0] / 3 * 10;
        bullet[2][0] = 0;
        bullet[0][3] += playerMauntain;
        bullet[1][3] += playerMauntain;


        int vist;
        vist = bullet[0][2] - bullet[1][2];
        bullet[0][2] = vist;
        bullet[1][2] = vist * -1;


        vist = bullet[0][3] - bullet[2][2];
        bullet[0][3] = vist;
        bullet[2][2] = vist * -1;

        vist = bullet[1][3] - bullet[2][3];
        bullet[1][3] = vist;
        bullet[2][3] = vist * -1;

        int[] result = new int[3];

        result[0] = bullet[0][2] + bullet[0][3];
        result[1] = bullet[1][2] + bullet[1][3];
        result[2] = bullet[2][2] + bullet[2][3];

        print(strings[0]);
        for(int i = 0; i < result.length; i++){

            if(players[i].toString().equals(playerName.trim())){
                String stringResult = players[i] + ": " +result[i];
                print(stringResult);
            }
        }
    }


    public void getDataAPI9(String playerName, String handNumber)throws Exception{
        boolean isFound = false;
        for(int i = 0 ; i < players.length; i++){
            if(players[i].toString().trim().equals(playerName.trim())){
                isFound = true;
            }
        }
        if(!isFound)throw new Exception("Ошибка: игрока с таким именем не существует");

        int[] typesOfGame = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }

        for(int i = 0; i < number; i++){
            String s = hands.get(i).getDatebase().toString();

            if(s.contains("РАСПАС"))typesOfGame[0]++;
            else if(s.contains("МИЗЕР"))typesOfGame[1]++;
            else typesOfGame[2]++;
        }

        for(int i = 0; i < number; i++){
            String s = hands.get(i).getDatebase().toString();
            String[] strings = s.trim().split("\n");
            for (int j = 0; j< strings.length; j++){
                s = strings[j].trim();
                if(s.matches("^" +playerName.trim() + ": выполняет заказ ..$"))typesOfGame[3]++;
                else if(s.matches("^" +playerName.trim() + ": не выполняет заказ ..$"))typesOfGame[4]++;
                else if(s.matches("^" +playerName.trim() + ": выполняет заказ МИЗЕР$"))typesOfGame[5]++;
                else if(s.matches("^" +playerName.trim() + ": не выполняет заказ МИЗЕР$"))typesOfGame[6]++;
                else if(s.matches("^" +playerName.trim() + ": выполняет заказ ВИСТ$"))typesOfGame[7]++;
                else if(s.matches("^" + playerName.trim() + ": не выполняет заказ ВИСТ$"))typesOfGame[8]++;
            }
        }

        String s = "Статистика игрока " + playerName + " после " + handNumber + " раздачи:";
        print(s);

        for (int i = 0; i < typesOfGame.length; i++){
            String s1 = "";
            if(typesOfGame[i] > 0){
                if(i == 0){
                    s1 = playerName + " сыграл распасовок - " + typesOfGame[i];
                }else if(i == 1){
                    s1 = playerName + " сыграл мизеров - " + typesOfGame[i];
                }else if(i == 2){
                    s1 = playerName + " сыграл игр на взятки - " + typesOfGame[i];
                }else if(i == 3){
                    s1 = playerName + " успешно выполняет заказ - " + typesOfGame[i];
                }else if(i == 4){
                    s1 = playerName + " не выполняет заказ - " + typesOfGame[i];
                }else if(i == 5){
                    s1 = playerName + " успешно выполняет заказ мизер - " + typesOfGame[i];
                }else if(i == 6){
                    s1 = playerName + " не выполняет заказ мизер - " + typesOfGame[i];
                }else if(i == 7){
                    s1 = playerName + " успешно выполняет вист - " + typesOfGame[i];
                }else if(i == 8){
                    s1 = playerName + " не выполняет вист - " + typesOfGame[i];
                }
            }
            if(!s1.equals("")){
                print(s1);
            }
        }
    }


    public void getDataAPI10(String handNumber)throws Exception{
        String string = "";
        int number = 0;
        try {
            number = Integer.parseInt(handNumber);
            string = hands.get(number - 1).getDatebase().toString();
        }catch (Exception e){
            throw new Exception("Ошибка: раздачи с таким номером не существует");
        }
        String[] strings = string.split("[*]{10}");
        String[] strings1 = strings[strings.length - 1].split("[*]{5}");
        int[][] bullet = new int[3][4];

        for (int i = 1; i < strings1.length ; i++){
             String[] strings2 = strings1[i].trim().split("\n");
             for (int j = 2; j < strings2.length; j++){
                 String[] strings3 = strings2[j].trim().split(" - ");
                 int result = Integer.parseInt(strings3[1]);
                 bullet[i - 1][j - 2] = result;
             }

        }

        int minMountain = bullet[0][0];

        for (int i = 1 ; i < bullet.length; i++){
            if(minMountain > bullet[i][0]) minMountain = bullet[i][0];
        }

        for (int i = 0 ; i < bullet.length; i++){
            bullet[i][0] = minMountain;
        }

        int playerMauntain;

        playerMauntain = bullet[0][0] / 3 * 10;
        bullet[0][0] = 0;
        bullet[1][2] += playerMauntain;
        bullet[2][2] += playerMauntain;

        playerMauntain = bullet[1][0] / 3 * 10;
        bullet[1][0] = 0;
        bullet[0][2] += playerMauntain;
        bullet[2][3] += playerMauntain;

        playerMauntain = bullet[2][0] / 3 * 10;
        bullet[2][0] = 0;
        bullet[0][3] += playerMauntain;
        bullet[1][3] += playerMauntain;


        int vist;
        vist = bullet[0][2] - bullet[1][2];
        bullet[0][2] = vist;
        bullet[1][2] = vist * -1;


        vist = bullet[0][3] - bullet[2][2];
        bullet[0][3] = vist;
        bullet[2][2] = vist * -1;

        vist = bullet[1][3] - bullet[2][3];
        bullet[1][3] = vist;
        bullet[2][3] = vist * -1;

        int[] result = new int[3];

        result[0] = bullet[0][2] + bullet[0][3];
        result[1] = bullet[1][2] + bullet[1][3];
        result[2] = bullet[2][2] + bullet[2][3];

        print(strings[0]);

        for(int i = 0; i < result.length; i++){
            String stringResult = players[i] + ": " +result[i];
            print(stringResult);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public boolean isOutputToFile() {
        return isOutputToFile;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setOutputToFile(boolean outputToFile) {
        isOutputToFile = outputToFile;
    }
}
