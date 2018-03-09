import java.lang.reflect.Array;
import java.util.*;

public class Player {
    private String name;
    private int aggression;
    private List<Card> cards;
    private List<Player> opponents;
    private Hand currentHand;
    private Card playerOrder;
    private int orderIndex;
    private Map<String, Integer> bullet;



    Player(String name, int aggression){
        this.name = name;
        this.aggression = aggression;
        opponents = new ArrayList<>();

    }


    //Игрок делает заявку в торговле.
    public void  getOrger(){
        List<Card> deck = currentHand.getDeckForAuction();
        try {
            if(aggression == 2){
                if((deck.get(0).compareTo(Deck.miser) == 0)){
                    playerOrder = deck.remove(0);
                }else playerOrder = Deck.pas;
            }else if(aggression ==  3){
                if((deck.get(0).compareTo(Deck.miser) == 0))deck.remove(0);
                int random = (int)(Math.random()* ((deck.size() - 1)));
                Card order = deck.get(random);
                Deck.getCardsFromTheDeck(deck, random + 1);
                playerOrder = order;
            }else playerOrder = Deck.pas;
        }catch (IndexOutOfBoundsException e){
            playerOrder = Deck.pas;
        }

    }


    //Игрок сбрасывает 2 карты после прикупа
    public List<Card> resetCards(int number){

        Deck.sortDeck(cards);
        if(playerOrder.compareTo(Deck.miser) > 0){
            return Deck.deleteMinCard(cards, number);
        }else return Deck.deleteMaxCard(cards, 2);

    }
    //Финальная заявка игрока
    public void finalOrder(){
        List<Card> deck = currentHand.getDeckForAuction();
        int suitMax = Deck.getSuitMax(cards);
        if(deck.size() != 0){
            int i;
            for (i = 0; i < deck.size(); i++){
                if(deck.get(i).getIndexSuit() == suitMax){
                    playerOrder = deck.get(i);
                    break;
                }
            }
            if(i == deck.size())playerOrder = deck.get(0);

        }
        orderIndex = playerOrder.getIndexCard();

    }
    //Ответные действия грока на финальную зафявку игрока
    public void getVistOrPas(){
        int ord = currentHand.getPlayerOrder().getPlayerOrder().getIndexCard();
        if(ord > 7)orderIndex = 1;
        else if(ord == 7)orderIndex = 2;
        else if(ord == 6)orderIndex = 4;
        if(aggression == 1){
            playerOrder = Deck.pas;
        }else if(aggression == 2){
            playerOrder = Deck.vist;
        }else{
            playerOrder = Deck.vist;
        }
    }

    //Игрок дает карту для розыгрыша
    public Card getCard(Card c){

        int lastSuit = c.getIndexSuit();
        int trump = currentHand.getTrump();
        if(c.compareTo(Deck.pas) == 0){

                if(playerOrder.compareTo(Deck.miser) == 0){
                    return cards.get(0);
                }else if((playerOrder.compareTo(Deck.pas) == 0)){
                    return cards.get(0);
                }else if((playerOrder.compareTo(Deck.vist) == 0)){
                    return cards.get(cards.size() - 1);
                }else return cards.get(cards.size() - 1);

        }else {

                if(playerOrder.compareTo(Deck.miser) == 0){

                    for (int i = 0; i < cards.size();i++){
                        if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);

                    }
                    if(trump == 0){
                        return cards.get(0);
                    }else {
                        for (int i = 0; i < cards.size();i++){
                            if(cards.get(i).getIndexSuit() == trump)return cards.get(i);
                        }
                        return cards.get(0);
                    }

                }else if((playerOrder.compareTo(Deck.pas) == 0)){
                    for (int i = 0; i < cards.size();i++){
                        if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);

                    }
                    if(trump == 0){
                        return cards.get(0);
                    }else {
                        for (int i = 0; i < cards.size();i++){
                            if(cards.get(i).getIndexSuit() == trump)return cards.get(i);
                        }
                        return cards.get(0);

                    }

                }else if((playerOrder.compareTo(Deck.vist) == 0)){
                    if(trump == 0){
                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);
                        }

