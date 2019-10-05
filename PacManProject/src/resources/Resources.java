package resources;

public class Resources {
	
	/**
	 * Return the absolute path of a resource file
	 * @param folder
	 * @param filename
	 * @return
	 */
	private String getPath(String folder,String filename) {
		return getClass().getResource(folder+filename).getPath();
	}
	
	/**
	 * Return the absolute path of a file in mazes folder
	 * @param mazeFilename: name of the maze file
	 * @return: absolute path
	 */
	public String getMazePath(String mazeFilename) {
		return getPath("./mazes/",mazeFilename);
	}
	
	/**
	 * Return the absolute path of a file in images folder
	 * @param imageFilename: name of the image file
	 * @return: absolute path
	 */
	public String getImagePath(String imageFilename) {
		return getPath("./images/",imageFilename);
	}
	
	/**
	 * Return the absolute path of a file in sounds folder
	 * @param soundFilename: name of the sound file
	 * @return: absolute path
	 */
	public String getSoundPath(String soundFilename) {
		return getPath("./sounds/",soundFilename);
	}	
	
	public static void main(String[] args) {
		Resources rsc = new Resources();
		System.out.println(rsc.getMazePath("maze.txt"));
		System.out.println(rsc.getSoundPath("beginning.wav"));
		System.out.println(rsc.getImagePath("pacmanTilesSheet.png"));
	}
}
