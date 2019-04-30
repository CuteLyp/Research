import java.util.*;

public class Hangman {
	public static void main(String[] args) {
		
		Scanner myscanner = new Scanner(System.in);
		int num = Integer.parseInt(myscanner.nextLine());
		String[] dictionary = new String[num];
    
		for(int i = 0; i < num; i++){
			dictionary[i]=myscanner.nextLine();
		}
		
		int games = 100;
		int score = 0;
		for(int x = 0; x < games; x++) {

			Random r = new Random();
			String target = dictionary[r.nextInt(num)];

			String blackout="";
			for(int i=0;i<target.length();i++){
				blackout=blackout+"_";
			}

			Brain mybrain = new Brain(dictionary, blackout);
			int lives=8;

			boolean running = true;

			while(running){
				char guess = mybrain.guessLetter();
				String original = mybrain.hiddenWord;
				char[] arrayform = original.toCharArray();
				for(int i=0;i<target.length();i++){
					if(target.charAt(i)==guess){
						arrayform[i]=guess;
					}
				}
				String newform = "";
				for(int i=0;i<target.length();i++){
					newform=newform+arrayform[i];
				}
				mybrain.hiddenWord=newform;
				if (newform.equals(original)) {
					lives = lives-1;
				}
				if (lives==0){
					running=false;
				}
				if (mybrain.hiddenWord.equals(target)) {
					running=false;
					score=score+1;
				}
			}
		}
		System.out.println("You got " + score + " correct out of " + games);
	}
}

class Brain{

	public String[] dictionary;
	public String hiddenWord="_____";
	public int[] lettersFrequency = new int[26];
	public boolean[] guessed = new boolean[26];
	public List<String> range = new ArrayList<>();

	public Brain(String[] wordlist, String target) {
		dictionary = wordlist;
		hiddenWord = target;
		for(String s : dictionary) {
			if(s.length() == target.length())  {
				range.add(s);
			}
		}
	}

	public char guessLetter(){
		//System.out.println(this.hiddenWord);
		for(int i = 0; i < hiddenWord.length(); i++) {
			if(hiddenWord.charAt(i) != '_') {
				updateRange(i, hiddenWord.charAt(i));
			}
		}
		updateFrequency();
		//System.out.println(this.range.size());
		char c = findUnguessedChar();
		return c;
	}
	
	public void updateRange(int index, char c) {
		Iterator<String> range = this.range.iterator();
		while(range.hasNext()) {
			String temp = range.next();
			if(temp.charAt(index) != c) {
				range.remove();
			}
		}
	}
	public void updateFrequency() {
		this.lettersFrequency = new int[26];
		for(String s : this.range) {
			for(int i = 0; i < s.length(); i++) {
				this.lettersFrequency[s.charAt(i) - 'a']++;
			}
		}
	}
	
	public char findUnguessedChar() {
		int max = 0;
		int index = 0;
		for(int i = 0; i < 26; i++) {
			if(!guessed[i] && this.lettersFrequency[i] > max) {
				max = this.lettersFrequency[i];
				index = i;
			}
		}
		this.guessed[index] = true;
		return (char)(index + 'a');
	}
}
