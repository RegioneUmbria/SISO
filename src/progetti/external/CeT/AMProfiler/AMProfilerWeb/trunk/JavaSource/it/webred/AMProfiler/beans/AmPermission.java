package it.webred.AMProfiler.beans;

// Generated 7-lug-2006 12.31.07 by Hibernate Tools 3.1.0.beta5

import java.util.HashSet;
import java.util.Set;

/**
 * AmPermission generated by hbm2java
 */
public class AmPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String permission;
	private AmItem amItem;
	private Set<AmRolePermission> amRolePermissions = new HashSet<AmRolePermission>(0);

	// Constructors

	/** default constructor */
	public AmPermission() {
	}

	/** minimal constructor */
	public AmPermission(String permission) {
		this.permission = permission;
	}

	/** full constructor */
	public AmPermission(String permission, AmItem amItem, Set<AmRolePermission> amRolePermissions) {
		this.permission = permission;
		this.amItem = amItem;
		this.amRolePermissions = amRolePermissions;
	}

	// Property accessors
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public AmItem getAmItem() {
		return amItem;
	}

	public void setAmItem(AmItem amItem) {
		this.amItem = amItem;
	}

	public Set<AmRolePermission> getAmRolePermissions() {
		return amRolePermissions;
	}

	public void setAmRolePermissions(Set<AmRolePermission> amRolePermissions) {
		this.amRolePermissions = amRolePermissions;
	}

}