package it.unibo.javapoly.model.impl.card;

import it.unibo.javapoly.model.api.card.CardDeck;
import it.unibo.javapoly.model.api.card.GameCard;

import java.util.*;

public class CardDeckImpl implements CardDeck {

    private final Deque<GameCard> drawPile = new ArrayDeque<>();
    private final Deque<GameCard> discardPile = new ArrayDeque<>();
    private final Map<GameCard, String> heldCards = new HashMap<>();
    private final Random random = new Random();

    public CardDeckImpl(final List<GameCard> cards) {
        this.drawPile.addAll(cards);
        shuffle();
    }

    @Override
    public GameCard draw(final String playerId) {
        
        if (drawPile.isEmpty()) {
            recycleDiscard();
        }

        final GameCard card = drawPile.removeFirst();

        if (card.isKeepUntilUsed()) {
            heldCards.put(card, playerId);
        }

        return card;
    }

    @Override
    public void discard(final GameCard card) {
        heldCards.remove(card);
        discardPile.addFirst(card);
    }

    @Override
    public void shuffle() {
        final List<GameCard> temp = new ArrayList<>(drawPile);
        Collections.shuffle(temp, random);
        drawPile.clear();
        drawPile.addAll(temp);
    }

    @Override
    public boolean isEmpty() {
        return drawPile.isEmpty() && discardPile.isEmpty();
    }

    private void recycleDiscard() {
        while (!discardPile.isEmpty()) {
            drawPile.addLast(discardPile.removeFirst());
        }
        shuffle();
    }
}
