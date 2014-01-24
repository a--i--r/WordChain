import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class HeadTails {

	public final Map<String, Integer> headTails = new ConcurrentHashMap<String, Integer>();
	public final Map<String, List<String>> headTailsOrigin = new ConcurrentHashMap<String, List<String>>();

	public HeadTails(List<String> keywords) {
		this.initialize(keywords);
	}

	public void initialize(List<String> keywords) {

		for (String keyword: keywords) {
			String headTail = new String(keyword.substring(0, 1) + keyword.substring(keyword.length() - 1));
			Integer times = headTails.get(headTail);
			if (times == null) {
				times = 0;
			}
			headTails.put(headTail, times += 1);

			if (headTailsOrigin.containsKey(headTail)) {
				headTailsOrigin.get(headTail).add(keyword);
			}
			else {
				List<String> addList = new CopyOnWriteArrayList<String>();
				addList.add(keyword);
				headTailsOrigin.put(headTail, addList);
			}
		}
	}

	public Integer countTimes(String headTail) {
		if (headTails.containsKey(headTail)) {
			return headTails.get(headTail);
		}
		return (Integer) 0;
	}

	public List<String> buildResultList(List<String> headTailList) {
		List<String> resultList = new CopyOnWriteArrayList<String>();
		Map<String, Integer> headTailsCount = new ConcurrentHashMap<String, Integer>();

		for (String key: headTailList) {
			Integer index = 0;
			if (headTailsCount.containsKey(key)) {
				index = headTailsCount.get(key);
			}
			if (headTailsOrigin.containsKey(key)) {
				resultList.add( headTailsOrigin.get(key).get(index) );
				headTailsCount.put(key, index+1);
			}
		}
		return resultList;
	}
}