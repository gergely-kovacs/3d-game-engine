package world;

import org.lwjgl.glfw.GLFW;

import objects.lights.DiffuseLight;
import util.Interpolator;

public class TimeManager {
	private final float DAWN_LENGTH, DUSK_LENGTH, MORNING_LENGTH, AFTERNOON_LENGTH,
	dayLength;
	private double dayTracker;
	private boolean first;
	private Interpolator posMorningToNoon, posNoonToAfternoon,
	colNightToDawn, colDawnToNoon, colNoonToDusk, colDuskToNight;
	
	public TimeManager(float dayLength) {
		this.dayLength = dayLength;
		MORNING_LENGTH = dayLength * 10 / 24;
		DAWN_LENGTH = dayLength * 4.0f / 24;
		AFTERNOON_LENGTH = dayLength * 10 / 24;
		DUSK_LENGTH = dayLength * 3 / 24;
		
		first = true;
		
		posMorningToNoon = new Interpolator();
		posNoonToAfternoon = new Interpolator();
		colNightToDawn = new Interpolator();
		colDawnToNoon = new Interpolator();
		colNoonToDusk = new Interpolator();
		colDuskToNight = new Interpolator();
	}
	
	public void passTime() {
		if (first == true) {
			dayTracker = GLFW.glfwGetTime();
			first = false;
		}
		
		if (isMorningTime()) {
			World.sun.setPosition(
				posMorningToNoon.lerp(DiffuseLight.SUN_INITIAL_POSITION, DiffuseLight.SUN_NOON_POSITION, MORNING_LENGTH));
			if (isDawnTime()) {
				World.sun.setColour(
					colNightToDawn.lerp(DiffuseLight.SUN_INITIAL_COLOR, DiffuseLight.SUN_DAWN_COLOR, DAWN_LENGTH)); }
			else {
				World.sun.setColour(
					colDawnToNoon.lerp(DiffuseLight.SUN_DAWN_COLOR, DiffuseLight.SUN_NOON_COLOR, MORNING_LENGTH - DAWN_LENGTH)); }
		} else if (isAfternoonTime()) {
			World.sun.setPosition(
				posNoonToAfternoon.lerp(DiffuseLight.SUN_NOON_POSITION, DiffuseLight.SUN_FINAL_POSITION, AFTERNOON_LENGTH));
			if (isDuskTime()) {
				World.sun.setColour(
					colDuskToNight.lerp(DiffuseLight.SUN_DUSK_COLOR, DiffuseLight.SUN_FINAL_COLOR, DUSK_LENGTH)); }
			else {
				World.sun.setColour(
					colNoonToDusk.lerp(DiffuseLight.SUN_NOON_COLOR, DiffuseLight.SUN_DUSK_COLOR, AFTERNOON_LENGTH - DUSK_LENGTH)); }
		} else if (isOver()) {
			first = true;
			resetLerps();
		}
	}
	
	private void resetLerps() {
		posMorningToNoon.reset();
		posNoonToAfternoon.reset();
		colNightToDawn.reset();
		colDawnToNoon.reset();
		colNoonToDusk.reset();
		colDuskToNight.reset();
	}

	public boolean isMorningTime() {
		if (getDayTime() <= MORNING_LENGTH &&
			!posMorningToNoon.isFinished() &&
			!colDawnToNoon.isFinished()) return true;
		return false;
	}
	
	public boolean isDawnTime() {
		if (getDayTime() <= DAWN_LENGTH &&
			!colNightToDawn.isFinished()) return true;
		return false;
	}
	
	public boolean isAfternoonTime() {
		if (getDayTime() > MORNING_LENGTH &&
			getDayTime() <= MORNING_LENGTH + AFTERNOON_LENGTH &&
			!isMorningTime() &&
			!posNoonToAfternoon.isFinished() &&
			!colNoonToDusk.isFinished()) return true;
		return false;
	}
	
	public boolean isDuskTime() {
		if (getDayTime() > MORNING_LENGTH + AFTERNOON_LENGTH - DUSK_LENGTH &&
			getDayTime() <= MORNING_LENGTH + AFTERNOON_LENGTH &&
			isAfternoonTime() &&
			!colDuskToNight.isFinished()) return true;
		return false;
	}
	
	public boolean isNightTime() {
		if (getDayTime() > MORNING_LENGTH + AFTERNOON_LENGTH &&
			getDayTime() <= dayLength &&
			!isAfternoonTime()) return true;
		return false;
	}
	
	private boolean isOver() {
		if (getDayTime() > dayLength) return true;
		return false;
	}
	
	public double getDayTime() {
		return GLFW.glfwGetTime() - dayTracker;
	}
}
