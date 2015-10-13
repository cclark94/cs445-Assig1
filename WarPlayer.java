// Contains the name, hand, and discard pile for
// one of the players in War.
public class WarPlayer
{
	private String name;
	private MultiDS<Card> hand;
	private MultiDS<Card> discard;
	
	public WarPlayer(String name)
	{
		this.name = name;
		hand = new MultiDS<Card>(52);
		discard = new MultiDS<Card>(52);
	}
	
	public MultiDS<Card> getHand()
	{
		return hand;
	}
	
	public MultiDS<Card> getDiscard()
	{
		return discard;
	}
	
	public String getName()
	{
		return name;
	}

	public int totalCards()
	{
		return hand.size() + discard.size();
	}
}