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
	
	public Mat4f loadIdentity() {
		m00 = 1.0f; m10 = 0.0f; m20 = 0.0f; m30 = 0.0f;
		m01 = 0.0f; m11 = 1.0f; m21 = 0.0f; m31 = 0.0f;
		m02 = 0.0f; m12 = 0.0f; m22 = 1.0f; m32 = 0.0f;
		m03 = 0.0f; m13 = 0.0f; m23 = 0.0f; m33 = 1.0f;
		return this;
	}
	
	public Mat4f loadPerspective(float fov, float ratio, float near, float far) {
		m11 = (float) (1.0f / (Math.tan(Math.toRadians(fov) / 2.0f)));
		m00 = m11 / ratio;
		m22 = -(far + near) / (far - near);
		m23 = -1.0f;
		m32 = -2.0f * far * near / (far - near);
		m33 = 0.0f;
		return this;
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
	
	public Mat4f translate(float x, float y, float z) {
		m30 = m00 * x + m10 * y + m20 * z + m30;
        m31 = m01 * x + m11 * y + m21 * z + m31;
        m32 = m02 * x + m12 * y + m22 * z + m32;
        m33 = m03 * x + m13 * y + m23 * z + m33;
        return this;
	}
	
	public Mat4f scale(float x, float y, float z) {
		m00 = m00 * x;
        m01 = m01 * x;
        m02 = m02 * x;
        m03 = m03 * x;
        m10 = m10 * y;
        m11 = m11 * y;
        m12 = m12 * y;
        m13 = m13 * y;
        m20 = m20 * z;
        m21 = m21 * z;
        m22 = m22 * z;
        m23 = m23 * z;
        return this;
	}
	
	public Mat4f rotateX(float x) {
		x = (float) Math.toRadians(x);
		float cos = (float) Math.cos(x);
        float sin = (float) Math.sin(x);
        float rm11 = cos;
        float rm12 = sin;
        float rm21 = -sin;
        float rm22 = cos;

        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        float nm13 = m13 * rm11 + m23 * rm12;
        
        m20 = m10 * rm21 + m20 * rm22;
        m21 = m11 * rm21 + m21 * rm22;
        m22 = m12 * rm21 + m22 * rm22;
        m23 = m13 * rm21 + m23 * rm22;
        
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;
        m13 = nm13;
        
        return this;
	}
	
	
	public Mat4f rotateY(float y) {
		y = (float) Math.toRadians(y);
		float cos = (float) Math.cos(y);
        float sin = (float) Math.sin(y);
        float rm00 = cos;
        float rm02 = -sin;
        float rm20 = sin;
        float rm22 = cos;

        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        float nm03 = m03 * rm00 + m23 * rm02;
        
        m20 = m00 * rm20 + m20 * rm22;
        m21 = m01 * rm20 + m21 * rm22;
        m22 = m02 * rm20 + m22 * rm22;
        m23 = m03 * rm20 + m23 * rm22;
        
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = nm03;
        
        return this;
	}
	
	public Mat4f rotateZ(float z) {
		z = (float) Math.toRadians(z);
		float cos = (float) Math.cos(z);
        float sin = (float) Math.sin(z);
        float rm00 = cos;
        float rm01 = sin;
        float rm10 = -sin;
        float rm11 = cos;

        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        float nm03 = m03 * rm00 + m13 * rm01;
        
        m10 = m00 * rm10 + m10 * rm11;
        m11 = m01 * rm10 + m11 * rm11;
        m12 = m02 * rm10 + m12 * rm11;
        m13 = m03 * rm10 + m13 * rm11;
        
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = nm03;
        
        return this;
	}
	
	public String toString() {
		return "m00: " + m00 + " m10: " +  m10 + " m20: " + m20 + " m30: " +  m30 + "\n"
				+ "m01: " + m01 + " m11: " + m11 + " m21: " +  m21 + " m31: " + m31 + "\n"
				+ "m02: " + m02 + " m12: " + m12 + " m22: " + m22 + " m32: " +  m32 + "\n"
				+ "m03: " + m03 + " m13: " + m13 + " m23: " + m23 + " m33: " +  m33;
	}
	
}
