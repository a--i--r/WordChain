import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WordChain {

	/** 定数 **/
	// 日付デフォルトフォーマット
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:S (z)";
	// プログラム説明
	private static final String PROGRAM_DESCRIPTION = "WordChain プログラム";
	// ライン
	private static final String LINE_DIVIDER	= "-----------------------------------------";
	private static final String PROGRAM_DIVIDER	= "============================================";

	private static String inputFile = "";

	/**
	 * メイン関数
	 * @param args
	 */
	public static void main(String[] args) {

		String fileName = "";
		SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date execDate = new Date();

		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if (stackTraceElements != null) {
			for (int i =0; i < stackTraceElements.length; i++) {
				StackTraceElement stackTraceElement = stackTraceElements[i];
				if (i == 1) {
					System.out.println(PROGRAM_DIVIDER);
					System.out.println("ClassName  : " + stackTraceElement.getClassName());
					//System.out.println("FileName   : " + stackTraceElement.getFileName());
					fileName = stackTraceElement.getFileName();
					//System.out.println("MethodName : " + stackTraceElement.getMethodName());
					//System.out.println("LineNumber : " + stackTraceElement.getLineNumber());

					System.out.println("ExecDate : " + sf.format(execDate));
					System.out.println(PROGRAM_DIVIDER);
				}
			}
		}

		System.out.println("このプログラムは、"+ PROGRAM_DESCRIPTION + " です。");
		if (args.length > 0) {
			inputFile = args[0];
			System.out.println("ARG0: " + inputFile);
		}
		else {
			System.out.println("読み込むファイル名を入力して下さい。");
			System.out.println("ex) " + fileName + " <input_filename>");
			System.out.println(LINE_DIVIDER);
			return;
		}
		System.out.println(LINE_DIVIDER);

		List<String> keywords = new CopyOnWriteArrayList<String>();
		try {
			FileInputStream fs = new FileInputStream(inputFile);
			InputStreamReader isr = new InputStreamReader(fs);
			BufferedReader br = new BufferedReader(isr);


			//String lineSeparator = System.getProperty("line.separator");
			String line = "";
			while ( (line = br.readLine()) != null ) {
				keywords.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Input words : " + keywords.toString());

		HeadTails headTails = new HeadTails(keywords);
		Followers followers = new Followers(headTails);

		LongestSequenceFinder finder = new LongestSequenceFinder(headTails, followers);
		List<String> resultList = headTails.buildResultList( finder.find() );

		System.out.println("Result words (" + String.valueOf(resultList.size()) + ") : " + resultList.toString());
		execDate = new Date();
		System.out.println("ExecDate : " + sf.format(execDate));
		System.out.println(PROGRAM_DIVIDER);
	}
}
