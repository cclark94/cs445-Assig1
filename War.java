public class War
{
	public static void main(String[] args)
	{
		final int MAX_ROUNDS = Integer.parseInt(args[0]);
		int rounds = 0;
		
		// Used to store the top card from each player's hand
		Card p0Card = null;
		Card p1Card = null;
		
		System.out.println("Welcome to the Game of War!\n");
		
		// Create deck, add all cards, shuffle
		MultiDS<Card> deck = new MultiDS(52);
		for ( Card.Suits s : Card.Suits.values() )
		{
			for ( Card.Ranks r : Card.Ranks.values() )
			{
				deck.addItem(new Card(s, r));
			}
		}
		deck.shuffle();
		
		// Create two WarPlayer objects (each of which includes a hand
		// and discard pile), and a MultiDS object for cards in play
		WarPlayer p0 = new WarPlayer("Player 0");
		WarPlayer p1 = new WarPlayer("Player 1");
		MultiDS<Card> inPlay = new MultiDS(52);
		
		System.out.println("Now dealing the cards to the players...\n");
		
		// Deal cards
		while ( !(deck.empty()) )
		{
			p0.getHand().addItem(deck.removeItem());
			p1.getHand().addItem(deck.removeItem());
		}
		
		System.out.println("Here is Player 0's hand:");
		System.out.println(p0.getHand().toString()+"\n");

		System.out.println("Here is Player 1's hand:");
		System.out.println(p1.getHand().toString()+"\n");
		
		System.out.println("Starting the WAR!\n");
		
		// Game continues until either the maximum number of rounds is reached
		// or one of the players runs out of cards.
		boolean gameDone = false;		
		while ( (rounds < MAX_ROUNDS) && (gameDone == false) )
		{
			boolean roundDone = false;
			while ( roundDone == false )
			{
				// Get a card from each player. getCard will move cards from
				// the discard pile to the hand and shuffle if necessary.
				p0Card = getCard(p0);
				p1Card = getCard(p1);
				
				// If getCard returns null for one of the players, it means that 
				// the player is out of cards. In that case the player loses.
				if ( (p0Card == null) || (p1Card == null) )
				{
					roundDone = true;
					gameDone = true;
				}
				
				else
				{
					// Compare the cards, and then add them to inPlay
					int p0wins = p0Card.compareTo(p1Card);
					inPlay.addItem(p0Card);
					inPlay.addItem(p1Card);
					
					// If p0Card wins the round, move the cards from 
					// inPlay to p0's discard. 
					if ( p0wins > 0 )
					{
						System.out.println(p0.getName() + " Wins: " + p0Card.toString() +
											" beats " + p1Card.toString());			
						transferCards(inPlay, p0.getDiscard());
						rounds++;
						roundDone = true;
					}
					
					// If p1Card wins the round, move the cards from 
					// inPlay to p1's discard.
					else if ( p0wins < 0 )
					{
						System.out.println(p1.getName() + " Wins: " + p1Card.toString() +
											" beats " + p0Card.toString());	
						transferCards(inPlay, p1.getDiscard());
						rounds++;
						roundDone = true;
					}
					
					// If there's a tie, add one card from each player's hand to inPlay,
					// and then	before repeat the process above. Check whether either 
					// player has run out of cards along the way. 
					else
					{
						System.out.println("WAR: " + p0Card.toString() + " ties " +
												p1Card.toString());
						
						// Get a new card from each player's hand to add to inPlay
						p0Card = getCard(p0);
						p1Card = getCard(p1);
						
						if ( (p0Card == null) || (p1Card == null) )
						{
							roundDone = true;
							gameDone = true;
						}
						
						else
						{
							System.out.println("\t" + p0.getName() + ":" + 
										p0Card.toString() + " and " + p1.getName()
										+ ":" + p1Card.toString() + " are at risk!");
							inPlay.addItem(p0Card);
							inPlay.addItem(p1Card);
							// roundDone remains false; go back to the top of this
							// while loop
						}
					} // end else
				} // end else
			} // end while
			
			//System.out.println("After " + rounds + " rounds:");
			//System.out.println("Here is Player 0's hand:");
			//System.out.println(p0.getHand().toString()+"\n");
			//System.out.println("Here is Player 0's discard:");
			//System.out.println(p0.getDiscard().toString()+"\n");
			//System.out.println("Here is Player 1's hand:");
			//System.out.println(p1.getHand().toString()+"\n");
			//System.out.println("Here is Player 1's discard:");
			//System.out.println(p1.getDiscard().toString()+"\n");
		} // end while
		
		System.out.println();
		
		// If the maximum number of rounds has been reached, compare how many cards
		// the players have and show the winner. Ties are possible.
		if ( rounds == MAX_ROUNDS )
		{
		
			System.out.println("After " + rounds + " rounds here is the status:");
			System.out.println("\t" + p0.getName() + " has " + p0.totalCards() + " cards");
			System.out.println("\t" + p1.getName() + " has " + p1.totalCards() + " cards");
			
			if ( p0.totalCards() > p1.totalCards() )
			{
				System.out.println(p0.getName() + " is the WINNER!");
			}
			
			else if ( p1.totalCards() > p0.totalCards() )
			{
				System.out.println(p1.getName() + " is the WINNER!");
			}
			
			else
			{
				System.out.println("It is a STALEMATE!");
			}
		}
		
		// If the game has ended but the maximum number of rounds has not been reached,
		// one of the players ran out of cards. That player's current card (p0Card or
		// p1Card) is null.
		else
		{
			if ( p0Card == null  )
			{
				System.out.println(p0.getName() + " is out of cards!");
				System.out.println(p1.getName() + " is the WINNER!");
			}
			
			// p1Card must be null in this case
			else
			{
				System.out.println(p1.getName() + " is out of cards!");
				System.out.println(p0.getName() + " is the WINNER!");
			}
		}
	}
	
	
	// Return the top card of the hand if possible. If the hand is empty, check the
	// discard pile, and it if isn't empty, move all of its cards over to the hand,
	// shuffle, and return the top card. If both the hand and discard pile are empty, 
	// return null.
	public static Card getCard(WarPlayer p)
	{
		if ( !(p.getHand().empty()) )
		{
			return p.getHand().removeItem();
		}
				
		if ( !(p.getDiscard().empty()) )
		{ 
			System.out.println("\tGetting and shuffling the pile for " + p.getName());
			transferCards(p.getDiscard(), p.getHand());
			p.getHand().shuffle();			
			return p.getHand().removeItem();
		}
		
		else
		{
			return null;
		}

	}
	
	// Move all of the cards from oldDeck to newDeck (in order).
	public static void transferCards(MultiDS<Card> oldDeck, MultiDS<Card> newDeck)
	{
		int size = oldDeck.size();
		for (int i=0; i<size; i++)
		{
			newDeck.addItem(oldDeck.removeItem());
		}
	}
} 