                        return cards.get(cards.size() - 1);

                    }else{
                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == trump)return cards.get(i);
                        }

                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);
                        }

                        return cards.get(cards.size() - 1);

                    }

                }else{
                    if(trump == 0){
                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);
                        }

                        return cards.get(cards.size() - 1);

                    }else{
                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == trump)return cards.get(i);
                        }

                        for (int i = cards.size() - 1; i >= 0 ;i--){
                            if(cards.get(i).getIndexSuit() == lastSuit)return cards.get(i);
                        }

                        return cards.get(cards.size() - 1);

                    }
                }
        }
    }
    //Метод проверяет - может ли игрок забрать вязтку
    public boolean takeBribe(List<Card> cards){
        int i;
        int max = cards.size() - 1;
        for (i = 0; i < cards.size(); i++){
            if(this.cards.remove(cards.get(i)))break;
        }
        if(i == max){

            orderIndex -= 1;
            if(currentHand.getPlayerOrder().playerOrder.compareTo(Deck.pas) != 0 &&
                    this != currentHand.getPlayerOrder()){
                for (int j = 0; j < opponents.size(); j++){
                    if(opponents.get(j) != currentHand.getPlayerOrder()){
                        opponents.get(j).setOrderIndex(orderIndex);
                    }
                }
            }

            int bribes = bullet.get(this + " взял взяток - ");
            bullet.put(this + " взял взяток - ", ++bribes);
            return true;
        }
        else return false;

    }
    //Обновление пульки в сообтветствии с результатами розыгрыша
    public void updateBullet(){
        Card order = currentHand.getPlayerOrder().playerOrder;
        int indexOrder = order.getIndexCard();
        int valueGame = 0;
        if(indexOrder == 6)valueGame = 2;
        else if(indexOrder == 7)valueGame = 4;
        else if(indexOrder == 8)valueGame = 6;
        else if(indexOrder == 9)valueGame = 8;
        else if(indexOrder == 10 || indexOrder == 1)valueGame = 10;
        else if(indexOrder == 0)valueGame = 1;

        if((this == currentHand.getPlayerOrder()) && (order.compareTo(Deck.miser) > 0)){
            if(opponents.get(0).getPlayerOrder().compareTo(Deck.pas) == 0
                    && opponents.get(1).getPlayerOrder().compareTo(Deck.pas) == 0){
                int result = bullet.get("пулька - ");
                bullet.put("пулька - ", result + valueGame);
            }
            else if(orderIndex <= 0){
                int result = bullet.get("пулька - ");
                bullet.put("пулька - ", result + valueGame);
            }else {
                int result = bullet.get("гора - ");
                bullet.put("гора - ", result + valueGame * orderIndex);
            }
        }else if((this == currentHand.getPlayerOrder()) && (order.compareTo(Deck.miser) == 0)){

             if(orderIndex == 0){
                int result = bullet.get("пулька - ");
                bullet.put("пулька - ", result + valueGame);
            }else {
                 int result = bullet.get("гора - ");
                 bullet.put("гора - ", result + valueGame * orderIndex * -1);
             }
        }

        else {
            if(order.compareTo(Deck.miser) > 0){
                if(playerOrder.compareTo(Deck.pas) != 0){
                    if(orderIndex > 0) {
                        int result = bullet.get("гора - ");
                        bullet.put("гора - ", result + valueGame * orderIndex);
                    }
                }
                int bribes = bullet.get(this + " взял взяток - ");
                int result = bullet.get("висты на " + currentHand.getPlayerOrder() + " - ");
                bullet.put("висты на " + currentHand.getPlayerOrder() + " - ", result + bribes * valueGame);
            }else if(order.compareTo(Deck.pas) == 0){
                int result = bullet.get("гора - ");
                bullet.put("гора - ", result + valueGame * orderIndex * -1);
            }
        }



    }
    //выполнил ли игрок свой контракт?
    public boolean isDoneOrder(){

        if(this == currentHand.getPlayerOrder()){
            if(playerOrder.compareTo(Deck.miser) > 0 && orderIndex <= 0){
                return true;
            }else if(playerOrder.compareTo(Deck.miser) == 0 && orderIndex == 0){
                return true;
            }else return false;
        }else{
           if(playerOrder.compareTo(Deck.vist)== 0 && orderIndex <= 0){
               return true;
           }
           else return false;
        }
    }
    //Сброс необходимых параметров игрока перед новой раздачей
    public void reset(){
        cards = null;
        currentHand = null;
        playerOrder = null;
        orderIndex = 0;
        bullet.put(this + " взял взяток - ", 0);

    }
    //Инициализация пульки игрока
    public void initializationBullet(){
        bullet = new LinkedHashMap<>();
        bullet.put(this + " взял взяток - ", 0);
        bullet.put("гора - ", 0);
        bullet.put("пулька - ", 0);
        bullet.put("висты на " + opponents.get(0) + " - ", 0);
        bullet.put("висты на " + opponents.get(1) + " - ", 0);
    }

    public String getName() {
        return name;
    }

    public int getAggression() {
        return aggression;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Hand getCurrentHand() {
        return currentHand;
    }

    public Card getPlayerOrder() {
        return playerOrder;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public List<Player> getOpponents() {
        return opponents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAggression(int aggression) {
        this.aggression = aggression;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setCurrentHand(Hand currentHand) {
        this.currentHand = currentHand;
    }

    public void setPlayerOrder(Card playerOrder) {
        this.playerOrder = playerOrder;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setOpponents(List<Player> opponents) {
        this.opponents = opponents;
    }

    public void addOpponent(Player opponent){
        this.opponents.add(opponent);
    }

    public Map<String, Integer> getBullet() {
        return bullet;
    }

    @Override
    public String toString() {
        return name;
    }
}
