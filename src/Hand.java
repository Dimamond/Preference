import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Класс инкапсулирует данные одной раздачи
public class Hand {
    private static int id = 0;
    private final int idHand;
    private Player[]players;
    //Колода карт для торговли
    private List<Card> deckForAuction = Deck.getDeckForAuction();
    //Колода карт для раздачи игрокам и прикупа
    private List<Card> deckForHand = Deck.getDeckForHand();
    //Игрок который заказал контракт или если распасовка то просто первый игрок
    private Player playerOrder;
    //Индекс козыря если он есть
    private int trump = 0;
    //Сюда записывает всю информацию о розыгрыше
    private StringBuilder datebase = new StringBuilder();
    //Поток вывода данных в фаил
    private BufferedWriter writer;
    //Если есть вывод данных в фаил то true;
    private boolean isOutputToFile;

    public Hand(Player[] players){
        idHand = ++id;
        this.players = players;
        for (int i = 0; i < players.length; i++){
            List<Card> newDeck = Deck.getCardsFromTheDeck(deckForHand, 10);
            Deck.sortDeck(newDeck);
            players[i].reset();
            players[i].setCards(newDeck);
            players[i].setCurrentHand(this);
            players[i].setPlayerOrder(Deck.pas);
        }
    }

    //Печать данных раздачи в консоль и фаил.
    private void print(String s)throws IOException, InterruptedException {
        Thread.sleep(200);
        if(isOutputToFile){
            writer.write(s);
            writer.newLine();
        }
        datebase.append(s);
        System.out.print(s);
    }
    //Игрокам раздаються карты и каказывается прикуп
    public void playersShowCards()throws InterruptedException, IOException{
        print("<<РАЗДАЧА #" + idHand + ">>\n");
        print("**********\n");
        List<Card> cardsPlayer;
        for (int i = 0; i < players.length; i++){
            cardsPlayer = players[i].getCards();
            print(players[i] + ": ");
            for(Card card: cardsPlayer){
                print(card + " ");
            }
            print("\n");
        }
        print("Прикуп: " + deckForHand.get(0) + " " + deckForHand.get(1) + "\n");
    }
    //Процесс торгов
    public void Auction()throws InterruptedException, IOException{
        print("**********\n");
        print("<<ТОРГИ>>\n");
        playerOrder = players[0];
        for (int i = 0 ; i < players.length; i++){
            players[i].getOrger();
            if(players[i].getPlayerOrder().compareTo(playerOrder.getPlayerOrder()) > 0){
                playerOrder = players[i];
            }
            print(players[i] + ": " + players[i].getPlayerOrder() + "\n");
        }
        if(playerOrder.getPlayerOrder().compareTo(Deck.miser) == 0){
            print(playerOrder + ": берет заказ - " + playerOrder.getPlayerOrder() + "\n");
            buyInAndReset();
            game();
        }
        else if(playerOrder.getPlayerOrder().compareTo(Deck.pas) == 0){
            print("РАСПАС\n");
            game();
        }
        else{
            print(playerOrder + ": берет заказ - " + playerOrder.getPlayerOrder() + "\n");
            buyInAndReset();
            finalOrderPlayer();
            vistOrPas();
        }
    }
    //Прикуп и сброс игрока который заказал контракт
    public void buyInAndReset()throws InterruptedException, IOException{
        if(playerOrder.getPlayerOrder().compareTo(Deck.pas) != 0){
            print("**********\n");
            print("<<ПРИКУП И СБРОС>>\n");
            print(playerOrder + ": забирает карты - " + deckForHand.get(0) + " " + deckForHand.get(1) + "\n");
            playerOrder.getCards().addAll(deckForHand);
            deckForHand.clear();
            List<Card> resetCards = playerOrder.resetCards(2);
            print(playerOrder + ": сбрасывает карты - " + resetCards.get(0) + " " + resetCards.get(1) + "\n");
        }
    }
    //Финальные действия игрока который заказал контракт
    public void finalOrderPlayer()throws InterruptedException, IOException{
        print("**********\n");
        print("<<ФИНАЛЬНАЯ ЗАЯВКА>>\n");
        playerOrder.finalOrder();
        print(playerOrder + ": " + playerOrder.getPlayerOrder() + "\n");
        trump = playerOrder.getPlayerOrder().getIndexSuit();
    }
    //Ответные действия других игроков
    public void vistOrPas()throws InterruptedException, IOException{
         print("**********\n");
         print("<<ЗАЯВКИ ДРУГИХ ИГРОКОВ>>\n");
         int indexVist = 0;
         for (int i = 0; i < players.length; i++){
             if(players[i] == playerOrder)continue;
             players[i].getVistOrPas();
             print(players[i] + ": " + players[i].getPlayerOrder() + "\n");
             if(players[i].getPlayerOrder().compareTo(Deck.vist) == 0)indexVist += 1;
         }

         if(indexVist == 0){
             playerOrder.setOrderIndex(0);
         }else{

             for (int i = 0; i < players.length; i++){
                 if(players[i] == playerOrder)continue;
                 if(players[i].getPlayerOrder().compareTo(Deck.pas) == 0){
                     print(players[i] + ": играет в открытую\n");
                 }else if(players[i].getPlayerOrder().compareTo(Deck.vist) == 0){
                     print(players[i] + ": играет в закрытую\n");
                 }
             }
             game();
         }
     }

