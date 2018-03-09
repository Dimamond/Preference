public class Card implements Comparable<Card> {

    private  SuitCard suitCard = null;
    private  TypeCard typeCard = null;
    private int indexSuit;
    private int indexCard;
    private String nameCard;

    Card(SuitCard suitCard, TypeCard typeCard, int indexSuit, int indexCard){
        this.typeCard = typeCard;
        this.suitCard = suitCard;
        this.indexSuit = indexSuit;
        this.indexCard = indexCard;
        this.nameCard = suitCard.toString() + typeCard.toString();
    }

    Card(String nameCard, int indexCard){
        this.indexCard = indexCard;
        this.nameCard = nameCard;
    }

    @Override
    public int compareTo(Card o) {
        if(indexCard != o.indexCard){
            if(indexCard > o.indexCard) return 1;
            else return -1;
        }else {
            if(indexSuit > o.indexSuit) return 1;
            else if(indexSuit < o.indexSuit)return -1;
            else return 0;
        }
    }

    public TypeCard getTypeCard() {
        return typeCard;
    }

    public SuitCard getSuitCard() {
        return suitCard;
    }

    public int getIndexSuit() {
        return indexSuit;
    }

    public int getIndexCard() {
        return indexCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    @Override
    public String toString() {
        return this.nameCard;
    }


}
