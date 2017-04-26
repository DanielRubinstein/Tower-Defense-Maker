package util.resourceEngine;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Utility program to handle file caching for specific file types
 * 
 * @author Gordon
 *
 */
public interface ResourceEngine {
	/**
	 * Add a FileType for the engine to look for and track
	 * 
	 * @param ft
	 *            FileType for ResourceEngine to search for
	 */
	public void addType(FileType ft);

	/**
	 * Remove a FileType from the engine's search criteria. Also removes cached
	 * files from ResourceEngine related to FileType
	 * 
	 * @param ft
	 *            FileType for ResourceEngine to remove
	 */
	public void removeType(FileType ft);

	/**
	 * Get a list of all the FileType this ResourceEngine is searching for
	 * 
	 * @return A list of FileTypes this ResourceEngine is associated with
	 */
	public List<FileType> getTypes();

	/**
	 * Have the ResourceEngine pull all files defined by its FileType from the
	 * directory as defined by fp.
	 * 
	 * @param fp
	 *            Directory file path ex: "src/images"
	 */
	public void pullFiles(String fp);

	/**
	 * Have the ResourceEngine pull all files defined by its FileType from the
	 * directory as defined by fp. Will also pull files for child directories
	 * and so forth to collect all the files descendant of fp.
	 * 
	 * @param fp
	 *            Directory file path ex: "src/images"
	 */
	public void pullFilesFromChildren(String fp);

	/**
	 * Retrieve a map of string to files given the ft
	 * 
	 * @param ft
	 *            FileType for ResourceEngine to return known files cached
	 * @return Return a copy of a Map<String, File> for a given ft, will return an empty map
	 *         if ft did not exist in ResourceEngine or there are no files
	 *         associated with that FileType.
	 */
	public Map<String, File> get(FileType ft);

	/**
	 * Retrieve a specific file with specific FileType and String name. For
	 * example: get(FileType.IMAGE, "ball") will return a File that consists of
	 * a ball.jpeg
	 * 
	 * @param ft
	 *            FileType of the desired file
	 * @param s
	 *            Name of the file, exactly the same as the file's name
	 * @return File of the desired File with given name and type
	 */
	public File get(FileType ft, String s);

}
