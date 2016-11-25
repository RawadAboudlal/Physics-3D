package com.rawad.phys.math;

public class Quaternionf implements Cloneable {
	
	/** For converting a 3D vector to a quaternion. */
	private static final int RIGHT_ANGLE = 90;
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	/**
	 * 
	 * @param theta Angle of rotation in degrees. Counter-clockwise looking in the direction of the vector.
	 * @param x
	 * @param y
	 * @param z
	 */
	public Quaternionf(float theta, float x, float y, float z) {
		super();
		
		double thetaInRads = Math.toRadians(theta);
		
		float c = (float) Math.cos(thetaInRads / 2d);
		float s = (float) Math.sin(thetaInRads / 2d);
		
		this.w = c;
		this.x = x * s;
		this.y = y * s;
		this.z = z * s;
		
	}
	
	public Quaternionf(Vector3f vec) {
		this(RIGHT_ANGLE, vec.x, vec.y, vec.z);
	}
	
	public Quaternionf() {
		this(0, 0, 1f, 0);// Important default value; which is 1f is irrelevant.
	}
	
	/**
	 * Rotates the given {@code vec} by this {@code Quaternionf} using the formula: p' = q * p * q^-1
	 * 
	 * @param vec
	 * @return
	 */
	public Vector3f rotate(Vector3f vec) {
		
		Quaternionf p = new Quaternionf(vec);
		Quaternionf qConjugate = this.conjugate();
		
		return this.multiply(p).multiply(qConjugate).toVector3f();// p' = q * p * q^-1 (^-1 = conjugate).
		// q = quaternion, p = original vector, p' = rotated vector.
		
	}
	
	public Quaternionf multiply(Quaternionf p) {
		
		Quaternionf result = new Quaternionf();
		
		result.w = p.w * this.w - p.x * this.x - p.y * this.y - p.z * this.z;
		result.x = p.w * this.x + p.x * this.w - p.y * this.z + p.z * this.y;
		result.y = p.w * this.y + p.x * this.z + p.y * this.w - p.z * this.x;
		result.z = p.w * this.z - p.x * this.y + p.y * this.x + p.z * this.w;
		
		return result;
		
	}
	
	public Quaternionf conjugate() {
		
		Quaternionf q = new Quaternionf();
		
		q.w = this.w;
		q.x = -this.x;
		q.y = -this.y;
		q.z = -this.z;
		
		return q;
		
	}
	
	public Vector4f toRotationAxisAngle() {
		
		double theta = this.getRotationAngle();
		
		float s = (float) Math.sin(theta / 2d);
		
		if(s == 0f) s = 1f;// Prevents divide by zero; x,y,z are all zero in this case anyways.
		
		return new Vector4f(this.x / s, this.y / s, this.z / s, (float) Math.toDegrees(theta));
		
	}
	
	public Vector3f toVector3f() {
		
		float s = (float) Math.sin(this.getRotationAngle() / 2d);
		// acos(w) = theta / 2 since w = cos(theta / 2), avoid inaccuracy this way.
		
		return new Vector3f(this.x / s, this.y / s, this.z / s);
		
	}
	
	public Quaternionf normalize() {
		
		float length = length();
		
		return new Quaternionf(this.w / length, this.x / length, this.y / length, this.z / length);
		
	}
	
	private double getRotationAngle() {
		
		float w = this.w;
		
		if(Math.abs(this.w) > 1) w /= length();// Normalizes w. Keeps w in doman of acos (range of cos). Occurs due 
		// to precision error.
		
		return 2d * Math.acos(w);
		
	}
	
	public float lengthSquared() {
		return w * w + x * x + y * y + z * z;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	@Override
	public Quaternionf clone() {
		return new Quaternionf(w, x, y, z);
	}
	
	@Override
	public String toString() {
		return "[" + w + ", " + x + ", " + y + ", " + z + "]";
	}
	
}
