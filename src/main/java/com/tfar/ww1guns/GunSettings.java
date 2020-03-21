package com.tfar.ww1guns;

public class GunSettings {
	protected int magazine_size = 1;
	protected double damage = 1;

	public GunSettings(){
	}

	public GunSettings setDamage(double damage) {
		this.damage = damage;
		return this;
	}

	public GunSettings setMagazineSize(int magazine_size) {
		this.magazine_size = magazine_size;
		return this;
	}
}
