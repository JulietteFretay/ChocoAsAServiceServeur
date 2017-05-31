package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by tandrieux on 26/05/2017.
 */
@Entity
public class Solution {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;

	public Solution(int id,Problem problem) {
		this.id = id;
	}
	
	@ManyToOne
	private Problem problem;

	
	public Problem getProblem() {
		return problem;
	}
	
	
}
