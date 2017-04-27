package util.resourceEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class to act as a resource engine for any project. The main utility is
 * the option to cache all present and future files needed for the program.
 * 
 * @author Gordon Huynh
 *
 */
public class MultiEngine implements ResourceEngine {

	private Map<FileType, Map<String, File>> files;

	/**
	 * Empty ResourceEngine constructor, will not look for any kind of files.
	 */
	public MultiEngine() {
		this(new ArrayList<FileType>());
	}

	/**
	 * ResourceEngine constructor to take in a list of FileExtensions to allow
	 * multiple file type definitions at once. ResourceEngine will search for
	 * all the input file types
	 * 
	 * @param extensionsIn
	 *            A list of FileExtension to define several file types to cache
	 */
	public MultiEngine(List<FileType> extensionsIn) {
		files = new HashMap<FileType, Map<String, File>>();
		for (FileType f : extensionsIn) {
			this.addType(f);
		}
	}

	/**
	 * ResourceEngine constructor to take in only one FileExtension. Will only
	 * cache file types associated with the one file extension
	 * 
	 * @param fe
	 *            A single FileExtension to define what files to cache
	 */
	public MultiEngine(FileType fe) {
		this(new ArrayList<FileType>(Arrays.asList(fe)));
	}

	/**
	 * Construct a copy of a ResourceEngine from a given ResourceEngine. Will
	 * also clone new instances of cached files.
	 * 
	 * @param old
	 *            An instance of ResourceEngine to clone
	 */
	public MultiEngine(MultiEngine old) {
		this(new ArrayList<FileType>(old.getTypes()));
		for (FileType fe : files.keySet()) {
			Map<String, File> temp = old.get(fe);
			Map<String, File> newMap = new HashMap<String, File>();
			for (String s : temp.keySet()) {
				newMap.put(s, new File(temp.get(s).toURI()));
			}
			files.put(fe, newMap);
		}
	}

	public void addType(FileType fe) {
		if (!files.containsKey(fe)) {
			files.put(fe, new HashMap<String, File>());
		}
	}

	public void removeType(FileType fe) {
		if (files.containsKey(fe)) {
			files.remove(fe);
		}
	}

	public List<FileType> getTypes() {
		return new ArrayList<FileType>(files.keySet());
	}

	public void pullFiles(String fp) {
		this.extractFiles(fp, false);
	}

	public void pullFilesFromChildren(String fp) {
		this.extractFiles(fp, true);
	}

	public Map<String, File> get(FileType fe) {
		return new HashMap<String,File>(files.get(fe));
	}

	public File get(FileType fe, String s) {
		if (!files.containsKey(fe)) {
			files.put(fe, new HashMap<String, File>());
		}
		return files.get(fe).get(s);
	}

	/**
	 * Extracts files from fp and places in them in correcct maps associated
	 * with file type
	 * 
	 * @param fp
	 *            Filepath to a directory
	 * @param children
	 *            boolean to determine whether to also extract files from child
	 *            directories
	 */
	private void extractFiles(String fp, boolean children) {
		File dir = new File(fp);
		if (dir.isDirectory()) {
			ArrayList<File> knownFiles = this.getFiles(dir, children);
			for (FileType f : files.keySet()) {
				this.filterFiles(f, knownFiles);
			}
		}
	}

	/**
	 * Retrieve all non-directory files located in specified file location. Will
	 * search through children of specified file if children is true
	 * 
	 * @param fileIn
	 *            The file of a directory
	 * @param children
	 *            boolean to
	 * @return
	 */
	private ArrayList<File> getFiles(File fileIn, boolean children) {
		ArrayList<File> ret = new ArrayList<File>();
		if (fileIn.isDirectory()) {
			for (File f : fileIn.listFiles()) {
				if (!f.isDirectory()) {
					ret.add(f);
				} else if (children) {
					ret.addAll(this.getFiles(f, children));
				}
			}
		}
		return ret;
	}

	/**
	 * Filter a list of files for specified file type. Will add these to private
	 * map and remove the files from the input list. The input list will be
	 * mutated
	 * 
	 * @param fe
	 *            The specified file type to extract from the list of files
	 * @param filesIn
	 *            The list of Files to filter from. WILL BE MUTATED
	 */
	private void filterFiles(FileType fe, List<File> filesIn) {
		ArrayList<File> toRemove = new ArrayList<File>();
		for (File f : filesIn) {
			for (String s : fe) {
				if (f.getName().toLowerCase().contains(s)) {
					files.get(fe).put(f.getName(), f);
					toRemove.add(f);
					break;
				}
			}
		}
		filesIn.removeAll(toRemove);
	}
}
