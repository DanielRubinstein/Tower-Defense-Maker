package util.resourceEngine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public enum FileType implements Iterable<String>{
	IMAGES (Arrays.asList(".jpg", ".jpeg", ".gif", ".png")),
	DATA (Arrays.asList(".xml", ".json")),
	SOUND(Arrays.asList(".wav", ".mp3"));
	
	private List<String> extensions;
	
	FileType(List<String> extensionsIn){
		this.extensions = extensionsIn;
	}
	
	public List<String> getExtensions(){
		return extensions;
	}

	@Override
	public Iterator<String> iterator() {
		return extensions.iterator();
	}
}
