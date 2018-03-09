import java.lang.reflect.Array;
import java.util.*;

public class Deck {

    //Индексы карт в и мастей в соответсвии с их силой
    public static int VIST = -1;
    public static int PAS = 0;
    public static int MISER = 1;
    public static int SPADES = 2;
    public static int CLUBS = 3;
    public static int DIAMONDS = 4;
    public static int HEARTS = 5;
    public static int SIX = 6;
    public static int SEVEN = 7;
    public static int EIGHT = 8;
    public static int NINE = 9;
    public static int TEN = 10;
    public static int JACK = 11;
    public static int QUEEN = 12;
    public static int KING = 13;
    public static int ACE = 14;

    //Обявление и инициализация действий игроков
    public static Card vist = new Card("ВИСТ", VIST);
    public static Card pas = new Card("ПАС", PAS);
    public static Card miser = new Card("МИЗЕР", MISER);
    //Объявление и инициализация колоды
    public static List<Card> deck = new ArrayList<>();
    static {
        deck.add(new Card(SuitCard.SPADES, TypeCard.SIX, SPADES, SIX));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.SIX, CLUBS, SIX));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.SIX, DIAMONDS, SIX));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.SIX, HEARTS, SIX));

        deck.add(new Card(SuitCard.SPADES, TypeCard.SEVEN, SPADES, SEVEN));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.SEVEN, CLUBS, SEVEN));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.SEVEN, DIAMONDS, SEVEN));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.SEVEN, HEARTS, SEVEN));

        deck.add(new Card(SuitCard.SPADES, TypeCard.EIGHT, SPADES, EIGHT));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.EIGHT, CLUBS, EIGHT));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.EIGHT, DIAMONDS, EIGHT));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.EIGHT, HEARTS, EIGHT));

        deck.add(new Card(SuitCard.SPADES, TypeCard.NINE, SPADES, NINE));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.NINE, CLUBS, NINE));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.NINE, DIAMONDS, NINE));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.NINE, HEARTS, NINE));

        deck.add(new Card(SuitCard.SPADES, TypeCard.TEN, SPADES, TEN));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.TEN, CLUBS, TEN));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.TEN, DIAMONDS, TEN));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.TEN, HEARTS, TEN));

        deck.add(new Card(SuitCard.SPADES, TypeCard.JACK, SPADES, JACK));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.JACK, CLUBS, JACK));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.JACK, DIAMONDS, JACK));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.JACK, HEARTS, JACK));

        deck.add(new Card(SuitCard.SPADES, TypeCard.QUEEN, SPADES, QUEEN));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.QUEEN, CLUBS, QUEEN));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.QUEEN, DIAMONDS, QUEEN));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.QUEEN, HEARTS, QUEEN));

        deck.add(new Card(SuitCard.SPADES, TypeCard.KING, SPADES, KING));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.KING, CLUBS, KING));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.KING, DIAMONDS, KING));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.KING, HEARTS, KING));

        deck.add(new Card(SuitCard.SPADES, TypeCard.ACE, SPADES, ACE));
        deck.add(new Card(SuitCard.CLUBS, TypeCard.ACE, CLUBS, ACE));
        deck.add(new Card(SuitCard.DIAMONDS, TypeCard.ACE, DIAMONDS, ACE));
        deck.add(new Card(SuitCard.HEARTS, TypeCard.ACE, HEARTS, ACE));




    }

    private  Deck(){ }

    //Метод удаляет нужное количество минимальных карт из колоды
    public static List<Card> deleteMinCard(List<Card> cards, int numberCards){
        List<Card> deleteCards = new ArrayList<>();


            try{

                for (int i = 0; i < numberCards;i++) {
                    deleteCards.add(cards.remove(0));
                }

            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Колода пуста");
            }



        return deleteCards;
    }
    //Определение каких мастей больше всего в колоде. Возращаеться индекс масти
    public static int getSuitMax(List<Card> cards){
        int suit[] = {0, 0, 0, 0};
        for (int i = 0; i < cards.size(); i++){
            if(cards.get(i).getIndexSuit() == SPADES)suit[0]++;
            else if(cards.get(i).getIndexSuit() == CLUBS)suit[1]++;
            else if(cards.get(i).getIndexSuit() == DIAMONDS)suit[2]++;
            else if((cards.get(i).getIndexSuit() == DIAMONDS))suit[3]++;
        }

        int max = suit[0];
        for (int i = 0; i < suit.length; i++){ if(max<suit[i])max = suit[i]; }
        for (int i = 0; i < suit.length; i++){ if(suit[i] == max)return i + 2;}
        return 0;

    }

    //Удаляет необходимое количесво карт с верхушки колоды
    public static List<Card> deleteMaxCard(List<Card> cards, int numberCards){
        List<Card> deleteCards = new ArrayList<>();

        try{

            for (int i = 0; i < numberCards;i++) {
                deleteCards.add(cards.remove(cards.size() - 1));
            }

        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Колода пуста");
        }



        return deleteCards;

    }
    //Сортировка колоды в соответствии с индексами карт и мастей
    public static void sortDeck(List<Card> deck){
        Collections.sort(deck);
    }

    public static void sortDeckWithTrump(List<Card> deck, int trump){
        if(trump > 0 && trump < 6){

            Collections.sort(deck, new Comparator<Card>() {
                @Override
                public int compare(Card o1, Card o2) {
                    if(o1.getIndexSuit() == trump && o2.getIndexSuit() == trump){
                        if(o1.getIndexCard() > o2.getIndexCard())return 1;
                        else if(o1.getIndexCard() < o2.getIndexCard())return -1;
                        return 0;
                    }else if(o1.getIndexSuit() == trump && o2.getIndexSuit() != trump){
                        return 1;
                    }else if(o1.getIndexSuit() != trump && o2.getIndexSuit() == trump){
                        return -1;
                    }else {
                        if(o1.getIndexCard() != o2.getIndexCard()){
                            if(o1.getIndexCard() > o2.getIndexCard()) return 1;
                            else return -1;
                        }else {
                            if(o1.getIndexSuit() > o2.getIndexSuit()) return 1;
                            else if(o1.getIndexSuit() < o2.getIndexSuit())return -1;
                            else return 0;
                        }
                    }

                }
            });

        }else System.out.println("Не правильный индекс масти");

    }

    //Метод перемешивает карты в колоде
    public static void mix(List<Card> cards, int max){
        for(int i = 0; i < max; i++ ){
            int random1 = (int)(Math.random() * cards.size());
            int random2 = (int)(Math.random() * cards.size());

            Card card1 = cards.get(random1);
            cards.set(random1, cards.get(random2));
            cards.set(random2, card1);
        }

    }

    //Возвращает список карт для торговли
    public static List<Card> getDeckForAuction(){
        List<Card> deckForAuction = new ArrayList<>();
        deckForAuction.add(miser);
        deckForAuction.addAll(deck.subList(0, 20));
        return deckForAuction;
    }
    //Возращает список карт для раздачи игрокам и розыгрыша
    public static List<Card> getDeckForHand(){
        List<Card> deckForHand = new ArrayList<>();
        deckForHand.addAll(deck.subList(4, deck.size()));
        mix(deckForHand, 10000);
        return deckForHand;
    }





    //создает новый список карт из старого, и удаляет карты нового списка и старого списка.
    public static List<Card> getCardsFromTheDeck(List<Card> cards,int number){
        List<Card> newDeck = new ArrayList<>();
            try{
                for(int i = 0; i < number; i++) {
                    newDeck.add(cards.remove(0));
                }
            }catch (Exception e){ }


        return newDeck;
    }

}
