package entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Created by tandrieux on 26/05/2017.
 */
@Entity
public class Problem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name="XML")
	private String xml;

	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
	private List<Solution> solutions;
	
	public Problem(){}

	public Problem(String xml) {
		solutions = new ArrayList<>();
		this.xml = xml;
	}

	public void solve() {

	}
	
	public List<Solution> getSolutions() {
		return solutions;
	}
	
	
}