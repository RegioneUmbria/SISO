package it.webred.AMProfiler.beans;

// Generated 7-lug-2006 12.31.07 by Hibernate Tools 3.1.0.beta5

/**
 * AmUserIr generated by hbm2java
 */
public class AmUserIr implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private AmItemRole amItemRole;
	private AmUser amUser;

	// Constructors

	/** default constructor */
	public AmUserIr() {
	}

	/** minimal constructor */
	public AmUserIr(long id) {
		this.id = id;
	}

	/** full constructor */
	public AmUserIr(long id, AmItemRole amItemRole, AmUser amUser) {
		this.id = id;
		this.amItemRole = amItemRole;
		this.amUser = amUser;
	}

	// Property accessors
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AmItemRole getAmItemRole() {
		return amItemRole;
	}

	public void setAmItemRole(AmItemRole amItemRole) {
		this.amItemRole = amItemRole;
	}

	public AmUser getAmUser() {
		return amUser;
	}

	public void setAmUser(AmUser amUser) {
		this.amUser = amUser;
	}

}
