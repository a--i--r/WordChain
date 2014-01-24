import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Followers {

	public final Map<String, List<String>>	followersTable = new ConcurrentHashMap<String, List<String>>();

	public Followers(HeadTails input) {
		this.initalize(input);
	}

	public void initalize(HeadTails input) {

		for (Map.Entry<String, Integer> i: input.headTails.entrySet()) {
			String key = i.getKey();
			String tailCh = new String(key.substring(key.length() - 1));
			List<String> followers = new CopyOnWriteArrayList<String>();

			for (Map.Entry<String, Integer> j: input.headTails.entrySet()) {
				String key2 = j.getKey();
				if (key.equals(key2) && j.getValue() < 2) {
					continue;
				}
				String headCh = new String(key2.substring(0, 1));
				if (headCh.equals(tailCh)) {
					followers.add(key2);
				}
			}
			followersTable.put(key, followers);
		}
	}

	public List<String> getFollowers(String key) {
		if (followersTable.containsKey(key)) {
			return followersTable.get(key);
		}
		return new CopyOnWriteArrayList<String>();
	}
}