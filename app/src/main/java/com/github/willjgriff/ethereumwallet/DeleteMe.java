package com.github.willjgriff.ethereumwallet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 29/03/2017.
 */

public class DeleteMe<T extends DeleteMe.Parent> {

	public T setSomething(T hola) {
		return hola;
	}

	public <J extends Parent> J setSomthingFunc(J holo) {

	}

	public static class Test {
		public Test() {

			List<? extends Parent> covariantList = new ArrayList<Child>();

			covariantList.add(new Child());
			covariantList.get(0).parentFunc();

			List<? super Parent> contravariantList = new ArrayList<Parent>();

			contravariantList.add(new Child());
			contravariantList.get(0).parentFunc();


			DeleteMe<Parent> deleteMe = new DeleteMe<>();
			List<Child> list = new ArrayList<>();
			deleteMe.setSomething(list);
		}
	}


	public static class Parent {
		public void parentFunc() {

		}
	}

	public static class Child extends Parent {
		public void childFunc() {

		}
	}

	public static class ChilChild extends Child {
		public void childChildFunc() {

		}
	}

	public static class Animal {
		public Child getThing() {
			return null;
		}

		public void setThing(Child parent) {

		}
	}

	public static class Cat extends Animal {
		@Override
		public Child getThing() {
			return null;
		}

		@Override
		public void setThing(Child parent) {
		}
	}
}
