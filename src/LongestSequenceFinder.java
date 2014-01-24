import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LongestSequenceFinder {

	public HeadTails headTails;
	public Followers followers;

	public LongestSequenceFinder(HeadTails ht, Followers fl) {
		this.initialize(ht, fl);
	}

	public void initialize(HeadTails ht, Followers fl) {
		headTails = ht;
		followers = fl;
	}

	public List<String> find() {
		List<String> longest = new CopyOnWriteArrayList<String>();

		for (Map.Entry<String, Integer> i: headTails.headTails.entrySet()) {
			String key = i.getKey();
			Map<String, Integer>usedHeadTails = new ConcurrentHashMap<String, Integer>();
			usedHeadTails.put(key, 1);

			List<String> seq = new CopyOnWriteArrayList<String>();
			seq.add(key);

			List<String> ret = this.findRecursive(usedHeadTails, seq);
			if (ret.size() > longest.size()) {
				longest = ret;
			}
		}
		return longest;
	}

	public List<String> findRecursive(Map<String, Integer>usedHeadTails, List<String> seq) {

		String lastHeadTail = "";
		if (seq.size() > 0) {
			lastHeadTail = seq.get(seq.size() - 1);
		}

		List<String> candidates = followers.getFollowers(lastHeadTail);
		boolean bFound = false;

		List<String> longest = new CopyOnWriteArrayList<String>();
		List<String> skipList = new CopyOnWriteArrayList<String>();

		for (String i: candidates) {

			Integer usedHeadTailsCount;
			if (usedHeadTails.containsKey(i)) {
				usedHeadTailsCount = usedHeadTails.get(i);
				if (usedHeadTailsCount >= headTails.countTimes(i)) { continue; }
			}
			else {
				usedHeadTailsCount = 0;
			}

			if (skipList.contains(i)) { continue; }

			bFound = true;

			usedHeadTails.put(i, usedHeadTailsCount + 1);
			seq.add(i);

			List<String> foundSeq = findRecursive(usedHeadTails, seq);

			if (usedHeadTails.containsKey(i)) {
				usedHeadTailsCount = usedHeadTails.get(i);
			}
			else {
				usedHeadTailsCount = 0;
			}
			usedHeadTails.put(i, usedHeadTailsCount - 1);
			if (seq.size() > 0) {
				seq.remove(seq.size() - 1);
			}

			if (foundSeq.size() > longest.size()) {
				longest = foundSeq;

				skipList = new CopyOnWriteArrayList<String>(longest.subList(seq.size(), longest.size()));
			}
		}

		if (bFound) {
			return longest;
		}
		return new CopyOnWriteArrayList<String>(seq);
	}
}