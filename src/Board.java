import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

    public static Board game;
    private Player[] players;
    //Количество розыгрышей
    private int number_of_hand;
    //Тут хранятся все розыгрышы
    private List<Hand> hands = new ArrayList<>();
    private BufferedWriter writer;
    private BufferedReader reader;
    private InformHandler handler;


    public static void main(String[] args)throws IOException, InterruptedException{
        game = new Board();
        game.run();
    }

    private void run()throws InterruptedException, IOException{
        initialization();
        handShake();
        for (int i = 0; i < number_of_hand ; i++){
            Hand hand = new Hand(game.players);
            if(writer != null){
                hand.setWriter(writer);
                hand.setOutputToFile(true);
            }
            hands.add(hand);
            hand.playersShowCards();
            hand.Auction();
            hand.resultHand();
            hand.printResults();
        }
        handler = new InformHandler(players, hands);
        if(writer != null){
            handler.setWriter(writer);
            handler.setOutputToFile(true);
        }
        menu();
        if(writer != null){
            writer.close();
        }
        reader.close();
    }

    //Игроки знакомятся друг с другом
    private void handShake(){
        try {
            players[0].addOpponent(players[1]);
            players[0].addOpponent(players[2]);

            players[1].addOpponent(players[0]);
            players[1].addOpponent(players[2]);

            players[2].addOpponent(players[0]);
            players[2].addOpponent(players[1]);

            for (Player player : players){
                player.initializationBullet();
            }

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Не все игроки проинициализированны");
        }

    }

    //Инициализация данных игры
    public void initialization()throws IOException, InterruptedException{
        reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isFound = false;
        players = new Player[3];
        int i = 0;
        System.out.println("Преферанс");
        Thread.sleep(200);
        while (true){
            System.out.println("Введите имя игрока" + (i + 1) + "(должно состоять из букв английского алфавита)\n"  +
                                "и через пробел укажите уровень агрессии(от 1 до 3):");
            String s = reader.readLine();
            String[] strings = s.split(" ");

            for (int j = 0; j < i; j++ ){
                if(strings[0].trim().equals(players[j].getName()))isFound = true;
            }
            if(isFound){
                isFound = false;
                System.out.println("Ошибка: игрок с таким именем уже существует.");
                continue;
            }
            if(!strings[0].trim().matches("[A-Za-z]+")){
                System.out.println("Ошибка: имя состоит не только из букв английского алфавита.");
                continue;
            }
            int agr = 0;
            try {
                agr = Integer.parseInt(strings[1].trim());
                if(agr < 1 || agr > 3)throw new Exception();
            }catch (Exception e){
                System.out.println("Ошибка: уровень агрессии введен не правильно. Попробуйте снова.");
                continue;
            }
            Player player = new Player(strings[0].trim(), agr);
            players[i++] = player;
            if(i == 3)break;
        }

        while (true){
            System.out.println("Введите количество раздач, которые будут сыграны:");
            int number;
            try {
                number = Integer.parseInt(reader.readLine());
                if(number < 1)throw new Exception();
            }catch (Exception e){
                System.out.println("Ошибка: данные введены не правильно ,попробуйте снова.");
                continue;
            }
            number_of_hand = number;
            break;
        }

        while (true){
            writer = null;
            System.out.println("Укажите путь к файлу ,в который будет логироваться процесс игры и выводиться\n" +
                               "статистика в конце игры, если вы не хотите , чтобы информация записывалась\n" +
                               "в фаил нажмите 'ENTER':");
            String s = reader.readLine();
            if(s.trim().equals(""))break;
            else{
                try {
                    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s)));
                    break;
                }catch (IOException e){
                    System.out.println("Путь к файлу введен не правильно.");
                    writer.close();
                    continue;
                }
            }
        }

    }
    //Меню для выводов результатов после игры.
    public void menu()throws IOException{
        String s = "";
        while (true){
            System.out.println("***МЕНЮ***");
            System.out.println("Для получения данных определенной раздачи - введите 1");
            System.out.println("Для получения данных о процессе торговли - введите 2");
            System.out.println("Для получения данных о процессе заявки игрока и реакции других игроков - введите 3");
            System.out.println("Для получения данных о процессе розыгрыша - введите 4");
            System.out.println("Для получения данных результата розыгрыша - введите 5");
            System.out.println("Для получения данных о полном процессе розыгрыша - введите 6");
            System.out.println("Для получения данных о состоянии пули, горы и вистах для игрока - введите 7");
            System.out.println("Для получени промежуточного результата игрока - введите 8");
            System.out.println("Для получени статистики по игроку - введите 9");
            System.out.println("Для получения промежуточного результата всех игроков - введите 10");
            System.out.println("Для выхода из игры - введите exit");

            s = reader.readLine();
            if(s.trim().equals("exit"))break;
            int n = 0;
            try {
                 n = Integer.parseInt(s.trim());
                if(n < 1 || n > 10)throw new Exception();
            }catch (Exception e){
                System.out.println("Ошибка: введены не правильные данные");
                continue;
            }

            try {
                switch (n){
                    case 1:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI1(reader.readLine());
                        break;
                    case 2:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI2(reader.readLine());
                        break;
                    case 3:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI3(reader.readLine());
                        break;
                    case 4:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI4(reader.readLine());
                        break;
                    case 5:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI5(reader.readLine());
                        break;
                    case 6:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI6(reader.readLine());
                        break;
                    case 7:
                        System.out.println("Введите номер раздачи:");
                        String s1 = reader.readLine();
                        System.out.println("Введите имя игрока:");
                        String s2 = reader.readLine();
                        handler.getDataAPI7(s2, s1);
                        break;
                    case 8:
                        System.out.println("Введите номер раздачи:");
                        String s3 = reader.readLine();
                        System.out.println("Введите имя игрока:");
                        String s4 = reader.readLine();
                        handler.getDataAPI8(s4, s3);
                        break;
                    case 9:
                        System.out.println("Введите номер раздачи:");
                        String s5 = reader.readLine();
                        System.out.println("Введите имя игрока:");
                        String s6 = reader.readLine();
                        handler.getDataAPI9(s6, s5);
                        break;
                    case 10:
                        System.out.println("Введите номер раздачи:");
                        handler.getDataAPI10(reader.readLine());
                        break;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNumber_of_hand() {
        return number_of_hand;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public InformHandler getHandler() {
        return handler;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setNumber_of_hand(int number_of_hand) {
        this.number_of_hand = number_of_hand;
    }

    public void setHands(List<Hand> hands) {
        this.hands = hands;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public void setHandler(InformHandler handler) {
        this.handler = handler;
    }
}
