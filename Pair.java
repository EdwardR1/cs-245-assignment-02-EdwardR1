import java.util.*;
public class Pair {
	private String key;
	private Set<String> value;

	public Pair(String key, Set<String> val){
		this.key = key;
		this.value = val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Set<String> getValue() {
		return value;
	}

	public void setValue(Set<String> value) {
		this.value = value;
	}
	


}