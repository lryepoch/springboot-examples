package com.springboot.jpa.pojo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//主键生成策略
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
}
