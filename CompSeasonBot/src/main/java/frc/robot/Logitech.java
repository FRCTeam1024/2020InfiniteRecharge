/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 * Add your docs here.
 */
public class Logitech extends Joystick{
    	/* Button Mappings */
	private static final int X_BUTTON_ID = 1;
	private static final int A_BUTTON_ID = 2;
	private static final int B_BUTTON_ID = 3;
	private static final int Y_BUTTON_ID = 4;
	private static final int LB_BUTTON_ID = 5;
	private static final int RB_BUTTON_ID = 6;
	private static final int LT_BUTTON_ID = 7;
	private static final int RT_BUTTON_ID = 8;
	private static final int BACK_BUTTON_ID = 9;
	private static final int START_BUTTON_ID = 10;
	private static final int LEFT_CLICK_ID = 11;
	private static final int RIGHT_CLICK_ID = 12;

	/* Axis Mappings */
	private static final int LEFT_STICK_X_AXIS_ID = 0;
	private static final int LEFT_STICK_Y_AXIS_ID = 1;
	private static final int RIGHT_STICK_X_AXIS_ID = 2;
	private static final int RIGHT_STICK_Y_AXIS_ID = 3;

	/* Instance Values */
	private final int port;
	private final Joystick controller;

	
	public final DirectionalPad dPad;
	public final Button x;
	public final Button a;
	public final Button b;
	public final Button y;
	public final Button lb;
	public final Button rb;
	public final Button lt;
	public final Button rt;
	public final Button back;
	public final Button start;
	public final Button rightClick;
	public final Button leftClick;

	/*
	 * @param: port
	 */
	public Logitech(final int port){
		super(port); // Extends Joystick...

		/* Initialize */
		this.port = port;
		this.controller = new Joystick(this.port); // Joystick referenced by
													// everything
		this.dPad = new DirectionalPad(this.controller);
		this.lt = new JoystickButton(this.controller, LT_BUTTON_ID);
		this.rt = new JoystickButton(this.controller, RT_BUTTON_ID);
		this.a = new JoystickButton(this.controller, A_BUTTON_ID);
		this.b = new JoystickButton(this.controller, B_BUTTON_ID);
		this.x = new JoystickButton(this.controller, X_BUTTON_ID);
		this.y = new JoystickButton(this.controller, Y_BUTTON_ID);
		this.lb = new JoystickButton(this.controller, LB_BUTTON_ID);
		this.rb = new JoystickButton(this.controller, RB_BUTTON_ID);
		this.back = new JoystickButton(this.controller, BACK_BUTTON_ID);
		this.start = new JoystickButton(this.controller, START_BUTTON_ID);
		this.rightClick = new JoystickButton(this.controller, RIGHT_CLICK_ID);
		this.leftClick = new JoystickButton(this.controller, LEFT_CLICK_ID);
	}

	/**
	 * This is the relation of direction and number for .getPOV() used in the
	 * DirectionalPad class.
	 */
	public static enum DPAD {
		UP(0), UP_RIGHT(45), RIGHT(90), DOWN_RIGHT(135), DOWN(180), DOWN_LEFT(225), LEFT(270), UP_LEFT(315);

		/* Instance Value */
		private int value;

		/**
		 * Constructor
		 * 
		 * @param value
		 */
		DPAD(final int value) {
			this.value = value;
		}

		/**
		 * Convert integers to DPAD values
		 * 
		 * @param value
		 * @return DPAD with matching angle
		 */
		public static DPAD getEnum(int angle) {
			angle = Math.abs(angle);
			angle %= 360;
			angle = Math.round(angle / 45) * 45; // May have rounding errors.
													// Due to rounding errors.

			DPAD[] all = DPAD.values();

			for (int i = 0; i < all.length; i++) {
				if (all[i].value == angle) {
					return all[i];
				}
			}
			// I don't know what to do here
			// throw new UnsupportedOperationException("Integer supplied (" +
			// angle + ") is not a possible value of this enum.");
			//System.out.println(
					// "[XboxController.DPAD.getEnum()] Angle supplied (" + angle + ") has no related DPad direction");
			return DPAD.UP;
		}
	}

	/**
	 * This is a weird object which is essentially just 8 buttons.
	 */
	public static class DirectionalPad extends Button {

		/* Instance Values */
		private final Joystick parent;

		public final Button up;
		public final Button upRight;
		public final Button right;
		public final Button downRight;
		public final Button down;
		public final Button downLeft;
		public final Button left;
		public final Button upLeft;

		/**
		 * Constructor
		 * 
		 * @param parent
		 */
		DirectionalPad(final Joystick parent) {

			/* Initialize */
			this.parent = parent;
			this.up = new DPadButton(this, DPAD.UP);
			this.upRight = new DPadButton(this, DPAD.UP_RIGHT);
			this.right = new DPadButton(this, DPAD.RIGHT);
			this.downRight = new DPadButton(this, DPAD.DOWN_RIGHT);
			this.down = new DPadButton(this, DPAD.DOWN);
			this.downLeft = new DPadButton(this, DPAD.DOWN_LEFT);
			this.left = new DPadButton(this, DPAD.LEFT);
			this.upLeft = new DPadButton(this, DPAD.UP_LEFT);
		}

		/**
		 * This class is used to represent each of the 8 values a dPad has as a button.
		 */
		public static class DPadButton extends Button {

			/* Instance Values */
			private final DPAD direction;
			private final DirectionalPad parent;

			/**
			 * Constructor
			 * 
			 * @param parent
			 * @param dPad
			 */
			DPadButton(final DirectionalPad parent, final DPAD dPadDirection) {

				/* Initialize */
				this.direction = dPadDirection;
				this.parent = parent;
			}

			/* Extended Methods */
			@Override
			public boolean get() {
				return parent.getAngle() == direction.value;
			}
		}

		private int angle() {
			return parent.getPOV();
		}

		/* Extended Methods */
		@Override
		public boolean get() {
			return angle() != -1;
		}

		/* Get Methods */
		/**
		 * UP 0; UP_RIGHT 45; RIGHT 90; DOWN_RIGHT 135; DOWN 180; DOWN_LEFT 225; LEFT
		 * 270; UP_LEFT 315;
		 * 
		 * @return A number between 0 and 315 indicating direction
		 */
		public int getAngle() {
			return angle();
		}

		/**
		 * Just like getAngle, but returns a direction instead of an angle
		 * 
		 * @return A DPAD direction
		 */
		public DPAD getDirection() {
			return DPAD.getEnum(angle());
		}
	}
	
	/**
	 * @return The port of this Logitech Controller
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return The Joystick of this Logitech Controller
	 */
	public Joystick getJoystick() {
		return controller;
	}
}
