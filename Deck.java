import java.util.Random;

public class Deck {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck

    /* 
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck(int numOfCardsPerSuit, int numOfSuits) {
	/**** ADD CODE HERE ****/
        if ((numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) || (numOfSuits < 1|| numOfSuits > suitsInOrder.length)){
	        throw new IllegalArgumentException("Invalid");
	    }
		Card prevCard = null;
		this.numOfCards = numOfCardsPerSuit * numOfSuits + 2;
		for (int i = 0; i < numOfSuits; i++) {
			for (int j = 1; j <= numOfCardsPerSuit; j++) {
				Card newCard = new PlayingCard(suitsInOrder[i], j);
				if(this.head == null) {
					this.head = newCard;
				}
				else {
					prevCard.next = newCard;
					newCard.prev = prevCard;
				}
				prevCard = newCard;
				
			}
		}
		Card redJoker = new Joker("red");
		prevCard.next = redJoker;
		redJoker.prev = prevCard;
		prevCard = redJoker;
		Card blackJoker = new Joker("black");
		prevCard.next = blackJoker;
		blackJoker.prev = prevCard;
		prevCard = blackJoker;
		
		prevCard.next = head;
		head.prev = prevCard;
    }

    /* 
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck(Deck d) {
	/**** ADD CODE HERE ****/
        this.numOfCards = d.numOfCards;
			Card headCard = d.head;
			this.head = headCard.getCopy();
			Card currentCard = this.head;
			Card prevCard = null;
			headCard = headCard.next;
			while (headCard != d.head) {
				prevCard = currentCard;
				Card newCard = headCard.getCopy();
				currentCard = newCard;
				prevCard.next = currentCard;
				currentCard.prev = prevCard;
				headCard = headCard.next;
				
			}
			currentCard.next = this.head;
			this.head.prev = currentCard;
    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck() {}

    /* 
     * TODO: Adds the specified card at the bottom of the deck. This 
     * method runs in $O(1)$. 
     */
    public void addCard(Card c) {
	/**** ADD CODE HERE ****/
        if(this.head == null) {
			this.head = c;
			c.prev = c;
			c.next = c;
		}
		else {
			Card tail = this.head.prev;
			tail.next = c;
			c.prev = tail;
			this.head.prev = c;
			c.next = head;
		}
		this.numOfCards++;
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf. 
     * This method runs in O(n) and uses O(n) space, where n is the total 
     * number of cards in the deck.
     */
    public void shuffle() {
	/**** ADD CODE HERE ****/ 
        Card cardsArray[] = new Card[numOfCards];
		Card currentCard = this.head;
		int totalCards = numOfCards;
		int i = 0;
		while(numOfCards > 0) {
			cardsArray[i] = currentCard;
			currentCard = currentCard.next;
			numOfCards --;
			i++;
		}
		Card temp = null;
		int j;
		for(i = totalCards - 1; i > 0; i--) {
			j = gen.nextInt(i + 1);
			temp = cardsArray[i];
			cardsArray[i] = cardsArray[j];
			cardsArray[j] = temp;
			for(int x = 0; x < cardsArray.length; x++) {
				System.out.print(cardsArray[x] + " ");
			}
			System.out.println(j);
		}
		for(i = 0; i < cardsArray.length; i++) {
			System.out.print(cardsArray[i] + " ");
		}
		this.head = null;
		for(i = 0; i < totalCards; i++) {
			this.addCard(cardsArray[i]);
		}
		System.out.println();
		Card temp1 = this.head;
		for(i = 0; i < totalCards; i++) {
			System.out.println(temp1 + " " + temp1.next + " " + temp1.prev);
			temp1 = temp1.next;
		}
    }

    /*
     * TODO: Returns a reference to the joker with the specified color in 
     * the deck. This method runs in O(n), where n is the total number of 
     * cards in the deck. 
     */
    public Joker locateJoker(String color) {
	/**** ADD CODE HERE ****/
        if(head == null) {
			return null;
		}
		Card currentCard = this.head;
		Joker jokerCard = null;
		while(currentCard.next != this.head) {
			if (currentCard instanceof Joker) {
				if (((Joker) currentCard).getColor().equalsIgnoreCase(color)) {
					jokerCard = (Joker) currentCard;
					return jokerCard;
				}
			}
			currentCard = currentCard.next;
		}
		if (currentCard instanceof Joker) {
			if (((Joker) currentCard).getColor().equalsIgnoreCase(color)) {
				jokerCard = (Joker) currentCard;
			}
		}
		return jokerCard;
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can 
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */
    public void moveCard(Card c, int p) {
	/**** ADD CODE HERE ****/
        if (c == null || p <= 0) {
			return;
		}
		Card temp = c.next;
		c.prev.next = c.next; //removing c, so the card before c will have the same next as c
	    c.next.prev = c.prev;
		for(int i = 0; i < p; i++) {
			temp = temp.next;
		}
		if (temp.prev == head) {
			head.next = c;
			c.prev = head;
			c.next = temp;
			temp.prev = c;
			
		}
		else {
			c.next = temp;
			c.prev = temp.prev;
			temp.prev.next = c;
			temp.prev = c; //update all pointers, not only the next pointers, also the prev pointers
		}
    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You 
     * can assume that the input cards belong to the deck and the first one is 
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
	/**** ADD CODE HERE ****/
        Card newHead = secondCard.next;
		Card newTail = firstCard.prev;
		Card lastCard = this.head.prev;
		Card headCard = this.head;
		if (firstCard == head || secondCard.next == head) {
			if(firstCard == head) {
				this.head = newHead;
			}
			else {
				this.head = firstCard;
			}
		}
		else {
			newHead.prev = newTail;
			newTail.next = newHead;
			
			lastCard.next = firstCard;
			firstCard.prev = lastCard;
			
			headCard.prev = secondCard;
			secondCard.next = headCard;
			
			this.head = newHead;
		}
    }

    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the 
     * bottom card is equal to a multiple of the number of cards in the deck, 
     * then the method should not do anything. This method runs in O(n).
     */
    public void countCut() {
	/**** ADD CODE HERE ****/
        if (head != null) {
			
			Card tail = head.prev;
			Card beforeTail = tail.prev; //the cards will be added between those 2 cards
			Card initialCard = this.head;
			Card headCard = this.head;
			int cardValue = tail.getValue();
			cardValue = cardValue % numOfCards;
			if (cardValue != 0 && cardValue != numOfCards - 1) {
				for(int i = 0; i < cardValue - 1; i++) {
					initialCard = initialCard.next;
				}
				Card newHead = initialCard.next; //the new head
				this.head = newHead;
				tail.next = newHead;
				newHead.prev = tail; //excluded the cards from head to the nth card
				
				beforeTail.next = headCard;
				headCard.prev = beforeTail;
				
				initialCard.next = tail;
				tail.prev = initialCard;
			}
		}
    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the 
     * card on the top of the deck, and counting down that many cards. If the 
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
	/**** ADD CODE HERE ****/
	    int cardValue = this.head.getValue();
		Card temp = this.head;
		for(int i = 0; i < cardValue; i++) {
			temp = temp.next;
		}
		if(temp instanceof Joker) {
			return null;
		}
		return temp;
    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {
	/**** ADD CODE HERE ****/
	    Card redJoker = this.locateJoker("red");
		this.moveCard(redJoker, 1);
		Card blackJoker = this.locateJoker("black");
		this.moveCard(blackJoker, 2);
		//checking which joker comes first
		
		Card temp = redJoker;
		boolean redJokerFirst = false;
		if(temp == head || head.prev == blackJoker) {
			redJokerFirst = true;
		}
		while (temp != this.head.prev) {
			temp = temp.next;
			if (temp == blackJoker) {
				redJokerFirst = true;
			}
		}
		if(redJokerFirst) {
			this.tripleCut(redJoker, blackJoker);
		}
		else {
			this.tripleCut(blackJoker, redJoker);
		}
		this.countCut();
		temp = lookUpCard();
		if (temp != null) {
			return temp.getValue();
		}
		return 0;
    }


    public abstract class Card { 
	public Card next;
	public Card prev;

	public abstract Card getCopy();
	public abstract int getValue();

    }

    public class PlayingCard extends Card {
	public String suit;
	public int rank;

	public PlayingCard(String s, int r) {
	    this.suit = s.toLowerCase();
	    this.rank = r;
	}

	public String toString() {
	    String info = "";
	    if (this.rank == 1) {
		//info += "Ace";
		info += "A";
	    } else if (this.rank > 10) {
		String[] cards = {"Jack", "Queen", "King"};
		//info += cards[this.rank - 11];
		info += cards[this.rank - 11].charAt(0);
	    } else {
		info += this.rank;
	    }
	    //info += " of " + this.suit;
	    info = (info + this.suit.charAt(0)).toUpperCase();
	    return info;
	}

	public PlayingCard getCopy() {
	    return new PlayingCard(this.suit, this.rank);   
	}

	public int getValue() {
	    int i;
	    for (i = 0; i < suitsInOrder.length; i++) {
		if (this.suit.equals(suitsInOrder[i]))
		    break;
	    }

	    return this.rank + 13*i;
	}

    }

    public class Joker extends Card{
	public String redOrBlack;

	public Joker(String c) {
	    if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
		throw new IllegalArgumentException("Jokers can only be red or black"); 

	    this.redOrBlack = c.toLowerCase();
	}

	public String toString() {
	    //return this.redOrBlack + " Joker";
	    return (this.redOrBlack.charAt(0) + "J").toUpperCase();
	}

	public Joker getCopy() {
	    return new Joker(this.redOrBlack);
	}

	public int getValue() {
	    return numOfCards - 1;
	}

	public String getColor() {
	    return this.redOrBlack;
	}
    }

}
