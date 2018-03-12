import java.io.*;
import java.util.Random;
import java.util.Vector;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedList;

public class Ngrams {
	private Random random;
	private int N;
	private Map<Queue<String>, Vector<String>> words;
	private Vector<Queue<String>> beginning_words;
	
	/* Tool */
	private boolean isInt(String str) {
		for (int i=0;i<str.length();i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	private String getRandomInVec(Vector<String> vec)
	{
		int tmp=random.nextInt(vec.size());
		return new String(vec.elementAt(tmp));
	}
	
	public Ngrams() {
		/* Set random seed */
		random = new Random();

		/* Open file */
		File file = new File("res/hamlet.txt");
		FileReader fileReader = null;
		
		try {
			fileReader = new FileReader(file);
		}catch (FileNotFoundException e) {
			System.out.println("文件不存在");
		}

		/* Get N */
		String user_input_N = null;
		Scanner scanner = new Scanner(System.in);
		N = 0;
		while (true) {
			System.out.print("pls enter N:");
			
			if (scanner.hasNext()) {
				user_input_N = scanner.next();
			}
			
			/* Check if valid */
			if (!isInt(user_input_N)) {
				System.out.println("Not a valid int.");
			}else {
				N = Integer.parseInt(user_input_N);
				if (N <= 2) {
					System.out.println("N should be larger than 2.");
				}
				else {
					break; // get suitable N and continue
				}
			}
		}
		System.out.println("processing...");
		
		/* Initialize */
		words = new HashMap<Queue<String>, Vector<String>>();
		beginning_words = new Vector<Queue<String>>();
		scanner = new Scanner(fileReader);
		
		Queue<String> tmp_que = new LinkedList<String>();
		for (int i = 0; i < N; i++) {
			if (scanner.hasNext()) {
				tmp_que.add(scanner.next());
			}
		}

		/* Read file */
		while (scanner.hasNext()) {
			String tmp_str = scanner.next();
			
			/* Insert pair */
			if (words.containsKey(tmp_que)) {
				words.get(tmp_que).addElement(tmp_str);
			}
			else {
				/* Create new vector */
				Vector<String> vec = new Vector<String>();
				vec.addElement(tmp_str);

				words.put(new LinkedList(tmp_que), vec);
			}
			
			char tmp_ch = tmp_que.peek().charAt(0);
			if (tmp_ch >= 'A' && tmp_ch <= 'Z') { //save beginning sentences
				beginning_words.add(new LinkedList(tmp_que));
			}

			tmp_que.add(tmp_str);
			tmp_que.remove();
		}
		
		System.out.println("complete!");
		
		/* Close files */
		try {
			scanner.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate()
	{
		/* Get the number of words to generate */
		Scanner scanner = new Scanner(System.in);
		String user_input_number = null;
		do {
			System.out.print("The number of random words to generate (0 to quit):");
			if (scanner.hasNext()) {
				user_input_number = scanner.next();
			}
			if (!isInt(user_input_number)) {
				System.out.println("Not an int.");
			}
		} while (!isInt(user_input_number));

		int generate_num = Integer.parseInt(user_input_number);

		/* Check number */
		if (generate_num == 0) {
			System.exit(0);
		}
		if (generate_num < N) {
			System.out.println("Unvalid input.");
			return;
		}

		/* Choose random beginning */
		final Queue<String> beginning = beginning_words.elementAt(random.nextInt(beginning_words.size()));

		/* Print beginning */
		Queue<String> tmp_que = new LinkedList(beginning);
		generate_num -= tmp_que.size();
		for (; tmp_que.size()>0;) {
			System.out.print(tmp_que.poll() + " ");
		}

		/* Print body */
		tmp_que = new LinkedList(beginning);;
		String tmp = null;
		while (generate_num > 0) {
			tmp = getRandomInVec(words.get(tmp_que));
			System.out.print(tmp + " ");
			generate_num--;

			tmp_que.poll();
			tmp_que.offer(tmp);
		}
		
		System.out.print("\n\n");
	}
	
	public static void main(String[] args) {
		Ngrams ngrams = new Ngrams();
		while (true) { ngrams.generate(); }
	}
}
