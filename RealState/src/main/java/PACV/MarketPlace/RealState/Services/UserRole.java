package PACV.MarketPlace.RealState.Services;

import java.util.ArrayList;
import java.util.List;

public class UserRole {

    private String name;
	private String value;
	private List<UserRole> subroles;
	
	protected UserRole(String name, String value) {
		super();
		this.name = name;
		this.value = value;
		this.subroles = new ArrayList<>();
	}
	public UserRole() {
		generateRoleTree();
	}

	public List<String> getAllValues() {
		return getAllValues(this);
	}
	protected List<String> getAllValues(UserRole root) {
		List<String> values = new ArrayList<>();
		if (root == null) {
			return values;
		}
		values.add(root.getValue());
		for (UserRole subrole : root.subroles) {
			values.addAll(getAllValues(subrole));
		}
		return values;
	}
	public List<String> getAllValuesByName(String name){
		return getAllValuesByName(name, this);
	}
	protected List<String> getAllValuesByName(String name, UserRole root){
		if (root.name.equals(name)) {
			return getAllValues(root);
		}
		for (UserRole subrole : root.subroles) {
			List<String> result = getAllValuesByName(name, subrole);
			if (result == null) {
				continue;
			}
			return result;
		}
		return null;
	}
	public List<String> getAllValuesByName(List<String> userRoleNames){
		List<String> userRoleValues = new ArrayList<>();
		for (String userRoleName : userRoleNames) {
			if (getAllValuesByName(userRoleName) == null) {
				continue;
			}
			userRoleValues.addAll(getAllValuesByName(userRoleName));
		}
		return userRoleValues;
	}
	
	protected void generateRoleTree() {
		UserRole administrator = new UserRole("Administrator", "Administrator");
		UserRole edp = new UserRole("EDP", "EDP");
		UserRole es = new UserRole("ES", "ES");
		UserRole sp = new UserRole("SP", "SP");
		UserRole iberdrola = new UserRole("Iberdrola", "Iberdrola");
		UserRole brasilia = new UserRole("Brasilia", "Brasilia");
		UserRole coelba = new UserRole("Coelba", "Coelba");
		UserRole cosern = new UserRole("Cosern", "Cosern");
		UserRole elektro = new UserRole("Elektro", "Elektro");
		UserRole ndb = new UserRole("NDB", "NDB");
		UserRole pernambuco = new UserRole("Pernambuco", "Pernambuco");
		UserRole v2com = new UserRole("V2COM", "V2COM");
		
		administrator.getSubroles().add(edp);
		administrator.getSubroles().add(iberdrola);
		administrator.getSubroles().add(v2com);
		
		edp.getSubroles().add(es);
		edp.getSubroles().add(sp);
		
		iberdrola.getSubroles().add(brasilia);
		iberdrola.getSubroles().add(coelba);
		iberdrola.getSubroles().add(cosern);
		iberdrola.getSubroles().add(elektro);
		iberdrola.getSubroles().add(ndb);
		iberdrola.getSubroles().add(pernambuco);
		
		this.name = administrator.name;
		this.value = administrator.value;
		this.subroles = administrator.subroles;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<UserRole> getSubroles() {
		return subroles;
	}
	public void setSubroles(List<UserRole> subroles) {
		this.subroles = subroles;
	}

}
