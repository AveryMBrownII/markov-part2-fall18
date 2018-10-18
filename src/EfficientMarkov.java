import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class EfficientMarkov extends BaseMarkov {
	private HashMap<String,ArrayList<String>> myMap; // keys are the k-grams in a k-order Markov model.
													// values are a list of single-character letters that follow the key
	public EfficientMarkov (int order) {
		super(order);
		myMap = new HashMap<>();
	}

	public EfficientMarkov() {
		super(3);
		myMap = new HashMap<>();
	}
	
	@Override
	public void setTraining(String text) {
		myText = text;
		myMap.clear();
		for (int index = 0; index < (myText.length()-myOrder); index++) {
			String kgram = myText.substring(index, index+myOrder);
			myMap.putIfAbsent(kgram, new ArrayList<String>());
			myMap.get(kgram).add(String.valueOf(myText.charAt(index+myOrder)));
		}
		String end = myText.substring(myText.length()-myOrder);
		myMap.putIfAbsent(end,new ArrayList<String>());
		myMap.get(end).add(PSEUDO_EOS);	
	}
	
	@Override
	public ArrayList<String> getFollows(String key){	
		if (myMap.containsKey(key)) {return myMap.get(key);}
		else {throw new NoSuchElementException(key+" not in map");}

	}
}
