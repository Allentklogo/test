package fenye;

import net.sf.json.JSONArray;

public class Versions {
	private Integer ye_now;//��ǰҳ��
	private Integer ye_conut;//��ҳ��
	private Integer amount;//�ܼ�¼��
	private Integer[] y;//��ǰҳ�����תҳ������
	private JSONArray json;//��ǰҳ���¼
	private String versions_id;//��ѯ����1���汾���
	private String name;//��ѯ����2���汾����

	public Versions(){
		this.setYe_now(0);
		this.setYe_conut(0);
		this.setAmount(0);
		this.setY(null);
		this.setJson(null);
		versions_id = new String();
		name = new String();
	}
	
	public Versions(Integer ye_now,Integer ye_count,Integer amount,
			Integer[] y,JSONArray json,String versions_id,String name){
		this.setAmount(amount);
		this.setJson(json);
		this.setY(y);
		this.setYe_conut(ye_count);
		this.setYe_now(ye_now);
		this.setVersions_id(versions_id);
		this.setName(name);
	}
	
	public Integer getYe_now() {
		return ye_now;
	}
	public void setYe_now(Integer ye_now) {
		this.ye_now = ye_now;
	}
	public Integer getYe_conut() {
		return ye_conut;
	}
	public void setYe_conut(Integer ye_conut) {
		this.ye_conut = ye_conut;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer[] getY() {
		return y;
	}
	public void setY(Integer[] y) {
		this.y = y;
	}
	public JSONArray getJson() {
		return json;
	}
	public void setJson(JSONArray json) {
		this.json = json;
	}
	public String getVersions_id() {
		return versions_id;
	}
	public void setVersions_id(String versions_id) {
		this.versions_id = versions_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
