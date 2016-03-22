package util.maths;

import java.nio.FloatBuffer;

public class Mat4f {
	public float m00, m10, m20, m30,
				 m01, m11, m21, m31,
				 m02, m12, m22, m32,
				 m03, m13, m23, m33;
	
	public Mat4f() {
		loadIdentity();
	}
	
	public Mat4f store(FloatBuffer buffer) {
		buffer.put(m00);
		buffer.put(m01);
		buffer.put(m02);
		buffer.put(m03);
		
		buffer.put(m10);
		buffer.put(m11);
		buffer.put(m12);
		buffer.put(m13);
		
		buffer.put(m20);
		buffer.put(m21);
		buffer.put(m22);
		buffer.put(m23);
		
		buffer.put(m30);
		buffer.put(m31);
		buffer.put(m32);
		buffer.put(m33);
		
		buffer.flip();
		
		return this;
	}
	
	public Mat4f loadIdentity() {
		m00 = 1.0f; m10 = 0.0f; m20 = 0.0f; m30 = 0.0f;
		m01 = 0.0f; m11 = 1.0f; m21 = 0.0f; m31 = 0.0f;
		m02 = 0.0f; m12 = 0.0f; m22 = 1.0f; m32 = 0.0f;
		m03 = 0.0f; m13 = 0.0f; m23 = 0.0f; m33 = 1.0f;
		return this;
	}
	
	public Mat4f loadPerspective(float fov, float ratio, float near, float far) {
		m11 = (float) (1.0f / (Math.tan(fov / 2.0f)));
		m00 = m11 / ratio;
		m22 = -(far + near) / (far - near);
		m23 = -1.0f;
		m32 = -2.0f * far * near / (far - near);
		m33 = 0.0f;
		return this;
	}
	
	public Mat4f translate(Vec3f vec) {
		m30 = vec.x;
		m31 = vec.y;
		m32 = vec.z;
		return this;
	}
	
	public Mat4f translate(float x, float y, float z) {
		m30 = x;
		m31 = y;
		m32 = z;
		return this;
	}
	
	public Mat4f scale(Vec3f vec) {
		m00 = vec.x;
		m11 = vec.y;
		m22 = vec.z;
		return this;
	}
	
	public Mat4f rotateX(float x) {
		m11 = (float) Math.cos(x);
		m12 = (float) Math.sin(x);
		m21 = (float) -(Math.sin(x));
		m22 = (float) Math.cos(x);
		return this;
	}
	
	public Mat4f rotateY(float y) {
		m00 = (float) Math.cos(y);
		m02 = (float) -(Math.sin(y));
		m20 = (float) Math.sin(y);
		m22 = (float) Math.cos(y);
		return this;
	}

	public Mat4f rotateZ(float z) {
		m00 = (float) Math.cos(z);
		m01 = (float) Math.sin(z);
		m10 = (float) -(Math.sin(z));
		m11 = (float) Math.cos(z);
		return this;
	}
	
}

/*public Mat4f mult(Mat4f other) {
Mat4f product = new Mat4f();
product.m00 = m00 * other.m00 + m10 * other.m01 + m20 * other.m02;
product.m01 = m01 * other.m00 + m11 * other.m01 + m21 * other.m02;
product.m02 = m02 * other.m00 + m12 * other.m01 + m22 * other.m02;
product.m03 = m03 * other.m00 + m13 * other.m01 + m23 * other.m02;
product.m10 = m00 * other.m10 + m10 * other.m11 + m20 * other.m12;
product.m11 = m01 * other.m10 + m11 * other.m11 + m21 * other.m12;
product.m12 = m02 * other.m10 + m12 * other.m11 + m22 * other.m12;
product.m13 = m03 * other.m10 + m13 * other.m11 + m23 * other.m12;
product.m20 = m00 * other.m20 + m10 * other.m21 + m20 * other.m22;
product.m21 = m01 * other.m20 + m11 * other.m21 + m21 * other.m22;
product.m22 = m02 * other.m20 + m12 * other.m21 + m22 * other.m22;
product.m23 = m03 * other.m20 + m13 * other.m21 + m23 * other.m22;
product.m30 = m00 * other.m30 + m10 * other.m31 + m20 * other.m32 + m30;
product.m31 = m01 * other.m30 + m11 * other.m31 + m21 * other.m32 + m31;
product.m32 = m02 * other.m30 + m12 * other.m31 + m22 * other.m32 + m32;
product.m33 = m03 * other.m30 + m13 * other.m31 + m23 * other.m32 + m33;
return product;
}*/