    //Процесс розыгрыша
    public void game()throws InterruptedException, IOException{
        print("**********\n");
        print("<<РОЗЫГРЫШ>>\n");
        if(trump != 0){
            Deck.sortDeckWithTrump(players[0].getCards(), trump);
            Deck.sortDeckWithTrump(players[1].getCards(), trump);
            Deck.sortDeckWithTrump(players[2].getCards(), trump);
        }else {
            Deck.sortDeck(players[0].getCards());
            Deck.sortDeck(players[1].getCards());
            Deck.sortDeck(players[2].getCards());
        }
        List<Card> board = new ArrayList<>();
        int max = 0;
        int i = 0;
        Player lastPlayer = players[0];
        Card card = Deck.pas ;
        while (true){

            if(deckForHand.size() != 0 && max == 0){
                card = deckForHand.remove(0);
                lastPlayer = players[0];
                print(card + "\n");
            }

            if(lastPlayer == players[i] || max != 0){
                card = players[i].getCard(card);
                print(players[i] + ": " + card + "\n");
                board.add(card);
                max += 1;
            }
            if(max == 3){
                if(trump == 0)Deck.sortDeck(board);
                else Deck.sortDeckWithTrump(board, trump);
                for (int j = 0; j < players.length;j++){
                    if(players[j].takeBribe(board)){
                        lastPlayer = players[j];
                        print(lastPlayer + ": забирает" + "\n");
                    }
                }
                board.clear();
                card = Deck.pas;
                max = 0;
                if(lastPlayer.getCards().size() == 0)break;
            }
            i += 1;
            if(i == 3)i = 0;
        }
    }

    //Результаты игроков
    public void resultHand()throws InterruptedException, IOException{
        print("**********\n");
        print("<<РЕЗУЛЬТАТЫ РОЗЫГРЫША>>\n");
        for (int i = 0; i < players.length; i++){
            if(players[i].isDoneOrder()){
                print(players[i] + ": выполняет заказ " + players[i].getPlayerOrder() + "\n");
            }else {
                if(playerOrder.getPlayerOrder().compareTo(Deck.miser) > 0 && players[i] == playerOrder){
                    print(players[i] + ": не выполняет заказ " + players[i].getPlayerOrder() + "\n");
                }
                else if(playerOrder.getPlayerOrder().compareTo(Deck.miser) == 0 && players[i] == playerOrder){
                    print(players[i] + ": не выполняет заказ " + players[i].getPlayerOrder() + "\n");
                }else if(players[i].getPlayerOrder().compareTo(Deck.vist) == 0){
                    print(players[i] + ": не выполняет заказ " + players[i].getPlayerOrder() + "\n");
                }
            }
        }
    }
    //Вывод пульки в конце розыгрыша
    public void printResults()throws InterruptedException, IOException{
        print("**********\n");
        print("<<ПУЛЯ>>\n");
        for (int i = 0; i < players.length; i++){
            players[i].updateBullet();
            players[i].getBullet();
            print("*****\n");
            print(players[i]+ ":\n");
            for (Map.Entry<String, Integer> entry : players[i].getBullet().entrySet()){
                print(entry.getKey() +  entry.getValue() + "\n");
            }
        }
    }

    public List<Card> getDeckForAuction() {
        return deckForAuction;
    }

    public StringBuilder getDatebase() {
        return datebase;
    }

    public Player getPlayerOrder() {
        return playerOrder;
    }

    public int getTrump() {
        return trump;
    }

    public static int getId() {
        return id;
    }

    public int getIdHand() {
        return idHand;
    }

    public Player[] getPlayers() {
        return players;
    }

    public List<Card> getDeckForHand() {
        return deckForHand;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public boolean isOutputToFile() {
        return isOutputToFile;
    }

    public static void setId(int id) {
        Hand.id = id;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public void setDeckForAuction(List<Card> deckForAuction) {
        this.deckForAuction = deckForAuction;
    }

    public void setDeckForHand(List<Card> deckForHand) {
        this.deckForHand = deckForHand;
    }

    public void setPlayerOrder(Player playerOrder) {
        this.playerOrder = playerOrder;
    }

    public void setTrump(int trump) {
        this.trump = trump;
    }

    public void setDatebase(StringBuilder datebase) {
        this.datebase = datebase;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setOutputToFile(boolean outputToFile) {
        isOutputToFile = outputToFile;
    }
}
