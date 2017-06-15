package entities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Created by tandrieux on 26/05/2017.
 */
@Entity
public class Problem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "PATH")
	private String path;
	
	@Column(name = "XML")
	private String xml;
	
	@Transient
	private File file;

	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
	private List<Solution> solutions;

	public Problem() {
		solutions = new ArrayList<>();
	}

	public void solve() {

	}

	public void setFile(String xml) throws IOException {
		path = "problem_" + id + ".xml";
		this.xml = xml;
		FileOutputStream fstream = new FileOutputStream(path);
		PrintWriter writer = new PrintWriter(fstream, true);
		writer.print(xml);
		writer.close();
	}

	public List<Solution> getSolutions() {
		return solutions;
	}

	public int getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public File getFile() {
		return file;
	}

	public String getXml() {
		return xml;
	}
}