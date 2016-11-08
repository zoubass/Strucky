package cz.zoubelu.codelist;

/**
 * Contains system information, such as name and unique systemID.
 * Used as the codelist value.
 */
public class SystemApp {
	private Integer id;
	private String name;

	public SystemApp(String name, Integer id) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}