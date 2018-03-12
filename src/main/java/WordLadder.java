import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class WordLadder {
	private Set<String> dic;
	private Scanner scanner;
	
	public WordLadder() { //Set up dictionary
		dic = setUpDic("res/dictionary.txt");
	}
	
	public Set<String> setUpDic(final String file_path) {
		File file = new File(file_path);
		FileReader fileReader = null;
		BufferedReader br = null;
		
		/* Initialize dictionary */
		Set<String> dic = new HashSet<String>();
		
		/* Initialize scanner */
		scanner = new Scanner(System.in);
		
		try {
			fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			/* Read file */
			String tmp;
			while ((tmp = br.readLine()) != null) {
				dic.add(tmp);
			}
			System.out.println("Reading complete!");
		}catch (FileNotFoundException e) {
			System.out.println("文件不存在");
		}catch (IOException e) {
			System.out.println("文件读取失败");
		}finally {
			/* Close files */
			try {
				br.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("number of words:" + dic.size());
		return dic;
	}
	
	public void findLadder() {
		/* Get words */
		String w1 = null;
		String w2 = null;
		
		System.out.print("Word #1:");
		if (scanner.hasNext()) {
			w1 = scanner.next();
		}
		System.out.print("Word #2:");
		if (scanner.hasNext()) {
			w2 = scanner.next();
		}
		w1 = w1.toLowerCase(); //turn into lower case
		w2 = w2.toLowerCase();
		
		/* Check words */
		if (w1.length() != w2.length()) {
			System.out.println("The two words must be the same length.");
			return;
		}
		if (w1 == w2) {
			System.out.println("The two words must be different.");
			return;
		}

		/* Find ladder */
		Set<String> used_words = new HashSet<String>();
		used_words.add(w1);

		Queue<Stack<String>> stacks = new LinkedList<Stack<String>>();
		stacks.add(new Stack<String>());
		stacks.peek().push(w1);

		while (!stacks.isEmpty()) {
			/* Pop and get top element */
			Stack<String> front_stack = stacks.remove();
			
			String top_string = front_stack.peek();

			for (int i = 0; i < top_string.length();i++) { //change a letter each time
				for (int j = 'a'; j <= 'z'; j++) { //try all possibilities
					if (top_string.charAt(i) == j) {
						continue;
					}

					/* Create the changed word */
					String new_string = top_string.substring(0, i) + (char) j;
					if (i != top_string.length() - 1) {
						new_string += top_string.substring(i + 1);
					}

					if (used_words.contains(new_string)) { //if it's used
						continue;
					}
					else if (!dic.contains(new_string)) {
						continue;
					} else if (new_string.equals(w2)) {
						/* Print result */
						System.out.printf("A ladder from %s to %s:\n", w2, w1);

						front_stack.push(w2);
						while (!front_stack.empty()) {
							System.out.printf("%s ", front_stack.peek());
							front_stack.pop();
						}
						System.out.print("\n");
						return;
					} else {
						used_words.add(new_string);
						Stack<String> tmp_stack = (Stack<String>)front_stack.clone();
						tmp_stack.add(new_string);
						stacks.add(tmp_stack);
					}
				}
			}
		}
		System.out.println("No such word ladder.");
	}
	
	public static void main (String[] args) {
		WordLadder wordLadder = new WordLadder();
		while (true) {wordLadder.findLadder();}
	}
}
