package sc.plugin2010;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sc.api.plugins.IPlayer;
import sc.api.plugins.host.IPlayerListener;
import sc.framework.plugins.SimplePlayer;

/**
 * Ein Spieler aus Hase- und Igel.
 * 
 * @author rra
 * @since Jul 4, 2009
 * 
 */
public class Player extends SimplePlayer
{
	/**
	 * Mögliche Aktionen, die durch das Ausspielen einer Hasenkarte ausgelöst
	 * werden können.
	 */
	public enum Action
	{
		/**
		 * Einmal aussetzen
		 */
		SUSPENDED,
		/**
		 * Ziehe ein Hasenfeld vor
		 */
		NEXT_RABBIT_FIELD,
		/**
		 * Falle ein Hasenfeld zurück
		 */
		LAST_RABBIT_FIELD,
		/**
		 * Ziehe gleich noch einmal
		 */
		MOVE_AGAIN,
		/**
		 * Der Zug war kostenlos
		 */
		FREE_MOVEMENT,
		/**
		 * Nehme 10 Karotten auf
		 */
		TAKE_10_CARROTS,
		/**
		 * Gebe 10 Karotten ab
		 */
		DROP_10_CARROTS,
		/**
		 * Iß sofort einen Salat
		 */
		EAT_SALAD,
		/**
		 * Falle eine Position zurück
		 */
		FALL_BACK,
		/**
		 * Rücke eine Position vor
		 */
		HURRY_AHEAD
	}

	/**
	 * Alle Spielfiguren aus dem Hase und Igel Original
	 */
	public enum FigureColor
	{
		RED, BLUE, YELLOW, WHITE, GREEN, ORANGE,
	}

	// Farbe der Spielfigure
	private FigureColor				color;

	// Position auf dem Spielbrett
	private int						position;

	// Anzahl der Karotten des Spielers
	private int						carrots;

	// Anzahl der bisher verspeisten Salate
	private int						saladsToEat;

	// verfügbare Hasenkarten
	private List<Action>			actions;

	// Muss der Spieler in der kommenden Runde aussetzen?
	private boolean					suspended;

	protected Player(FigureColor color)
	{
		initialize(color, 0);
	}

	protected Player(FigureColor color, int position)
	{
		initialize(color, position);
	}

	private void initialize(FigureColor c, int p)
	{
		position = p;
		color = c;
		carrots = 68;
		saladsToEat = 5;
		suspended = false;
	}

	/**
	 * Die Anzahl an Karotten die der Spieler zur Zeit auf der Hand hat.
	 * 
	 * @return
	 */
	public int getCarrotsAvailable()
	{
		return carrots;
	}

	/**
	 * Die Anzahl der Salate, die dieser Spieler noch verspeisen muss. 
	 * 
	 * @return
	 */
	public int getSaladsToEat()
	{
		return saladsToEat;
	}

	/**
	 * Eine Hasenkarte kann den Spieler eine Runde aussetzen lassen.
	 * <code>TRUE</code>, falls der Spieler in der kommenden Runde aussetzen
	 * muss.
	 * 
	 * @return
	 */
	public boolean isSuspended()
	{
		return suspended;
	}

	/**
	 * Gibt die für diesen Spieler verfügbaren Hasenkarten zurück.
	 * 
	 * @return
	 */
	public List<Action> getActions()
	{
		return actions;
	}

	/**
	 * Die aktuelle Position der Figure auf dem Spielfeld. Vor dem ersten Zug
	 * steht eine Figure immer auf Spielfeld 0
	 * 
	 * @return
	 */
	public final int getPosition()
	{
		return this.position;
	}

	protected final void setPosition(final int pos)
	{
		this.position = pos;
	}

	/**
	 * Die Farbe dieses Spielers auf dem Spielbrett
	 * 
	 * @return
	 */
	public final FigureColor getColor()
	{
		return this.color;
	}
}
