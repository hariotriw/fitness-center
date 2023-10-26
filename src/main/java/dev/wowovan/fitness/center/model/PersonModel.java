package dev.wowovan.fitness.center.model;

import io.vertx.core.json.JsonObject;

public class PersonModel {

	public String name;
	public Integer age;
	public String address;

    public PersonModel(){

	}

    public PersonModel(String name, Integer age, String address){
		this.name = name;
		this.age = age;
		this.address = address;
	}

    public PersonModel(JsonObject obj){
		this.name = obj.getString("name");
		this.age = obj.getInteger("age");
		this.address = obj.getString("address");
	}

}
