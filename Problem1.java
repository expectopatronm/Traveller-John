import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class CityOrder implements Comparator<String>
{

	@Override
	public int compare(String o1, String o2) {
		String nameParts1[] = o1.split(",");
		String nameParts2[] = o2.split(",");
		
		return nameParts1[2].trim().compareTo(nameParts2[2].trim());
	}
	
}

public class Solution3 {

	public sttic String solution(String S) {
		
		if (S.length() < 1 || S == "") {
			return null;
		}

		HashMap<String, PriorityQueue<String>> sortedCites = new HashMap<>();
		String lines[] = S.split("\\r?\\n");

		// for (String string : lines) {
		// 	System.out.println(string);
		// }
		// System.out.println("=======");
		for (int i = 0; i < lines.length; i++) {
			String nameParts[] = lines[i].split(",");
			if (sortedCites.containsKey(nameParts[1].trim())) {
				sortedCites.get(nameParts[1].trim()).add(lines[i]);
			}
			else {
				PriorityQueue<String> tmp = new PriorityQueue<String>(new CityOrder());
				tmp.add(lines[i]);
				sortedCites.put(nameParts[1].trim(), tmp);
			}
		}
		
		HashMap<String, String> oldTonew = new HashMap<>();
		for (String key : sortedCites.keySet()) {
			PriorityQueue<String> tmp = sortedCites.get(key);
			PriorityQueue<String> tmp1 = new PriorityQueue<>();
			
			int noOfZeros = Integer.toString(tmp.size()).length();
			int i = 1;
			while (!tmp.isEmpty()) {
				String oldFullName = tmp.poll();
				int diffPad = noOfZeros - Integer.toString(i).length();
				String pad = "";
				for (int j=0; j < diffPad; j++) {
					pad+= "0";
				}
				
				String part1 = oldFullName.split(",")[0];
				String ext = part1.substring(part1.lastIndexOf('.'));
				String newName = key + pad + i + ext;
				oldTonew.put(oldFullName, newName);
				tmp1.add(newName);
				i++;
			}
			sortedCites.put(key, tmp1);
			// System.out.println("Buckets:");
			// System.out.println(tmp1);
		}
		// System.out.println("oldToNew:");
		// System.out.println(oldTonew);
		// System.out.println("=======");
		
		String output = "";
		for (int i = 0; i < lines.length - 1; i++) {
			output += oldTonew.get(lines[i]) + "\n";
		}
		output += oldTonew.get(lines[lines.length-1]);
		
		return output;
	}
