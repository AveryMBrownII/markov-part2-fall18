import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {
	private HashMap<WordGram,ArrayList<String>> myMap; // keys are the wordgrams in a k-order Markov model.
													// values are a list of words that follow the key
	public EfficientWordMarkov (int order) {
		super(order);
		myMap = new HashMap<>();
	}

	public EfficientWordMarkov() {
		super(2);
		myMap = new HashMap<>();
	}
	
	@Override
	public void setTraining(String text) {
		myWords = text.split("\\s+");
		myMap.clear();
		for (int index = 0; index < (myWords.length-myOrder); index++) {
			WordGram kgram =  new WordGram(myWords, index, myOrder);
			myMap.putIfAbsent(kgram, new ArrayList<String>());
			myMap.get(kgram).add(myWords[index+myOrder]);
		}
		WordGram end = new WordGram(myWords, myWords.length-myOrder, myOrder);
		myMap.putIfAbsent(end,new ArrayList<String>());
		myMap.get(end).add(PSEUDO_EOS);	
	}
	
	@Override
	public ArrayList<String> getFollows(WordGram kGram) {
		if (myMap.containsKey(kGram)) {return myMap.get(kGram);}
		else {throw new NoSuchElementException(kGram+" not in map");}
	}
}
