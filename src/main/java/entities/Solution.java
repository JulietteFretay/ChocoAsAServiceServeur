package entities;

import javax.persistence.*;

/**
 * Created by tandrieux on 26/05/2017.
 */
@Entity
public class Solution {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	public Solution(){
		
	}
	
	public Solution(int id,Problem problem) {
		this.id = id;
	}
	
	@ManyToOne
	private Problem problem;

	
	public Problem getProblem() {
		return problem;
	}
	
	
}
