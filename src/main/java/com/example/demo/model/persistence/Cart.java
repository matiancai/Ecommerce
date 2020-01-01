package com.example.demo.model.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	@Column
	private Long id;
	
	@ManyToMany
	@JsonProperty
	@Column
    private List<Item> items = new ArrayList<Item>();
	
	@OneToOne(mappedBy = "cart")
	@JsonProperty
    private AppUser appUser;
	
	@Column
	@JsonProperty
	private BigDecimal total = new BigDecimal(0);
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		if(item!=null){
			items.add(item);
			total = total.add(item.getPrice());
		}
	}
	
	public void removeItem(Item item) {
		if(item != null && items.contains(item)){
			items.remove(item);
			total = total.subtract(item.getPrice());
		}
	}

	@Override
	public String toString() {
		return "Cart{" +
				"id=" + id +
				", items=" + items +
				", total=" + total +
				'}';
	}
}
