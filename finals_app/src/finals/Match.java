package finals;

import java.util.LinkedList;
import java.util.List;

public class Match {
	public Match(Contestant first, Contestant second, List<Round> rounds) {
		super();
		this.first = first;
		this.second = second;
		this.rounds = rounds;
	}

	public Match(Contestant first, Contestant second) {
		super();
		this.first = first;
		this.second = second;
		this.rounds = new LinkedList<Round>();
	}

	Contestant first;
	Contestant second;
	List<Round> rounds;
	int firstCurrentScore = 0;
	int secondCurrentScore = 0;
	private int currentStep = 0;

	public void addRound(Round round) {
		this.rounds.add(round);
	}

	public void doNextStep() {
		if (currentStep < rounds.size()) {
			Round currentRound = rounds.get(currentStep);
			if (currentRound.winner.equals(first)) {
				firstCurrentScore++;
			} else {
				secondCurrentScore++;
			}
			currentStep++;
		}
	}

	public void undoLastStep() {
		if (currentStep > 0) {
			Round currentRound = rounds.get(currentStep);
			if (currentRound.winner.equals(first)) {
				firstCurrentScore--;
			} else {
				secondCurrentScore--;
			}
			currentStep--;
		}
	}

	public void undoAllSteps() {
		currentStep = 0;
		firstCurrentScore = 0;
		secondCurrentScore = 0;
	}

	public void doAllSteps() {
		undoAllSteps();
		for (Round round : rounds) {
			if (round.winner.equals(first)) {
				firstCurrentScore++;
			} else {
				secondCurrentScore++;
			}
		}
	}

	public Contestant getFirst() {
		return first;
	}

	public Contestant getSecond() {
		return second;
	}

	public int getFirstCurrentScore() {
		return firstCurrentScore;
	}

	public int getSecondCurrentScore() {
		return secondCurrentScore;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public boolean isFinished() {
		System.out.println("Current rounds size: " + rounds.size());
		return (currentStep == rounds.size() ? true : false);
	}

	public int getCurrentStep() {
		return this.currentStep;
	}
}